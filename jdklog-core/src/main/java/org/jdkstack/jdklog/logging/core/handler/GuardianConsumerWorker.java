package org.jdkstack.jdklog.logging.core.handler;

import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.worker.StudyWorker;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class GuardianConsumerWorker implements StudyWorker<Handler> {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void handle(final Handler handler) {
    // 得到处理器最后一条日志消息的时间.
    final long sys = handler.getSys();
    // 得到当前系统的时间.
    final long currentTime = System.currentTimeMillis();
    // 如果当前处理器最后一条日志的时间不为空(为空代表没有接收到日志消息),并且距离当前时刻,系统时间超过了2秒.
    final long freeTime = currentTime - sys;
    final boolean isCheck = Constants.MAX_FREE_TIME < freeTime;
    final boolean isSys = 0L != sys;
    if (isSys && isCheck) {
      final int size = handler.size();
      if (0 != size) {
        // 一次处理100条.
        handler.process(size);
      }
      // 检查状态,调用handler级别的关闭资源方法,怎么关闭? 立即关闭? 等待30秒关闭?.
    }
  }
}
