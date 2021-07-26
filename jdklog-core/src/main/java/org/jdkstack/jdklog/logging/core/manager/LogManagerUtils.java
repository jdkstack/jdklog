package org.jdkstack.jdklog.logging.core.manager;

import java.util.Map;
import org.jdkstack.jdklog.logging.api.manager.LoaderLogInfo;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public final class LogManagerUtils {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  private LogManagerUtils() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key .
   * @param value .
   * @return String .
   * @author admin
   */
  public static String getProperty(final String key, final String value) {
    // 获取日志管理器存储的所有ClassLoader.
    final Map<ClassLoader, LoaderLogInfo> classLoaderLoggers =
        AbstractLogManager.getClassLoaderLoggers1();
    // 获取当前的系统类加载器.
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    // 获取当前系统类加载器的日志信息.
    final LoaderLogInfo classLoaderLogInfo = classLoaderLoggers.get(classLoader);
    return classLoaderLogInfo.getProperty(key, value);
  }
}
