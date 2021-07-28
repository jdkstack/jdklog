package org.jdkstack.jdklog.logging.api.manager;

import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.logger.Recorder;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface LogManager {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logger 日志logger对象.
   * @return b.
   * @author admin
   */
  boolean addLogger(Recorder logger);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name 属性名.
   * @return String.
   * @author admin
   */
  String getProperty(String name);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @throws SecurityException 安全异常检查.
   * @author admin
   */
  void readConfiguration();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param listener 添加监听器.
   * @author admin
   */
  void addConfigurationListener(Runnable listener);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param listener 移除监听器.
   * @author admin
   */
  void removeConfigurationListener(Runnable listener);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param classLoader classLoader.
   * @param info info.
   * @author admin
   */
  void put(ClassLoader classLoader, LoaderLogInfo info);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logger logger.
   * @param loggerName loggerName.
   * @author admin
   */
  void put(String loggerName, Recorder logger);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param current current.
   * @return LoaderLogInfo .
   * @author admin
   */
  LoaderLogInfo get(ClassLoader current);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Logger Logger.
   * @author admin
   */
  Recorder getRootLogger();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param loggerName loggerName.
   * @return boolean .
   * @author admin
   */
  boolean containsKey(String loggerName);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name name.
   * @return String .
   * @author admin
   */
  String findProperty(String name);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name name.
   * @return Logger.
   * @author admin
   */
  Recorder getLogger1(String name);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param loggerName loggerName.
   * @return Handler[] .
   * @author admin
   */
  Handler[] getHandlers(String loggerName);
}
