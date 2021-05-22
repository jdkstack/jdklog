package org.jdkstack.jdklog.logging.core.queue;

import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.api.queue.StudyQueue;

/**
 * 生产者线程任务.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ProducerRunnable implements Runnable {
  /** . */
  private final StudyQueue<? super Record> studyQueue;
  /** 生产一个元素. */
  private final Record logRecord;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord 放入队列中的元素.
   * @param studyQueue studyQueue.
   * @author admin
   */
  ProducerRunnable(final Record logRecord, final StudyQueue<? super Record> studyQueue) {
    this.logRecord = logRecord;
    this.studyQueue = studyQueue;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void run() {
    this.studyQueue.enqueue(this.logRecord);
  }
}
