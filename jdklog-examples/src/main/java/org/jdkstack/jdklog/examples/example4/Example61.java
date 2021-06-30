package org.jdkstack.jdklog.examples.example4;

import org.jdkstack.jdklog.logging.api.spi.Log;
import org.jdkstack.jdklog.logging.core.factory.LogFactory;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public final class Example61 {

  private Example61() {
    //
  }

  /**
   * This is a class description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public static class Examples6 {
    /** . */
    private static final Log LOG = LogFactory.getLog(Examples6.class);

    /**
     * This is a method description.
     *
     * <p>Another description after blank line.
     *
     * @param i i.
     * @author admin
     */
    public final void logMain(final int i) {
      LOG.error("Examples6>error>>>>我要去的日志文件是4study,当前的日志计数是:{}", " " + i);
      LOG.info("Examples6>info>>>>我要去的日志文件是4study,当前的日志计数是:{}", " " + i);
      LOG.warn("Examples6>warn>>>>我要去的日志文件是4study,当前的日志计数是:{}", " " + i);
      LOG.fatal("Examples6>fatal>>>>我要去的日志文件是4study,当前的日志计数是:{}", " " + i);
      LOG.debug("Examples6>debug>>>>我要去的日志文件是4study,当前的日志计数是:{}", " " + i);
      LOG.trace("Examples6>trace>>>>我要去的日志文件是4study,当前的日志计数是:{}", " " + i);
    }
  }
}
