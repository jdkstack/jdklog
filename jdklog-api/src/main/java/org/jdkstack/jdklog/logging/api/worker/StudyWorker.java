package org.jdkstack.jdklog.logging.api.worker;

import java.util.function.BooleanSupplier;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @param <E> the parameter of the class.
 * @author admin
 */
@FunctionalInterface
public interface StudyWorker<E> extends BooleanSupplier {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param event 任意对象.
   * @author admin
   */
  void handle(E event);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return boolean 函数式标准接口方法.
   * @author admin
   */
  @Override
  default boolean getAsBoolean() {
    return false;
  }
}
