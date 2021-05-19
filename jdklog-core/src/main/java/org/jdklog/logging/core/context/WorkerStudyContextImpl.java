package org.jdklog.logging.core.context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import org.jdklog.logging.api.context.WorkerContext;
import org.jdklog.logging.api.worker.StudyWorker;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class WorkerStudyContextImpl extends AbstractStudyContext implements WorkerContext {

  /** . */
  private final ExecutorService executorService;
  /** . */
  private final ScheduledExecutorService scheduledExecutorService;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param executorService 执行任务的线程池.
   * @param scheduledExecutorService 定时调度的线程池.
   * @author admin
   */
  public WorkerStudyContextImpl(
      final ExecutorService executorService,
      final ScheduledExecutorService scheduledExecutorService) {
    this.executorService = executorService;
    this.scheduledExecutorService = scheduledExecutorService;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void executeInExecutorService(final Runnable task) {
    this.executorService.execute(task);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final <T> void executeInExecutorService(final T event, final StudyWorker<T> handler) {
    final Runnable task = () -> this.dispatch(event, handler);
    this.executorService.submit(task);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final ScheduledExecutorService getScheduledExecutorService() {
    return this.scheduledExecutorService;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param unique .
   * @param event .
   * @param handler .
   * @author admin
   */
  @Override
  public final <T> void executeInExecutorServiceV2(
      final String unique, final T event, final StudyWorker<T> handler) {
    final Runnable task = () -> this.dispatchV2(unique, event, handler);
    this.executorService.submit(task);
  }

  @Override
  <T> void runAsContext(final StudyWorker<T> handler) {
    //
  }
}
