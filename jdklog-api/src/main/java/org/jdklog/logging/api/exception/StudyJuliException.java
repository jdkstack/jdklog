package org.jdklog.logging.api.exception;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class StudyJuliException extends Exception {

  private static final long serialVersionUID = -1L;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 异常消息.
   * @author admin
   */
  public StudyJuliException(final String message) {
    super(message);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 异常消息.
   * @param cause 异常对象.
   * @author admin
   */
  public StudyJuliException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param cause 异常对象.
   * @author admin
   */
  public StudyJuliException(final Throwable cause) {
    super(cause);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 异常消息.
   * @param cause 异常对象.
   * @param enableSuppression 是否启动.
   * @param writableStackTrace 是否堆栈.
   * @author admin
   */
  public StudyJuliException(
      final String message,
      final Throwable cause,
      final boolean enableSuppression,
      final boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
