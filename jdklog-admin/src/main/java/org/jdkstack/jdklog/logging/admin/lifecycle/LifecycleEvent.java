package org.jdkstack.jdklog.logging.admin.lifecycle;

import java.util.EventObject;

/**
 * 生命周期事件
 *
 * @author admin
 */
public class LifecycleEvent extends EventObject {

  private static final long serialVersionUID = 1L;
  /** The event data associated with this event. */
  private final transient Object data;

  /** The event type this instance represents. */
  private final String type;

  public LifecycleEvent(Lifecycle lifecycle, String type, Object data) {
    super(lifecycle);
    this.type = type;
    this.data = data;
  }

  /** @return the event data of this event. */
  public Object getData() {
    return data;
  }

  /** @return the Lifecycle on which this event occurred. */
  public Lifecycle getLifecycle() {
    return (Lifecycle) getSource();
  }

  /** @return the event type of this event. */
  public String getType() {
    return this.type;
  }
}
