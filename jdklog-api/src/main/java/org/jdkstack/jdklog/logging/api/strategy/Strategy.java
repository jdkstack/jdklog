package org.jdkstack.jdklog.logging.api.strategy;

import java.io.File;
import org.jdkstack.jdklog.logging.api.handler.Handler;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface Strategy {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void execute();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param source 处理程序.
   * @param destination 处理程序.
   * @author admin
   */
  void execute(File source, File destination);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handler 处理程序.
   * @author admin
   */
  void executeByHandler(Handler handler);
}
