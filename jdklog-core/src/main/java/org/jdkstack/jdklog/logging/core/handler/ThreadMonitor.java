package org.jdkstack.jdklog.logging.core.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdkstack.jdklog.logging.api.context.StudyThreadImpl;
import org.jdkstack.jdklog.logging.api.context.WorkerContext;
import org.jdkstack.jdklog.logging.api.monitor.Monitor;
import org.jdkstack.jdklog.logging.api.worker.StudyWorker;

/**
 * 定时检查线程的运行时间.
 *
 * <p>当线程的run方法运行时间不超过组大阻塞时间blockTime,但是超过了线程运行的最大时间.
 *
 * <p>打印危险的消息,如果超过最大阻塞时间,打算线程堆栈信息,看看线程的run中运行的代码是什么.
 *
 * @author admin
 */
public class ThreadMonitor implements Monitor {
  /** . */
  private static final Logger LOGGER = Logger.getLogger(ThreadMonitor.class.toString());
  /** 保存所有的线程,key是线程名字,value是线程. */
  private final Map<String, Thread> threads = new ConcurrentHashMap<>(32);
  /** . */
  private final StudyWorker<StudyThreadImpl> threadMonitorWorker = new ThreadMonitorWorker();
  /** 调用任务,优雅关闭时,调用对象shutdown方法. */
  private ScheduledFuture<?> scheduledFuture;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public ThreadMonitor() {
    //
  }

  /**
   * 添加要监控的线程,这个线程必须是StudyThread.
   *
   * <p>线程必须继承Thread.
   *
   * @param thread 要监控的线程.
   * @author admin
   */
  @Override
  public final void registerThread(final Thread thread) {
    // 添加要监控的线程,这个线程必须是StudyThread.
    final String name = thread.getName();
    this.threads.put(name, thread);
  }

  /**
   * 定时监控的线程的运行时间.
   *
   * <p>标志位用于检测是否停止timerTask,在关闭定时器之前,停止run方法的执行 .
   *
   * @author admin
   */
  @Override
  public final void monitor(final WorkerContext context) {
    // 得到上下文中的定时器线程池.
    final ScheduledExecutorService scheduledExecutorService = context.getScheduledExecutorService();
    // 创建一个定时任务.
    final Runnable runnable =
        () -> {
          try {
            // 执行业务方法.
            this.execute(context);
          } catch (final Exception e) {
            LOGGER.log(Level.SEVERE, "定时线程运行异常?堆栈信息:", e);
          }
        };
    // 启动定时器,每5s运行一次任务,检查所有线程的运行情况.
    this.scheduledFuture =
        scheduledExecutorService.scheduleAtFixedRate(
            runnable, Constants.INITIAL_DELAY, Constants.PERIOD, TimeUnit.MILLISECONDS);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param context c.
   * @author admin
   */
  private void execute(final WorkerContext context) {
    // 原打算使用异步的方式,但是感觉不太合理.
    for (final Map.Entry<String, Thread> entry : this.threads.entrySet()) {
      // 要检查的线程(注册线程时必须是StudyThread).
      final StudyThreadImpl studyThread = (StudyThreadImpl) entry.getValue();
      // 将处理器提交给线程池.
      context.executeInExecutorService(studyThread, this.threadMonitorWorker);
    }
  }

  /**
   * 用于关闭定时器.
   *
   * <p>关闭定时器之前,先将标志位设置为true,停止timerTask的run方法.
   *
   * @author admin
   */
  @Override
  public final void close() {
    // 如果不为空.
    if (null != this.scheduledFuture) {
      // 尽可能关闭定时任务对象.
      this.scheduledFuture.cancel(true);
    }
  }
}
