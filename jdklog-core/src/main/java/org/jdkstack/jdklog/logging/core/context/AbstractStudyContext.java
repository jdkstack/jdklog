package org.jdkstack.jdklog.logging.core.context;

import org.jdkstack.jdklog.logging.api.context.StudyContext;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;
import org.jdkstack.jdklog.logging.api.worker.StudyWorker;

/**
 * 一个抽象的上下文对象,主要提供一些方法,用于监控线程的运行情况.
 *
 * <p>一般来说,上下文对象主要保存一些系统信息.
 *
 * @author admin
 */
public abstract class AbstractStudyContext implements StudyContext {

  /**
   * 用于在执行方法前后增加开始时间和结束时间.
   *
   * <p>用于监控方法的执行时间.
   *
   * @param event 作为handler的参数传递.
   * @param handler 一个处理器,用于执行具体业务逻辑.
   * @author admin
   */
  @Override
  public final <T> void dispatch(final T event, final StudyWorker<T> handler) {
    try {
      // 增加开始时间.
      this.beginDispatch();
      // 执行具体业务,传递event参数.
      handler.handle(event);
    } catch (final Exception e) {
      throw new StudyJuliRuntimeException(e);
    } finally {
      // 增加结束时间.
      this.endDispatch();
    }
  }

  /**
   * 用于在执行方法前后增加开始时间和结束时间.
   *
   * <p>用于监控方法的执行时间.
   *
   * @param handler 一个Runnable任务,用于执行具体业务逻辑.
   * @author admin
   */
  @Override
  public final void dispatch(final Runnable handler) {
    try {
      // 增加开始时间.
      this.beginDispatch();
      // 执行具体业务.
      handler.run();
    } catch (final Exception e) {
      throw new StudyJuliRuntimeException(e);
    } finally {
      // 增加结束时间.
      this.endDispatch();
    }
  }

  /**
   * 首先判断,当前线程是不是StudyThread.
   *
   * <p>不处理main线程.
   *
   * @author admin
   */
  @Override
  public final void beginDispatch() {
    // 获取当前线程.
    final Thread thread = Thread.currentThread();
    // 当前线程是不是StudyThread.
    if (thread instanceof StudyThreadImpl) {
      // 得到当前真实线程.
      final StudyThreadImpl th = (StudyThreadImpl) thread;
      // 设置开始时间和当前上下文对象.
      th.beginEmission(this);
    }
  }

  /**
   * 首先判断,当前线程是不是StudyThread.
   *
   * <p>不处理main线程.
   *
   * @author admin
   */
  @Override
  public final void endDispatch() {
    // 获取当前线程.
    final Thread thread = Thread.currentThread();
    // 当前线程是不是StudyThread.
    if (thread instanceof StudyThreadImpl) {
      // 得到当前真实线程.
      final StudyThreadImpl th = (StudyThreadImpl) thread;
      // 设置结束时间和将前上下文对象设置成空.
      th.endEmission();
    }
  }

  /**
   * 用于在执行方法前后增加开始时间和结束时间.
   *
   * <p>用于监控方法的执行时间.
   *
   * @param unique .
   * @param event 作为handler的参数传递.
   * @param handler 一个处理器,用于执行具体业务逻辑.
   * @author admin
   */
  @Override
  public final <T> void dispatchV2(
      final String unique, final T event, final StudyWorker<T> handler) {
    try {
      // 增加开始时间.
      this.beginDispatchV2(unique);
      // 执行具体业务,传递event参数.
      handler.handle(event);
    } catch (final Exception e) {
      throw new StudyJuliRuntimeException(e);
    } finally {
      // 增加结束时间.
      this.endDispatchV2();
    }
  }

  /**
   * 首先判断,当前线程是不是StudyThread.
   *
   * <p>不处理main线程.
   *
   * @author admin
   */
  @Override
  public final void beginDispatchV2(final String unique) {
    // 获取当前线程.
    final Thread thread = Thread.currentThread();
    // 当前线程是不是StudyThread.
    if (thread instanceof StudyThreadImpl) {
      // 得到当前真实线程.
      final StudyThreadImpl th = (StudyThreadImpl) thread;
      // 设置开始时间和当前上下文对象.
      th.beginEmissionV2(unique, this);
    }
  }

  /**
   * 首先判断,当前线程是不是StudyThread.
   *
   * <p>不处理main线程.
   *
   * @author admin
   */
  @Override
  public final void endDispatchV2() {
    // 获取当前线程.
    final Thread thread = Thread.currentThread();
    // 当前线程是不是StudyThread.
    if (thread instanceof StudyThreadImpl) {
      // 得到当前真实线程.
      final StudyThreadImpl th = (StudyThreadImpl) thread;
      // 设置结束时间和将前上下文对象设置成空.
      th.endEmissionV2();
    }
  }

  /**
   * 在当前上下文上运行.
   *
   * <p>.
   *
   * @param handler 处理任务.
   * @param <T> .
   * @author admin
   */
  abstract <T> void runAsContext(StudyWorker<T> handler);
}
