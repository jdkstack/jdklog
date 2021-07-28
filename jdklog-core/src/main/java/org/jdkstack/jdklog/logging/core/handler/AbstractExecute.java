package org.jdkstack.jdklog.logging.core.handler;

import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.api.queue.StudyQueue;
import org.jdkstack.jdklog.logging.api.worker.StudyWorker;
import org.jdkstack.jdklog.logging.core.manager.AbstractLogManager;
import org.jdkstack.jdklog.logging.core.queue.ProducerWorker;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public abstract class AbstractExecute extends AbstractMetric implements Execute {
  /** . */
  protected final StudyQueue<Record> queue;
  /** 生产日志处理器. */
  protected final StudyWorker<Record> producerWorker;
  /** lock. */
  private final Object lock = new Object();
  /** 代表当前处理器接收到最后一条日志的时间,0L表示从来没接收到. */
  protected long sys;
  /** 代表当前处理器状态. */
  private int state;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param queue queue.
   * @author admin
   */
  protected AbstractExecute(final StudyQueue<Record> queue) {
    this.queue = queue;
    this.producerWorker = new ProducerWorker(this.queue);
  }

  /**
   * 用Key从JDL LogManager读取一个配置的value.
   *
   * <p>Another description after blank line.
   *
   * @return int size.
   * @author admin
   */
  public final int size() {
    return this.queue.size();
  }

  /**
   * 用Key从JDL LogManager读取一个配置的value.
   *
   * <p>Another description after blank line.
   *
   * @param name 属性名.
   * @param defaultValue 默认属性名.
   * @return 返回属性名对应的值.
   * @author admin
   */
  public final String getProperty(final String name, final String defaultValue) {
    // 获取当前类的配置属性.
    String value = AbstractLogManager.getProperty1(name);
    // 如果空,使用默认值.
    if (null == value) {
      value = defaultValue;
    } else {
      // 如果不为空,去掉空格.
      value = value.trim();
    }
    return value;
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param lr lr.
   * @author admin
   */
  protected void metris(final Record lr) {
    // 记录当前处理器最后一次处理日志的时间.
    this.sys = System.currentTimeMillis();
    // 全局Handler处理的日志数量.
    final long globalCounter = GLOBAL_COUNTER.incrementAndGet();
    lr.setCustom("globalHandlerCounter", Long.toString(globalCounter));
    // 单个Handler处理的日志数量.
    final long singleCounter = counter.incrementAndGet();
    lr.setCustom("singleHandlerCounter", Long.toString(singleCounter));
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return int int.
   * @author admin
   */
  public int state() {
    synchronized (lock) {
      return this.state;
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param state state.
   * @author admin
   */
  public void setState(final int state) {
    synchronized (lock) {
      this.state = state;
    }
  }
}
