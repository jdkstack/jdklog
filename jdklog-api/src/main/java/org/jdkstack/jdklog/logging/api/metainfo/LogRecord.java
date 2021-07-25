package org.jdkstack.jdklog.logging.api.metainfo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jdkstack.jdklog.logging.api.context.Bean;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class LogRecord implements Record {
  /** . */
  private final Map<String, String> customs = new LinkedHashMap<>(Constants.MAP_CAPACITY);
  /** 0分配数组,需要优化. */
  private final List<Object> parameters = new ArrayList<>(Constants.MAP_CAPACITY);
  /** . */
  private Level logLevel;
  /** . */
  private String message;
  /** . */
  private int threadId;
  /** . */
  private String threadName;
  /** . */
  private Throwable thrown;
  /** . */
  private String loggerName;
  /** . */
  private Instant instant;
  /** . */
  private String uniqueId;
  /** 上下午数据. */
  private Bean contextBean;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel 日志级别.
   * @param msg 日志消息.
   * @author admin
   */
  public LogRecord(final Level logLevel, final String msg) {
    this.logLevel = logLevel;
    this.message = msg;
    this.instant = Instant.now();
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
  public final int getThreadId() {
    return this.threadId;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param threadId .
   * @author admin
   */
  @Override
  public final void setThreadId(final int threadId) {
    this.threadId = threadId;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getThreadName() {
    return this.threadName;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param threadName .
   * @author admin
   */
  @Override
  public final void setThreadName(final String threadName) {
    this.threadName = threadName;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getUniqueId() {
    return this.uniqueId;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param uniqueId .
   * @author admin
   */
  @Override
  public final void setUniqueId(final String uniqueId) {
    this.uniqueId = uniqueId;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getLoggerName() {
    return this.loggerName;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @author admin
   */
  @Override
  public final void setLoggerName(final String name) {
    this.loggerName = name;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public final String getMessage() {
    return this.message;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message .
   * @author admin
   */
  @Override
  public final void setMessage(final String message) {
    this.message = message;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Object .
   * @author admin
   */
  @Override
  public final List<Object> getParameters() {
    return Collections.unmodifiableList(this.parameters);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param parameter .
   * @author admin
   */
  @Override
  public final void setParameter(final Object parameter) {
    this.parameters.add(parameter);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Instant .
   * @author admin
   */
  @Override
  public final Instant getInstant() {
    return this.instant;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param instant .
   * @author admin
   */
  @Override
  public final void setInstant(final Instant instant) {
    this.instant = instant;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Throwable .
   * @author admin
   */
  @Override
  public final Throwable getThrown() {
    return this.thrown;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param thrown .
   * @author admin
   */
  @Override
  public final void setThrown(final Throwable thrown) {
    this.thrown = thrown;
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
  public final Level getLevel() {
    return this.logLevel;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logLevel .
   * @author admin
   */
  @Override
  public final void setLevel(final Level logLevel) {
    this.logLevel = logLevel;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Map .
   * @author admin
   */
  @Override
  public final Map<String, String> getCustoms() {
    return Collections.unmodifiableMap(this.customs);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param key 自定义属性key.
   * @param value 自定义属性值.
   * @author admin
   */
  @Override
  public final void setCustom(final String key, final String value) {
    this.customs.put(key, value);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return int 日志级别.
   * @author admin
   */
  @Override
  public final int intValue() {
    return this.logLevel.intValue();
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String 日志级别名.
   * @author admin
   */
  @Override
  public final String getLevelName() {
    return this.logLevel.getName();
  }

  @Override
  public Bean getContextBean() {
    return contextBean;
  }

  @Override
  public void setContextBean(Bean contextBean) {
    this.contextBean = contextBean;
  }
}
