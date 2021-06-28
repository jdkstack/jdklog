package org.jdkstack.jdklog.examples.example;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.jdkstack.jdklog.logging.api.context.WorkerContext;
import org.jdkstack.jdklog.logging.api.monitor.Monitor;
import org.jdkstack.jdklog.logging.api.spi.Log;
import org.jdkstack.jdklog.logging.core.context.StudyThreadFactory;
import org.jdkstack.jdklog.logging.core.context.WorkerStudyContextImpl;
import org.jdkstack.jdklog.logging.core.factory.LogFactory;
import org.jdkstack.jdklog.logging.core.handler.StudyRejectedPolicy;
import org.jdkstack.jdklog.logging.core.handler.ThreadMonitor;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public final class Examples {
  private static final Log log = LogFactory.getLog(Examples.class);
  /** 线程阻塞的最大时间时10秒.如果不超过15秒,打印warn.如果超过15秒打印异常堆栈. */
  private static final Monitor CHECKER = new ThreadMonitor(15000L);
  /** 线程池. CallerRunsPolicy 策略是一种背压机制.会使用主线程运行任务,但是使用这个策略,会导致主线程状态改变. */
  private static final ExecutorService LOG_BUSINESS =
      new ThreadPoolExecutor(
          3,
          3,
          0,
          TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<>(1000),
          new StudyThreadFactory("log-business", CHECKER),
          new StudyRejectedPolicy());
  /** 服务器端的定时调度线程池. */
  private static final ScheduledExecutorService STUDY_BUSINESS_SCHEDULED_EXECUTOR_SERVICE =
      new ScheduledThreadPoolExecutor(1, new StudyThreadFactory("study_business_scheduled", null));
  /** 工作任务上下文. */
  private static final WorkerContext LOG_BUSINESS_CONTEXT =
      new WorkerStudyContextImpl(LOG_BUSINESS, STUDY_BUSINESS_SCHEDULED_EXECUTOR_SERVICE);

  private Examples() {
    //
  }

  public static void main(final String... args) {
    final long start = System.currentTimeMillis();
    final ExamplesWorker examplesWorker = new ExamplesWorker();
    // 1000个任务,会生成35W条日志.
    for (int i = 0; i < 1000; i++) {
      // 参数传递一个唯一消息ID.子线程也可以利用这个唯一消息ID.
      LOG_BUSINESS_CONTEXT.executeInExecutorServiceV2(
          UUID.randomUUID().toString(), i, examplesWorker);
    }
    final long e1 = System.currentTimeMillis();
    System.out.println("消耗的时间:" + (e1 - start) / 1000 + "秒");
    try {
      Thread.sleep(15000000);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }
}
