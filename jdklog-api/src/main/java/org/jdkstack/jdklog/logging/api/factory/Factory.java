package org.jdkstack.jdklog.logging.api.factory;

import java.util.function.BooleanSupplier;
import org.jdkstack.jdklog.logging.api.spi.Log;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
@FunctionalInterface
public interface Factory extends BooleanSupplier {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name 使用简单名称作为日志的标志.
   * @return 返回一个Log对象.
   * @author admin
   */
  Log getInstance(String name);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return boolean .
   * @author admin
   */
  @Override
  default boolean getAsBoolean() {
    return false;
  }
}
