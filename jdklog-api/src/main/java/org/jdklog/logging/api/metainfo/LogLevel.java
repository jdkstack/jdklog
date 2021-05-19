package org.jdklog.logging.api.metainfo;

import java.util.HashMap;
import java.util.Map;

/**
 * This is a method description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public final class LogLevel implements Level {
  /** . */
  public static final Level OFF = new LogLevel("OFF", Integer.MAX_VALUE);
  /** . */
  public static final Level SEVERE = new LogLevel("SEVERE", 1000);
  /** . */
  public static final Level WARNING = new LogLevel("WARNING", 900);
  /** . */
  public static final Level INFO = new LogLevel("INFO", 800);
  /** . */
  public static final Level CONFIG = new LogLevel("CONFIG", 700);
  /** . */
  public static final Level FINE = new LogLevel("FINE", 500);
  /** . */
  public static final Level FINER = new LogLevel("FINER", 400);
  /** . */
  public static final Level FINEST = new LogLevel("FINEST", 300);
  /** . */
  public static final Level ALL = new LogLevel("ALL", Integer.MIN_VALUE);
  /** . */
  private static final Map<String, Level> STANDARD_LEVELS = new HashMap<>(16);

  static {
    STANDARD_LEVELS.put("OFF", OFF);
    STANDARD_LEVELS.put("SEVERE", SEVERE);
    STANDARD_LEVELS.put("WARNING", WARNING);
    STANDARD_LEVELS.put("INFO", INFO);
    STANDARD_LEVELS.put("CONFIG", CONFIG);
    STANDARD_LEVELS.put("FINE", FINE);
    STANDARD_LEVELS.put("FINER", FINER);
    STANDARD_LEVELS.put("FINEST", FINEST);
    STANDARD_LEVELS.put("ALL", ALL);
  }

  /** . */
  private final String name;
  /** . */
  private final int value;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name 日志级别的名称.
   * @param value 日志级别的值.
   * @author admin
   */
  private LogLevel(final String name, final int value) {
    this.name = name;
    this.value = value;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name 日志级别的名称.
   * @return Level .
   * @author admin
   */
  public static Level findLevel(final String name) {
    return STANDARD_LEVELS.get(name);
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return int .
   * @author admin
   */
  @Override
  public int intValue() {
    return this.value;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String .
   * @author admin
   */
  @Override
  public String toString() {
    return "Level{" + "name='" + this.name + '\'' + ", value=" + this.value + '}';
  }
}
