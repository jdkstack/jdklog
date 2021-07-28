package org.jdkstack.jdklog.logging.core.context;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jdkstack.jdklog.logging.api.context.Bean;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public class ContextBean implements Bean {
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
