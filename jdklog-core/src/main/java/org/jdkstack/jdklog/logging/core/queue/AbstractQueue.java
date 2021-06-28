package org.jdkstack.jdklog.logging.core.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.jdkstack.jdklog.logging.api.queue.StudyQueue;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @param <T> 泛型对象.
 * @author admin
 */
public abstract class AbstractQueue<T> implements StudyQueue<T> {
  /** 有界数组阻塞队列,为了避免垃圾回收,采用数组而非链表Queue/Dqueue,数组对处理器的缓存机制更加友好. */
  private final BlockingQueue<T> queue;
  /** 队列初始容量默认2000. */
  private int capacity = Constants.CAPACITY;

  /**
   * 创建一个容量Integer.MAX_VALUE的队列.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  protected AbstractQueue() {
    this.queue = new ArrayBlockingQueue<>(this.capacity);
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
    this.queue = new ArrayBlockingQueue<>(capacity);
  }

  /**
   * 消费端采用poll方法,非阻塞,队列为空时返回空对象.
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
   * 将数据快速放入列队中,采用阻塞方法put.
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
