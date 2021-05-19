package org.jdklog.logging.core.queue;

import org.jdklog.logging.api.metainfo.Record;
import org.jdklog.logging.api.queue.StudyQueue;
import org.jdklog.logging.api.worker.StudyWorker;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ProducerWorker implements StudyWorker<Record> {
  /** . */
  private final StudyQueue<? super Record> studyQueue;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param studyQueue 放入队列中的元素.
   * @author admin
   */
  public ProducerWorker(final StudyQueue<? super Record> studyQueue) {
    this.studyQueue = studyQueue;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord 放入队列中的元素.
   * @author admin
   */
  @Override
  public final void handle(final Record logRecord) {
    this.studyQueue.enqueue(logRecord);
  }
}
