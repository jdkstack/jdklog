package org.jdkstack.jdklog.logging.api.context;

import java.util.Map;

/**
 * .
 *
 * <p>.
 *
 * <p>.
 *
 * @author admin
 */
public interface Bean {

  /**
   * .
   *
   * <p>.
   *
   * @return String String.
   * @author admin
   */
  String getSpanId0();

  /**
   * .
   *
   * <p>.
   *
   * @param spanId0 spanId0.
   * @author admin
   */
  void setSpanId0(String spanId0);

  /**
   * .
   *
   * <p>.
   *
   * @return String String.
   * @author admin
   */
  String getSpanId1();

  /**
   * .
   *
   * <p>.
   *
   * @param spanId1 spanId1.
   * @author admin
   */
  void setSpanId1(String spanId1);

  /**
   * .
   *
   * <p>.
   *
   * @return String String.
   * @author admin
   */
  String getTraceId();

  /**
   * .
   *
   * <p>.
   *
   * @param traceId traceId.
   * @author admin
   */
  void setTraceId(String traceId);

  /**
   * .
   *
   * <p>.
   *
   * @param key key.
   * @param value value.
   * @author admin
   */
  void setCustom(String key, String value);

  /**
   * .
   *
   * <p>.
   *
   * @return Map .
   * @author admin
   */
  Map<String, String> getCustoms();
}
