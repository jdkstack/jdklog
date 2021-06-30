package org.jdkstack.jdklog.logging.api.context;

import java.util.concurrent.ScheduledExecutorService;
import org.jdkstack.jdklog.logging.api.worker.StudyWorker;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface WorkerContext {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param task 任务.
   * @author admin
   */
  void executeInExecutorService(Runnable task);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param event 处理对象.
   * @param handler 处理器.
   * @param <T> 传入handler的元素.
   * @author admin
   */
  <T> void executeInExecutorService(T event, StudyWorker<T> handler);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return ScheduledExecutorService.
   * @author admin
   */
  ScheduledExecutorService getScheduledExecutorService();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param contextBean 线程上下文数据.
   * @param event 处理对象.
   * @param handler 处理器.
   * @param <T> 传入handler的元素.
   * @author admin
   */
  <T> void executeInExecutorServiceV2(Bean contextBean, T event, StudyWorker<T> handler);
}
