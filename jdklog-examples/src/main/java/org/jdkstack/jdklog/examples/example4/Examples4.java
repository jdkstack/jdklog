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
public class Examples4 {

  private static final Log log = LogFactory.getLog(Examples4.class);

  public void main(final int i) {
    log.error("Examples4>error>>>>我要去的日志文件是4study,当前的日志计数是:{}", String.valueOf(i));
    log.info("Examples4>info>>>>我要去的日志文件是4study,当前的日志计数是:{}", String.valueOf(i));
    log.warn("Examples4>warn>>>>我要去的日志文件是4study,当前的日志计数是:{}", String.valueOf(i));
    log.fatal("Examples4>fatal>>>>我要去的日志文件是4study,当前的日志计数是:{}", String.valueOf(i));
    log.debug("Examples4>debug>>>>我要去的日志文件是4study,当前的日志计数是:{}", String.valueOf(i));
    log.trace("Examples4>trace>>>>我要去的日志文件是4study,当前的日志计数是:{}", String.valueOf(i));
  }
}
