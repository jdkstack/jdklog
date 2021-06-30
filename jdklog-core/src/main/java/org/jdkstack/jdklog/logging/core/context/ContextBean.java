package org.jdkstack.jdklog.logging.core.context;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jdkstack.jdklog.logging.api.context.Bean;
import org.jdkstack.jdklog.logging.api.metainfo.Constants;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class ContextBean implements Bean {
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

  /** . */
  private final Map<String, String> customs = new LinkedHashMap<>(Constants.MAP_CAPACITY);

  /**
   * .
   *
   * <p>.
   *
   * @author admin
   */
  public ContextBean() {
    //
  }

  /**
   * .
   *
   * <p>.
   *
   * @return String String.
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
   * .
   *
   * <p>.
   *
   * @return String String.
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
   * .
   *
   * <p>.
   *
   * @return String String.
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
   * .
   *
   * <p>.
   *
   * @return String String.
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
   * .
   *
   * <p>.
   *
   * @return String String.
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
   * .
   *
   * <p>.
   *
   * @return String String.
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
   * .
   *
   * <p>.
   *
   * @return String String.
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
   * .
   *
   * <p>.
   *
   * @return String String.
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
   * .
   *
   * <p>.
   *
   * @return String String.
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
   * .
   *
   * <p>.
   *
   * @return String String.
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
   * .
   *
   * <p>.
   *
   * @return String String.
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
   * .
   *
   * <p>.
   *
   * @return String String.
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

  /**
   * .
   *
   * <p>.
   *
   * @param key key.
   * @param value value.
   * @author admin
   */
  @Override
  public final void setCustom(final String key, final String value) {
    this.customs.put(key, value);
  }

  /**
   * .
   *
   * <p>.
   *
   * @return Map String.
   * @author admin
   */
  @Override
  public Map<String, String> getCustoms() {
    return Collections.unmodifiableMap(customs);
  }
}
