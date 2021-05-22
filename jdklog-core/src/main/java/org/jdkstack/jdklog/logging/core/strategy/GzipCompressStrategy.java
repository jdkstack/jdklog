package org.jdkstack.jdklog.logging.core.strategy;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;
import org.jdkstack.jdklog.logging.api.metainfo.Constants;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class GzipCompressStrategy extends AbstractCompressStrategy {
  /** . */
  private static final int BUF_SIZE = 1024;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param source .
   * @param destination .
   * @author admin
   */
  @Override
  public final void execute(final File source, final File destination) {
    try (final FileInputStream fis = new FileInputStream(source);
        final OutputStream fos = new FileOutputStream(destination);
        final OutputStream gzipOut =
            new GzipOutputStreamByLevel(fos, BUF_SIZE, Constants.GZIP_ONE);
        final OutputStream os = new BufferedOutputStream(gzipOut, BUF_SIZE)) {
      final byte[] buff = new byte[BUF_SIZE];
      int n;
      // assignment expressions nested inside other expressions. While admirably terse, such
      // expressions may be confusing, and violate the general design principle that a given
      // construct should do precisely one thing.
      while (-1 != (n = fis.read(buff))) {
        os.write(buff, 0, n);
      }
    } catch (final Exception e) {
      throw new StudyJuliRuntimeException(e);
    }
  }

  @Override
  public void execute() {
    //
  }
}
