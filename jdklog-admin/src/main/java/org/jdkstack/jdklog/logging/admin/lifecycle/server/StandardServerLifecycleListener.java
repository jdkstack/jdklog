package org.jdkstack.jdklog.logging.admin.lifecycle.server;

import java.util.logging.Logger;
import org.jdkstack.jdklog.logging.admin.lifecycle.LifecycleEvent;

/**
 * 标准的服务器生命周期侦听器
 *
 * @author admin
 */
public class StandardServerLifecycleListener implements ServerLifecycleListener {

  private static final Logger LOG = null;

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
    if (event.getType().equals(ServerLifecycleState.STARTED)) {
      // start();
    }
    if (event.getType().equals(ServerLifecycleState.STOPPED)) {
      // stop();
    }
  }
}
