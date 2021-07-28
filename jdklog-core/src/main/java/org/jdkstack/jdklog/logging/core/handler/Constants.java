package org.jdkstack.jdklog.logging.core.handler;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public final class Constants {
  /** . */
  public static final String FORMATTER =
      "org.jdkstack.jdklog.logging.core.formatter.StudyJuliMessageTextFormatter";
  /** . */
  public static final String FILTER = "org.jdkstack.jdklog.logging.core.filter.StudyJuliFilter";
  /** . */
  public static final int BATCH_SIZE = 100;
  /** . */
  public static final int FLUSH_COUNT = 100;
  /** . */
  public static final int BATCH_BUF_SIZE = 8192;
  /** . */
  public static final long MAX_FREE_TIME = 2000L;
  /** . */
  public static final int LOOP_COUNT = 5;
  /** . */
  public static final String UNIQUE = ".unique";
  /** . */
  public static final String TRUE = "true";
  /** . */
  public static final String FALSE = "false";
  /** . */
  public static final long PERIOD = 5000L;
  /** . */
  public static final long INITIAL_DELAY = 5000L;
  /** 线程执行时间低水位. */
  public static final int LOW = 10;
  /** 线程执行时间高水位. */
  public static final int HIGH = 15;

  private Constants() {
    //
  }
}
