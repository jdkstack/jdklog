package org.jdkstack.jdklog.logging.core.handler;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jdkstack.jdklog.logging.api.metainfo.Record;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public abstract class AbstractMetric implements Metric {
  /** 全局handler日志计数. */
  protected static final AtomicLong GLOBAL_COUNTER = new AtomicLong(0L);
  /** 单个handler日志计数. */
  protected final AtomicLong counter = new AtomicLong(0L);
  /** 一个非公平锁,fair=false. */
  private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock(false);
  /** 非公平读锁. */
  protected final Lock readLock = this.readWriteLock.readLock();
  /** 非公平写锁. */
  protected final Lock writeLock = this.readWriteLock.writeLock();
  /** 代表当前处理器接收到最后一条日志的时间,0L表示从来没接收到. */
  protected long sys;

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
}
