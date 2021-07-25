package org.jdkstack.jdklog.logging.core.formatter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.jdkstack.jdklog.logging.api.context.Bean;
import org.jdkstack.jdklog.logging.api.context.StudyThreadImpl;
import org.jdkstack.jdklog.logging.api.formatter.Formatter;
import org.jdkstack.jdklog.logging.api.manager.LoaderLogInfo;
import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.core.manager.AbstractLogManager;

/**
 * 日志格式化工具.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public abstract class AbstractMessageFormatter implements Formatter {
  /** . */
  protected DateTimeFormatter pattern;

  protected AbstractMessageFormatter() {
    // 获取运行时的类,并获取简单名.
    String simpleName = this.getClass().getSimpleName();
    // 根据简单名获取时间格式字符串.
    String timeFormat =
        AbstractLogManager.getProperty1(simpleName + Constants.DATETIME_FORMAT_NAME);
    // 如果为空.
    if (Objects.isNull(timeFormat)) {
      // 使用默认的格式化.
      timeFormat = Constants.DATETIME_FORMAT_VALUE;
    }
    // 创建一个日期时间格式化实例.
    this.pattern = DateTimeFormatter.ofPattern(timeFormat);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param timeFormat 日期格式化.
   * @author admin
   */
  protected AbstractMessageFormatter(final String timeFormat) {
    // 如果为空.
    if (Objects.isNull(timeFormat)) {
      // 使用默认的格式化.
      this.pattern = DateTimeFormatter.ofPattern(Constants.DATETIME_FORMAT_VALUE);
    } else {
      // 使用自定义的格式化.
      this.pattern = DateTimeFormatter.ofPattern(timeFormat);
    }
  }

  /**
   * 兼容JDK原生的日志格式.
   *
   * <p>对新的日志格式来说,性能上几乎0损耗,运行1亿方法,耗时5毫秒(只做是否检查).
   *
   * <p>java.util.logging.Formatter 136行.
   *
   * @param logRecord .
   * @return String.
   * @author admin
   */
  protected String defaultFormat(final Record logRecord) {
    // 得到日志消息.
    final String message = logRecord.getMessage();
    // 默认是一个空数组,不是空对象.
    final List<Object> parameters = logRecord.getParameters();
    // 参数的值个数,按照个数进行参数替换.
    final int size = parameters.size();
    // 拷贝一份,在临时对象上进行操作.
    String messageTemp = message;
    // 循环替换,这个方式相对于格式化对象MessageFormat.format来说性能好一些 .
    for (int i = 0; i < size; i++) {
      // 获取参数的值.
      final Object o = parameters.get(i);
      final String s = String.valueOf(o);
      // 获取参数占位符.
      final String msg = getMsg(i);
      // 用参数的值替换参数占位符.
      messageTemp = messageTemp.replace(msg, s);
    }
    // 返回格式化后的消息字符串.
    return messageTemp;
  }

  private static String getMsg(final int i) {
    return Constants.LEFT + i + Constants.RIGHT;
  }

  /**
   * 一亿次调用大概2.5秒.
   *
   * <p>Another description after blank line.
   *
   * @return boolean .
   * @author admin
   */
  protected static boolean checkUnique() {
    final Map<ClassLoader, LoaderLogInfo> classLoaderLoggers =
        AbstractLogManager.getClassLoaderLoggers1();
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    final LoaderLogInfo classLoaderLogInfo = classLoaderLoggers.get(classLoader);
    final String unique = classLoaderLogInfo.getProperty(Constants.UNIQUE, Constants.FALSE);
    return Boolean.parseBoolean(unique);
  }

  /**
   * 自定义字段填充.
   *
   * <p>.
   *
   * <p>.
   *
   * @param logRecord .
   * @author admin
   */
  protected Map<String, String> before(final Record logRecord) {
    final Map<String, String> customs = new LinkedHashMap<>(16);
    // UTC时区获取当前系统的日期.
    final Instant instant = logRecord.getInstant();
    final ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
    // 日期格式化.
    final String format = this.pattern.format(zdt);
    // 拼接日期时间格式化.
    customs.put("timestamp", format);
    // 日志级别.
    final String levelName = logRecord.getLevelName();
    customs.put("levelName", levelName);
    // 当前执行的线程名.
    final Thread thread = Thread.currentThread();
    final String name = thread.getName();
    customs.put("thread", name);
    return customs;
  }

  /**
   * 自定义字段填充.
   *
   * <p>.
   *
   * <p>.
   *
   * @param logRecord .
   * @author admin
   */
  protected Map<String, String> after(final Record logRecord) {
    final Map<String, String> customs = new LinkedHashMap<>(16);
    final Thread thread = Thread.currentThread();
    // 打印特殊字段.
    if (checkUnique()) {
      // 如果不是StudyThread,无法处理唯一日志消息ID.
      if (thread instanceof StudyThreadImpl) {
        final StudyThreadImpl studyThread = (StudyThreadImpl) thread;
        Bean contextBean = studyThread.getContextBean();
        final String traceId = contextBean.getTraceId();
        customs.put("traceId", traceId);
        final String spanId0 = contextBean.getSpanId0();
        customs.put("spanId0", spanId0);
        final String spanId1 = contextBean.getSpanId1();
        customs.put("spanId1", spanId1);
      }
    }
    return customs;
  }
}
