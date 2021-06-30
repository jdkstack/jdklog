package org.jdkstack.jdklog.examples.example;

import org.jdkstack.jdklog.logging.api.spi.Log;
import org.jdkstack.jdklog.logging.core.factory.LogFactory;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class Test {
  /** . */
  private static final Log LOG = LogFactory.getLog(Test.class);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param i i.
   * @author admin
   */
  public final void logMain(final int i) {
    LOG.error("Test>error>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    LOG.info("Test>info>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    LOG.warn("Test>warn>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    LOG.fatal("Test>fatal>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    LOG.debug("Test>debug>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    LOG.trace("Test>trace>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
  }
}
