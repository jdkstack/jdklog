package org.jdkstack.jdklog.logging.api.handler;

import org.jdkstack.jdklog.logging.api.filter.Filter;
import org.jdkstack.jdklog.logging.api.formatter.Formatter;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.Record;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Handler {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord 日志对象.
   * @author admin
   */
  void publish(Record logRecord);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void flush();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @throws SecurityException 安全异常检查.
   * @author admin
   */
  void close();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param newFormatter .
   * @author admin
   */
  void setFormatter(Formatter newFormatter);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param newFilter .
   * @throws SecurityException .
   * @author admin
   */
  void setFilter(Filter newFilter);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param newLogLevel .
   * @throws SecurityException .
   * @author admin
   */
  void setLevel(Level newLogLevel);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return int 返回数量.
   * @author admin
   */
  int size();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param size 一次处理的批.
   * @author admin
   */
  void process(int size);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return long 返回最后一次接收到日志的时间戳.
   * @author admin
   */
  long getSys();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name name.
   * @param defaultValue defaultValue.
   * @return String String.
   * @author admin
   */
  String getProperty(String name, String defaultValue);
}
