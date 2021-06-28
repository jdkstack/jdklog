package org.jdkstack.jdklog.logging.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;

/**
 * 反射工具.
 *
 * <p>反射生成对象的工具类.
 *
 * @author admin
 */
public final class ClassLoadingUtils {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  private ClassLoadingUtils() {
    //
  }

  /**
   * 根据全限定名反射生成对象.
   *
   * <p>java:S1452:返回值最好不要使用通配符,因为jdk底层方法使用了?通配符.此处忽略处理.
   *
   * @param className 全限定名.
   * @return Constructor
   * @author admin
   */
  @SuppressWarnings("java:S1452")
  public static Constructor<?> constructor(final String className) {
    // 获取当前类加载器.
    final ClassLoader systemClassLoader = Thread.currentThread().getContextClassLoader();
    try {
      // 使用当前的类加载器生成类.
      final Class<?> classObj = systemClassLoader.loadClass(className);
      return classObj.getConstructor();
    } catch (final ClassNotFoundException | NoSuchMethodException e) {
      throw new StudyJuliRuntimeException("构造函数反射异常.", e);
    }
  }

  /**
   * 根据全限定名反射生成对象.
   *
   * <p>java:S1452:返回值最好不要使用通配符,因为jdk底层方法使用了?通配符.此处忽略处理.
   *
   * @param className 全限定名.
   * @return Constructor
   * @author admin
   */
  @SuppressWarnings("java:S1452")
  public static Constructor<?> constructor2(final String className) {
    final ClassLoader systemClassLoader = Thread.currentThread().getContextClassLoader();
    try {
      final Class<?> classObj = systemClassLoader.loadClass(className);
      return classObj.getConstructor(String.class);
    } catch (final ClassNotFoundException | NoSuchMethodException e) {
      throw new StudyJuliRuntimeException("构造函数反射异常.", e);
    }
  }

  /**
   * 构造函数反射生成对象.
   *
   * <p>Another description after blank line.
   *
   * @param constructor 构造函数对象.
   * @return 返回反射生成的对象.
   * @author admin
   */
  public static Object newInstance(final Constructor<?> constructor) {
    try {
      return constructor.newInstance();
    } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new StudyJuliRuntimeException("构造函数反射动态创建对象异常.", e);
    }
  }

  /**
   * 构造函数反射生成对象.
   *
   * <p>Another description after blank line.
   *
   * @param constructor 构造函数对象.
   * @param param 构造函数需要的参数.
   * @return 返回反射生成的对象.
   * @author admin
   */
  public static Object newInstance(final Constructor<?> constructor, final String param) {
    try {
      return constructor.newInstance(param);
    } catch (final InstantiationException | IllegalAccessException | InvocationTargetException e) {
      throw new StudyJuliRuntimeException("构造函数反射动态创建对象异常.", e);
    }
  }
}
