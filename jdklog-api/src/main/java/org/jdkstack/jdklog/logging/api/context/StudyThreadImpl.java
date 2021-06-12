package org.jdkstack.jdklog.logging.api.context;

import java.util.concurrent.TimeUnit;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface StudyThreadImpl {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return TimeUnit TimeUnit.
   * @author admin
   */
  TimeUnit maxExecTimeUnit();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return int int.
   * @author admin
   */
  int threadType();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return long long.
   * @author admin
   */
  long startTime();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return long long.
   * @author admin
   */
  long maxExecTime();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return String String.
   * @author admin
   */
  String getUnique();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param unique unique.
   * @author admin
   */
  void setUnique(String unique);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param contextParam contextParam.
   * @author admin
   */
  void beginEmission(StudyContext contextParam);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void endEmission();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param uniqueParam uniqueParam.
   * @param contextParam contextParam.
   * @author admin
   */
  void beginEmissionV2(String uniqueParam, StudyContext contextParam);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  void endEmissionV2();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return StackTraceElement[] StackTraceElement[].
   * @author admin
   */
  StackTraceElement[] getStackTrace();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param on on.
   * @author admin
   */
  void setDaemon(boolean on);
}
