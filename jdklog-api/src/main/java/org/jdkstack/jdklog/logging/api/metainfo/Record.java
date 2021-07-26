package org.jdkstack.jdklog.logging.api.metainfo;

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
