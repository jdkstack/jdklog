package org.jdklog.logging.api.queue;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @param <T> 泛型对象.
 * @author admin
 */
public interface StudyQueue<T> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return 返回队列中元素的数量.
   * @author admin
   */
  int size();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return T .
   * @author admin
   */
  T poll();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param t t.
   * @param timeout timeout.
   * @return boolean boolean.
   * @author admin
   */
  boolean isEnqueue(T t, long timeout);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord logRecord.
   * @author admin
   */
  void enqueue(T logRecord);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String String.
   * @author admin
   */
  String getTarget();
}
