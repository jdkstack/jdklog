package org.jdklog.logging.core.handler;

import java.io.BufferedWriter;
import java.io.File;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.jdklog.logging.api.exception.StudyJuliRuntimeException;
import org.jdklog.logging.api.filter.Filter;
import org.jdklog.logging.api.formatter.Formatter;
import org.jdklog.logging.api.metainfo.Constants;
import org.jdklog.logging.api.metainfo.Level;
import org.jdklog.logging.api.metainfo.LogLevel;
import org.jdklog.logging.api.metainfo.Record;
import org.jdklog.logging.api.queue.StudyQueue;
import org.jdklog.logging.api.worker.StudyWorker;
import org.jdklog.logging.core.queue.FileQueue;
import org.jdklog.logging.core.queue.ProducerWorker;
import org.jdklog.logging.core.utils.ClassLoadingUtils;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class FileHandlerV2 extends AbstractHandler {
  /** . */
  private final Runnable consumerRunnable;
  /** . */
  private final StudyQueue<Record> fileQueue;
  /** . */
  private String suffix;
  /** . */
  private String prefix;
  /** . */
  private String directory;
  /** . */
  private BufferedWriter bufferedWriter;
  /** . */
  private File logFilePath;

  /** 生产日志处理器. */
  private final StudyWorker<Record> producerWorker;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public FileHandlerV2() {
    // 读取日志配置文件,初始化配置.
    this.config();
    // 动态配置队列属性.
    this.fileQueue = new FileQueue(this.prefix);
    this.producerWorker = new ProducerWorker(this.fileQueue);
    this.consumerRunnable = new ConsumerRunnable(this.fileQueue, this);
    // 开始创建文件流,用于日志写入.
    this.open();
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param prefix prefix.
   * @author admin
   */
  public FileHandlerV2(final String prefix) {
    this.prefix = prefix;
    // 读取日志配置文件,初始化配置.
    this.config();
    // 动态配置队列属性.
    this.fileQueue = new FileQueue(this.prefix);
    this.producerWorker = new ProducerWorker(this.fileQueue);
    this.consumerRunnable = new ConsumerRunnable(this.fileQueue, this);
    // 开始创建文件流,用于日志写入.
    this.open();
  }

  /**
   * 每向队列中产生一条日志,会触发flush这个方法.
   *
   * <p>关于读写锁,参考JDK ReentrantReadWriteLock第137行例子.
   *
   * @author admin
   */
  @Override
  public final void flush() {
    // UTC时区获取当前系统的日期.
    final Instant now = Instant.now();
    final ZonedDateTime zdt = ZonedDateTime.ofInstant(now, ZoneOffset.UTC);
    final String current = this.intervalFormatter.format(zdt);
    // 首先加一个读锁.
    this.readLock.lock();
    try {
      final long currentLong = Long.parseLong(current);
      // 检查当前日期和上一次的日期.如果不相等,需要重新打开一个新的日志文件.
      if (this.checkState(currentLong)) {
        // 释放读锁.
        this.readLock.unlock();
        // 加一个写锁.
        this.writeLock.lock();
        try {
          // 重新检查状态.
          if (this.checkState(currentLong)) {
            // 关闭流之前,消费掉队列里面的全部数据.
            this.process(Constants.BATCH_SIZE);
            // 关闭原来的文件流.
            this.closeIo();
            // 改变日志interval标志.
            this.initialization = currentLong;
            // 将interval传递给队列对象.
            this.logFilePath = this.getFile();
            // 重新打开新的的文件流.
            this.open();
          }
          // 加一个读锁.
          this.readLock.lock();
        } finally {
          // 释放写锁.
          this.writeLock.unlock();
        }
      }
      // 具体业务逻辑.
      LOG_CONSUMER_CONTEXT.executeInExecutorService(this.consumerRunnable);
    } finally {
      // 释放读锁.
      this.readLock.unlock();
    }
  }

  /**
   * JDK会调用这个方法.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void publish(final Record logRecord) {
    // 记录当前处理器最后一次处理日志的时间.
    this.sys = System.currentTimeMillis();
    GLOBAL_COUNTER.incrementAndGet();
    this.counter.incrementAndGet();
    // 处理器可以处理日志的级别.
    final int levelValue = this.logLevel.intValue();
    // 用户发送日志的级别.
    final int recordLevel = logRecord.intValue();
    // 如果日志的消息级别,比当前处理器的级别小则不处理日志. 如果当前处理器关闭日志级别,处理器也不处理日志.
    final boolean intValue = recordLevel < levelValue;
    final boolean offValue = levelValue == LogLevel.OFF.intValue();
    if (intValue || offValue) {
      return;
    }
    // 如果过滤器返回false,当前日志消息丢弃.
    if (this.filter.isLoggable(logRecord)) {
      return;
    }
    // 启动一个线程,开始生产日志.(考虑将LogRecord预先格式化成字符串消息,LogRecord对象生命周期结束.)
    LOG_PRODUCER_CONTEXT.executeInExecutorService(logRecord, this.producerWorker);
    // 如果队列容量大于等于5000,通知消费者消费.如果此时生产者不再生产数据,则队列中会有<5000条数据永久存在,因此需要启动一个守护者线程GUARDIAN处理.
    final int size = this.fileQueue.size();
    // 当前处理器的队列中日志消息达到5000条,处理一次.
    if (Constants.BATCH_SIZE <= size) {
      // 提交一个任务,用于通知消费者线程去消费队列数据.
      LOG_PRODUCER_NOTICE_CONSUMER_CONTEXT.executeInExecutorService(
          this, this.producerNoticeConsumerWorker);
    }
  }

  /**
   * 读写锁状态检查,日志文件按照日期进行切换的条件.
   *
   * <p>Another description after blank line.
   *
   * @param current .
   * @return boolean .
   * @author admin
   */
  private boolean checkState(final long current) {
    // 文件翻转开关打开并且当前系统时间减去初始化的时间大于间隔时间,即可进行翻转日志文件.
    final long intervalTemp = current - this.initialization;
    final boolean isInterval = intervalTemp >= this.interval;
    return this.rotatable && isInterval;
  }

  /**
   * 配置方法.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  private void config() {
    try {
      // 获取当前的类的全路径.
      final Class<? extends AbstractHandler> aClass = this.getClass();
      final String className = aClass.getName();
      // 获取前缀.
      final String tempPrefix = this.prefix + className;
      // 日志文件翻转开关.
      final String rotatableStr = this.getProperty(tempPrefix + ".rotatable", "true");
      this.rotatable = Boolean.parseBoolean(rotatableStr);
      // 设置日志文件翻转开关.
      this.directory = this.getProperty(tempPrefix + ".directory", "logs");
      // 设置日志文件前缀.
      this.suffix = this.getProperty(tempPrefix + ".suffix", ".log");
      // 设置日志文件后缀.
      // 设置日志文件翻转间隔.
      final String intervalStr = this.getProperty(tempPrefix + ".interval", "1");
      this.interval = Integer.parseInt(intervalStr);
      // 设置日志文件翻转间隔格式化.
      final String intervalFormatterStr =
          this.getProperty(tempPrefix + ".intervalFormatter", "yyyyMMdd");
      this.intervalFormatter = DateTimeFormatter.ofPattern(intervalFormatterStr);
      // UTC时区获取当前系统的日期.
      final Instant now = Instant.now();
      final ZonedDateTime zdt = ZonedDateTime.ofInstant(now, ZoneOffset.UTC);
      // 设置处理器创建时当前的系统时间.
      final String format = this.intervalFormatter.format(zdt);
      this.initialization = Long.parseLong(format);
      // 设置处理器创建时当前的系统时间.
      // 设置日志文件的编码.
      final String encodingStr = this.getProperty(tempPrefix + ".encoding", "UTF-8");
      this.setEncoding(encodingStr);
      // 设置日志文件的级别.
      final String logLevelName = LogLevel.ALL.getName();
      final String levelStr = this.getProperty(tempPrefix + ".level", logLevelName);
      final Level level = LogLevel.findLevel(levelStr);
      this.setLevel(level);
      // 设置日志文件的过滤器.
      final String filterName = this.getProperty(tempPrefix + ".filter", Constants.FILTER);
      // 设置过滤器.
      final Constructor<?> filterConstructor = ClassLoadingUtils.constructor(filterName);
      final Filter filterTemp = (Filter) ClassLoadingUtils.newInstance(filterConstructor);
      this.setFilter(filterTemp);
      // 获取日志格式化器.
      final String formatterName = this.getProperty(tempPrefix + ".formatter", Constants.FORMATTER);
      // 设置日志格式化器.
      final Constructor<?> formatterConstructor = ClassLoadingUtils.constructor(formatterName);
      final Formatter formatterTemp =
          (Formatter) ClassLoadingUtils.newInstance(formatterConstructor);
      // 设置日志格式化器.
      this.setFormatter(formatterTemp);
      // 日志的文件对象.
      this.logFilePath = this.getFile();
    } catch (final Exception e) {
      throw new StudyJuliRuntimeException(e);
    }
  }

  private File getFile() {
    // 日志完整目录部分(日志子目录).
    final File path = new File(this.directory + File.separator + this.prefix);
    // 不存在,创建目录和子目录.
    if (!path.exists() && !path.mkdirs()) {
      throw new StudyJuliRuntimeException("目录创建异常.");
    }
    // 日志文件名.
    String logName = "";
    // 文件切割打开.
    if (this.rotatable) {
      logName = Long.toString(this.initialization);
    }
    // 日志文件名的完整部分.
    final String logFileName = this.prefix + logName + this.suffix;
    // 得到日志的完整路径部分+日志文件名完整部分.
    return new File(path, logFileName);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param size .
   * @author admin
   */
  @Override
  public final void process(final int size) {
    try {
      boolean flag = false;
      // 获取一批数据,写入磁盘.
      for (int i = 0; i < size; i++) {
        // 10, TimeUnit.MILLISECONDS,不等待.
        final Record logRecord = this.fileQueue.poll();
        // 如果数量不够,导致从队列获取空对象.
        if (null != logRecord) {
          flag = true;
          final String format = this.formatter.format(logRecord);
          this.bufferedWriter.write(format);
        }
      }
      if (flag) {
        // 刷新一次IO磁盘.
        this.bufferedWriter.flush();
      }
    } catch (final Exception e) {
      //
      throw new StudyJuliRuntimeException(e);
    }
  }

  /**
   * 创建kafka客户端.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  private void open() {
    this.writeLock.lock();
    try {
      final Path path = this.logFilePath.toPath();
      this.bufferedWriter =
          Files.newBufferedWriter(
              path,
              StandardCharsets.UTF_8,
              StandardOpenOption.CREATE,
              StandardOpenOption.WRITE,
              StandardOpenOption.APPEND);
      this.bufferedWriter.write("");
    } catch (final Exception e) {
      // 如何任何阶段发生了异常,主动关闭所有IO资源.
      this.closeIo();
      throw new StudyJuliRuntimeException(e);
    } finally {
      this.writeLock.unlock();
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  private void closeIo() {
    this.writeLock.lock();
    try {
      // 尝试关闭buffered writer流.
      if (null != this.bufferedWriter) {
        this.bufferedWriter.write("");
        this.bufferedWriter.flush();
        this.bufferedWriter.close();
        this.bufferedWriter = null;
      }
    } catch (final Exception e) {
      //
      throw new StudyJuliRuntimeException(e);
    } finally {
      this.writeLock.unlock();
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return FileQueue .
   * @author admin
   */
  @Override
  public final StudyQueue<Record> getFileQueue() {
    return this.fileQueue;
  }

  @Override
  public final int size() {
    return this.fileQueue.size();
  }
}
