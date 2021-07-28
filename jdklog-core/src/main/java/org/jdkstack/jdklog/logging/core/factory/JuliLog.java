package org.jdkstack.jdklog.logging.core.factory;

import org.jdkstack.jdklog.logging.api.metainfo.LogLevel;

/**
 * Juli日志的核心API,提供所有日志级别的方法,由LogFactory动态创建.
 *
 * <p>每当使用LogFactory.getLog方法时都会创建一个Logging对象,并绑定到JDK Logger对象.
 *
 * @author admin
 */
public class JuliLog extends AbstractJuliLog {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public JuliLog() {
    //
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
    super(name);
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
   * @param args 日志传递进来的参数.
   * @author admin
   */
  @Override
  public final void error(final String message, final Object... args) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.SEVERE, message, args);
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
   * @param args 日志传递进来的参数.
   * @author admin
   */
  @Override
  public final void off(final String message, final Object... args) {
    // Logger的日志级别优先过滤不合法的日志,之后用处理器handler的日志级别过滤不合法的日志,最后才是Filter自定义过滤.
    this.logCore(LogLevel.OFF, message, args);
  }
}
