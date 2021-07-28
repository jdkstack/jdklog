package org.jdkstack.jdklog.logging.core.factory;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.logger.Recorder;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.LogRecord;
import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.api.spi.Log;
import org.jdkstack.jdklog.logging.core.formatter.StudyJuliMessageFormat;
import org.jdkstack.jdklog.logging.core.manager.JuliRecorder;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public abstract class AbstractJuliLog implements Log {
  /** 全局Logger日志计数. */
  private static final AtomicLong GLOBAL_COUNTER = new AtomicLong(0L);
  /** 单个Logger日志计数. */
  protected final AtomicLong counter = new AtomicLong(0L);
  /** 方法的当前堆栈元素,采用全局变量的原因是同一个对象,方法调用栈都是相同的,不用每次方法都调用一次(否则性能下降很多). */
  private StackTraceElement stackTraceElement;
  /** 一个Logger对象对应一个Logging对象. */
  protected final Recorder logger;

  protected AbstractJuliLog() {
    String simpleName = this.getClass().getSimpleName();
    this.logger = JuliRecorder.getLogger(simpleName);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @author admin
   */
  protected AbstractJuliLog(final String name) {
    this.logger = JuliRecorder.getLogger(name);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handler .
   * @throws SecurityException .
   * @author admin
   */
  @Override
  public final void addHandler(final Handler handler) {
    Objects.requireNonNull(handler);
    this.logger.addHandler(handler);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param newLogLevel .
   * @throws SecurityException .
   * @author admin
   */
  @Override
  public final void setLevel(final Level newLogLevel) {
    this.logger.setLevel(newLogLevel);
  }

  /**
   * 日志的核心方法.此方法必须经过logCore调用.
   *
   * <p>否则方法调用栈会出现异常.
   *
   * @param logLevel 日志打印级别.
   * @param message 日志消息.
   * @param throwable 日志传递进来的异常.
   * @author admin
   */
  private void log(final Level logLevel, final String message, final Throwable throwable) {
    final Record lr = new LogRecord(logLevel, message);
    // 处理方法调用栈(异常堆栈).
    handlerStackTrace();
    // 设置异常栈.
    lr.setThrown(throwable);
    // 处理公共的字段.
    handler(lr);
    this.logger.logp(lr);
  }

  private void handler(final Record lr) {
    // 获取当前方法调用者的类全路径.
    final String className = this.logger.getName();
    // 获取当前方法调用者的类方法.
    final String classMethod = this.stackTraceElement.getMethodName();
    // 设置日志调用的源类路径和方法.
    lr.setCustom("className", className);
    lr.setCustom("classMethod", classMethod);
    // 设置行号.
    final int lineNumber = this.stackTraceElement.getLineNumber();
    lr.setCustom("lineNumber", Integer.toString(lineNumber));
    // 全局logger处理的日志数量.
    final long globalCounter = GLOBAL_COUNTER.incrementAndGet();
    lr.setCustom("globalLoggerCounter", Long.toString(globalCounter));
    // 单个logger处理的日志数量.
    final long singleCounter = counter.incrementAndGet();
    lr.setCustom("singleLoggerCounter", Long.toString(singleCounter));
  }

  private void handlerStackTrace() {
    // 一个Logging实例初始化一次. 因为调用栈都是一样的,直接获取某个元素即可.
    if (null == this.stackTraceElement) {
      // 当前方法的异常.
      final Throwable stackTrace = new Throwable();
      // 当前方法的调用栈.
      final StackTraceElement[] stackTraceElements = stackTrace.getStackTrace();
      // 当前方法的调用栈深度是4. 因此获取第三个元素即可拿到调用者类.
      this.stackTraceElement = stackTraceElements[Constants.STACK_TRACE_ELEMENT];
    }
  }

  /**
   * 日志的核心方法.
   *
   * <p>所有公共的方法,必须先调用logCore调用.
   *
   * @param logLevel 日志打印级别.
   * @param message 日志消息.
   * @param args 日志传递进来的参数.
   * @author admin
   */
  protected void logCore(final Level logLevel, final String message, final Object... args) {
    Objects.requireNonNull(message, "日志消息不能为空.");
    Objects.requireNonNull(args, "日志消息参数不能为空.");
    // 检查,当前logger配置,比如logger动态增加了过滤器,改变了日志级别,增加了handler等.
    // 参数的个数.
    final int argsLen = args.length;
    // 没找到大括号,直接返回原消息.
    final int index = message.indexOf(Constants.BRACE);
    // 如果参数的长度为0,代表不需要格式化.
    final boolean isLen = 0 == args.length;
    // 如果没有大括号,代表消息不需要格式化.
    final boolean isIndex = -1 == index;
    // 只要满条一个条件,代表消息不需要格式化.
    if (isLen || isIndex) {
      // 调用具体逻辑.
      this.log(logLevel, message, null);
    } else {
      // 检查最后一个参数是不是异常参数.
      final int lastArrIdx = argsLen - 1;
      final Object lastEntry = args[lastArrIdx];
      // 检查对象是否是Throwable.
      Throwable throwable = getThrowable(lastEntry);
      // 使用参数对消息进行格式化.
      final String messageFormat = StudyJuliMessageFormat.format(message, args);
      // 调用具体逻辑.
      this.log(logLevel, messageFormat, throwable);
    }
  }

  private Throwable getThrowable(final Object lastEntry) {
    Throwable throwable = null;
    if (lastEntry instanceof Throwable) {
      throwable = (Throwable) lastEntry;
    }
    return throwable;
  }
}
