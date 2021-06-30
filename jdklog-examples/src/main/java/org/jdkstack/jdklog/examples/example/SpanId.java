package org.jdkstack.jdklog.examples.example;

/**
 * .
 *
 * <p>.
 *
 * @author admin
 */
public final class SpanId {

  private SpanId() {
    //
  }

  /**
   * .
   *
   * <p>.
   *
   * @param cu cu.
   * @return String .
   * @author admin
   */
  public static String nextSpanId(final String cu) {
    return cu + "." + 0;
  }
}
