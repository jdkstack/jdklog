package org.jdkstack.jdklog.logging.core.handler;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdkstack.jdklog.logging.api.context.StudyThreadImpl;
import org.jdkstack.jdklog.logging.api.worker.StudyWorker;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ThreadMonitorWorker implements StudyWorker<StudyThreadImpl> {
  /** . */
  private static final Logger LOGGER = Logger.getLogger(ThreadMonitorWorker.class.toString());

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void handle(final StudyThreadImpl studyThread) {
    // 当前系统时间毫秒数.
    final long currentTimeMillis = System.currentTimeMillis();
    // 线程开始执行时间的毫秒数.
    final long execStart = studyThread.startTime();
    // 线程执行的时间.
    final long duration = currentTimeMillis - execStart;
    // 线程开始时间为0,表示线程还没运行(或者执行完毕).
    final boolean isExecStart = 0 == execStart;
    // 如果线程执行时间<低水位10秒,直接返回.
    final boolean isMaxExecTime = duration < Constants.LOW;
    if (isExecStart || isMaxExecTime) {
      return;
    }
    // 获取线程的名字.
    final String name = ThreadMonitor.class.getName();
    // 如果线程执行时间>0秒但是<=高水位(15秒),打印详细日志说明即可.
    if (duration <= Constants.HIGH) {
      // 如果小于等于阻塞时间,打印线程异常warn信息.
      LOGGER.logp(Level.SEVERE, name, "execute1", "线程{0}", studyThread);
      LOGGER.logp(Level.SEVERE, name, "execute2", "锁定{0}毫秒", duration);
      LOGGER.logp(Level.SEVERE, name, "execute3", "限制{0}毫秒", Constants.HIGH);
    } else {
      // 如果大于高水位15秒,打印线程可能的异常信息.
      final StackTraceElement[] stackTraces = studyThread.getStackTrace();
      for (final StackTraceElement stackTrace : stackTraces) {
        LOGGER.logp(Level.SEVERE, name, "execute4", "线程运行异常?堆栈信息:{0}", stackTrace);
      }
    }
  }
}
