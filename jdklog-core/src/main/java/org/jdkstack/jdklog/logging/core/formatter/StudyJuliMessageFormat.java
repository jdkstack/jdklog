package org.jdkstack.jdklog.logging.core.formatter;

import java.util.regex.Pattern;

/**
 * 对消息进行处理,增强以后,传递给消息格式化.
 *
 * <p>用参数替换掉消息中的 "{}" 大括号.
 *
 * @author admin
 */
public final class StudyJuliMessageFormat {
  /** 采用模式匹配的方式性能更高,相比较字符串切割方法. */
  private static final Pattern COMPILE = Pattern.compile("\\{}");

  private StudyJuliMessageFormat() {
    //
  }

  /**
   * This is a method description.
   *
   * @param message This is a param description.
   * @param args This is a param description.
   * @return This is a return description.
   * @author admin
   */
  public static String format(final String message, final Object... args) {
    // 获取消息中大括号的数量,-1的意思是严格进行分割,即使最后一个是空值,使用模式匹配是字符串切割的2倍性能.
    final String[] braces = COMPILE.split(message, -1);
    // 大括号的数量.
    final int braceCount = braces.length - 1;
    final int argsLen = args.length;
    // 大括号的数量如果大于参数数量,会出现越界的异常.
    if (braceCount <= argsLen) {
      // 消息长度增加初始化为原消息长度.
      final int length = message.length();
      final StringBuilder sb = new StringBuilder(length);
      // 将参数替换消息中的大括号.
      for (int i = 0; i < braceCount; i++) {
        sb.append(braces[i]);
        sb.append(args[i]);
      }
      return sb.toString();
    } else {
      // 不处理错误的括号和参数(数量不匹配),返回原消息.
      return message;
    }
  }
}
