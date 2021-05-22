package org.jdkstack.jdklog.logging.admin.lifecycle.server;

/**
 * 生命周期异常
 *
 * @author admin
 */
public class ServerLifecycleException extends Exception {

  public ServerLifecycleException() {
    super();
  }

  public ServerLifecycleException(String message) {
    super(message);
  }

  public ServerLifecycleException(Throwable throwable) {
    super(throwable);
  }

  public ServerLifecycleException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
