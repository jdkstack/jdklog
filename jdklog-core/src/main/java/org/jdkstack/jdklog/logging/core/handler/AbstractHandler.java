package org.jdkstack.jdklog.logging.core.handler;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jdkstack.jdklog.logging.api.context.WorkerContext;
import org.jdkstack.jdklog.logging.api.filter.Filter;
import org.jdkstack.jdklog.logging.api.formatter.Formatter;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.LogLevel;
import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.api.monitor.Monitor;
import org.jdkstack.jdklog.logging.api.worker.StudyWorker;
import org.jdkstack.jdklog.logging.core.context.StudyThreadFactory;
import org.jdkstack.jdklog.logging.core.context.WorkerStudyContextImpl;
import org.jdkstack.jdklog.logging.core.manager.AbstractLogManager;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public abstract class AbstractHandler implements Handler {
  /** 线程阻塞的最大时间时10秒.如果不超过15秒,打印warn.如果超过15秒打印异常堆栈. */
  private static final Monitor CHECKER = new ThreadMonitor(15000L);
  /** 线程池. */
  private static final Monitor GUARDIAN = new GuardianConsumerMonitor();
  /** 线程池. */
  private static final ExecutorService LOG_PRODUCER =
      new ThreadPoolExecutor(
          1,
          1,
          0,
          TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<>(5000),
          new StudyThreadFactory("log-producer", CHECKER),
          new StudyRejectedPolicy());

  /** 线程池. */
  private static final ExecutorService LOG_GUARDIAN_CONSUMER =
      new ThreadPoolExecutor(
          1,
          1,
          0,
          TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<>(5000),
          new StudyThreadFactory("log-guardian-consumer", CHECKER),
          new StudyRejectedPolicy());

  /** 线程池. CallerRunsPolicy 拒绝策略不丢数据,因为在主线程上执行. */
  private static final ExecutorService LOG_CONSUMER =
      new ThreadPoolExecutor(
          1,
          1,
          0,
          TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<>(5000),
          new StudyThreadFactory("log-consumer", CHECKER),
          new StudyRejectedPolicy());
  /** 服务器端的定时调度线程池. */
  private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE =
      new ScheduledThreadPoolExecutor(3, new StudyThreadFactory("study_scheduled", null));
  /** 工作任务上下文. */
  protected static final WorkerContext LOG_PRODUCER_CONTEXT =
      new WorkerStudyContextImpl(LOG_PRODUCER, SCHEDULED_EXECUTOR_SERVICE);
  /** 工作任务上下文. */
  protected static final WorkerContext LOG_CONSUMER_CONTEXT =
      new WorkerStudyContextImpl(LOG_CONSUMER, SCHEDULED_EXECUTOR_SERVICE);
  /** 工作任务上下文. */
  private static final WorkerContext LOG_GUARDIAN_CONSUMER_CONTEXT =
      new WorkerStudyContextImpl(LOG_GUARDIAN_CONSUMER, SCHEDULED_EXECUTOR_SERVICE);
  /** 线程池. */
  private static final ExecutorService LOG_PRODUCER_NOTICE_CONSUMER =
      new ThreadPoolExecutor(
          1,
          1,
          0,
          TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<>(5000),
          new StudyThreadFactory("log-producer-notice-consumer", CHECKER),
          new StudyRejectedPolicy());
  /** 工作任务上下文. */
  protected static final WorkerContext LOG_PRODUCER_NOTICE_CONSUMER_CONTEXT =
      new WorkerStudyContextImpl(LOG_PRODUCER_NOTICE_CONSUMER, SCHEDULED_EXECUTOR_SERVICE);
  /** 是否开启处理器级别的日志处理. */
  private static final int OFF_VALUE = LogLevel.OFF.intValue();
  /** 全局handler日志计数. */
  protected static final AtomicLong GLOBAL_COUNTER = new AtomicLong(0L);

  static {
    // 线程监控任务.
    CHECKER.monitor(LOG_PRODUCER_CONTEXT);
    // 线程监控任务.
    CHECKER.monitor(LOG_CONSUMER_CONTEXT);
    // 守护消费监控任务.
    GUARDIAN.monitor(LOG_GUARDIAN_CONSUMER_CONTEXT);
  }

  /** 单个handler日志计数. */
  protected final AtomicLong counter = new AtomicLong(0L);
  /** 生产通知消费处理器.为Handler自己的队列创建一个生产者通知消费者处理程序. */
  protected final StudyWorker<Handler> producerNoticeConsumerWorker =
      new ProducerNoticeConsumerWorker();
  /** 一个非公平锁,fair=false. */
  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
  /** 非公平读锁. */
  protected final Lock readLock = this.readWriteLock.readLock();
  /** 非公平写锁. */
  protected final Lock writeLock = this.readWriteLock.writeLock();
  /** 代表当前处理器接收到最后一条日志的时间,0L表示从来没接收到. */
  protected long sys;
  /** 按照文件名翻转日志文件. */
  protected long initialization;
  /** 间隔. */
  protected int interval;
  /** 间隔格式化. */
  protected DateTimeFormatter intervalFormatter;
  /** . */
  protected boolean rotatable = true;
  /** 处理器级别的日志过滤器. */
  protected Filter filter;
  /** . */
  protected Formatter formatter;
  /** 处理器级别的日志级别. */
  protected Level logLevel = LogLevel.ALL;
  /** . */
  private String encoding;

  /**
   * 关闭资源方法,一般处理优雅关闭应用程序时调用.
   *
   * <p>如果强行kill -9 方法是不会调用的.
   *
   * @throws SecurityException 抛出安全异常.
   * @author admin
   */
  @Override
  public final void close() {
    // 每个子类也要关闭响应的资源,暂时未处理.
    LOG_CONSUMER.shutdown();
    LOG_PRODUCER.shutdown();
  }

  /**
   * 用Key从JDL LogManager读取一个配置的value.
   *
   * <p>Another description after blank line.
   *
   * @param name 属性名.
   * @param defaultValue 默认属性名.
   * @return 返回属性名对应的值.
   * @author admin
   */
  @Override
  public final String getProperty(final String name, final String defaultValue) {
    // 获取当前类的配置属性.
    String value = AbstractLogManager.getProperty1(name);
    // 如果空,使用默认值.
    if (null == value) {
      value = defaultValue;
    } else {
      // 如果不为空,去掉空格.
      value = value.trim();
    }
    return value;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return 返回处理器最后接收到日志的时间.
   * @author admin
   */
  @Override
  public final long getSys() {
    return this.sys;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Formatter
   * @author admin
   */
  @Override
  public final Formatter getFormatter() {
    return this.formatter;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param newFormatter .
   * @throws SecurityException .
   * @author admin
   */
  @Override
  public final void setFormatter(final Formatter newFormatter) {
    this.formatter = Objects.requireNonNull(newFormatter);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String
   * @author admin
   */
  @Override
  public final String getEncoding() {
    return this.encoding;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param encoding .
   * @author admin
   */
  @Override
  public final void setEncoding(final String encoding) {
    this.encoding = encoding;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Filter
   * @author admin
   */
  @Override
  public final Filter getFilter() {
    return this.filter;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @throws SecurityException .
   * @author admin
   */
  @Override
  public final void setFilter(final Filter newFilter) {
    this.filter = newFilter;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Level .
   * @author admin
   */
  @Override
  public final Level getLevel() {
    return this.logLevel;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @throws SecurityException .
   * @author admin
   */
  @Override
  public final void setLevel(final Level newLogLevel) {
    this.logLevel = newLogLevel;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final boolean isLoggable(final Record logRecord) {
    final int levelValue = this.logLevel.intValue();
    final boolean intValue = logRecord.intValue() < levelValue;
    final boolean offValue = levelValue == OFF_VALUE;
    if (intValue || offValue) {
      return false;
    }
    // 如果过滤器是空的,或者过滤器返回真.
    final boolean isFilter = null == this.filter;
    final boolean isLoggable = !isFilter && this.filter.isLoggable(logRecord);
    return isFilter || isLoggable;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return AtomicLong .
   * @author admin
   */
  @Override
  public final AtomicLong getCounter() {
    return this.counter;
  }
}
