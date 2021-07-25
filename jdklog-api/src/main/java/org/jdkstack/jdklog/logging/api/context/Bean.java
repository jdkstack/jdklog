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
