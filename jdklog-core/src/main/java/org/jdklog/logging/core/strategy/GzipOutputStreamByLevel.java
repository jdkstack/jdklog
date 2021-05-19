package org.jdklog.logging.core.strategy;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class GzipOutputStreamByLevel extends GZIPOutputStream {

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param out .
   * @param bufSize .
   * @param level .
   * @author admin
   */
  public GzipOutputStreamByLevel(final OutputStream out, final int bufSize, final int level)
      throws IOException {
    super(out, bufSize);
    this.def.setLevel(level);
  }
}
