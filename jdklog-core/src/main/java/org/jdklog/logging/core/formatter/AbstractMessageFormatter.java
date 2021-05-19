package org.jdklog.logging.core.formatter;

import java.util.List;
import java.util.Map;
import org.jdklog.logging.api.formatter.Formatter;
import org.jdklog.logging.api.manager.LoaderLogInfo;
import org.jdklog.logging.api.metainfo.Record;
import org.jdklog.logging.core.manager.AbstractLogManager;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public abstract class AbstractMessageFormatter implements Formatter {

  protected AbstractMessageFormatter() {
    //
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
  protected static String defaultFormat(final Record logRecord) {
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
    final Map<ClassLoader, LoaderLogInfo> classLoaderLoggers =  AbstractLogManager.getClassLoaderLoggers1();
    final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    final LoaderLogInfo classLoaderLogInfo = classLoaderLoggers.get(classLoader);
    final String unique = classLoaderLogInfo.getProperty(Constants.UNIQUE, Constants.FALSE);
    return Boolean.parseBoolean(unique);
  }
}
