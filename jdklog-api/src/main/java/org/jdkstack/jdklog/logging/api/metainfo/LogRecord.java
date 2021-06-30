package org.jdkstack.jdklog.logging.api.metainfo;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
  /** . */
  private Level logLevel;
  /** . */
  private String sourceClassName;
  /** . */
  private String sourceMethodName;
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
  /** 0分配数组,需要优化. */
  private final List<Object> parameters = new ArrayList<>(Constants.MAP_CAPACITY);
  /** . */
  private String uniqueId;
  /** . */
  private long serialNumber;
  /** . */
  private int lineNumber;
  /** . */
  private String host;
  /** . */
  private String port;
  /** . */
  private String sourceHost;
  /** . */
  private String sourceIp;
  /** . */
  private String sourcePort;
  /** . */
  private String currentHost;
  /** . */
  private String currentIp;
  /** . */
  private String currentPort;
  /** . */
  private String destinationHost;
  /** . */
  private String destinationIp;
  /** . */
  private String destinationPort;
  /** 开始->前一个结束. */
  private String spanId0;
  /** 结束->下一个开始. */
  private String spanId1;
  /** 整个链路一样. */
  private String traceId;

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
  public final String getSourceClassName() {
    return this.sourceClassName;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param sourceClassName .
   * @author admin
   */
  @Override
  public final void setSourceClassName(final String sourceClassName) {
    this.sourceClassName = sourceClassName;
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
  public final String getSourceMethodName() {
    return this.sourceMethodName;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param sourceMethodName .
   * @author admin
   */
  @Override
  public final void setSourceMethodName(final String sourceMethodName) {
    this.sourceMethodName = sourceMethodName;
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
   * @return long .
   * @author admin
   */
  @Override
  public final long getSerialNumber() {
    return this.serialNumber;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param serialNumber .
   * @author admin
   */
  @Override
  public final void setSerialNumber(final long serialNumber) {
    this.serialNumber = serialNumber;
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
   * @return int .
   * @author admin
   */
  @Override
  public final int getLineNumber() {
    return this.lineNumber;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param lineNumber .
   * @author admin
   */
  @Override
  public final void setLineNumber(final int lineNumber) {
    this.lineNumber = lineNumber;
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
  public final String getHost() {
    return this.host;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param host .
   * @author admin
   */
  @Override
  public final void setHost(final String host) {
    this.host = host;
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
  public final String getPort() {
    return this.port;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param port .
   * @author admin
   */
  @Override
  public final void setPort(final String port) {
    this.port = port;
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

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String sourceHost.
   * @author admin
   */
  @Override
  public String getSourceHost() {
    return sourceHost;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param sourceHost sourceHost.
   * @author admin
   */
  @Override
  public void setSourceHost(final String sourceHost) {
    this.sourceHost = sourceHost;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String sourceIp.
   * @author admin
   */
  @Override
  public String getSourceIp() {
    return sourceIp;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param sourceIp sourceIp.
   * @author admin
   */
  @Override
  public void setSourceIp(final String sourceIp) {
    this.sourceIp = sourceIp;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String sourcePort.
   * @author admin
   */
  @Override
  public String getSourcePort() {
    return sourcePort;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param sourcePort sourcePort.
   * @author admin
   */
  @Override
  public void setSourcePort(final String sourcePort) {
    this.sourcePort = sourcePort;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String currentHost.
   * @author admin
   */
  @Override
  public String getCurrentHost() {
    return currentHost;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param currentHost currentHost.
   * @author admin
   */
  @Override
  public void setCurrentHost(final String currentHost) {
    this.currentHost = currentHost;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String currentIp.
   * @author admin
   */
  @Override
  public String getCurrentIp() {
    return currentIp;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param currentIp currentIp.
   * @author admin
   */
  @Override
  public void setCurrentIp(final String currentIp) {
    this.currentIp = currentIp;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String currentPort.
   * @author admin
   */
  @Override
  public String getCurrentPort() {
    return currentPort;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param currentPort currentPort.
   * @author admin
   */
  @Override
  public void setCurrentPort(final String currentPort) {
    this.currentPort = currentPort;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String destinationHost.
   * @author admin
   */
  @Override
  public String getDestinationHost() {
    return destinationHost;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param destinationHost destinationHost.
   * @author admin
   */
  @Override
  public void setDestinationHost(final String destinationHost) {
    this.destinationHost = destinationHost;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String destinationIp.
   * @author admin
   */
  @Override
  public String getDestinationIp() {
    return destinationIp;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param destinationIp destinationIp.
   * @author admin
   */
  @Override
  public void setDestinationIp(final String destinationIp) {
    this.destinationIp = destinationIp;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String destinationPort.
   * @author admin
   */
  @Override
  public String getDestinationPort() {
    return destinationPort;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param destinationPort destinationPort.
   * @author admin
   */
  @Override
  public void setDestinationPort(final String destinationPort) {
    this.destinationPort = destinationPort;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String spanId0.
   * @author admin
   */
  @Override
  public String getSpanId0() {
    return spanId0;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param spanId0 spanId0.
   * @author admin
   */
  @Override
  public void setSpanId0(final String spanId0) {
    this.spanId0 = spanId0;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String spanId1.
   * @author admin
   */
  @Override
  public String getSpanId1() {
    return spanId1;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param spanId1 spanId1.
   * @author admin
   */
  @Override
  public void setSpanId1(final String spanId1) {
    this.spanId1 = spanId1;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String traceId.
   * @author admin
   */
  @Override
  public String getTraceId() {
    return traceId;
  }

  /**
   * .
   *
   * <p>.
   *
   * @param traceId traceId.
   * @author admin
   */
  @Override
  public void setTraceId(final String traceId) {
    this.traceId = traceId;
  }
}
