package org.jdkstack.jdklog.logging.core.factory;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public final class Constants {
  /** . */
  public static final String JULI_CONFIG_EXCEPTION_MESSAGE =
      "SPI服务没有读取到任何实现,调试查看BuiltinClassLoader类1045行checkURL方法.";
  /** 日志消息中有多个大括号对. */
  public static final String BRACE = "{}";
  /** 调用栈的元素号,需要跟方法的调用栈的号码一致,如果方法嵌套改变,这个号码需要增加或者减少. */
  public static final int STACK_TRACE_ELEMENT = 4;

  private Constants() {
    //
  }
}
