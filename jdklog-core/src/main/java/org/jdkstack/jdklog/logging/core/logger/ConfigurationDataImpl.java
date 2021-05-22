package org.jdkstack.jdklog.logging.core.logger;

import java.util.List;
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
public interface ConfigurationDataImpl {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param h h.
   * @author admin
   */
  void addHandler(Handler h);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param h h.
   * @author admin
   */
  void removeHandler(Handler h);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return boolean boolean.
   * @author admin
   */
  boolean isUseParentHandlers();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param flag flag.
   * @author admin
   */
  void setUseParentHandlers(boolean flag);

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
   * @param newFilter newFilter.
   * @author admin
   */
  void setFilter(Filter newFilter);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Level Level.
   * @author admin
   */
  Level getLevelObject();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param newLogLevel newLogLevel.
   * @author admin
   */
  void setLevelObject(Level newLogLevel);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return int int.
   * @author admin
   */
  int getLevelValue();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param v v.
   * @author admin
   */
  void setLevelValue(int v);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return List List.
   * @author admin
   */
  List<Handler> getHandlers();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord logRecord.
   * @return boolean boolean.
   * @author admin
   */
  boolean isLoggable(Record logRecord);
}
