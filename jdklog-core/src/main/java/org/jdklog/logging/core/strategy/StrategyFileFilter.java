package org.jdklog.logging.core.strategy;

import java.io.File;
import java.io.FileFilter;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class StrategyFileFilter implements FileFilter {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param paramFile .
   * @return boolean .
   * @author admin
   */
  @Override
  public final boolean accept(final File paramFile) {
    final String name = paramFile.getName();
    return name.endsWith(".log");
  }
}
