package org.jdkstack.jdklog.logging.api.context;

import org.jdkstack.jdklog.logging.api.worker.StudyWorker;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface StudyContext {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handler 处理器程序.
   * @param event 处理器事件.
   * @param <T> 处理器元素.
   * @author admin
   */
  <T> void dispatch(T event, StudyWorker<T> handler);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handler 处理业务的Runnable.
   * @author admin
   */
  void dispatch(Runnable handler);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void beginDispatch();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void endDispatch();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param contextBean 线程上下文数据.
   * @param handler 处理器程序.
   * @param event 处理器事件.
   * @param <T> 处理器元素.
   * @author admin
   */
  <T> void dispatchV2(Bean contextBean, T event, StudyWorker<T> handler);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param contextBean 线程上下文数据.
   * @author admin
   */
  void beginDispatchV2(Bean contextBean);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void endDispatchV2();
}
