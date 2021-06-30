package org.jdkstack.jdklog.logging.core.factory;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.jdkstack.jdklog.logging.api.context.Bean;
import org.jdkstack.jdklog.logging.api.context.StudyThreadImpl;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.logger.Logger;
import org.jdkstack.jdklog.logging.api.metainfo.Constants;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.LogLevel;
import org.jdkstack.jdklog.logging.api.metainfo.LogRecord;
import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.api.spi.Log;
import org.jdkstack.jdklog.logging.core.formatter.StudyJuliMessageFormat;
import org.jdkstack.jdklog.logging.core.manager.JuliLogger;
import org.jdkstack.jdklog.logging.core.manager.LogManagerUtils;

/**
 * Juli日志的核心API,提供所有日志级别的方法,由LogFactory动态创建.
 *
 * <p>每当使用LogFactory.getLog方法时都会创建一个Logging对象,并绑定到JDK Logger对象.
 *
 * @author admin
 */
public class JuliLog implements Log {
  /** 全局日志计数. */
  private static final AtomicLong GLOBAL_COUNTER = new AtomicLong(0L);
  /** 一个Logger对象对应一个Logging对象. */
  private final Logger logger;
  /** 方法的当前堆栈元素,采用全局变量的原因是同一个对象,方法调用栈都是相同的,不用每次方法都调用一次(否则性能下降很多). */
  private StackTraceElement stackTraceElement;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public JuliLog() {
    final String name = JuliLog.class.getName();
    this.logger = JuliLogger.getLogger(name);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @author admin
   */
  public JuliLog(final String name) {
    this.logger = JuliLogger.getLogger(name);
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
    // 一个Logging实例初始化一次. 因为调用栈都是一样的,直接获取某个元素即可.
    if (null == this.stackTraceElement) {
      // 当前方法的异常.
      final Throwable stackTrace = new Throwable();
      // 当前方法的调用栈.
      final StackTraceElement[] stackTraceElements = stackTrace.getStackTrace();
      // 当前方法的调用栈深度是4. 因此获取第三个元素即可拿到调用者类.
      this.stackTraceElement = stackTraceElements[Constants.STACK_TRACE_ELEMENT];
    }
    // 设置异常栈.
    lr.setThrown(throwable);
    // 获取当前方法调用者的类全路径.
    final String className = this.logger.getName();
    // 获取当前方法调用者的类方法.
    final String classMethod = this.stackTraceElement.getMethodName();
    // 设置日志调用的源类路径和方法.
    lr.setSourceClassName(className);
    lr.setSourceMethodName(classMethod);
    // 设置行号.
    final int lineNumber = this.stackTraceElement.getLineNumber();
    lr.setLineNumber(lineNumber);
    // 查看是否使用唯一序列号ID.
    final String unique = LogManagerUtils.getProperty(Constants.UNIQUE, Constants.FALSE);
    if (Constants.TRUE.equals(unique)) {
      // 获取当前线程.
      final Thread thread = Thread.currentThread();
      // 如果不是StudyThread,无法处理唯一日志消息ID.
      if (thread instanceof StudyThreadImpl) {
        final StudyThreadImpl studyThread = (StudyThreadImpl) thread;
        // 处理线程上下文数据.
        this.contextBean(lr, studyThread);
      }
    }
    // 为每条日志设置一个自增长的序列号.
    final long globalCounter = GLOBAL_COUNTER.incrementAndGet();
    lr.setSerialNumber(globalCounter);
    this.logger.logp(lr);
  }

  private void contextBean(final Record lr, final StudyThreadImpl studyThread) {
    Bean contextBean = studyThread.getContextBean();
    if (contextBean != null) {
      lr.setSpanId0(contextBean.getSpanId0());
      lr.setSpanId1(contextBean.getSpanId1());
      lr.setTraceId(contextBean.getTraceId());
      Map<String, String> customs = contextBean.getCustoms();
      for (Map.Entry<String, String> entry : customs.entrySet()) {
        lr.setCustom(entry.getKey(), entry.getValue());
      }
      // 反射得到方法,匹配方法配置.如果匹配则,追加到lr中.
    }
  }

  /**
   * 所有日志级别对象的方法首先调用logCore方法.
   *
   * <p>这样的目的是保证方法调用栈的正确性,直接调用log方法会导致调用栈异常.
   *
   * @param logLevel 日志打印级别.
   * @param message 日志消息.
   * @author admin
   */
  private void logCore(final Level logLevel, final String message) {
    this.log(logLevel, message, null);
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
  private void logCore(final Level logLevel, final String message, final Object... args) {
    Objects.requireNonNull(message, "日志消息不能为空.");
    Objects.requireNonNull(args, "日志消息参数不能为空.");
    final int argsLen = args.length;
    // 没找到大括号,直接返回原消息.
    final int index = message.indexOf(Constants.BRACE);
    final boolean isLen = 0 == args.length;
    final boolean isIndex = -1 == index;
    if (isLen || isIndex) {
      this.log(logLevel, message, null);
    } else {
      // 检查最后一个参数是不是异常参数.
      final int lastArrIdx = argsLen - 1;
      final Object lastEntry = args[lastArrIdx];
      Throwable throwable = null;
      if (lastEntry instanceof Throwable) {
        throwable = (Throwable) lastEntry;
      }
      final String messageFormat = StudyJuliMessageFormat.format(message, args);
      this.log(logLevel, messageFormat, throwable);
    }
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
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void info(final String message, final Object... args) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.INFO, message, args);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @author admin
   */
  @Override
  public final void info(final String message) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.OFF, message);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @param args 日志传递进来的参数.
   * @author admin
   */
  @Override
  public final void debug(final String message, final Object... args) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.FINE, message, args);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @author admin
   */
  @Override
  public final void debug(final String message) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.OFF, message);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @param args 日志传递进来的参数.
   * @author admin
   */
  @Override
  public final void trace(final String message, final Object... args) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.FINER, message, args);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @author admin
   */
  @Override
  public final void trace(final String message) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.OFF, message);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @param args 日志传递进来的参数.
   * @author admin
   */
  @Override
  public final void warn(final String message, final Object... args) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.WARNING, message, args);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @author admin
   */
  @Override
  public final void warn(final String message) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.OFF, message);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @param args 日志传递进来的参数.
   * @author admin
   */
  @Override
  public final void error(final String message, final Object... args) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.SEVERE, message, args);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @author admin
   */
  @Override
  public final void error(final String message) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.OFF, message);
  }

  /**
   * fatal日志消息,代表致命错误,优先级最高的日志级别.
   *
   * <p>.
   *
   * @param message 日志消息.
   * @param args 日志传递进来的参数.
   * @author admin
   */
  @Override
  public final void fatal(final String message, final Object... args) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.SEVERE, message, args);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @author admin
   */
  @Override
  public final void fatal(final String message) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.OFF, message);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @author admin
   */
  @Override
  public final void config(final String message) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.CONFIG, message);
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @param args 日志传递进来的参数.
   * @author admin
   */
  @Override
  public final void config(final String message, final Object... args) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.CONFIG, message, args);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @author admin
   */
  @Override
  public final void all(final String message) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.ALL, message);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @param args 日志传递进来的参数.
   * @author admin
   */
  @Override
  public final void all(final String message, final Object... args) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.ALL, message, args);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @author admin
   */
  @Override
  public final void off(final String message) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.

    this.logCore(LogLevel.OFF, message);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 日志消息.
   * @param args 日志传递进来的参数.
   * @author admin
   */
  @Override
  public final void off(final String message, final Object... args) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.OFF, message, args);
  }
}
