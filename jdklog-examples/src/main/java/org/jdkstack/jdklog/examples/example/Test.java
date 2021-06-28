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

  private static final Log log = LogFactory.getLog(Test.class);

  public void main(final int i) {
    log.error("Test>error>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    log.info("Test>info>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    log.warn("Test>warn>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    log.fatal("Test>fatal>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    log.debug("Test>debug>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
    log.trace("Test>trace>>>>我要去的日志文件是1study,当前的日志计数是:{}", String.valueOf(i));
  }
}
