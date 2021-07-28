package org.jdkstack.jdklog.logging.core.manager;

import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.logger.Recorder;
import org.jdkstack.jdklog.logging.api.manager.LoaderLogInfo;
import org.jdkstack.jdklog.logging.api.manager.LogManager;
import org.jdkstack.jdklog.logging.core.utils.ClassLoadingUtils;

/**
 * 日志核心,启动时初始化整个日志环境(需要优化).
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public abstract class AbstractLogManager implements LogManager {
  /** . */
  private static final LogManager LOGMANAGER;

  /** 自定义类加载器,目前只支持系统类加载器,不支持自定义类加载器. */
  private static final Map<ClassLoader, LoaderLogInfo> CLASSLOADERLOGGERS =
      new ConcurrentHashMap<>(10);

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
  public final void put(final String loggerName, final Recorder logger) {
    final LoaderLogInfo temp = getLoaderLogInfo();
    temp.put(loggerName, logger);
  }

  @Override
  public final LoaderLogInfo get(final ClassLoader current) {
    return CLASSLOADERLOGGERS.get(current);
  }

  @Override
  public final Recorder getRootLogger() {
    final LoaderLogInfo temp = getLoaderLogInfo();
    return temp.getRootLogger();
  }

  @Override
  public final Recorder getLogger1(final String name) {
    final LoaderLogInfo temp = getLoaderLogInfo();
    return temp.get(name);
  }

  private LoaderLogInfo getLoaderLogInfo() {
    final Thread thread = Thread.currentThread();
    final ClassLoader classLoader = thread.getContextClassLoader();
    return CLASSLOADERLOGGERS.get(classLoader);
  }

  @Override
  public final boolean containsKey(final String loggerName) {
    final LoaderLogInfo temp = getLoaderLogInfo();
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
    final LoaderLogInfo temp = getLoaderLogInfo();
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
    final Recorder logger1 = this.getLogger1(loggerName);
    return logger1.getHandlers();
  }
}
