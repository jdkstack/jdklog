package org.jdkstack.jdklog.logging.admin.lifecycle;

import java.util.EventListener;

/**
 * 摘要生命周期的监听器 生命周期的监听器 抽象类必须有默认的方法实现和字段,否则用接口
 *
 * @author admin
 */
public interface LifecycleListener extends EventListener {

  /** 在开始之前 */
  void beforeStart();

  /** 后开始 */
  void afterStart();

  /** 之前停止 */
  void beforeStop();

  /** 后停止 */
  void afterStop();

  /** 之前关闭 */
  void beforeClose();

  /** 后关闭 */
  void afterClose();

  /**
   * 触发生命周期事件
   *
   * @param event 事件
   */
  void lifecycleEvent(LifecycleEvent event);
}
