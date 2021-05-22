package org.jdkstack.jdklog.logging.core.strategy;

import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.strategy.Strategy;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public abstract class AbstractStrategy implements Strategy {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void executeByHandler(final Handler handler) {
    //
    throw new UnsupportedOperationException("方法没有实现.");
  }
}
