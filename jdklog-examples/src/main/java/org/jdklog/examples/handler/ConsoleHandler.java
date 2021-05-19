package org.jdklog.examples.handler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import org.jdklog.logging.api.exception.StudyJuliRuntimeException;
import org.jdklog.logging.api.filter.Filter;
import org.jdklog.logging.api.formatter.Formatter;
import org.jdklog.logging.api.metainfo.Constants;
import org.jdklog.logging.api.metainfo.Level;
import org.jdklog.logging.api.metainfo.LogLevel;
import org.jdklog.logging.api.metainfo.Record;
import org.jdklog.logging.api.queue.StudyQueue;
import org.jdklog.logging.core.handler.AbstractHandler;
import org.jdklog.logging.core.utils.ClassLoadingUtils;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ConsoleHandler extends AbstractHandler {
  /** . */
  private PrintWriter writer;
  /** . */
  private OutputStreamWriter streamWriter;
  /** . */
  private String prefix;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public ConsoleHandler() {
    // 读取日志配置文件,初始化配置.
    this.config();
    // 开始创建文件流,用于日志写入.
    this.open();
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param prefix prefix.
   * @author admin
   */
  public ConsoleHandler(final String prefix) {
    this.prefix = prefix;
    // 读取日志配置文件,初始化配置.
    this.config();
    // 开始创建文件流,用于日志写入.
    this.open();
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord .
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
    final String msg = this.formatter.format(logRecord);
    this.writer.write(msg);
    this.writer.flush();
  }

  @Override
  public final void flush() {
    //
  }

  @Override
  public final int size() {
    return 0;
  }

  @Override
  public final void process(final int size) {
    //
  }

  @Override
  public final StudyQueue<Record> getFileQueue() {
    return null;
  }

  @SuppressWarnings({"java:S2093", "java:S106"})
  private void open() {
    this.writeLock.lock();
    try {
      // 创建一个输出流,使用UTF-8 编码.
      final PrintStream err = System.err;
      this.streamWriter = new OutputStreamWriter(err, StandardCharsets.UTF_8);
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

  private void config() {
    try {
      // 获取当前的类的全路径.
      final Class<? extends AbstractHandler> aClass = this.getClass();
      final String className = aClass.getName();
      final String tempPrefix = this.prefix + className;
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
    } catch (final Exception e) {
      throw new StudyJuliRuntimeException(e);
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
      // 尝试关闭stream writer流.
      if (null != this.streamWriter) {
        try {
          this.streamWriter.flush();
          this.streamWriter.close();
        } catch (final IOException e) {
          throw new StudyJuliRuntimeException(e);
        }
      }
      // 尝试关闭print writer流.
      if (null != this.writer) {
        this.writer.write("");
        this.writer.flush();
        this.writer.close();
        this.writer = null;
      }
    } finally {
      this.writeLock.unlock();
    }
  }
}
