package org.jdkstack.jdklog.logging.core.handler;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdkstack.jdklog.logging.api.context.WorkerContext;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.manager.LoaderLogInfo;
import org.jdkstack.jdklog.logging.api.worker.StudyWorker;
import org.jdkstack.jdklog.logging.core.manager.AbstractLogManager;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class GuardianConsumerMonitorRunnable implements Runnable {
  /** . */
  private static final Logger LOGGER =
      Logger.getLogger(GuardianConsumerMonitorRunnable.class.toString());
  /** . */
  private final WorkerContext context;
  /** . */
  private final StudyWorker<Handler> guardianConsumerWorker = new GuardianConsumerWorker();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param context .
   * @author admin
   */
  public GuardianConsumerMonitorRunnable(final WorkerContext context) {
    this.context = context;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Override
  public final void run() {
    try {
      // 得到当前的类加载器.
      final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      // 当前类加载器对象的日志信息.
      final Map<ClassLoader, LoaderLogInfo> classLoaderLoggers =
          AbstractLogManager.getClassLoaderLoggers1();
      final LoaderLogInfo info = classLoaderLoggers.get(classLoader);
      // 得到所有的处理器.
      final Map<String, Handler> handlers = info.getHandlers();
      // 循环所有的处理器.
      for (final Map.Entry<String, Handler> entry : handlers.entrySet()) {
        // 将处理器转成FileHandler(目前只支持FileHandler,后面会支持KafkaHandler).
        final Handler handler = entry.getValue();
        // 将处理器提交给线程池.
        this.context.executeInExecutorService(handler, this.guardianConsumerWorker);
      }
    } catch (final Exception e) {
      final String name = GuardianConsumerMonitorRunnable.class.getName();
      LOGGER.logp(Level.SEVERE, name, "run", "守护消费监听线程出现异常", e);
    }
  }
}
