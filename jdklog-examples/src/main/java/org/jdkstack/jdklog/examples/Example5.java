package org.jdkstack.jdklog.examples;

import org.jdkstack.jdklog.logging.api.spi.Log;
import org.jdkstack.jdklog.logging.core.factory.LogFactory;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public final class Example5 {

  private Example5() {
    //
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public static class Examples5 {
    /** . */
    private static final Log LOG = LogFactory.getLog(Examples5.class);

    /**
     * This is a method description.
     *
     * <p>Another description after blank line.
     *
     * @param i i.
     * @author admin
     */
    public final void logMain(final int i) {
      LOG.error("Examples5>error>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
      LOG.info("Examples5>info>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
      LOG.warn("Examples5>warn>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
      LOG.fatal("Examples5>fatal>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
      LOG.debug("Examples5>debug>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
      LOG.trace("Examples5>trace>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    }
  }
}
