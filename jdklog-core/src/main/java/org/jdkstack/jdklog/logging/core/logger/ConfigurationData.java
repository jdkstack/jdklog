package org.jdkstack.jdklog.logging.core.logger;

import java.util.ArrayList;
import java.util.List;
import org.jdkstack.jdklog.logging.api.filter.Filter;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.logger.ConfigurationDataImpl;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.LogLevel;
import org.jdkstack.jdklog.logging.api.metainfo.Record;

/**
 * 保存配置信息,比如handler,filter等.
 *
 * <p>Another description after blank line.@SuppressWarnings({"java:S2250"})
 *
 * @author admin
 */
public class ConfigurationData implements ConfigurationDataImpl {
  /** . */
  private final List<Handler> handlers = new ArrayList<>(16);
  /** . */
  private boolean useParentHandlers;
  /** . */
  private Filter filter;
  /** . */
  private Level logLevelObject;
  /** . */
  private int levelValue;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public ConfigurationData() {
    this.useParentHandlers = true;
    this.logLevelObject = LogLevel.INFO;
    this.levelValue = LogLevel.INFO.intValue();
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param h .
   * @author admin
   */
  @Override
  public final void addHandler(final Handler h) {
    this.handlers.add(h);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param h .
   * @author admin
   */
  @Override
  public final void removeHandler(final Handler h) {
    this.handlers.remove(h);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return boolean .
   * @author admin
   */
  @Override
  public final boolean isUseParentHandlers() {
    return this.useParentHandlers;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param flag .
   * @author admin
   */
  @Override
  public final void setUseParentHandlers(final boolean flag) {
    this.useParentHandlers = flag;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Filter .
   * @author admin
   */
  @Override
  public final Filter getFilter() {
    return this.filter;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param newFilter .
   * @author admin
   */
  @Override
  public final void setFilter(final Filter newFilter) {
    this.filter = newFilter;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Level .
   * @author admin
   */
  @Override
  public final Level getLevelObject() {
    return this.logLevelObject;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param newLogLevel .
   * @author admin
   */
  @Override
  public final void setLevelObject(final Level newLogLevel) {
    this.logLevelObject = newLogLevel;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return int .
   * @author admin
   */
  @Override
  public final int getLevelValue() {
    return this.levelValue;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param v .
   * @author admin
   */
  @Override
  public final void setLevelValue(final int v) {
    this.levelValue = v;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return List Handler.
   * @author admin
   */
  @Override
  public final List<Handler> getHandlers() {
    return new ArrayList<>(this.handlers);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord logRecord.
   * @return boolean .
   * @author admin
   */
  @Override
  public final boolean isLoggable(final Record logRecord) {
    return this.filter.isLoggable(logRecord);
  }
}
