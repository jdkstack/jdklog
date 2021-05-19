package org.jdklog.logging.core.handler;

import org.jdklog.logging.api.handler.Handler;
import org.jdklog.logging.api.worker.StudyWorker;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ProducerNoticeConsumerWorker implements StudyWorker<Handler> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void handle(final Handler handler) {
    handler.flush();
  }
}
