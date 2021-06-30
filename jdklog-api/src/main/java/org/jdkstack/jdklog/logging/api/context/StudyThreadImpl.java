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
   * @param contextBean contextBean.
   * @param contextParam contextParam.
   * @author admin
   */
  void beginEmissionV2(Bean contextBean, StudyContext contextParam);

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

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @return Bean Bean.
   * @author admin
   */
  Bean getContextBean();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param contextBean contextBean.
   * @author admin
   */
  void setContextBean(Bean contextBean);
}
