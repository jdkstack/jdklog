package org.jdkstack.jdklog.logging.admin.lifecycle;

/**
 * 生命周期 不应在接口中定义常量
 *
 * @author admin
 */
public interface Lifecycle {

  /**
   * .
   *
   * @throws LifecycleException .
   */
  void init() throws LifecycleException;

  /**
   * .
   *
   * @throws LifecycleException .
   */
  void start() throws LifecycleException;

  /**
   * .
   *
   * @throws LifecycleException .
   */
  void stop() throws LifecycleException;

  /**
   * .
   *
   * @throws LifecycleException .
   */
  void close() throws LifecycleException;

  /**
   * .
   *
   * @throws LifecycleException .
   */
  void destroy() throws LifecycleException;

  void addLifecycleListener(LifecycleListener listener);

  LifecycleListener[] findLifecycleListeners();

  void removeLifecycleListener(LifecycleListener listener);

  String getStateName();

  LifecycleState getState();
}
