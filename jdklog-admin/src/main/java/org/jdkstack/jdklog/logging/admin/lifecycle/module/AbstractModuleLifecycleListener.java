package org.jdkstack.jdklog.logging.admin.lifecycle.module;

import java.util.logging.Logger;
import org.jdkstack.jdklog.logging.admin.lifecycle.LifecycleEvent;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * <p>
 *
 * @author admin
 * @version 2020-09-23 22:40
 * @since 2020-09-23 22:40:00
 */
public abstract class AbstractModuleLifecycleListener implements ModuleLifecycleListener {

  private static final Logger LOG =
      null; // LogManager.getLogger(AbstractModuleLifecycleListener.class);

  @Override
  public void beforeStart() {
    LOG.info("beforeStart");
  }

  @Override
  public void afterStart() {
    LOG.info("afterStart");
  }

  @Override
  public void beforeStop() {
    LOG.info("beforeStop");
  }

  @Override
  public void afterStop() {
    LOG.info("afterStop");
  }

  @Override
  public void beforeClose() {
    LOG.info("beforeClose");
  }

  @Override
  public void afterClose() {
    LOG.info("afterClose");
  }

  @Override
  public void lifecycleEvent(LifecycleEvent event) {
    LOG.info("lifecycleEvent");
    doLifecycleEvent(event);
  }

  public abstract void doLifecycleEvent(LifecycleEvent event);
}
