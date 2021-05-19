package org.jdklog.logging.core.queue;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class EventQueue extends AbstractQueue<String> {

  /** 阻塞队列名称,按照业务划分. */
  private final String target;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param target .
   * @author admin
   */
  public EventQueue(final String target) {
    this.target = target;
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param capacity .
   * @param target .
   * @author admin
   */
  public EventQueue(final int capacity, final String target) {
    super(capacity);
    this.target = target;
  }

  @Override
  public final String getTarget() {
    return this.target;
  }
}
