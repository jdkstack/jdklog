package org.jdkstack.jdklog.logging.core.handler;

import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.jdkstack.jdklog.logging.api.context.WorkerContext;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;
import org.jdkstack.jdklog.logging.api.filter.Filter;
import org.jdkstack.jdklog.logging.api.formatter.Formatter;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.metainfo.Constants;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.LogLevel;
import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.api.monitor.Monitor;
import org.jdkstack.jdklog.logging.api.queue.StudyQueue;
import org.jdkstack.jdklog.logging.core.context.StudyThreadFactory;
import org.jdkstack.jdklog.logging.core.context.WorkerStudyContextImpl;
import org.jdkstack.jdklog.logging.core.manager.AbstractLogManager;
import org.jdkstack.jdklog.logging.core.utils.ClassLoadingUtils;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public abstract class AbstractHandler extends AbstractExecute implements Handler {
  /** 线程阻塞的最大时间时10秒.如果不超过15秒,打印warn.如果超过15秒打印异常堆栈. */
  private static final Monitor CHECKER = new ThreadMonitor(15000L);
  /** 线程池. */
  private static final Monitor GUARDIAN = new GuardianConsumerMonitor();
  /** 线程池. */
  private static final ExecutorService LOG_PRODUCER =
      new ThreadPoolExecutor(
          2,
          2,
          0,
          TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<>(5000),
          new StudyThreadFactory("log-producer", CHECKER),
          new StudyRejectedPolicy());

  /** 线程池. */
  private static final ExecutorService LOG_GUARDIAN_CONSUMER =
      new ThreadPoolExecutor(
          2,
          2,
          0,
          TimeUnit.MILLISECONDS,
          new LinkedBlockingQueue<>(5000),
          new StudyThreadFactory("log-guardian-consumer", CHECKER),
          new StudyRejectedPolicy());

  /** 线程池. CallerRunsPolicy 拒绝策略不丢数据,因为在主线程上执行. */
  private static final ExecutorService LOG_CONSUMER =
      new ThreadPoolExecutor(
          2,
          2,
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

  static {
    // 线程监控任务.
    CHECKER.monitor(LOG_PRODUCER_CONTEXT);
    // 线程监控任务.
    CHECKER.monitor(LOG_CONSUMER_CONTEXT);
    // 守护消费监控任务.
    GUARDIAN.monitor(LOG_GUARDIAN_CONSUMER_CONTEXT);
  }

  /** . */
  protected final Runnable consumerRunnable;
  /** . */
  protected String prefix;
  /** 处理器级别的日志过滤器. */
  protected Filter filter;
  /** . */
  protected Formatter formatter;
  /** 处理器级别的日志级别. */
  protected Level logLevel = LogLevel.ALL;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param prefix prefix.
   * @author admin
   */
  protected AbstractHandler(final String prefix, final StudyQueue<Record> queue) {
    super(queue);
    this.prefix = prefix;
    this.consumerRunnable = new ConsumerRunnable(this.queue, this);
  }

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
   * 调用日志log方法时,最后会调用此方法,将日志发送到队列中.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void publish(final Record logRecord) {
    // 每次发布日志消息是,先进行计算.
    metris(logRecord);
    // 处理器可以处理日志的级别.
    final int levelValue = this.logLevel.intValue();
    // 用户发送日志的级别.
    final int recordLevel = logRecord.intValue();
    // 如果日志的消息级别,比当前处理器的级别小则不处理日志.
    final boolean intValue = recordLevel < levelValue;
    // 如果当前处理器关闭日志级别,处理器也不处理日志.
    final boolean offValue = levelValue == LogLevel.OFF.intValue();
    // 如果过滤器返回true,当前日志消息丢弃.
    boolean loggable = this.filter.isLoggable(logRecord);
    // 只要有一个条件为true,则日志不处理.
    if (intValue || offValue || loggable) {
      // 忽略处理.
      ignoreHandle(logRecord);
    } else {
      // 批量处理.
      batchHandle(logRecord);
    }
  }

  private void ignoreHandle(final Record logRecord) {
    // 写到异常日志文件中.
  }

  private void batchHandle(final Record logRecord) {
    // 启动一个线程,开始生产日志.(考虑将LogRecord预先格式化成字符串消息,LogRecord对象生命周期结束.)
    LOG_PRODUCER_CONTEXT.executeInExecutorService(logRecord, this.producerWorker);
    // 如果队列容量大于等于100,通知消费者消费.如果此时生产者不再生产数据,则队列中会有<100条数据永久存在,因此需要启动一个守护者线程GUARDIAN处理.
    final int size = this.queue.size();
    // 当前处理器的队列中日志消息达到100条,处理一次.
    if (Constants.BATCH_SIZE <= size) {
      // 提交一个任务,用于通知消费者线程去消费队列数据.
      this.flush();
    }
  }

  @Override
  public final int size() {
    return this.queue.size();
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
   * @throws SecurityException .
   * @author admin
   */
  @Override
  public final void setLevel(final Level newLogLevel) {
    this.logLevel = newLogLevel;
  }

  /**
   * 配置方法.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public void configHandler() {
    try {
      // 设置日志文件的级别.
      final String logLevelName = LogLevel.ALL.getName();
      final String levelStr = this.getValue("level", logLevelName);
      final Level level = LogLevel.findLevel(levelStr);
      this.setLevel(level);
      // 设置日志文件的过滤器.
      final String filterName = this.getValue("filter", Constants.FILTER);
      // 设置过滤器.
      final Constructor<?> filterConstructor = ClassLoadingUtils.constructor(filterName);
      final Filter filterTemp = (Filter) ClassLoadingUtils.newInstance(filterConstructor);
      this.setFilter(filterTemp);
      // 获取日志格式化器.
      final String formatterName = this.getValue("formatter", Constants.FORMATTER);
      // 设置日志格式化器.
      final Constructor<?> formatterConstructor = ClassLoadingUtils.constructor(formatterName);
      final Formatter formatterTemp =
          (Formatter) ClassLoadingUtils.newInstance(formatterConstructor);
      // 设置日志格式化器.
      this.setFormatter(formatterTemp);
    } catch (final Exception e) {
      throw new StudyJuliRuntimeException(e);
    }
  }

  private String getTempPrefix() {
    // 获取当前的类的全路径.
    final Class<? extends AbstractHandler> aClass = this.getClass();
    final String className = aClass.getName();
    return this.prefix + className;
  }

  public String getValue(String key, String defaultValue) {
    // 获取当前的类的全路径.
    String tempPrefix = getTempPrefix();
    // 设置日志文件翻转开关.
    return this.getProperty(tempPrefix + "." + key, defaultValue);
  }
}
