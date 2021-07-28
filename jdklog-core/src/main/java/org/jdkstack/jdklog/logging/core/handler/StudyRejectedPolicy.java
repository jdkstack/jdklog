package org.jdkstack.jdklog.logging.core.handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.LockSupport;
import org.jdkstack.jdklog.logging.core.manager.LogManagerUtils;

/**
 * 线程池任务队列背压策略.
 *
 * <p>当前策略会阻塞上游线程,并利用上游线程执行当前任务.
 *
 * @author admin
 */
public class StudyRejectedPolicy implements RejectedExecutionHandler {

  /**
   * A handler for rejected tasks that runs the rejected task directly in the calling thread of the
   * execute method, unless the executor has been shut down, in which case the task is discarded.
   *
   * <p>Another description after blank line.
   *
   * @param r .
   * @param e .
   * @author admin
   */
  @Override
  public final void rejectedExecution(final Runnable r, final ThreadPoolExecutor e) {
    // 如果线程池关闭了,直接返回.
    if (!e.isShutdown()) {
      // 获取日志唯一ID的全局配置.
      final String unique = LogManagerUtils.getProperty(Constants.UNIQUE, Constants.FALSE);
      if (unique.equals(Constants.FALSE)) {
        // 在主线执行任务.背压方式使用主线程执行无法放入队列的任务.
        r.run();
      } else {
        // 不在主线执行任务,但是可以在主线程上阻塞,将任务重新加入到队列.
        final BlockingQueue<Runnable> queue = e.getQueue();
        // 循环执行,直到返回true为止.
        while (!queue.offer(r) && !Thread.currentThread().isInterrupted()) {
          LockSupport.parkNanos(Constants.LOOP_COUNT);
        }
      }
    }
  }
}
