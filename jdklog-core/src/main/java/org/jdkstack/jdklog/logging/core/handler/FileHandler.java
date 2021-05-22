package org.jdkstack.jdklog.logging.core.handler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;
import org.jdkstack.jdklog.logging.api.filter.Filter;
import org.jdkstack.jdklog.logging.api.formatter.Formatter;
import org.jdkstack.jdklog.logging.api.metainfo.Constants;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.LogLevel;
import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.api.queue.StudyQueue;
import org.jdkstack.jdklog.logging.api.worker.StudyWorker;
import org.jdkstack.jdklog.logging.core.queue.FileQueue;
import org.jdkstack.jdklog.logging.core.queue.ProducerWorker;
import org.jdkstack.jdklog.logging.core.utils.ClassLoadingUtils;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class FileHandler extends AbstractHandler {
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
  private PrintWriter writer;
  /** . */
  private FileOutputStream fileStream;
  /** . */
  private BufferedOutputStream bufferedStream;
  /** . */
  private OutputStreamWriter streamWriter;
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
  public FileHandler() {
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
  public FileHandler(final String prefix) {
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
   * <p>Lock acquired but not safely unlocked. 这个约束检查暂时不能解决.
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
    // 全局处理器日志消息数量.
    GLOBAL_COUNTER.incrementAndGet();
    // 单个处理器日志消息数量.
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
   * .
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
        // 非阻塞方法获取队列元素.
        final Record logRecord = this.fileQueue.poll();
        // 如果数量不够,导致从队列获取空对象.
        if (null != logRecord) {
          // 设置不为空的标志.
          flag = true;
          // 需要加写锁,可能会关闭.
          if (null != this.writer) {
            // 写入缓存(如果在publish方法中先格式化,则性能下降30%,消费端瓶颈取决于磁盘IO,生产端速度达不到最大,并发不够).
            final String format = this.formatter.format(logRecord);
            this.writer.write(format);
          }
        }
      }
      // 如果缓存中由数据,刷新一次.
      final boolean isWriter = null != this.writer;
      if (flag && isWriter) {
        // 刷新一次IO磁盘.
        this.writer.flush();
      }
    } catch (final Exception e) {
      // ignore Exception.
      throw new StudyJuliRuntimeException(e);
    }
  }

  @SuppressWarnings("java:S2093")
  private void open() {
    this.writeLock.lock();
    try {
      // java:S2093 这个严重问题,暂时无法解决,先忽略sonar的警告.因为文件不能关闭,需要长时间打开.但是sonar检测,需要关闭IO资源.
      this.fileStream = new FileOutputStream(this.logFilePath, true);
      // 创建一个buffered流,缓存大小默认8192.
      this.bufferedStream = new BufferedOutputStream(this.fileStream, Constants.BATCH_BUF_SIZE);
      // 创建一个输出流,使用UTF-8 编码.
      this.streamWriter = new OutputStreamWriter(this.bufferedStream, StandardCharsets.UTF_8);
      // 创建一个PrintWriter,启动自动刷新.
      this.writer = new PrintWriter(this.streamWriter, true);
      // 尝试写入一个空"".
      this.writer.write("");
    } catch (final Exception e) {
      // 如何任何阶段发生了异常,主动关闭所有IO资源.
      this.closeIo();
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
   * @author admin
   */
  private void closeIo() {
    this.writeLock.lock();
    try {
      this.fileStreamClose();
      this.bufferedStreamClose();
      this.streamWriterClose();
      this.writerClose();
    } finally {
      this.writeLock.unlock();
    }
  }

  private void writerClose() {
    // 尝试关闭print writer流.
    if (null != this.writer) {
      this.writer.write("");
      this.writer.flush();
      this.writer.close();
      this.writer = null;
    }
  }

  private void streamWriterClose() {
    // 尝试关闭stream writer流.
    if (null != this.streamWriter) {
      try {
        this.streamWriter.flush();
        this.streamWriter.close();
      } catch (final IOException e) {
        throw new StudyJuliRuntimeException(e);
      }
    }
  }

  private void bufferedStreamClose() {
    // 尝试关闭buff文件流.
    if (null != this.bufferedStream) {
      try {
        this.bufferedStream.flush();
        this.bufferedStream.close();
      } catch (final IOException e) {
        throw new StudyJuliRuntimeException(e);
      }
    }
  }

  private void fileStreamClose() {
    // 尝试关闭文件流.
    if (null != this.fileStream) {
      try {
        this.fileStream.flush();
        this.fileStream.close();
      } catch (final IOException e) {
        throw new StudyJuliRuntimeException(e);
      }
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
