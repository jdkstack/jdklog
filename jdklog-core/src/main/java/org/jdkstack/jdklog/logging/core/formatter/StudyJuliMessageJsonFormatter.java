package org.jdkstack.jdklog.logging.core.formatter;

import java.util.Map;
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
    // 最终的日志消息.
    final StringBuilder sb = new StringBuilder(50);
    // json字符串开始.
    sb.append("{");
    // 处理之前.
    handle(sb, before(logRecord));
    // 日志自定义字段.
    handle(sb, logRecord.getCustoms());
    // 处理之后.
    handle(sb, after());
    // 首先兼容JDK原生的日志格式,然后进行格式化处理.
    final String message = defaultFormat(logRecord);
    // json key.
    final String messageKey = inQuotes("message");
    // json value.
    final String messageValue = inQuotes(message);
    // 已经格式化后的日志消息.
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
    // json字符串结束.
    sb.append('}').append(lineSeparator);
    return sb.toString();
  }

  private void handle(final StringBuilder sb, final Map<String, String> customs) {
    // 循环处理自定义字段.
    for (final Map.Entry<String, String> entry : customs.entrySet()) {
      final String key = entry.getKey();
      final String value = entry.getValue();
      final String customKey = inQuotes(key);
      sb.append(customKey).append(": ");
      final String customValue = inQuotes(value);
      sb.append(customValue);
      sb.append(',');
    }
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
