package org.jdklog.examples.example2;

import org.jdklog.logging.api.spi.Log;
import org.jdklog.logging.core.factory.LogFactory;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class Examples8 {

  private static final Log log = LogFactory.getLog(Examples8.class);

  public void main(int i) {
    log.error("Examples>error>>>>我要去的日志文件是2example,当前的日志计数是:{}", "" + i);
    log.info("Examples>info>>>>我要去的日志文件是2example,当前的日志计数是:{}", "" + i);
    log.warn("Examples>warn>>>>我要去的日志文件是2example,当前的日志计数是:{}", "" + i);
    log.fatal("Examples>fatal>>>>我要去的日志文件是2example,当前的日志计数是:{}", "" + i);
    log.debug("Examples>debug>>>>我要去的日志文件是2example,当前的日志计数是:{}", "" + i);
    log.trace("Examples>trace>>>>我要去的日志文件是2example,当前的日志计数是:{}", "" + i);
  }
}
