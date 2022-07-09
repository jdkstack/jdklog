package org.jdkstack.jdklog.logging.core.utils;

/**
 * 转义字符(Escape character),javase 11.
 *
 * <p>转义序列(Escape Sequences)允许在不使用Unicode转义的情况下表示某些非图形字符.
 *
 * <p><a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-3.10.6">...</a>
 *
 * <p><a href="https://docs.oracle.com/javase/specs/jls/se11/html/jls-3.html#jls-3.3">...</a>
 *
 * <p>需要考虑unicode字符转义和八进制字符转义.
 *
 * <pre>
 *  // unicode 开始位置.
 *  int start = index;
 *  // unicode 结束位置.
 *  index += 4;
 *  //截取完整的unicode.
 *  String unicode = str.substring(start + 1, index + 1);
 *  int value = Integer.parseInt(unicode, 16);
 *  sb.append((char) value);
 * </pre>
 *
 * <pre>
 *    // 转义原始符号(c>=32 && c<=127).
 *     if (c >= 32 && c <= 127) {
 *       //
 *
 *     }
 *     if (c > 0xfff) {
 *       // 转义原始字符(c>4095).
 *       // sb.append("\\u").append(hex(c));
 *     }
 *     if (c > 0xff) {
 *       // 转义原始字符(c>255 && c<=4095).
 *       // sb.append("\\u0").append(hex(c));
 *     }
 *     if (c > 0x7f) {
 *       // 转义原始字符(c>127 && c<=255).
 *       // sb.append("\\u00").append(hex(c));
 *     }
 *     if (c > 0xf && c < 32) {
 *       // 转义原始字符(c>15 && c<32).
 *       // sb.append("\\u00").append(hex(c));
 *     }
 *     if (c <= 0xf) {
 *       // 转义原始字符(c<=15).
 *       // sb.append("\\u000").append(hex(c));
 *     }
 * </pre>
 *
 * @author admin
 */
public final class MessageHandlerUtils {

  private MessageHandlerUtils() {
    //
  }

  /**
   * 原始字符串->转义字符串.
   *
   * <p>原始字符,原始字符串,转义字符,转义字符串四个词语需要理解.
   *
   * @param str 原始字符串.
   * @return 返回转义字符串(Escape String).
   * @author admin
   */
  public static String encode(final String str) {
    // 原始字符串长度.
    final int len = str.length();
    // 转义字符串.
    final StringBuilder sb = new StringBuilder(len);
    // 当前被处理的原始字符位置.
    int index = 0;
    // 循环处理每一个原始字符.
    while (index < len) {
      // 获取一个原始字符.
      final char c = str.charAt(index);
      // 处理所有原始字符.
      switch (c) {
        case '\b':
          // 转义\b.
          sb.append('\\').append('b');
          break;
        case '\t':
          // 转义\t.
          sb.append('\\').append('t');
          break;
        case '\n':
          // 转义\n.
          sb.append('\\').append('n');
          break;
        case '\f':
          // 转义\f.
          sb.append('\\').append('f');
          break;
        case '\r':
          // 转义\r.
          sb.append('\\').append('r');
          break;
        case '"':
          // 转义双引号.
          sb.append('\\').append('"');
          break;
        case '\'':
          // 转义单引号.
          sb.append('\\').append('\'');
          break;
        case '\\':
          // 转义反斜杠.
          sb.append('\\').append('\\');
          break;
        default:
          // 剩下的所有原始字符都不转义.
          sb.append(c);
          break;
      }
      index++;
    }
    return sb.toString();
  }

  /**
   * 转义字符串->原始字符串.
   *
   * <p>Another description after blank line.
   *
   * @param str 转义字符串(Escape String).
   * @return 返回原始字符串.
   * @author admin
   */
  public static String decode(final String str) {
    // 转义字符串长度.
    final int len = str.length();
    // 原始字符串.
    final StringBuilder sb = new StringBuilder(len);
    // 当前被处理的转义字符位置.
    int index = 0;
    // 循环处理每一个转义字符.
    while (index < len) {
      // 获取当前位置的转义字符.
      final char ch = str.charAt(index);
      // 处理转义字符.
      if (ch == '\\') {
        // 移动到下一个转义字符.
        index++;
        // 得到下一个转义字符.
        final char next = str.charAt(index);
        // 处理下一个转义字符.
        switch (next) {
            // 下一个转义字符是反斜杠.
          case '\\':
            sb.append('\\');
            break;
            // 下一个转义字符是单引号.
          case '\'':
            sb.append('\'');
            break;
            // 下一个转义字符是双引号.
          case '\"':
            sb.append('"');
            break;
            // 下一个转义字符是r.
          case 'r':
            sb.append('\r');
            break;
            // 下一个转义字符是f.
          case 'f':
            sb.append('\f');
            break;
            // 下一个转义字符是t.
          case 't':
            sb.append('\t');
            break;
            // 下一个转义字符是n.
          case 'n':
            sb.append('\n');
            break;
            // 下一个转义字符是b.
          case 'b':
            sb.append('\b');
            break;
            // 下一个转义字符是u.
          case 'u':
            // 不处理unicode字符.
            sb.append("\\").append("u");
            break;
            // 下一个转义字符是正常字符.
          default:
            sb.append(next);
            break;
        }
        // 不处理非转义字符.
      } else {
        sb.append(ch);
      }
      // 处理下一个转义字符.
      index++;
    }
    return sb.toString();
  }

  /**
   * 字符->16进制字符串.
   *
   * <p>Another description after blank line.
   *
   * @param unicodeChar unicode字符.
   * @return 16进制字符串.
   * @author admin
   */
  public static String toHex(final char unicodeChar) {
    // unicode字符->16进制字符串.
    return Integer.toHexString(unicodeChar);
  }

  /**
   * 16进制字符串->字符.
   *
   * <p>Another description after blank line.
   *
   * @param str 16进制字符串.
   * @return unicode字符.
   * @author admin
   */
  public static char toChar(final String str) {
    // 16进制字符串->unicode码点.
    int codePoint = Integer.parseInt(str, 16);
    // 转成.
    char[] unicodeChars = Character.toChars(codePoint);
    // unicode码点->unicode字符.
    return unicodeChars[0];
  }
}
