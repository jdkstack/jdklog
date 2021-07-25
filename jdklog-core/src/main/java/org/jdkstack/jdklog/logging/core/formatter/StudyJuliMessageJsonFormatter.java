package org.jdkstack.jdklog.logging.core.formatter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import org.jdkstack.jdklog.logging.api.context.Bean;
import org.jdkstack.jdklog.logging.api.metainfo.Record;

/**
 * 日志文本行Json格式化,扩展JDK提供的简单格式化.
 *
 * <p>按行输出纯json格式的消息.
 *
 * @author admin
 */
public final class StudyJuliMessageJsonFormatter extends AbstractMessageFormatter {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public StudyJuliMessageJsonFormatter() {
    //
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param timeFormat 日期格式化.
   * @author admin
   */
  public StudyJuliMessageJsonFormatter(final String timeFormat) {
    super(timeFormat);
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
    logRecord.setCustom("timestamp", format);
    // 日志级别.
    // final String level = inQuotes("level");
    final String levelName = logRecord.getLevelName();
    logRecord.setCustom("level", levelName);
    // 线程名称.
    final Thread thread = Thread.currentThread();
    final String name = thread.getName();
    logRecord.setCustom("thread", name);
    if (checkUnique()) {
      final Bean contextBean = logRecord.getContextBean();
      final String traceIdStr = contextBean.getTraceId();
      logRecord.setCustom("traceId", traceIdStr);
      final String spanId0Str = contextBean.getSpanId0();
      logRecord.setCustom("spanId0", spanId0Str);
      final String spanId1Str = contextBean.getSpanId1();
      logRecord.setCustom("spanId1", spanId1Str);
    }
    final StringBuilder sb = new StringBuilder(50);
    // 日志自定义字段.
    final Map<String, String> customs = logRecord.getCustoms();
    for (final Map.Entry<String, String> entry : customs.entrySet()) {
      final String key = entry.getKey();
      final String value = entry.getValue();
      final String customKey = inQuotes(key);
      sb.append(customKey).append(": ");
      final String customValue = inQuotes(value);
      sb.append(customValue);
      sb.append(',');
    }
    // 首先兼容JDK原生的日志格式,然后进行格式化处理.
    final String message = defaultFormat(logRecord);
    final String messageKey = inQuotes("message");
    final String messageValue = inQuotes(message);
    sb.append(messageKey).append(": ").append(messageValue);
    // 如果有异常堆栈信息,则打印出来.
    final Throwable thrown = logRecord.getThrown();
    if (null != thrown) {
      final String stacktrace = inQuotes("stacktrace");
      final String thrownStr = thrown.toString();
      final String thrownStr2 = inQuotes(thrownStr);
      sb.append(',').append(stacktrace).append(": ").append('[').append(thrownStr2).append(',');
      String separator = "";
      final StackTraceElement[] stackTraceElements = thrown.getStackTrace();
      for (final StackTraceElement stackTraceElement : stackTraceElements) {
        final String stackTraceElementStr = stackTraceElement.toString();
        final String stackTraceElementStr2 = inQuotes(stackTraceElementStr);
        sb.append(separator).append(stackTraceElementStr2);
        separator = ",";
      }
      sb.append(']');
    }
    // 增加一个换行符号(按照平台获取)
    final String lineSeparator = System.lineSeparator();
    sb.append('}').append(lineSeparator);
    return sb.toString();
  }

  /**
   * This is a method description.
   *
   * @param s This is a param description.
   * @return This is a return description.
   * @author admin
   */
  private static String inQuotes(final String s) {
    return '"' + s + '"';
  }
}
