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
public class Examples2 {

  private static final Log log = LogFactory.getLog(Examples2.class);

  public void main(int i) {
    log.error("Examples>error>>>>我要去的日志文件是3example,当前的日志计数是:{}", "" + i);
    log.info("Examples>info>>>>我要去的日志文件是3example,当前的日志计数是:{}", "" + i);
    log.warn("Examples>warn>>>>我要去的日志文件是3example,当前的日志计数是:{}", "" + i);
    log.fatal("Examples>fatal>>>>我要去的日志文件是3example,当前的日志计数是:{}", "" + i);
    log.debug("Examples>debug>>>>我要去的日志文件是3example,当前的日志计数是:{}", "" + i);
    log.trace("Examples>trace>>>>我要去的日志文件是3example,当前的日志计数是:{}", "" + i);
  }
}
