package org.jdkstack.jdklog.logging.admin.lifecycle.server;

/**
 * 服务器生命周期状态
 *
 * @author admin
 */
public enum ServerLifecycleState {
  /** 服务创建初始状态为新建 */
  NEW(false, "new"),

  INIT_BEFORE(false, "init_before"),
  INITIALIZING_START(false, "initializing_start"),
  INITIALIZING_END(false, "initializing_end"),
  INIT_AFTER(false, "init_after"),

  INITIALIZED(false, "initialized"),

  START_BEFORE(false, "start_before"),
  START_START(false, "start_start"),
  START_END(false, "start_end"),
  STARTED(true, "started"),
  START_AFTER(true, "start_after"),
  /** 设置属性=空,或者调用方法释放等操作 List.clear() 优雅终结方法的使用不包括(getters/setters和此组件的生命周期方法) */
  STOP_BEFORE(true, "stop_before"),
  STOP_START(false, "stop_start"),
  STOP_END(false, "stop_end"),
  STOPPED(false, "stopped"),
  STOP_AFTER(false, "stop_after"),

  /** 销毁资源 */
  DESTROY_BEFORE(false, "destroy_before"),
  DESTROYED(false, "destroyed"),
  DESTROY_AFTER(false, "destroy_after"),
  /** 服务创建初始状态为新建 */
  // CLOSED(false, "closed"),
  /** 服务失败 */
  FAILED(false, "failed");
  private final boolean available;

  private final String lifecycleEvent;

  ServerLifecycleState(boolean available, String lifecycleEvent) {
    this.available = available;
    this.lifecycleEvent = lifecycleEvent;
  }

  public boolean isAvailable() {
    return available;
  }

  public String getLifecycleEvent() {
    return lifecycleEvent;
  }
}
