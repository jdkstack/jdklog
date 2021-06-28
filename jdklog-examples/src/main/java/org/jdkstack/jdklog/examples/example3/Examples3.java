package org.jdkstack.jdklog.examples.example3;

import org.jdkstack.jdklog.logging.api.spi.Log;
import org.jdkstack.jdklog.logging.core.factory.LogFactory;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class Examples3 {

  private static final Log log = LogFactory.getLog(Examples3.class);

  public void main(final int i) {
    log.error("Examples3>error>>>>我要去的日志文件是3study,当前的日志计数是:{}", String.valueOf(i));
    log.info("Examples3>info>>>>我要去的日志文件是3study,当前的日志计数是:{}", String.valueOf(i));
    log.warn("Examples3>warn>>>>我要去的日志文件是3study,当前的日志计数是:{}", String.valueOf(i));
    log.fatal("Examples3>fatal>>>>我要去的日志文件是3study,当前的日志计数是:{}", String.valueOf(i));
    log.debug("Examples3>debug>>>>我要去的日志文件是3study,当前的日志计数是:{}", String.valueOf(i));
    log.trace("Examples3>trace>>>>我要去的日志文件是3study,当前的日志计数是:{}", String.valueOf(i));
    log.error(
        "Examples>error>>>>我要去的日志文件是3study,当前的日志计数是:{}",
        String.valueOf(i),
        new RuntimeException("我要去的日志文件是3study"));
  }
}
