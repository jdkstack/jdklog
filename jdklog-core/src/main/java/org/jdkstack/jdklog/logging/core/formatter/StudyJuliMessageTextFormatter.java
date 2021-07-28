package org.jdkstack.jdklog.logging.core.formatter;

import java.util.Map;
import org.jdkstack.jdklog.logging.api.metainfo.Record;

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
    // 最终的日志消息.
    final StringBuilder sb = new StringBuilder(50);
    // 处理之前.
    handle(sb, before(logRecord));
    // 日志自定义字段.
    handle(sb, logRecord.getCustoms());
    // 处理之后.
    handle(sb, after());
    // 处理消息.
    handlerMessage(logRecord, sb);
    // 如果有异常堆栈信息,则打印出来.
    handlerThrowable(logRecord, sb);
    // 系统换行符.
    final String lineSeparator = System.lineSeparator();
    sb.append(lineSeparator);
    return sb.toString();
  }

  private void handlerMessage(final Record logRecord, final StringBuilder sb) {
    // 首先兼容JDK原生的日志格式,然后进行格式化处理.
    final String message = defaultFormat(logRecord);
    // 已经格式化后的日志消息.
    sb.append(message);
  }

  private void handlerThrowable(final Record logRecord, final StringBuilder sb) {
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
  }

  private void handle(final StringBuilder sb, final Map<String, String> customs) {
    // 循环处理自定义字段.
    for (final Map.Entry<String, String> entry : customs.entrySet()) {
      final String value = entry.getValue();
      sb.append(value);
      sb.append(' ');
    }
  }
}
