package org.jdkstack.jdklog.logging.admin.plugin.rest;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import org.jdkstack.jdklog.logging.admin.http.annotation.DeleteMapping;
import org.jdkstack.jdklog.logging.admin.http.annotation.GetMapping;
import org.jdkstack.jdklog.logging.admin.http.annotation.PatchMapping;
import org.jdkstack.jdklog.logging.admin.http.annotation.PostMapping;
import org.jdkstack.jdklog.logging.admin.http.annotation.PutMapping;
import org.jdkstack.jdklog.logging.admin.http.annotation.RestController;
import org.jdkstack.jdklog.logging.admin.http.metadata.ControllerMetaData;
import org.jdkstack.jdklog.logging.admin.http.metadata.MethodMetaData;
import org.jdkstack.jdklog.logging.admin.plugin.Plugin;
import org.jdkstack.jdklog.logging.admin.plugin.PluginEntity;
import org.jdkstack.jdklog.logging.admin.plugin.PluginServiceLifecycle;
import org.jdkstack.jdklog.logging.admin.plugin.PluginServices;

/**
 * rest服务模块的生命周期
 *
 * @author admin
 */
public final class RestServiceModuleLifecycle extends AbstractRestServiceModuleLifecycle {
  private static final RestServiceModuleLifecycle instance = new RestServiceModuleLifecycle();
  private final Map<String, String> fullyQualifiedNames = new HashMap<>();
  /** 存储RestController的根URL路径和全限定名的映射,把类主动加载到就jvm中 */
  private final Map<String, Class<?>> fullyQualifiedClasses = new HashMap<>();

  private RestServiceModuleLifecycle() {}

  public static RestServiceModuleLifecycle getInstance() {
    return RestServiceModuleLifecycle.instance;
  }

  @Override
  public void doInit() throws Exception {
    // 手动配置Rest类静态信息
    // 每增加一个Rest类,都需要在这个配置
    this.fullyQualifiedNames.put(
        "user", "org.nation.core.server.core.http.rest.controller.UserController");
    this.fullyQualifiedNames.put(
        "base", "org.nation.core.server.core.http.rest.controller.TestController");
    // 加载Rest类到jvm,保存类元数据和方法元数据
    for (final Map.Entry<String, String> entry : this.fullyQualifiedNames.entrySet()) {
      // Rest类简单名称(类名)
      final String basePath = entry.getKey();
      // Rest类全限定名(包名+类型)
      final String fullyQualifiedName = entry.getValue();
      // 用Class.forName方法返回类的Class
      final Class<?> classObj = this.classForName(fullyQualifiedName);
      // 如果为空,jvm中不存在这个类
      if (classObj != null) {
        this.fullyQualifiedClasses.put(basePath, classObj);
      }
    }
    // 注册插件中的rest服务
    final PluginServices pluginServices = PluginServices.getInstance();
    final Map<String, PluginServiceLifecycle> pluginServiceLifecycles =
        pluginServices.getPluginServiceLifecycles();
    final PluginServiceLifecycle pluginServiceLifecycle = pluginServiceLifecycles.get("rest");
    final Map<String, PluginEntity> pluginEntitys = pluginServiceLifecycle.getPluginEntitys();
    // 注册插件中的rest服务
    for (final Map.Entry<String, PluginEntity> entry : pluginEntitys.entrySet()) {
      final String basePath = entry.getKey();
      final PluginEntity pluginEntity = entry.getValue();
      final Class<? extends Plugin> classObj = pluginEntity.getPlugin().getClass();
      this.fullyQualifiedClasses.put(basePath, classObj);
    }
  }

  @Override
  public void doStart() {
    for (final Map.Entry<String, Class<?>> entry : this.fullyQualifiedClasses.entrySet()) {
      // 如果存在,利用反射,获取类的方法,注解,参数等数据,用Map保存起来
      // Netty Http服务拦截到客户端发起的Http请求以后,根据Http URL匹配Map中保存的数据
      // 包括方法,类,参数名,参数类型等
      // 配置的basePath 和插件中的name,暂时没有使用，但是不代表以后不用，内部使用
      // Rest controller 注解 Request Mapping的值代替了。
      // String basePath = entry.getKey();
      final Class<?> classObj = entry.getValue();
      // , basePath
      this.controllerMetaData(classObj);
    }
    // 保存方法元数据
    for (final Map.Entry<String, ControllerMetaData> entry :
        this.controllerMetaDataMap.entrySet()) {
      final String basePath = entry.getKey();
      final ControllerMetaData controllerMetaData = entry.getValue();
      final Class<?> classObj = controllerMetaData.getClassObj();
      this.methodMetaData(classObj, basePath);
    }
    // 保存参数元数据
    for (final Map.Entry<String, MethodMetaData> entry : this.fullyPaths.entrySet()) {
      final String fullPath = entry.getKey();
      final MethodMetaData methodMetaData = entry.getValue();
      final Method method = methodMetaData.getMethod();
      this.parameterMetaData(method, fullPath);
    }
  }

  /**
   * get方法路径
   *
   * @param method 方法
   * @param annotationClass 注释类
   * @return {@link String}
   */
  @Override
  public String getMethodPath(
      final Method method, final Class<? extends Annotation> annotationClass) {
    String methodPath = "";
    // 如果Rest 方法注解是GetMapping
    if (annotationClass.equals(GetMapping.class)) {
      methodPath = method.getAnnotation(GetMapping.class).value();
    }
    if (annotationClass.equals(PostMapping.class)) {
      methodPath = method.getAnnotation(PostMapping.class).value();
    }
    if (annotationClass.equals(PutMapping.class)) {
      methodPath = method.getAnnotation(PutMapping.class).value();
    }
    if (annotationClass.equals(DeleteMapping.class)) {
      methodPath = method.getAnnotation(DeleteMapping.class).value();
    }
    if (annotationClass.equals(PatchMapping.class)) {
      methodPath = method.getAnnotation(PatchMapping.class).value();
    }
    return methodPath;
  }

  @Override
  protected void doStop() {
    System.out.println();
  }

  @Override
  protected void doClose() {
    System.out.println();
  }

  @Override
  protected void doDestroy() {
    System.out.println();
  }

  /**
   * 检查元数据(AnnotatedElement 代替 class类) 用于进行检查操作
   *
   * @param classObj 类obj
   * @return boolean
   */
  public boolean checkMetaData(final AnnotatedElement classObj) {
    final RestController restController = classObj.getAnnotation(RestController.class);
    if (restController == null) {
      return false;
    }
    // 如果是true,则正常单例加载类,如果是false,停止加载
    return restController.singleton();
  }

  /**
   * 检查元数据(AnnotatedElement 代替 class类) 用于进行检查操作
   *
   * @param fullyQualifiedName .
   * @return Class .
   */
  public Class<?> classForName(final String fullyQualifiedName) {
    Class<?> classObj = null;
    try {
      classObj = Class.forName(fullyQualifiedName);
    } catch (final ClassNotFoundException e) {
      //
    }
    return classObj;
  }

  /**
   * .
   *
   * @return Map .
   */
  public Map<String, String> getFullyQualifiedNames() {
    return this.fullyQualifiedNames;
  }

  /**
   * .
   *
   * @return Map .
   */
  public Map<String, Class<?>> getFullyQualifiedClasses() {
    return this.fullyQualifiedClasses;
  }
}
