package org.jdklog.logging.core.filter;

import org.jdklog.logging.api.filter.Filter;
import org.jdklog.logging.api.metainfo.Record;

/**
 * 对日志进行过滤.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class StudyJuliFilter implements Filter {

  /**
   * 默认空的日志拦截器.
   *
   * <p>Another description after blank line.
   *
   * @param logRecord 消息记录.
   * @return boolean true代表日志消息丢弃.
   * @author admin
   */
  @Override
  public final boolean isLoggable(final Record logRecord) {
    return null == logRecord.getLevel();
  }
}
