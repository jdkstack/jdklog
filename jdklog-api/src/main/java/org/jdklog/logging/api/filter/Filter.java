package org.jdklog.logging.api.filter;

import java.util.function.BooleanSupplier;
import org.jdklog.logging.api.metainfo.Record;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
@FunctionalInterface
public interface Filter extends BooleanSupplier {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord 日志对象.
   * @return b.
   * @author admin
   */
  boolean isLoggable(Record logRecord);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return b.
   * @author admin
   */
  @Override
  default boolean getAsBoolean() {
    return false;
  }
}
