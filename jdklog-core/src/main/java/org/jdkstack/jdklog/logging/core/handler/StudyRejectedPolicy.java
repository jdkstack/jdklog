package org.jdkstack.jdklog.logging.core.handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.jdkstack.jdklog.logging.api.metainfo.Constants;
import org.jdkstack.jdklog.logging.core.manager.LogManagerUtils;

/**
 * 线程池任务队列背压策略.
 *
 * <p>当前策略会阻塞上游线程,并利用上游线程执行当前任务.
 *
 * @author admin
 */
public class StudyRejectedPolicy implements RejectedExecutionHandler {
  /** . */
  private final Lock lock = new ReentrantLock();
  /** . */
  private final Condition condition = this.lock.newCondition();

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
        this.executionTask(r, e);
      }
    }
  }

  private void executionTask(final Runnable r, final ThreadPoolExecutor e) {
    // 不在主线执行任务,但是可以在主线程上阻塞,将任务重新加入到队列.
    final BlockingQueue<Runnable> queue = e.getQueue();
    // 循环执行,直到返回true为止.
    while (!queue.offer(r) && !Thread.currentThread().isInterrupted()) {
      this.lock.lock();
      try {
        // 每次等5毫秒. 没有任何地方调用线程唤醒方法,当前等待方法自动释放. await和signal需要配套使用.
        final boolean flag = this.condition.await(Constants.LOOP_COUNT, TimeUnit.MILLISECONDS);
        if (flag) {
          //
        }
      } catch (final InterruptedException interruptedException) {
        Thread.currentThread().interrupt();
      } finally {
        this.lock.unlock();
      }
    }
  }
}
