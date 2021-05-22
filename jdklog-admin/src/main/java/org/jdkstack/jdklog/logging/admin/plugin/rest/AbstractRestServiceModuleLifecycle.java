package org.jdkstack.jdklog.logging.admin.plugin.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdkstack.jdklog.logging.admin.exception.ServerRuntimeException;
import org.jdkstack.jdklog.logging.admin.http.annotation.PathVariable;
import org.jdkstack.jdklog.logging.admin.http.annotation.RequestBody;
import org.jdkstack.jdklog.logging.admin.http.annotation.RequestMapping;
import org.jdkstack.jdklog.logging.admin.http.annotation.RequestParam;
import org.jdkstack.jdklog.logging.admin.http.annotation.RestController;
import org.jdkstack.jdklog.logging.admin.http.metadata.ControllerMetaData;
import org.jdkstack.jdklog.logging.admin.http.metadata.MethodMetaData;
import org.jdkstack.jdklog.logging.admin.http.metadata.ParameterMetaData;
import org.jdkstack.jdklog.logging.admin.lifecycle.module.AbstractModuleLifecycle;
import org.jdkstack.jdklog.logging.admin.plugin.ModuleService;

/**
 * 抽象的rest服务模块的生命周期 模块服务生命周期
 *
 * @author admin
 */
public abstract class AbstractRestServiceModuleLifecycle extends AbstractModuleLifecycle
    implements ModuleService {
  /** 存储RestController简单名和全限定名的映射,把类主动加载到就jvm中 */
  protected Map<String, List<ParameterMetaData>> fullyPathsParams = new HashMap<>();
  /** 存储RestController简单名和全限定名的映射,把类主动加载到就jvm中 */
  protected Map<String, MethodMetaData> fullyPaths = new HashMap<>();
  /** 存储RestController简单名和全限定名的映射,把类主动加载到就jvm中 */
  protected Map<String, ControllerMetaData> controllerMetaDataMap = new HashMap<>();
  /** Http URL路径的分割符 */
  protected String pathSeparator = "/";

  /**
   * 参数元数据(Executable 代替 Method) 保存参数的元数据信息
   *
   * @param method 方法
   * @param fullPath 完整路径
   */
  protected void parameterMetaData(final Executable method, final String fullPath) {
    final Parameter[] parameters = method.getParameters();
    final List<ParameterMetaData> params = new ArrayList<>();
    for (final Parameter parameter : parameters) {
      final ParameterMetaData pmd = new ParameterMetaData();
      // 得到参数的类型
      final Class<?> type = parameter.getType();
      // 得到参数的名字(反射只能获取arg0,arg1等)
      final String name = parameter.getName();
      // 设置参数的类型
      pmd.setParameterType(type);
      final Annotation[] annotations = parameter.getAnnotations();
      if (annotations.length == 0) {
        pmd.setAnnotation(false);
        // String simpleName = type.getSimpleName();
        if ("arg0".equals(name)) {
          pmd.setParameterName("request");
        } else if ("arg1".equals(name)) {
          pmd.setParameterName("response");
        } else {
         //
        }
      }
      if (annotations.length != 0) {
        this.customMethod(parameter, pmd);
      }
      // 添加参数对象
      params.add(pmd);
    }
    // 全路径+参数
    this.fullyPathsParams.put(fullPath, params);
  }

  private void customMethod(final Parameter parameter, final ParameterMetaData pmd) {
    // 如果参数注解是RequestParam
    final RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
    if (requestParam != null) {
      pmd.setParameterName(requestParam.value());
    }
    // 如果参数注解是RequestBody
    final RequestBody requestBody = parameter.getAnnotation(RequestBody.class);
    if (requestBody != null) {
      pmd.setParameterName(requestBody.value());
    }
    // 如果参数注解是PathVariable
    final PathVariable pathVariable = parameter.getAnnotation(PathVariable.class);
    if (pathVariable != null) {
      pmd.setParameterName(pathVariable.value());
    }
  }

  /**
   * 元数据方法 保存方法的元数据信息
   *
   * @param clazz clazz
   * @param basePath 基本路径
   */
  protected void methodMetaData(final Class<?> clazz, final String basePath) {
    // 循环所有方法
    final Method[] methods = clazz.getDeclaredMethods();
    for (final Method method : methods) {
      // Rest Controller 类每一个方法上,有且只能有一个注解
      final Annotation[] annotations = method.getAnnotations();
      if (annotations.length != 1) {

        continue;
      }
      // 获取这个唯一注解的类型,分别进行判断,是不是HttpAction动作的注解类型
      final Class<? extends Annotation> annotationClass = annotations[0].annotationType();
      final String methodPath = this.getMethodPath(method, annotationClass);
      // 如果不符合path或者数量不符合
      if (!methodPath.startsWith(this.pathSeparator) || methodPath.length() == 1) {
        throw new ServerRuntimeException("xx");
      }
      // http rest 方法的请求全路径
      final String fullPath = basePath + methodPath;
      this.fullyPaths.put(fullPath, new MethodMetaData(method, fullPath, basePath));
    }
  }

  /**
   * 控制器元数据 反射获取Class中信息 , String configBasePath
   *
   * @param clazz clazz
   */
  protected void controllerMetaData(final Class<?> clazz) {
    try {
      // 要想暴露Http Action接口,必须有RestController注解
      final RestController restController = clazz.getAnnotation(RestController.class);
      if (restController == null) {

        return;
      }
      final RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
      // Http Action接口URL的跟路径
      String basePath = requestMapping != null ? requestMapping.value() : "";
      // 如果注解的basePath不正确,则忽略给定的值,用""代替(必须/开头,后面必须至少有一个字符)
      if (!basePath.startsWith(this.pathSeparator) || basePath.length() == 1) {
        basePath = "";
      }
      // 用来校验basePath是否正确
      // 参考 插件配置文件中的name
      // if (!configBasePath.equals(basePath)) {
      //   throw new RuntimeException("fullyQualifiedNames中配置的basePath与Rest类中basePath不匹配.");
      // }
      // 单例还是多,默认是单例
      final boolean singleton = restController.singleton();
      if (singleton) {
        // 创建一个对象,反射方法时,对象作为参数传入
        final Constructor<?> constructor = clazz.getConstructor();
        // 使用构造方法,反射创建对象
        final Object obj = constructor.newInstance();
        // 一个Rest类只有一个basePath,对应一个对象
        this.controllerMetaDataMap.put(basePath, new ControllerMetaData(true, obj, clazz));
      } else {
        // 如果是多例,不保存对象,使用的时候动态的创建对象
        this.controllerMetaDataMap.put(basePath, new ControllerMetaData(false, null, clazz));
      }
    } catch (final Exception e) {

    }
  }

  /**
   * get方法路径
   *
   * @param method 方法
   * @param annotationClass 注释类
   * @return {@link String}
   */
  public abstract String getMethodPath(Method method, Class<? extends Annotation> annotationClass);

  /**
   * 元数据映射得到控制器
   *
   * @return {@link Map<String, ControllerMetaData>}
   */
  public Map<String, ControllerMetaData> getControllerMetaDataMap() {
    return this.controllerMetaDataMap;
  }

  /**
   * 得到充分的路径
   *
   * @return {@link Map<String, MethodMetaData>}
   */
  public Map<String, MethodMetaData> getFullyPaths() {
    return this.fullyPaths;
  }

  /**
   * 得到完全路径参数
   *
   * @return {@link Map<String, List<ParameterMetaData>>}
   */
  public Map<String, List<ParameterMetaData>> getFullyPathsParams() {
    return this.fullyPathsParams;
  }
}
