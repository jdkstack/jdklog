package org.jdkstack.jdklog.logging.core.queue;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import org.jdkstack.jdklog.logging.api.queue.StudyQueue;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @param <T> 泛型对象.
 * @author admin
 */
public abstract class AbstractQueue<T> implements StudyQueue<T> {
  /** 双端链表阻塞队列,可以头尾操作. */
  private final LinkedBlockingDeque<T> queue;
  /** 队列初始容量默认5000. */
  private int capacity = Constants.CAPACITY;

  /**
   * 创建一个容量Integer.MAX_VALUE的队列.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  protected AbstractQueue() {
    this.queue = new LinkedBlockingDeque<>(this.capacity);
  }

  /**
   * 创建一个容量capacity的队列.
   *
   * <p>Another description after blank line.
   *
   * @param capacity 队列容量.
   * @author admin
   */
  protected AbstractQueue(final int capacity) {
    this.capacity = capacity;
    this.queue = new LinkedBlockingDeque<>(capacity);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return T .
   * @author admin
   */
  @Override
  public final T poll() {
    return this.queue.poll();
  }

  /**
   * 将数据快速放入列队中
   *
   * <p>Another description after blank line.
   *
   * @param logRecord .
   * @author admin
   */
  @Override
  public final void enqueue(final T logRecord) {
    try {
      // 使用阻塞方法将元素插入队列. 天然的背压方式,当队列满后阻塞.
      this.queue.put(logRecord);
    } catch (final InterruptedException interruptedException) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * 将数据快速放入列队中(等待一定时间)
   *
   * <p>Another description after blank line.
   *
   * @param t 放入队列中的元素.
   * @param timeout 最大等待时间.
   * @return true, 成功放入队列. false,队列满,放入失败
   * @author admin
   */
  @Override
  public final boolean isEnqueue(final T t, final long timeout) {
    boolean isSuccess = false;
    try {
      // 使用阻塞方法将元素插入队列.
      isSuccess = this.queue.offer(t, timeout, TimeUnit.MILLISECONDS);
    } catch (final InterruptedException e) {
      Thread.currentThread().interrupt();
    }
    return isSuccess;
  }

  /**
   * 实时获取队列元素数量.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final int size() {
    return this.queue.size();
  }
}
