package org.jdkstack.jdklog.logging.core.handler;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdkstack.jdklog.logging.api.context.WorkerContext;
import org.jdkstack.jdklog.logging.api.monitor.Monitor;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public abstract class AbstractMonitor implements Monitor {
  /** . */
  private static final Logger LOGGER = Logger.getLogger(AbstractMonitor.class.toString());
  /** 调用任务,优雅关闭时,调用对象shutdown方法. */
  protected ScheduledFuture<?> scheduledFuture;

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

  /**
   * .
   *
   * <p>.
   *
   * @param context context.
   * @author admin
   */
  public abstract void execute(final WorkerContext context);
}
