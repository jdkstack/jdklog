package org.jdklog.examples.example6;

import org.jdklog.logging.api.spi.Log;
import org.jdklog.logging.core.factory.LogFactory;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class Example6 {

  private static final Log log = LogFactory.getLog(Example6.class);

  public void main(int i) {
    log.error("Ex9>error>>>>我要去的日志文件是study_juli,当前的日志计数是:{}", " " + i);
    log.info("Ex9>info>>>>我要去的日志文件是study_juli,当前的日志计数是:{}", " " + i);
    log.warn("Ex9>warn>>>>我要去的日志文件是study_juli,当前的日志计数是:{}", " " + i);
    log.fatal("Ex9>fatal>>>>我要去的日志文件是study_juli,当前的日志计数是:{}", " " + i);
    log.debug("Ex9>debug>>>>我要去的日志文件是study_juli,当前的日志计数是:{}", " " + i);
    log.trace("Ex9>trace>>>>我要去的日志文件是study_juli,当前的日志计数是:{}", " " + i);
  }
}
