package org.jdkstack.jdklog.examples.example2;

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

  private static final Log log = LogFactory.getLog(Examples2.class);

  public void main(final int i) {
    log.error("Examples>error>>>>我要去的日志文件是2study,当前的日志计数是:{}", String.valueOf(i));
    log.info("Examples>info>>>>我要去的日志文件是2study,当前的日志计数是:{}", String.valueOf(i));
    log.warn("Examples>warn>>>>我要去的日志文件是2study,当前的日志计数是:{}", String.valueOf(i));
    log.fatal("Examples>fatal>>>>我要去的日志文件是2study,当前的日志计数是:{}", String.valueOf(i));
    log.debug("Examples>debug>>>>我要去的日志文件是2study,当前的日志计数是:{}", String.valueOf(i));
    log.trace("Examples>trace>>>>我要去的日志文件是2study,当前的日志计数是:{}", String.valueOf(i));
  }
}
