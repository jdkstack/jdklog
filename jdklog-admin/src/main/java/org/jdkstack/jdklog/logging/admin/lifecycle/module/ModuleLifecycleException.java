package org.jdkstack.jdklog.logging.admin.lifecycle.module;

/**
 * 模块生命周期异常
 *
 * @author admin
 */
public class ModuleLifecycleException extends Exception {

  /** Construct a new LifecycleException with no other information. */
  public ModuleLifecycleException() {
    super();
  }

  /**
   * Construct a new LifecycleException for the specified message.
   *
   * @param message Message describing this exception
   */
  public ModuleLifecycleException(String message) {
    super(message);
  }

  /**
   * Construct a new LifecycleException for the specified throwable.
   *
   * @param throwable Throwable that caused this exception
   */
  public ModuleLifecycleException(Throwable throwable) {
    super(throwable);
  }

  /**
   * Construct a new LifecycleException for the specified message and throwable.
   *
   * @param message Message describing this exception
   * @param throwable Throwable that caused this exception
   */
  public ModuleLifecycleException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
