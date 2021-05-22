package org.jdkstack.jdklog.logging.admin.exception;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * <p>
 *
 * @author admin
 * @version 2020-08-22 17:35
 * @since 2020-08-22 17:35:00
 */
public class ServerRuntimeException extends RuntimeException {
  /**
   * 服务器运行时异常
   *
   * @param message 消息
   */
  public ServerRuntimeException(final String message) {
    super(message);
  }

  /**
   * 服务器运行时异常
   *
   * @param message 消息
   * @param cause 导致
   */
  public ServerRuntimeException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
