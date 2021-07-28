package org.jdkstack.jdklog.logging.core.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.jdklog.logging.api.context.StudyThreadImpl;
import org.jdkstack.jdklog.logging.api.context.WorkerContext;
import org.jdkstack.jdklog.logging.api.worker.StudyWorker;

/**
 * 定时检查线程的运行时间.
 *
 * <p>当线程的run方法运行时间不超过组大阻塞时间blockTime,但是超过了线程运行的最大时间.
 *
 * <p>打印危险的消息,如果超过最大阻塞时间,打算线程堆栈信息,看看线程的run中运行的代码是什么.
 *
 * @author admin
 */
public class ThreadMonitor extends AbstractMonitor {
  /** 保存所有的线程,key是线程名字,value是线程. */
  protected final Map<String, Thread> threads = new ConcurrentHashMap<>(32);
  /** . */
  protected final StudyWorker<StudyThreadImpl> threadMonitorWorker = new ThreadMonitorWorker();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public ThreadMonitor() {
    //
  }

  /**
   * 添加要监控的线程,这个线程必须是StudyThread.
   *
   * <p>线程必须继承Thread.
   *
   * @param thread 要监控的线程.
   * @author admin
   */
  @Override
  public final void registerThread(final Thread thread) {
    // 添加要监控的线程,这个线程必须是StudyThread.
    final String name = thread.getName();
    this.threads.put(name, thread);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param context c.
   * @author admin
   */
  @Override
  public void execute(final WorkerContext context) {
    // 原打算使用异步的方式,但是感觉不太合理.
    for (final Map.Entry<String, Thread> entry : this.threads.entrySet()) {
      // 要检查的线程(注册线程时必须是StudyThread).
      final StudyThreadImpl studyThread = (StudyThreadImpl) entry.getValue();
      // 将处理器提交给线程池.
      context.executeInExecutorService(studyThread, this.threadMonitorWorker);
    }
  }
}
