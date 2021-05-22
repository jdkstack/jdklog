package org.jdkstack.jdklog.logging.admin.lifecycle;

/**
 * 生命周期异常
 *
 * @author admin
 */
public class LifecycleException extends Exception {

  public LifecycleException() {
    super();
  }

  public LifecycleException(String message) {
    super(message);
  }

  public LifecycleException(Throwable throwable) {
    super(throwable);
  }

  public LifecycleException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
