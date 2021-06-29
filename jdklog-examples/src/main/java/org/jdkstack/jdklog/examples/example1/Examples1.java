package org.jdkstack.jdklog.examples.example1;

import org.jdkstack.jdklog.logging.api.spi.Log;
import org.jdkstack.jdklog.logging.core.factory.LogFactory;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class Examples1 {

  private static final Log LOG = LogFactory.getLog(Examples1.class);

  public void main(final int i) {
    LOG.error("Examples>error>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    LOG.info("Examples>info>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    LOG.warn("Examples>warn>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    LOG.fatal("Examples>fatal>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    LOG.debug("Examples>debug>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    LOG.trace("Examples>trace>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
  }
}
