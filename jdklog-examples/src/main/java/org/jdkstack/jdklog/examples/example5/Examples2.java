package org.jdkstack.jdklog.examples.example5;

import org.jdkstack.jdklog.logging.api.spi.Log;
import org.jdkstack.jdklog.logging.core.factory.LogFactory;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class Examples2 {
  /** . */
  private static final Log LOG = LogFactory.getLog(Examples2.class);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param i i.
   * @author admin
   */
  public void logMain(final int i) {
    LOG.error("Examples>error>>>>我要去的日志文件是5example,当前的日志计数是:{}", String.valueOf(i));
    LOG.info("Examples>info>>>>我要去的日志文件是5example,当前的日志计数是:{}", String.valueOf(i));
    LOG.warn("Examples>warn>>>>我要去的日志文件是5example,当前的日志计数是:{}", String.valueOf(i));
    LOG.fatal("Examples>fatal>>>>我要去的日志文件是5example,当前的日志计数是:{}", String.valueOf(i));
    LOG.debug("Examples>debug>>>>我要去的日志文件是5example,当前的日志计数是:{}", String.valueOf(i));
    LOG.trace("Examples>trace>>>>我要去的日志文件是5example,当前的日志计数是:{}", String.valueOf(i));
  }
}
