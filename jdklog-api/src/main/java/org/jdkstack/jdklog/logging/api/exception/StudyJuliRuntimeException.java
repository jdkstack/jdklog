package org.jdkstack.jdklog.logging.api.exception;

/**
 * Study Juli日志运行时异常.
 *
 * <p>日志配置运行时异常.
 *
 * @author admin
 */
public class StudyJuliRuntimeException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 消息.
   * @author admin
   */
  public StudyJuliRuntimeException(final String message) {
    super(message);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param cause 异常.
   * @author admin
   */
  public StudyJuliRuntimeException(final Throwable cause) {
    super(cause);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param message 消息.
   * @param cause 异常.
   * @author admin
   */
  public StudyJuliRuntimeException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
