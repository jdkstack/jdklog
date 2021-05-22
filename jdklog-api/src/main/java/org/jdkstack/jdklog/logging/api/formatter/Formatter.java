package org.jdkstack.jdklog.logging.api.formatter;

import java.util.function.BooleanSupplier;
import org.jdkstack.jdklog.logging.api.metainfo.Record;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
@FunctionalInterface
public interface Formatter extends BooleanSupplier {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord 日志对象.
   * @return s.
   * @author admin
   */
  String format(Record logRecord);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return s.
   * @author admin
   */
  @Override
  default boolean getAsBoolean() {
    return false;
  }
}
