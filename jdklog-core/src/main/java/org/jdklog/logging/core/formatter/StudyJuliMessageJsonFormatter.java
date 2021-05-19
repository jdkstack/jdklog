package org.jdklog.logging.core.formatter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import org.jdklog.logging.api.metainfo.Record;
import org.jdklog.logging.core.manager.AbstractLogManager;

/**
 * 日志文本行Json格式化,扩展JDK提供的简单格式化.
 *
 * <p>按行输出纯json格式的消息.
 *
 * @author admin
 */
public final class StudyJuliMessageJsonFormatter extends AbstractMessageFormatter {
  /** . */
  private final DateTimeFormatter pattern;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public StudyJuliMessageJsonFormatter() {
    // 获取当前处理器配置的格式化.
    final String name = StudyJuliMessageJsonFormatter.class.getName();
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
  public StudyJuliMessageJsonFormatter(final String timeFormat) {
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
    final StringBuilder sb = new StringBuilder(50);
    final String timestamp = inQuotes("timestamp");
    final String formatStr = inQuotes(format);
    sb.append('{').append(timestamp).append(": ").append(formatStr).append(',');
    // 日志级别.
    final String level = inQuotes("level");
    final String levelName = logRecord.getLevelName();
    final String levelNameStr = inQuotes(levelName);
    sb.append(level).append(": ").append(levelNameStr).append(',');
    // 线程名称.
    final String threadKey = inQuotes("thread");

    final Thread thread = Thread.currentThread();
    final String name = thread.getName();
    final String threadName = inQuotes(name);
    sb.append(threadKey).append(": ").append(threadName).append(',');
    // 日志当前的类全限定名.
    final String fullClassPath = inQuotes("fullClassPath");
    final String sourceClassName = logRecord.getSourceClassName();
    final String sourceClassNameStr = inQuotes(sourceClassName);
    sb.append(fullClassPath).append(": ").append(sourceClassNameStr).append(',');
    // 日志当前类的方法.
    final String methodKey = inQuotes("method");
    final String methodStr = logRecord.getSourceMethodName();
    final String methodValue = inQuotes(methodStr);
    sb.append(methodKey).append(": ").append(methodValue).append(',');
    // 行号.
    final String lineNumberKey = inQuotes("lineNumber");
    final int lineNumber = logRecord.getLineNumber();
    final String lineNumberStr = String.valueOf(lineNumber);
    final String lineNumberValue = inQuotes(lineNumberStr);
    sb.append(lineNumberKey).append(": ").append(lineNumberValue).append(',');
    if (checkUnique()) {
      final String uniqueIdKey = inQuotes("uniqueId");
      final String uniqueIdStr = logRecord.getUniqueId();
      final String uniqueIdValue = inQuotes(uniqueIdStr);
      sb.append(uniqueIdKey).append(": ").append(uniqueIdValue).append(',');
    }
    final String serialNumberKey = inQuotes("serialNumber");
    final long serialNumber = logRecord.getSerialNumber();
    sb.append(serialNumberKey).append(": ").append(serialNumber).append(',');
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
