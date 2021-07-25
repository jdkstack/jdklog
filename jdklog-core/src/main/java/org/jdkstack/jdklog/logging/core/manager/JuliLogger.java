package org.jdkstack.jdklog.logging.core.manager;

import java.util.List;
import java.util.Objects;
import org.jdkstack.jdklog.logging.api.filter.Filter;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.logger.Logger;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.LogRecord;
import org.jdkstack.jdklog.logging.api.metainfo.Record;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class JuliLogger extends AbstractJuliLogger {

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @author admin
   */
  protected JuliLogger(final String name) {
    super(name);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @return JuliLogger .
   * @author admin
   */
  public static Logger getLogger(final String name) {
    Logger result = MANAGER.demandLogger(name);
    // 吐过没有获取到,热创建一个新的.
    if (null == result) {
      final Logger newLogger = new JuliLogger(name);
      do {
        if (MANAGER.addLogger(newLogger)) {
          return newLogger;
        }
        result = MANAGER.getLogger(name);
      } while (null == result);
    }
    return result;
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return Filter .
   * @author admin
   */
  @Override
  public final Filter getFilter() {
    return this.config.getFilter();
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param newFilter .
   * @throws SecurityException .
   * @author admin
   */
  @Override
  public final void setFilter(final Filter newFilter) {
    this.config.setFilter(newFilter);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param sourceClass .
   * @param sourceMethod .
   * @param msg .
   * @author admin
   */
  @Override
  public final void logp(
      final Level logLevel, final String sourceClass, final String sourceMethod, final String msg) {
    final Record lr = new LogRecord(logLevel, msg);
    lr.setCustom("sourceClass", sourceClass);
    lr.setCustom("sourceMethod", sourceMethod);
    this.doLog(lr);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param sourceClass .
   * @param sourceMethod .
   * @param msg .
   * @param param1 .
   * @author admin
   */
  @Override
  public final void logp(
      final Level logLevel,
      final String sourceClass,
      final String sourceMethod,
      final String msg,
      final Object param1) {
    final Record lr = new LogRecord(logLevel, msg);
    lr.setCustom("sourceClass", sourceClass);
    lr.setCustom("sourceMethod", sourceMethod);
    lr.setParameter(param1);
    lr.setUniqueId("-");
    this.doLog(lr);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param sourceClass .
   * @param sourceMethod .
   * @param msg .
   * @param params .
   * @author admin
   */
  @Override
  public final void logp(
      final Level logLevel,
      final String sourceClass,
      final String sourceMethod,
      final String msg,
      final Object... params) {
    final Record lr = new LogRecord(logLevel, msg);
    lr.setCustom("sourceClass", sourceClass);
    lr.setCustom("sourceMethod", sourceMethod);
    for (final Object param : params) {
      lr.setParameter(param);
    }
    lr.setUniqueId("-");
    this.doLog(lr);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param lr .
   * @author admin
   */
  @Override
  public final void logp(final Record lr) {
    this.doLog(lr);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param sourceClass .
   * @param sourceMethod .
   * @param msg .
   * @param thrown .
   * @author admin
   */
  @Override
  public final void logp(
      final Level logLevel,
      final String sourceClass,
      final String sourceMethod,
      final String msg,
      final Throwable thrown) {
    final Record lr = new LogRecord(logLevel, msg);
    lr.setCustom("sourceClass", sourceClass);
    lr.setCustom("sourceMethod", sourceMethod);
    lr.setThrown(thrown);
    this.doLog(lr);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return boolean .
   * @author admin
   */
  @Override
  public final boolean isLevelInitialized() {
    return null != this.config.getLevelObject();
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return Level .
   * @author admin
   */
  @Override
  public final Level getLevel() {
    return this.config.getLevelObject();
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param newLogLevel .
   * @author admin
   */
  @Override
  public final void setLevel(final Level newLogLevel) {
    this.config.setLevelObject(newLogLevel);
    final int i = newLogLevel.intValue();
    this.config.setLevelValue(i);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getName() {
    return this.name;
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param handler .
   * @author admin
   */
  @Override
  public final void addHandler(final Handler handler) {
    Objects.requireNonNull(handler);
    this.config.addHandler(handler);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param handler .
   * @author admin
   */
  @Override
  public final void removeHandler(final Handler handler) {
    this.config.removeHandler(handler);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return Handler .
   * @author admin
   */
  @Override
  public final Handler[] getHandlers() {
    return this.accessCheckedHandlers();
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return Handler .
   * @author admin
   */
  @Override
  public final Handler[] accessCheckedHandlers() {
    final List<Handler> handlers = this.config.getHandlers();
    return handlers.toArray(EMPTY_HANDLERS);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param useParentHandlers .
   * @author admin
   */
  @Override
  public final void setUseParentHandlers(final boolean useParentHandlers) {
    this.config.setUseParentHandlers(useParentHandlers);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return boolean .
   * @author admin
   */
  @Override
  public final boolean hasUseParentHandlers() {
    return this.config.isUseParentHandlers();
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return JuliLogger .
   * @author admin
   */
  @Override
  public final Logger getParent() {
    return this.parent;
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param parent .
   * @author admin
   */
  @Override
  public final void setParent(final Logger parent) {
    this.doSetParent(parent);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param newParent .
   * @author admin
   */
  private void doSetParent(final Logger newParent) {
    this.parent = newParent;
  }
}
