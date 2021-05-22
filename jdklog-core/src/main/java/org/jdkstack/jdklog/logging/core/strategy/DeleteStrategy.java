package org.jdkstack.jdklog.logging.core.strategy;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class DeleteStrategy extends AbstractStrategy {

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param source .
   * @author admin
   */
  public static void delete(final File source) {
    try {
      final Path path = source.toPath();
      Files.delete(path);
    } catch (final IOException e) {
      throw new StudyJuliRuntimeException(e);
    }
  }

  @Override
  public void execute() {
    //
  }

  @Override
  public void execute(final File source, final File destination) {
    //
  }
}
