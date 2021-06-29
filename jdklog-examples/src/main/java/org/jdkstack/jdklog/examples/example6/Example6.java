package org.jdkstack.jdklog.examples.example6;

import org.jdkstack.jdklog.logging.api.spi.Log;
import org.jdkstack.jdklog.logging.core.factory.LogFactory;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class Example6 {

  private static final Log LOG = LogFactory.getLog(Example6.class);

  public void main(final int i) {
    LOG.error("Ex9>error>>>>我要去的日志文件是study_juli,当前的日志计数是:{}", " " + i);
    LOG.info("Ex9>info>>>>我要去的日志文件是study_juli,当前的日志计数是:{}", " " + i);
    LOG.warn("Ex9>warn>>>>我要去的日志文件是study_juli,当前的日志计数是:{}", " " + i);
    LOG.fatal("Ex9>fatal>>>>我要去的日志文件是study_juli,当前的日志计数是:{}", " " + i);
    LOG.debug("Ex9>debug>>>>我要去的日志文件是study_juli,当前的日志计数是:{}", " " + i);
    LOG.trace("Ex9>trace>>>>我要去的日志文件是study_juli,当前的日志计数是:{}", " " + i);
  }
}
