package org.jdkstack.jdklog.logging.api.metainfo;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Record {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return int .
   * @author admin
   */
  int getThreadId();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param threadId .
   * @author admin
   */
  void setThreadId(int threadId);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getThreadName();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param threadName .
   * @author admin
   */
  void setThreadName(String threadName);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getUniqueId();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param uniqueId .
   * @author admin
   */
  void setUniqueId(String uniqueId);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getLoggerName();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @author admin
   */
  void setLoggerName(String name);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getSourceClassName();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param sourceClassName .
   * @author admin
   */
  void setSourceClassName(String sourceClassName);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getSourceMethodName();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param sourceMethodName .
   * @author admin
   */
  void setSourceMethodName(String sourceMethodName);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getMessage();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @author admin
   */
  void setMessage(String message);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Object .
   * @author admin
   */
  List<Object> getParameters();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param parameter .
   * @author admin
   */
  void setParameter(Object parameter);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Instant .
   * @author admin
   */
  Instant getInstant();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param instant .
   * @author admin
   */
  void setInstant(Instant instant);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Throwable .
   * @author admin
   */
  Throwable getThrown();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param thrown .
   * @author admin
   */
  void setThrown(Throwable thrown);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Level .
   * @author admin
   */
  Level getLevel();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @author admin
   */
  void setLevel(Level logLevel);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return long .
   * @author admin
   */
  long getSerialNumber();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param serialNumber .
   * @author admin
   */
  void setSerialNumber(long serialNumber);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Map .
   * @author admin
   */
  Map<String, String> getCustoms();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key 自定义属性key.
   * @param value 自定义属性值.
   * @author admin
   */
  void setCustom(String key, String value);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return int .
   * @author admin
   */
  int getLineNumber();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param lineNumber .
   * @author admin
   */
  void setLineNumber(int lineNumber);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getHost();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param host .
   * @author admin
   */
  void setHost(String host);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  String getPort();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param port .
   * @author admin
   */
  void setPort(String port);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return int 日志级别.
   * @author admin
   */
  int intValue();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String 日志级别名称.
   * @author admin
   */
  String getLevelName();
}
