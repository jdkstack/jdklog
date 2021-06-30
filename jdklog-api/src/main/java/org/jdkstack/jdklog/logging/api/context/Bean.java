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
  String getSourceHost();

  /**
   * .
   *
   * <p>.
   *
   * @param sourceHost sourceHost.
   * @author admin
   */
  void setSourceHost(String sourceHost);

  /**
   * .
   *
   * <p>.
   *
   * @return String String.
   * @author admin
   */
  String getSourceIp();

  /**
   * .
   *
   * <p>.
   *
   * @param sourceIp sourceIp.
   * @author admin
   */
  void setSourceIp(String sourceIp);

  /**
   * .
   *
   * <p>.
   *
   * @return String String.
   * @author admin
   */
  String getSourcePort();

  /**
   * .
   *
   * <p>.
   *
   * @param sourcePort sourcePort.
   * @author admin
   */
  void setSourcePort(String sourcePort);

  /**
   * .
   *
   * <p>.
   *
   * @return String String.
   * @author admin
   */
  String getCurrentHost();

  /**
   * .
   *
   * <p>.
   *
   * @param currentHost currentHost.
   * @author admin
   */
  void setCurrentHost(String currentHost);

  /**
   * .
   *
   * <p>.
   *
   * @return String String.
   * @author admin
   */
  String getCurrentIp();

  /**
   * .
   *
   * <p>.
   *
   * @param currentIp currentIp.
   * @author admin
   */
  void setCurrentIp(String currentIp);

  /**
   * .
   *
   * <p>.
   *
   * @return String String.
   * @author admin
   */
  String getCurrentPort();

  /**
   * .
   *
   * <p>.
   *
   * @param currentPort currentPort.
   * @author admin
   */
  void setCurrentPort(String currentPort);

  /**
   * .
   *
   * <p>.
   *
   * @return String String.
   * @author admin
   */
  String getDestinationHost();

  /**
   * .
   *
   * <p>.
   *
   * @param destinationHost destinationHost.
   * @author admin
   */
  void setDestinationHost(String destinationHost);

  /**
   * .
   *
   * <p>.
   *
   * @return String String.
   * @author admin
   */
  String getDestinationIp();

  /**
   * .
   *
   * <p>.
   *
   * @param destinationIp destinationIp.
   * @author admin
   */
  void setDestinationIp(String destinationIp);

  /**
   * .
   *
   * <p>.
   *
   * @return String String.
   * @author admin
   */
  String getDestinationPort();

  /**
   * .
   *
   * <p>.
   *
   * @param destinationPort destinationPort.
   * @author admin
   */
  void setDestinationPort(String destinationPort);

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
