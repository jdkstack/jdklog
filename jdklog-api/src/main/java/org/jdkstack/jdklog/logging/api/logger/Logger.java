package org.jdkstack.jdklog.logging.api.logger;

import org.jdkstack.jdklog.logging.api.filter.Filter;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.Record;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Logger {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handler 日志级别.
   * @author admin
   */
  void addHandler(Handler handler);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getName();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Handler[]
   * @author admin
   */
  Handler[] getHandlers();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Level
   * @author admin
   */
  Level getLevel();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param newLogLevel 日志级别.
   * @author admin
   */
  void setLevel(Level newLogLevel);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param useParentHandlers 日志级别.
   * @author admin
   */
  void setUseParentHandlers(boolean useParentHandlers);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param lr 日志对象.
   * @author admin
   */
  void logp(Record lr);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel 日志级别.
   * @param sourceClass 日志源类权限定名.
   * @param sourceMethod 日志源方法.
   * @param msg 日志消息.
   * @param params 日志异常.
   * @author admin
   */
  void logp(Level logLevel, String sourceClass, String sourceMethod, String msg, Object... params);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel 日志级别.
   * @param sourceClass 日志源类权限定名.
   * @param sourceMethod 日志源方法.
   * @param msg 日志消息.
   * @param param1 日志异常.
   * @author admin
   */
  void logp(Level logLevel, String sourceClass, String sourceMethod, String msg, Object param1);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel 日志级别.
   * @param sourceClass 日志源类权限定名.
   * @param sourceMethod 日志源方法.
   * @param msg 日志消息.
   * @author admin
   */
  void logp(Level logLevel, String sourceClass, String sourceMethod, String msg);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel 日志级别.
   * @param sourceClass 日志源类权限定名.
   * @param sourceMethod 日志源方法.
   * @param msg 日志消息.
   * @param thrown 日志异常.
   * @author admin
   */
  void logp(Level logLevel, String sourceClass, String sourceMethod, String msg, Throwable thrown);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel 日志级别.
   * @param msg 日志消息.
   * @param thrown 日志异常.
   * @author admin
   */
  void log(Level logLevel, String msg, Throwable thrown);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel 日志级别.
   * @param msg 日志消息.
   * @param param1 日志异常.
   * @author admin
   */
  void log(Level logLevel, String msg, Object param1);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel 日志级别.
   * @param msg 日志消息.
   * @param params 日志异常.
   * @author admin
   */
  void log(Level logLevel, String msg, Object... params);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Filter Filter.
   * @author admin
   */
  Filter getFilter();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param newFilter .
   * @author admin
   */
  void setFilter(Filter newFilter);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return boolean .
   * @author admin
   */
  boolean isLevelInitialized();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handler .
   * @author admin
   */
  void removeHandler(Handler handler);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Handler[] .
   * @author admin
   */
  Handler[] accessCheckedHandlers();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return boolean .
   * @author admin
   */
  boolean hasUseParentHandlers();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Logger .
   * @author admin
   */
  Logger getParent();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param parent .
   * @author admin
   */
  void setParent(Logger parent);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  void severe(String msg);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  void warning(String msg);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  void info(String msg);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  void config(String msg);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  void fine(String msg);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  void finer(String msg);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  void finest(String msg);
}
