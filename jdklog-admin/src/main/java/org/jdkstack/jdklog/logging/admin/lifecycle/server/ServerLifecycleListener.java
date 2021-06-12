package org.jdkstack.jdklog.logging.admin.lifecycle.server;

import org.jdkstack.jdklog.logging.admin.lifecycle.LifecycleListener;

/**
 * 摘要生命周期的监听器 生命周期的监听器 抽象类必须有默认的方法实现和字段,否则用接口
 *
 * @author admin
 */
public interface ServerLifecycleListener extends LifecycleListener {
  /**
   * 触发生命周期事件
   *
   * @param event 事件
   */
  // void lifecycleEvent(ServerLifecycleEvent event);
}
