package org.jdkstack.jdklog.logging.core.manager;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.logger.Logger;
import org.jdkstack.jdklog.logging.api.manager.LoaderLogInfo;
import org.jdkstack.jdklog.logging.api.metainfo.Level;
import org.jdkstack.jdklog.logging.api.metainfo.LogLevel;
import org.jdkstack.jdklog.logging.core.utils.ClassLoadingUtils;

/**
 * 增强JDK默认的日志管理(需要优化).
 *
 * <p>Another description after blank line.
 *
 * <p>Classes should not be loaded dynamically(动态加载类).
 *
 * @author admin
 */
public final class StudyJuliLogManager extends AbstractLogManager {

  /** 自定义锁,用于在对配置文件CRUD时锁定业务方法. */
  private static final Lock CONFIGURATIONLOCK = new ReentrantLock();

  /**
   * Logger代表使用日志的类,比如在类中创建日志对象,并打印日志消息,那么这个类就叫Logger.
   *
   * <p>这个方法需要仔细研究,重构,目前业务逻辑清晰,但是代码很糟糕. @SuppressWarnings({"java:S3776"})
   *
   * @author admin
   */
  @Override
  public boolean addLogger(final Logger logger) {
    // 得到类全路径,org.jdkstack.jdklog.example.Examples .
    final String loggerName = logger.getName();
    // 得到类包路径,org.jdkstack.jdklog.example .
    String packageName = "";
    // 获取这个类 当前的包名.
    final int dotIndex = loggerName.lastIndexOf('.');
    // 如果当前Logger名使用的是类的全路径.
    if (0 <= dotIndex) {
      // 获取到当前Logger名中包的最长路径(去掉类的最简单名).
      packageName = loggerName.substring(0, dotIndex);
    }
    // 1.首先看这个类以前是不是加过.
    if (this.containsKey(loggerName)) {
      // 2.如果加过,直接跳过.
      return false;
    }
    // 得到当前的类加载器.
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    // 3.如果没加过.首先看类配置没有,如果配置不看包.
    final boolean extracted = this.isExtracted(logger, classLoader, loggerName);
    // 如果配置项存在当前Logger的名.
    if (extracted) {
      // 直接注册这个logger.
      this.put(loggerName, logger);
      // 4.如果配置项不存在当前Logger的名,使用包路径,再试试.
    } else {
      // 首先查看包是不是加载路.
      if (this.containsKey(packageName)) {
        this.extracted(logger, loggerName, packageName);
      } else {
        // 首先查看包没有加载路.首先尝试获取包的配置项.
        final boolean extracted1 = this.isExtracted(logger, classLoader, packageName);
        // 如果成功获取了包的配置项目.
        if (extracted1) {
          // 添加当前Logger.
          this.put(packageName, logger);
        } else {
          this.extracted(logger, loggerName);
        }
      }
    }
    return true;
  }

  private void extracted(final Logger logger, final String loggerName, final String packageName) {
    // 如果包加载过.
    final Logger packageNameLogger = this.getLogger1(packageName);
    // 获取到这个包下的所有处理器.
    final Handler[] handlers = packageNameLogger.getHandlers();
    // 循环每个处理器.
    for (final Handler h : handlers) {
      // 将包的处理器全部添加到当前Logger.
      logger.addHandler(h);
    }
    // 将当前的日志级别设置成包的日志级别.
    final Level level = packageNameLogger.getLevel();
    logger.setLevel(level);
    // 添加当前Logger.
    this.put(loggerName, logger);
  }

  private void extracted(final Logger logger, final String loggerName) {
    // 5.如果包没配置,使用默认的全局的.获取全局的Logger.
    final Logger rootLogger = this.getLogger1("");
    // 看看rootLogger是否存在.
    if (null != rootLogger) {
      // 如果存在,获取rootLogger的所有处理器.
      final Handler[] rootHandlers = rootLogger.getHandlers();
      // 循环rootLogger的所有处理器.
      for (final Handler rootHandler : rootHandlers) {
        // 添加每一个处理器到当前Logger中.
        logger.addHandler(rootHandler);
      }
      // 将当前的日志级别设置成包的日志级别.
      final Level level = rootLogger.getLevel();
      logger.setLevel(level);
    } else {
      // 当前类加载器对象的日志信息. 如果存在,获取rootLogger的所有处理器.
      final Logger rootLogger1 = this.getRootLogger();
      final Handler[] rootHandlers = rootLogger1.getHandlers();
      // 循环rootLogger的所有处理器.
      for (final Handler rootHandler : rootHandlers) {
        // 添加每一个处理器到当前Logger中.
        logger.addHandler(rootHandler);
      }
      // 将当前的日志级别设置成包的日志级别.
      final Logger rootLogger2 = this.getRootLogger();
      final Level level = rootLogger2.getLevel();
      logger.setLevel(level);
    }
    // 添加当前Logger.
    this.put(loggerName, logger);
  }

  /**
   * 方法需要重构,暂时不处理.
   *
   * <p>Another description after blank line.
   *
   * @param logger logger.
   * @param classLoader classLoader.
   * @param loggerName loggerName.
   * @return boolean.
   * @author admin
   */
  private boolean isExtracted(
      final Logger logger, final ClassLoader classLoader, final String loggerName) {
    // 获取当前Logger的日志级别.
    final String levelString = this.getProperty(loggerName + Constants.LEVEL);
    // 如果日志级别没配置,直接采用全局配置的日志级别.
    final boolean isLevelString = Objects.nonNull(levelString);
    if (isLevelString) {
      // 设置日志级别到当前Logger.
      final String trim = levelString.trim();
      final Level level = LogLevel.findLevel(trim);
      logger.setLevel(level);
    } else {
      // 获取当前类加载器下存储的信息.获取全局的Logger.
      final Logger rootLogger = this.getRootLogger();
      // 将当前的日志级别设置成包的日志级别.
      final Level level = rootLogger.getLevel();
      logger.setLevel(level);
    }
    // 获取日志处理器,并注册到当前logger,如果没有日志处理器,可以考虑当前包的日志处理器.如果包也没有配置,则考虑使用全局配置的处理器.
    final String handlers = this.getProperty(loggerName + Constants.HANDLERS);
    if (null != handlers) {
      // 默认当前Logger会继承父类的.但是这个handlers处理全局handlers配置,不需要继承.
      logger.setUseParentHandlers(false);
      // 得到所有的全局handlers处理器.
      final StringTokenizer tok = new StringTokenizer(handlers, ",");
      // 获取每一个全局handlers.
      while (tok.hasMoreTokens()) {
        // 获取handler的处理器名称.
        final String handlerName = tok.nextToken().trim();
        // 获取当前类加载器下存储的信息.
        final LoaderLogInfo info = this.get(classLoader);
        // 根据handler名获取处理器.
        final Handler handler = info.getHandler(handlerName);
        // 如果全局处理器不为空,添加这个全局处理器到当前Logger.
        if (null != handler) {
          logger.addHandler(handler);
        }
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name .
   * @return Logger
   * @author admin
   */
  @Override
  public Logger getLogger(final String name) {
    return this.getLogger1(name);
  }

  /**
   * 读取日志的配置文件,日志管理器必须和日志文件一起配置.
   *
   * <p>Another description after blank line.
   *
   * @throws SecurityException 由JDK框架处理.
   * @author admin
   */
  @Override
  public void readConfiguration() {
    // 获取日志配置文件.
    final String configFileStr = System.getProperty(Constants.CONFIG_FILE);
    // 获取当前执行线程的类加载器.
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    final LoaderLogInfo info = new ClassLoaderLogInfo();
    this.put(classLoader, info);
    // 如果配置为空,配置默认的日志级别和处理器.
    if (null == configFileStr) {
      info.setProperty(Constants.LEVEL, Constants.DEFAULT_LEVEL);
      info.setProperty(Constants.HANDLERS, Constants.DEFAULT_HANDLERS_SINGLE);
    } else {
      final File file = new File(configFileStr);
      final Path target = file.toPath();
      // 读取日志Properties文件,然后关闭IO,try(){}语法会自动关闭.
      CONFIGURATIONLOCK.lock();
      try (final InputStream inputStream = Files.newInputStream(target)) {
        info.load(inputStream);
      } catch (final Exception e) {
        throw new StudyJuliRuntimeException("自定义配置文件加载异常.", e);
      } finally {
        CONFIGURATIONLOCK.unlock();
      }
    }
    // 设置全局的日志级别.
    final String level = info.getProperty(Constants.LEVEL);
    info.setLevel(level);
    // 获取全局的.handlers.
    final String rootHandlers = info.getProperty(Constants.HANDLERS);
    // 注册.handlers.
    handler(info, rootHandlers, true);
    // 获取单个类或者单个包handlers.
    final String handlers = info.getProperty(Constants.HANDLERS_SINGLE);
    // 注册handlers.
    handler(info, handlers, false);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param info .
   * @param handlers .
   * @param flag .
   * @author admin
   */
  private static void handler(final LoaderLogInfo info, final String handlers, final boolean flag) {
    if (null != handlers) {
      final StringTokenizer tok = new StringTokenizer(handlers, ",");
      // 循环获取每一个handler,并得到前缀和handlers的className.
      while (tok.hasMoreTokens()) {
        final String handlerName = tok.nextToken().trim();
        final Handler handler = registerHandler(info, handlerName);
        if (flag) {
          info.addHandler(handler);
        }
      }
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param info .
   * @param handlerName .
   * @return Handler 注册的处理器.
   * @author admin
   */
  private static Handler registerHandler(final LoaderLogInfo info, final String handlerName) {
    // 如果第一个字符不是数字.
    String handlerClassName = handlerName;
    // 前缀.
    String prefixTem = "study_juli.";
    // 如果第一个字符是数字.
    final char c = handlerClassName.charAt(0);
    if (Character.isDigit(c)) {
      // 得到第一个'.'位置.
      final int pos = handlerClassName.indexOf('.');
      // 如果找到了.
      if (0 <= pos) {
        // 得到前缀.
        prefixTem = handlerClassName.substring(0, pos + 1);
        // 得到真实的handler className.
        handlerClassName = handlerClassName.substring(pos + 1);
      }
    }
    try {
      // 反射创建一个处理器.
      final Constructor<?> constructor = ClassLoadingUtils.constructor2(handlerClassName);
      final Handler handler = (Handler) ClassLoadingUtils.newInstance(constructor, prefixTem);
      // 将处理器添加到系统类加载内.
      info.putIfAbsent(handlerName, handler);
      return handler;
    } catch (final Exception e) {
      throw new StudyJuliRuntimeException("初始化handler异常.", e);
    }
  }
}
