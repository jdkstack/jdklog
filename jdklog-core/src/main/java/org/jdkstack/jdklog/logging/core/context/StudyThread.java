package org.jdkstack.jdklog.logging.core.context;

import java.util.concurrent.TimeUnit;
import org.jdkstack.jdklog.logging.api.context.Bean;
import org.jdkstack.jdklog.logging.api.context.StudyContext;
import org.jdkstack.jdklog.logging.api.context.StudyThreadImpl;

/**
 * 自定义线程,便于系统内线程的监控.
 *
 * <p>比如设置自定义的线程名,线程计数等.
 *
 * <p>Class directly extends Thread 这个约束暂时无法解决.
 *
 * @author admin
 */
public final class StudyThread extends Thread implements StudyThreadImpl {
  /** 线程类型. */
  private final int threadType;
  /** 线程最大的执行时间. */
  private final long maxExecTime;
  /** 线程最大的执行单位. */
  private final TimeUnit maxExecTimeUnit;
  /** 线程开始运行的时间(毫秒). */
  private long execStart;
  /** 线程运行的上下文环境. */
  private StudyContext context;
  /** 线程运行的上下文数据. */
  private Bean contextBean;

  /**
   * 自定义线程.
   *
   * <p>参数需要加final修饰.
   *
   * @param targetParam 线程任务.
   * @param nameParam 线程名.
   * @param threadTypeParam 线程类型.
   * @param maxExecTimeParam 线程最大执行时间.
   * @param maxExecTimeUnitParam 线程最大执行时间单位.
   * @author admin
   */
  public StudyThread(
      final Runnable targetParam,
      final String nameParam,
      final int threadTypeParam,
      final long maxExecTimeParam,
      final TimeUnit maxExecTimeUnitParam) {
    super(targetParam, nameParam);
    this.threadType = threadTypeParam;
    this.maxExecTime = maxExecTimeParam;
    this.maxExecTimeUnit = maxExecTimeUnitParam;
  }

  /**
   * 返回线程的最大执行时间单位.
   *
   * @return 返回线程的最大执行时间单位.
   * @author admin
   */
  @Override
  public TimeUnit maxExecTimeUnit() {
    return this.maxExecTimeUnit;
  }

  /**
   * 返回线程的类型.
   *
   * @return 返回线程的类型.
   * @author admin
   */
  @Override
  public int threadType() {
    return this.threadType;
  }

  /**
   * 获取线程运行的开始时间.
   *
   * @return 返回线程的开始运行时间.
   * @author admin
   */
  @Override
  public long startTime() {
    return this.execStart;
  }

  /**
   * 获取线程的最大运行时间.
   *
   * @return 返回线程的最大运行时间.
   * @author admin
   */
  @Override
  public long maxExecTime() {
    return this.maxExecTime;
  }

  /**
   * 获取线程的上下文对象.
   *
   * @return 返回线程的上习武对象.
   * @author admin
   */
  StudyContext context() {
    return this.context;
  }

  /**
   * 当线程开始时,开始时间设置成当前系统的时间戳毫秒数.
   *
   * @author admin
   */
  private void executeStart() {
    // 如果当前上下文为空.
    if (null == this.context) {
      // 设置当前系统时间为开始时间,代表线程开始执行.
      this.execStart = System.currentTimeMillis();
    }
  }

  private void executeEnd() {
    // 如果当前上下文为空.
    if (null == this.context) {
      // 设置当前系统时间为0,代表线程执行完毕.
      this.execStart = 0;
    }
  }

  /**
   * 给线程设置一个上下文环境对象.
   *
   * <p>代表线程正在运行着.
   *
   * @param contextParam 上下文对象.
   * @author admin
   */
  @Override
  public void beginEmission(final StudyContext contextParam) {
    // 设置执行开始时间.
    this.executeStart();
    // 设置上下文.
    this.context = contextParam;
  }

  /**
   * 将线程上下文环境对象设置为空.
   *
   * <p>代表线程运行完毕.
   *
   * @author admin
   */
  @Override
  public void endEmission() {
    // 设置当前上下文为空.
    this.context = null;
    // 设置执行结束时间.
    this.executeEnd();
  }

  /**
   * 当线程开始时,开始时间设置成当前系统的时间戳毫秒数.
   *
   * @param contextBeanParam .
   * @author admin
   */
  private void executeStartV2(final Bean contextBeanParam) {
    // 如果当前上下文为空.
    if (null == this.context) {
      // 设置当前系统时间为开始时间,代表线程开始执行.
      this.execStart = System.currentTimeMillis();
      // 线程开始运行之前设置上下文Bean.
      this.contextBean = contextBeanParam;
    }
  }

  private void executeEndV2() {
    // 如果当前上下文为空.
    if (null == this.context) {
      // 设置当前系统时间为0,代表线程执行完毕.
      this.execStart = 0;
      //  线程开始运行之后清空上下文Bean.
      this.contextBean = null;
    }
  }

  /**
   * 给线程设置一个上下文环境对象.
   *
   * <p>代表线程正在运行着.
   *
   * @param contextBeanParam .
   * @param contextParam 上下文对象.
   * @author admin
   */
  @Override
  public void beginEmissionV2(final Bean contextBeanParam, final StudyContext contextParam) {
    // 设置执行开始时间.
    this.executeStartV2(contextBeanParam);
    // 设置上下文.
    this.context = contextParam;
  }

  /**
   * 将线程上下文环境对象设置为空.
   *
   * <p>代表线程运行完毕.
   *
   * @author admin
   */
  @Override
  public void endEmissionV2() {
    // 设置当前上下文为空.
    this.context = null;
    // 设置执行结束时间.
    this.executeEndV2();
  }

  @Override
  public Bean getContextBean() {
    return contextBean;
  }

  @Override
  public void setContextBean(final Bean contextBean) {
    this.contextBean = contextBean;
  }
}
