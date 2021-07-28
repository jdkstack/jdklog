package org.jdkstack.jdklog.logging.core.handler;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
}
