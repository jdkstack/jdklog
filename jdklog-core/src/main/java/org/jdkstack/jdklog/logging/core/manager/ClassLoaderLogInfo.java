package org.jdkstack.jdklog.logging.core.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.logger.Logger;
import org.jdkstack.jdklog.logging.api.manager.LoaderLogInfo;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.LogLevel;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ClassLoaderLogInfo implements LoaderLogInfo {
  /** 这个rootLogger是为了给系统初始化时的类使用的. */
  private final Logger rootLogger = new JuliLogger("RootLogger");
  /** 保存系统加载时所有的Logger. */
  private final Map<String, Logger> loggers = new ConcurrentHashMap<>(1000);
  /** 保存系统加载时所有的Handler,包括公用的以及自定义的. */
  private final Map<String, Handler> handlers = new ConcurrentHashMap<>(32);
  /** 保存系统加载时配置信息. */
  private final Properties props = new Properties();

  /**
   * 向rootLogger添加公用的处理器.
   *
   * <p>study_juli本身,study_juli依赖的库使用.
   *
   * <p>引用study_juli的程序使用""通用Logger.
   *
   * @param handler 通用的处理器.
   * @author admin
   */
  @Override
  public final void addHandler(final Handler handler) {
    // 向rootLogger添加公用的处理器.
    this.rootLogger.addHandler(handler);
  }

  /**
   * .
   *
   * <p>.
   *
   * @param levelName .
   * @author admin
   */
  @Override
  public final void setLevel(final String levelName) {
    final Level level = LogLevel.findLevel(levelName);
    this.rootLogger.setLevel(level);
  }

  @Override
  public final Logger getRootLogger() {
    return this.rootLogger;
  }

  @Override
  public final Map<String, Handler> getHandlers() {
    return Collections.unmodifiableMap(this.handlers);
  }

  @Override
  public final void setProperty(final String key, final String value) {
    this.props.setProperty(key, value);
  }

  @Override
  public final String getProperty(final String key, final String defaultValue) {
    return this.props.getProperty(key, defaultValue);
  }

  @Override
  public final String getProperty(final String key) {
    return this.props.getProperty(key);
  }

  @Override
  public final void load(final InputStream inputStream) throws IOException {
    this.props.load(inputStream);
  }

  @Override
  public final boolean isEmpty() {
    return this.props.isEmpty();
  }

  @Override
  public final boolean containsKey(final String loggerName) {
    return this.loggers.containsKey(loggerName);
  }

  @Override
  public final Logger get(final String name) {
    return this.loggers.get(name);
  }

  @Override
  public final void put(final String name, final Logger logger) {
    this.loggers.put(name, logger);
  }

  @Override
  public final void putIfAbsent(final String handlerName, final Handler handler) {
    this.handlers.putIfAbsent(handlerName, handler);
  }

  @Override
  public final Handler getHandler(final String handlerName) {
    return this.handlers.get(handlerName);
  }
}
