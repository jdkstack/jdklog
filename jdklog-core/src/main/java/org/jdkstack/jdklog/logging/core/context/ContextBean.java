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
