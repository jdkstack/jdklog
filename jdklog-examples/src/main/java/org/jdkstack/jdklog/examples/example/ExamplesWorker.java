package org.jdkstack.jdklog.examples.example;

import org.jdkstack.jdklog.examples.Example5.Examples5;
import org.jdkstack.jdklog.examples.example1.Test1;
import org.jdkstack.jdklog.examples.example2.Examples1;
import org.jdkstack.jdklog.examples.example2.Test2;
import org.jdkstack.jdklog.examples.example3.Examples3;
import org.jdkstack.jdklog.examples.example3.Test3;
import org.jdkstack.jdklog.examples.example4.Examples4;
import org.jdkstack.jdklog.examples.example4.Test4;
import org.jdkstack.jdklog.examples.example4.example6.Examples6;
import org.jdkstack.jdklog.examples.example5.Test5;
import org.jdkstack.jdklog.examples.example6.Example6;
import org.jdkstack.jdklog.logging.api.spi.Log;
import org.jdkstack.jdklog.logging.api.worker.StudyWorker;
import org.jdkstack.jdklog.logging.core.factory.LogFactory;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ExamplesWorker implements StudyWorker<Integer> {
  /** . */
  private static final Log LOG = LogFactory.getLog(ExamplesWorker.class);
  /** . */
  private final Examples1 examples2 = new Examples1();
  /** . */
  private final Examples3 examples3 = new Examples3();
  /** . */
  private final Examples4 examples4 = new Examples4();
  /** . */
  private final Test test = new Test();
  /** . */
  private final Examples5 examples5 = new Examples5();
  /** . */
  private final Examples6 examples6 = new Examples6();
  /** . */
  private final Example6 example6 = new Example6();
  /** . */
  private final Test1 test1 = new Test1();
  /** . */
  private final Test2 test2 = new Test2();
  /** . */
  private final Test3 test3 = new Test3();
  /** . */
  private final Test4 test4 = new Test4();
  /** . */
  private final Test5 test5 = new Test5();

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param i .
   * @author admin
   */
  @Override
  public void handle(final Integer i) {
    // 输入到日志1example文件.
    test1.test(i);
    // 输入到日志2example文件.
    test2.test(i);
    // 输入到日志3example文件.
    test3.test(i);
    // 输入到日志4example文件.
    test4.test(i);
    // 输入到日志5example文件.
    test5.test(i);
    // 输入到日志2study文件.
    examples2.main(i);
    // 输入到日志3study文件.
    examples3.main(i);
    // 输入到日志4study文件.
    examples4.main(i);
    // 输入到日志2study文件.
    test.main(i);
    // 输入到日志2study文件.
    examples5.main(i);
    // 输入到日志4study文件.
    examples6.main(i);
    // 输入到日志study_juli文件.
    example6.main(i);
    // 输入到日志1study文件.
    LOG.error("Examples>error>>>>我要去的日志文件是1study,当前的日志计数是:{}", " " + i);
    LOG.info("Examples>info>>>>我要去的日志文件是1study,当前的日志计数是:{}", " " + i);
    LOG.warn("Examples>warn>>>>我要去的日志文件是1study,当前的日志计数是:{}", " " + i);
    LOG.fatal("Examples>fatal>>>>我要去的日志文件是1study,当前的日志计数是:{}", " " + i);
    LOG.debug("Examples>debug>>>>我要去的日志文件是1study,当前的日志计数是:{}", " " + i);
    LOG.trace("Examples>trace>>>>我要去的日志文件是1study,当前的日志计数是:{}", " " + i);
  }
}
