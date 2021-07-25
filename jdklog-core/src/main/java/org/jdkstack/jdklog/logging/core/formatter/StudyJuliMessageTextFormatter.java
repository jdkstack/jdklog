package org.jdkstack.jdklog.logging.core.formatter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Map;
import org.jdkstack.jdklog.logging.api.context.Bean;
import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.core.manager.LogManagerUtils;

/**
 * 日志文本行格式化,扩展JDK提供的简单格式化.
 *
 * <p>按行输出纯文本的消息.
 *
 * @author admin
 */
public final class StudyJuliMessageTextFormatter extends AbstractMessageFormatter {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public StudyJuliMessageTextFormatter() {
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
  public StudyJuliMessageTextFormatter(final String timeFormat) {
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
    // 日志自定义字段.
    final Map<String, String> customs = logRecord.getCustoms();
    for (final Map.Entry<String, String> entry : customs.entrySet()) {
      final String value = entry.getValue();
      sb.append(value);
      sb.append(' ');
    }
    // 打印特殊字段.
    final String unique = LogManagerUtils.getProperty(Constants.UNIQUE, Constants.FALSE);
    if (unique.equals(Constants.TRUE)) {
      final Bean contextBean = logRecord.getContextBean();
      final String traceId = contextBean.getTraceId();
      sb.append(traceId);
      sb.append(' ');
      final String spanId0 = contextBean.getSpanId0();
      sb.append(spanId0);
      sb.append(' ');
      final String spanId1 = contextBean.getSpanId1();
      sb.append(spanId1);
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
