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
   * @param handler handler.
   * @author admin
   */
  void removeHandler(Handler handler);
}
