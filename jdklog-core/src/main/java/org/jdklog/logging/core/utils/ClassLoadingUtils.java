package org.jdklog.logging.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.jdklog.logging.api.exception.StudyJuliRuntimeException;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
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
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * <p>java:S1452:返回值最好不要使用通配符,因为jdk底层方法使用了?通配符.此处忽略处理.
   *
   * @param className .
   * @return Constructor<?>
   * @author admin
   */
  @SuppressWarnings("java:S1452")
  public static Constructor<?> constructor(final String className) {
    final ClassLoader systemClassLoader = Thread.currentThread().getContextClassLoader();
    try {
      final Class<?> classObj = systemClassLoader.loadClass(className);
      return classObj.getConstructor();
    } catch (final ClassNotFoundException | NoSuchMethodException e) {
      throw new StudyJuliRuntimeException("构造函数反射异常.", e);
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * <p>java:S1452:返回值最好不要使用通配符,因为jdk底层方法使用了?通配符.此处忽略处理.
   *
   * @param className .
   * @return Constructor<?>
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
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param constructor .
   * @return Object
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
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param constructor .
   * @param param .
   * @return Object
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
