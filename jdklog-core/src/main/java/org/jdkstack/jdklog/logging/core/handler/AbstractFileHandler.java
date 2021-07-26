package org.jdkstack.jdklog.logging.core.handler;

import java.io.File;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;
import org.jdkstack.jdklog.logging.api.metainfo.Constants;
import org.jdkstack.jdklog.logging.core.queue.FileQueue;

/**
 * This is a method description.
 *
 * <p>PrintWriter.
 *
 * @author admin
 */
public abstract class AbstractFileHandler extends AbstractHandler {
  /** . */
  protected File logFilePath;
  /** 按照文件名翻转日志文件. */
  protected long initialization;
  /** 间隔格式化. */
  protected DateTimeFormatter intervalFormatter;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param prefix prefix.
   * @author admin
   */
  protected AbstractFileHandler(final String prefix) {
    // 动态配置队列属性.
    super(prefix, new FileQueue(prefix));
    // 读取日志配置文件,初始化配置.
    this.config();
  }

  /**
   * 每向队列中产生一条日志,会触发flush这个方法.
   *
   * <p>关于读写锁,参考JDK ReentrantReadWriteLock第137行例子.
   *
   * <p>Lock acquired but not safely unlocked. 这个约束检查暂时不能解决.
   *
   * <pre>
   *   lock 锁是否可以采用CAS的方式代替?
   * </pre>
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
      firstCheck(currentLong);
      // 具体业务逻辑.
      LOG_CONSUMER_CONTEXT.executeInExecutorService(this.consumerRunnable);
    } finally {
      // 释放读锁.
      this.readLock.unlock();
    }
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
      // 初始化File配置.
      configFileHandler();
      // 委托初始化配置.
      configHandler();
      // 日志的文件对象.
      this.logFilePath = this.getFile();
    } catch (final Exception e) {
      throw new StudyJuliRuntimeException(e);
    }
  }

  private void configFileHandler() {
    // 设置日志文件翻转间隔格式化.
    final String intervalFormatterStr = this.getValue("intervalFormatter", "yyyyMMdd");
    this.intervalFormatter = DateTimeFormatter.ofPattern(intervalFormatterStr);
    // UTC时区获取当前系统的日期.
    final Instant now = Instant.now();
    final ZonedDateTime zdt = ZonedDateTime.ofInstant(now, ZoneOffset.UTC);
    // 设置处理器创建时当前的系统时间.
    final String format = this.intervalFormatter.format(zdt);
    this.initialization = Long.parseLong(format);
  }

  public File getFile() {
    // 设置日志文件翻转开关.
    final String directory = this.getValue("directory", "logs");
    // 日志完整目录部分(日志子目录).
    final File path = new File(directory + File.separator + this.prefix);
    // 不存在,创建目录和子目录.
    if (!path.exists() && !path.mkdirs()) {
      throw new StudyJuliRuntimeException("目录创建异常.");
    }
    final String logFileName = getLogFileName();
    // 得到日志的完整路径部分+日志文件名完整部分.
    return new File(path, logFileName);
  }

  private String getLogFileName() {
    // 日志文件翻转开关.
    final String rotatableStr = this.getValue("rotatable", "true");
    final boolean rotatable = Boolean.parseBoolean(rotatableStr);
    // 日志文件名.
    String logName = "";
    // 文件切割打开.
    if (rotatable) {
      logName = Long.toString(this.initialization);
    }
    final String suffix = this.getValue("suffix", ".log");
    // 日志文件名的完整部分.
    return this.prefix + logName + suffix;
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
  protected boolean checkState(final long current) {
    // 日志文件翻转开关.
    final String rotatableStr = this.getValue(this.prefix + "rotatable", "true");
    final boolean rotatable = Boolean.parseBoolean(rotatableStr);
    // 文件翻转开关打开并且当前系统时间减去初始化的时间大于间隔时间,即可进行翻转日志文件.
    final long intervalTemp = current - this.initialization;
    final String intervalStr = this.getValue(this.prefix + "interval", "1");
    int interval = Integer.parseInt(intervalStr);
    final boolean isInterval = intervalTemp >= interval;
    return rotatable && isInterval;
  }

  private void firstCheck(long currentLong) {
    if (this.checkState(currentLong)) {
      // 释放读锁.
      this.readLock.unlock();
      // 加一个写锁.
      this.writeLock.lock();
      try {
        secondCheck(currentLong);
        // 加一个读锁.
        this.readLock.lock();
      } finally {
        // 释放写锁.
        this.writeLock.unlock();
      }
    }
  }

  private void secondCheck(long currentLong) {
    // 重新检查状态.
    if (this.checkState(currentLong)) {
      // 关闭流之前,消费掉队列里面的全部数据.
      this.process(Constants.BATCH_SIZE);
      // 关闭原来的文件流.
      this.doClose();
      // 改变日志interval标志.
      this.initialization = currentLong;
      // 将interval传递给队列对象.
      this.logFilePath = this.getFile();
      // 重新打开新的的文件流.
      this.doOpen();
    }
  }

  /**
   * 打开流.
   *
   * @author admin
   */
  public abstract void doOpen();

  /**
   * 关闭流.
   *
   * @author admin
   */
  public abstract void doClose();
}
