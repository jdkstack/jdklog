package org.jdkstack.jdklog.logging.admin.exception;

/**
 * 服务器异常 This is a class description.
 *
 * <p>Another description after blank line.
 *
 * <p>
 *
 * @author admin
 * @version 2020-08-22 17:45
 * @since 2020-08-22 17:45:00
 */
public class ServerException extends Exception {
  /**
   * 服务器异常
   *
   * @param message 消息
   */
  public ServerException(final String message) {
    super(message);
  }

  /**
   * 服务器异常
   *
   * @param message 消息
   * @param cause 导致
   */
  public ServerException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
