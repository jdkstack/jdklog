package org.jdkstack.jdklog.logging.core.handler;

import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.api.queue.StudyQueue;
import org.jdkstack.jdklog.logging.api.worker.StudyWorker;
import org.jdkstack.jdklog.logging.core.queue.ProducerWorker;

public abstract class AbstractExecute extends AbstractMetric implements Execute {
  /** . */
  protected final StudyQueue<Record> queue;
  /** 生产日志处理器. */
  protected final StudyWorker<Record> producerWorker;
  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param prefix prefix.
   * @author admin
   */
  protected AbstractExecute(final StudyQueue<Record> queue) {
    this.queue = queue;
    this.producerWorker = new ProducerWorker(this.queue);
  }
}
