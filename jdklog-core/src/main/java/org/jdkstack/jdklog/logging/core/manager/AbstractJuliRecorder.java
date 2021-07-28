package org.jdkstack.jdklog.logging.core.manager;

import java.util.List;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.logger.ConfigurationDataImpl;
import org.jdkstack.jdklog.logging.api.logger.Recorder;
import org.jdkstack.jdklog.logging.api.manager.LogManager;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.LogLevel;
import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.core.logger.ConfigurationData;

/**
 * 提供所有日志的方法.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public abstract class AbstractJuliRecorder implements Recorder {
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

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param name name.
   * @author admin
   */
  protected AbstractJuliRecorder(final String name) {
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
      // 当前的handler状态.
      int state = handler.state();
      // 状态为0,表示正常.
      if (state == 0) {
        handler.publish(logRecord);
      }
      // 获取额外的handler,进行数据的处理,需要一个策略,日志丢弃?日期存储到其他地方?.
    }
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
    // 如果日志级别不匹配.
    if (!isLoggableBySystem) {
      // 需要考虑输出不符合的日志.
      return;
    }
    // Handler级别的日志过滤器,可以自定义过滤规则.
    final boolean isFilter = null != this.config.getFilter();
    // 如果过滤器对象不是空,并且过滤器规则返回真,则过滤掉日志.
    if (isFilter && this.config.isLoggable(logRecord)) {
      // 需要考虑输出不符合的日志.
      return;
    }
    // 如果日志级别正常.打印日志.
    this.sendLog(logRecord);
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
}
