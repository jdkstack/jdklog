package org.jdkstack.jdklog.logging.core.manager;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.logger.Logger;
import org.jdkstack.jdklog.logging.api.manager.LoaderLogInfo;
import org.jdkstack.jdklog.logging.api.manager.LogManager;
import org.jdkstack.jdklog.logging.core.utils.ClassLoadingUtils;

/**
 * This is a class description(需要优化).
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public abstract class AbstractLogManager implements LogManager {
  /** . */
  private static final LogManager LOGMANAGER;

  /** 自定义类加载器,目前只支持系统类加载器,不支持自定义类加载器. */
  private static final Map<ClassLoader, LoaderLogInfo> CLASSLOADERLOGGERS = new ConcurrentHashMap<>(10);

  static {
    final String logManagerName =
        System.getProperty(Constants.LOG_MANAGER, Constants.STUDY_JULI_LOG_MANAGER);
    final Constructor<?> constructor = ClassLoadingUtils.constructor(logManagerName);
    LOGMANAGER = (LogManager) ClassLoadingUtils.newInstance(constructor);
    LOGMANAGER.readConfiguration();
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return AbstractLogManager.
   * @author admin
   */
  public static LogManager getLogManager() {
    return LOGMANAGER;
  }

  @Override
  public final void readConfiguration(final InputStream ins) {
    //
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION_MESSAGE);
  }

  @Override
  public final void reset() {
    //
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION_MESSAGE);
  }

  @Override
  public final void updateConfiguration(
      final Function<String, BiFunction<String, String, String>> mapper) {
    //
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION_MESSAGE);
  }

  @Override
  public final void updateConfiguration(
      final InputStream ins, final Function<String, BiFunction<String, String, String>> mapper) {
    //
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION_MESSAGE);
  }

  @Override
  public final void checkAccess() {
    //
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION_MESSAGE);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void addConfigurationListener(final Runnable listener) {
    //
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION_MESSAGE);
  }

  @Override
  public final void removeConfigurationListener(final Runnable listener) {
    //
    throw new UnsupportedOperationException(Constants.UNSUPPORTED_OPERATION_MESSAGE);
  }

  @Override
  public final void checkPermission() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @return JuliLogger
   * @author admin
   */
  @Override
  public final Logger demandLogger(final String name) {
    return this.getLogger(name);
  }

  @Override
  public final Map<ClassLoader, LoaderLogInfo> getClassLoaderLoggers() {
    return Collections.unmodifiableMap(CLASSLOADERLOGGERS);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return JuliLogger
   * @author admin
   */
  public static Map<ClassLoader, LoaderLogInfo> getClassLoaderLoggers1() {
    return Collections.unmodifiableMap(CLASSLOADERLOGGERS);
  }

  @Override
  public final void put(final ClassLoader classLoader, final LoaderLogInfo info) {
    CLASSLOADERLOGGERS.put(classLoader, info);
  }

  @Override
  public final void put(final String loggerName, final Logger logger) {
    final Thread thread = Thread.currentThread();
    final ClassLoader classLoader = thread.getContextClassLoader();
    final LoaderLogInfo temp = CLASSLOADERLOGGERS.get(classLoader);
    temp.put(loggerName, logger);
  }

  @Override
  public final LoaderLogInfo get(final ClassLoader current) {
    return CLASSLOADERLOGGERS.get(current);
  }

  @Override
  public final Logger getRootLogger() {
    final Thread thread = Thread.currentThread();
    final ClassLoader classLoader = thread.getContextClassLoader();
    final LoaderLogInfo temp = CLASSLOADERLOGGERS.get(classLoader);
    return temp.getRootLogger();
  }

  @Override
  public final Logger getLogger1(final String name) {
    final Thread thread = Thread.currentThread();
    final ClassLoader classLoader = thread.getContextClassLoader();
    final LoaderLogInfo temp = CLASSLOADERLOGGERS.get(classLoader);
    return temp.get(name);
  }

  @Override
  public final boolean containsKey(final String loggerName) {
    final Thread thread = Thread.currentThread();
    final ClassLoader classLoader = thread.getContextClassLoader();
    final LoaderLogInfo temp = CLASSLOADERLOGGERS.get(classLoader);
    return temp.containsKey(loggerName);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final String getProperty(final String name) {
    return this.findProperty(name);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name name.
   * @return String.
   * @author admin
   */
  @Override
  public final String findProperty(final String name) {
    final Thread thread = Thread.currentThread();
    final ClassLoader classLoader = thread.getContextClassLoader();
    final LoaderLogInfo temp = CLASSLOADERLOGGERS.get(classLoader);
    return temp.getProperty(name);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @return JuliLogger
   * @author admin
   */
  public static String getProperty1(final String name) {
    return LOGMANAGER.getProperty(name);
  }

  @Override
  public final Handler[] getHandlers(final String loggerName) {
    final Logger logger1 = this.getLogger1(loggerName);
    return logger1.getHandlers();
  }
}
