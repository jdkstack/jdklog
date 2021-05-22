package org.jdkstack.jdklog.logging.admin.lifecycle;

/**
 * 生命周期 不应在接口中定义常量
 *
 * @author admin
 */
public interface Lifecycle {

  /** 初始化 启动非守护线程,阻止jvm退出之前,初始化资源 */
  void init() throws LifecycleException;

  /** 开始 启动非守护线程,阻止jvm退出 */
  void start() throws LifecycleException;

  /** 停止 停止非守护线程,jvm退出 */
  void stop() throws LifecycleException;

  /** 关闭 停止非守护线程,jvm退出之前,关闭资源 */
  void close() throws LifecycleException;

  /** 摧毁 停止非守护线程,jvm退出之前,销毁资源 */
  void destroy() throws LifecycleException;

  void addLifecycleListener(LifecycleListener listener);

  LifecycleListener[] findLifecycleListeners();

  void removeLifecycleListener(LifecycleListener listener);

  String getStateName();

  LifecycleState getState();
}
