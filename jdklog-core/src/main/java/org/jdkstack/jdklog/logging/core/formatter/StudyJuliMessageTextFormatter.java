package org.jdkstack.jdklog.logging.core.formatter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.core.manager.AbstractLogManager;
import org.jdkstack.jdklog.logging.core.manager.LogManagerUtils;

/**
 * 日志文本行格式化,扩展JDK提供的简单格式化.
 *
 * <p>按行输出纯文本的消息.
 *
 * @author admin
 */
public final class StudyJuliMessageTextFormatter extends AbstractMessageFormatter {
  /** . */
  private final DateTimeFormatter pattern;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public StudyJuliMessageTextFormatter() {
    // 获取当前处理器配置的格式化.
    final String name = StudyJuliMessageTextFormatter.class.getName();
    String timeFormat = AbstractLogManager.getProperty1(name + Constants.DATETIME_FORMAT_NAME);
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
  public StudyJuliMessageTextFormatter(final String timeFormat) {
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
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord 需要格式化的消息.
   * @return 返回格式化后的消息.
   * @author admin
   */
  @Override
  public String format(final Record logRecord) {
    // UTC时区获取当前系统的日期.
    final Instant instant = logRecord.getInstant();
    final ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, ZoneOffset.UTC);
    // 日期格式化.
    final String format = this.pattern.format(zdt);
    // 组装完整的日志消息.100->16试试看.
    final StringBuilder sb = new StringBuilder(16);
    // 拼接日期时间格式化.
    sb.append(format);
    sb.append(' ');
    // 日志级别.
    final String levelName = logRecord.getLevelName();
    sb.append(levelName);
    sb.append(' ');
    sb.append('[');
    // 当前执行的线程名.
    final Thread thread = Thread.currentThread();
    final String name = thread.getName();
    sb.append(name);
    sb.append(']');
    sb.append(' ');
    // 日志由哪个类打印的.
    final String sourceClassName = logRecord.getSourceClassName();
    sb.append(sourceClassName);
    sb.append(' ');
    // 日志由哪个方法打印的.
    final String sourceMethodName = logRecord.getSourceMethodName();
    sb.append(sourceMethodName);
    sb.append(' ');
    // 日志方法行.
    final int lineNumber = logRecord.getLineNumber();
    sb.append(lineNumber);
    sb.append(' ');
    final String unique = LogManagerUtils.getProperty(Constants.UNIQUE, Constants.FALSE);
    if (unique.equals(Constants.TRUE)) {
      final String traceId = logRecord.getTraceId();
      sb.append(traceId);
      sb.append(' ');
      final String spanId0 = logRecord.getSpanId0();
      sb.append(spanId0);
      sb.append(' ');
      final String spanId1 = logRecord.getSpanId1();
      sb.append(spanId1);
      sb.append(' ');
    }
    // 日志序列号.
    final long serialNumber = logRecord.getSerialNumber();
    sb.append(serialNumber);
    sb.append(' ');
    // 日志自定义字段.
    final Map<String, String> customs = logRecord.getCustoms();
    for (final Map.Entry<String, String> entry : customs.entrySet()) {
      final String value = entry.getValue();
      sb.append(value);
      sb.append(' ');
    }
    // 首先兼容JDK原生的日志格式,然后进行格式化处理.
    final String message = defaultFormat(logRecord);
    // 已经格式化后的日志消息.
    sb.append(message);
    // 如果有异常堆栈信息,则打印出来.
    final Throwable thrown = logRecord.getThrown();
    if (null != thrown) {
      sb.append(' ');
      sb.append('[');
      sb.append(thrown);
      sb.append(',');
      String separator = "";
      final StackTraceElement[] stackTraceElements = thrown.getStackTrace();
      for (final StackTraceElement stackTraceElement : stackTraceElements) {
        sb.append(separator);
        sb.append(stackTraceElement);
        separator = ",";
      }
      sb.append(']');
    }
    // 系统换行符.
    final String lineSeparator = System.lineSeparator();
    sb.append(lineSeparator);
    return sb.toString();
  }
}
