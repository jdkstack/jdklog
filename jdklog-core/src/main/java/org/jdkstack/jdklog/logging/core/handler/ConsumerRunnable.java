package org.jdkstack.jdklog.logging.core.handler;

import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.metainfo.Constants;
import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.api.queue.StudyQueue;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ConsumerRunnable implements Runnable {
  /** . */
  private final StudyQueue<Record> studyQueue;
  /** . */
  private final Handler handler;

  /**
   * 在执行业务之前,进行检查.
   *
   * <p>Another description after blank line.
   *
   * @param handler handler.
   * @param studyQueue studyQueue.
   * @author admin
   */
  public ConsumerRunnable(final StudyQueue<Record> studyQueue, final Handler handler) {
    this.studyQueue = studyQueue;
    this.handler = handler;
  }

  /**
   * 在执行业务之前,进行检查.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void run() {
    // 重新获取队列元素数.
    final int size = this.studyQueue.size();
    // 如果队列为空,不执行业务.
    if (0 != size) {
      // 如果元素数大于flushCount(默认100),则每次获取100条.否则直接获取全部元素.
      final int min = Math.min(size, Constants.FLUSH_COUNT);
      this.handler.process(min);
    }
  }
}
