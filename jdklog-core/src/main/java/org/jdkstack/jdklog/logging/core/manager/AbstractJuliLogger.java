package org.jdkstack.jdklog.logging.core.manager;

import java.util.ArrayList;
import java.util.List;
import org.jdkstack.jdklog.logging.api.filter.Filter;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.logger.ConfigurationDataImpl;
import org.jdkstack.jdklog.logging.api.logger.Logger;
import org.jdkstack.jdklog.logging.api.manager.LogManager;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.LogLevel;
import org.jdkstack.jdklog.logging.api.metainfo.LogRecord;
import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.core.logger.ConfigurationData;

/**
 * 提供所有日志的方法.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public abstract class AbstractJuliLogger implements Logger {
  /** 是否开启Logger级别日志处理. */
  private static final int OFF_VALUE = LogLevel.OFF.intValue();
  /** . */
  protected static final Handler[] EMPTY_HANDLERS = new Handler[0];
  /** . */
  protected static final LogManager MANAGER = AbstractLogManager.getLogManager();
  /** . */
  protected static final Level LEVEL1 = LogLevel.ALL;
  /** . */
  protected final ConfigurationDataImpl config;
  /** . */
  protected final String name;
  /** . */
  protected Logger parent;
  /** . */
  protected Logger next;
  /** . */
  protected Logger previous;
  /** . */
  protected int levelName;
  /** . */
  protected int levelValue1;
  /** 当前Logger配置的处理器. */
  protected final List<Handler> handlers1 = new ArrayList<>(16);
  /** 当前Logger配置的过滤器. */
  protected final List<Filter> filters1 = new ArrayList<>(16);
  /** 继承父Logger(包). */
  protected boolean isInherit;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param name name.
   * @author admin
   */
  protected AbstractJuliLogger(final String name) {
    this.config = new ConfigurationData();
    this.name = name;
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param logRecord 日志对象.
   * @author admin
   */
  private void sendLog(final Record logRecord) {
    // 获取Logger注册的处理器集合.
    final List<Handler> handlers = this.config.getHandlers();
    // 同步发送和异步发送需要考虑,当初有异步发送的实现,后来去掉了. 后续需要继续看,是否应该实现.
    for (final Handler handler : handlers) {
      handler.publish(logRecord);
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param msg .
   * @author admin
   */
  private void log(final Level logLevel, final String msg) {
    final Record lr = new LogRecord(logLevel, msg);
    this.doLog(lr);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param logRecord .
   * @author admin
   */
  private void log(final Record logRecord) {
    // Logger级别日志过滤规则, 如果日志不符合内置过滤规则,则过滤日志.
    final Level level = logRecord.getLevel();
    final boolean isLoggableBySystem = this.isLoggable(level);
    // Logger级别的志过滤器,可以自定义过滤规则.
    final boolean isFilter = null != this.config.getFilter();
    // 如果过滤器对象不是空,并且过滤器规则返回真,则过滤掉日志.
    if (isFilter && this.config.isLoggable(logRecord)) {
      //
    }
    // 如果日志级别正常.打印日志.
    if (isLoggableBySystem) {
      this.sendLog(logRecord);
    } else {
      //
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param msg .
   * @param param1 .
   * @author admin
   */
  @Override
  public final void log(final Level logLevel, final String msg, final Object param1) {
    final Record lr = new LogRecord(logLevel, msg);
    lr.setParameter(param1);
    this.doLog(lr);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param msg .
   * @param params .
   * @author admin
   */
  @Override
  public final void log(final Level logLevel, final String msg, final Object... params) {
    final Record lr = new LogRecord(logLevel, msg);
    for (final Object param : params) {
      lr.setParameter(param);
    }
    this.doLog(lr);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @param msg .
   * @param thrown .
   * @author admin
   */
  @Override
  public final void log(final Level logLevel, final String msg, final Throwable thrown) {
    final Record lr = new LogRecord(logLevel, msg);
    lr.setThrown(thrown);
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
  protected final void doLog(final Record lr) {
    lr.setLoggerName(this.name);
    this.log(lr);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @return boolean .
   * @author admin
   */
  private boolean isLoggable(final Level logLevel) {
    final int levelValue = this.config.getLevelValue();
    // 如果是真,则表示日志是正常的处理范围.
    final boolean isIntValue = logLevel.intValue() >= levelValue;
    // 如果是真,则表示日志开关处于打开状态.
    final boolean isLevelValue = levelValue != OFF_VALUE;
    // 只要有一个不是真,则表示日志会过滤掉.
    return isIntValue && isLevelValue;
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  @Override
  public final void severe(final String msg) {
    this.log(LogLevel.SEVERE, msg);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  @Override
  public final void warning(final String msg) {
    this.log(LogLevel.WARNING, msg);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  @Override
  public final void info(final String msg) {
    this.log(LogLevel.INFO, msg);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  @Override
  public final void config(final String msg) {
    this.log(LogLevel.CONFIG, msg);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  @Override
  public final void fine(final String msg) {
    this.log(LogLevel.FINE, msg);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  @Override
  public final void finer(final String msg) {
    this.log(LogLevel.FINER, msg);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param msg .
   * @author admin
   */
  @Override
  public final void finest(final String msg) {
    this.log(LogLevel.FINEST, msg);
  }
}
