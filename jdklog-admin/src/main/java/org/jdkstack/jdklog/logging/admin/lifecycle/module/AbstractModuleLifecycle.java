package org.jdkstack.jdklog.logging.admin.lifecycle.module;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.jdkstack.jdklog.logging.admin.lifecycle.LifecycleException;
import org.jdkstack.jdklog.logging.admin.lifecycle.LifecycleListener;
import org.jdkstack.jdklog.logging.admin.lifecycle.LifecycleState;

/**
 * 抽象模块生命周期
 *
 * @author admin
 */
public abstract class AbstractModuleLifecycle implements ModuleLifecycle {

  protected Object lifecycle = new Object();
  // protected ModuleLifecycleState moduleLifecycleState = ModuleLifecycleState.NEW;
  protected ModuleLifecycleState moduleLifecycleState = ModuleLifecycleState.INITIALIZED;
  protected List<LifecycleListener> listeners = new CopyOnWriteArrayList<>();

  protected AbstractModuleLifecycle() {}

  @Override
  public LifecycleListener[] findLifecycleListeners() {
    return listeners.toArray(new LifecycleListener[0]);
  }

  @Override
  public String getStateName() {
    return null;
  }

  @Override
  public ModuleLifecycleState getModuleLifecycleState() {
    return null;
  }

  @Override
  public void addLifecycleListener(LifecycleListener listener) {
    listeners.add(listener);
  }

  @Override
  public void setLifecycleEvent(ModuleLifecycleState state) {}

  @Override
  public void setLifecycleEvent(ModuleLifecycleState state, Object data) {}

  @Override
  public void removeLifecycleListener(LifecycleListener listener) {
    listeners.remove(listener);
  }

  /**
   * Allow sub classes to fire {@link Lifecycle} events. 触发事件
   *
   * @param type Event type
   * @param data Data event.
   */
  public void fireLifecycleEvent(String type, Object data) {
    ModuleLifecycleEvent event = new ModuleLifecycleEvent(this, type, data);
    for (LifecycleListener listener : listeners) {
      listener.lifecycleEvent(event);
    }
  }

  @Override
  public void start() throws LifecycleException {
    synchronized (lifecycle) {
      if (!canMoveToStarted()) {
        return;
      }
      for (LifecycleListener listener : listeners) {
        listener.beforeStart();
      }
      try {
        doStart();
      } catch (Exception e) {
        // ParameterizedMessage parameterizedMessage =
        // new ParameterizedMessage(
        //   "close connection exception caught on transport layer [{}], disconnecting from relevant
        // node11");
        // LOG.error(parameterizedMessage, e);
      }
      moveToStarted();
      for (LifecycleListener listener : listeners) {
        listener.afterStart();
      }
    }
  }

  /**
   * 做开始
   *
   * @throws Exception 异常
   */
  public abstract void doStart() throws Exception;

  @Override
  public void stop() throws LifecycleException {
    synchronized (lifecycle) {
      if (!canMoveToStopped()) {
        return;
      }
      for (LifecycleListener listener : listeners) {
        listener.beforeStop();
      }
      moveToStopped();
      try {
        doStop();
      } catch (Exception e) {
        // /ParameterizedMessage parameterizedMessage =
        //   new ParameterizedMessage(
        //   "close connection exception caught on transport layer [{}], disconnecting from relevant
        // node22");
        //  LOG.error(parameterizedMessage, e);
      }
      for (LifecycleListener listener : listeners) {
        listener.afterStop();
      }
    }
  }

  /**
   * 停止做
   *
   * @throws Exception 异常
   */
  protected abstract void doStop() throws Exception;

  @Override
  public void close() throws LifecycleException {
    synchronized (lifecycle) {
      if (started()) {
        stop();
      }
      if (!canMoveToClosed()) {
        return;
      }
      for (LifecycleListener listener : listeners) {
        listener.beforeClose();
      }
      moveToClosed();
      try {
        doClose();
      } catch (Exception e) {
        // ParameterizedMessage parameterizedMessage =
        // new ParameterizedMessage(
        //       "close connection exception caught on transport layer [{}], disconnecting from
        // relevant node33");
        // LOG.error(parameterizedMessage, e);
      } finally {
        for (LifecycleListener listener : listeners) {
          listener.afterClose();
        }
      }
    }
  }

  /**
   * 做亲密的
   *
   * @throws Exception 异常
   */
  protected abstract void doClose() throws Exception;

  @Override
  public void init() throws LifecycleException {
    synchronized (lifecycle) {
      if (started()) {
        stop();
      }
      if (!canMoveToClosed()) {
        return;
      }
      for (LifecycleListener listener : listeners) {
        listener.beforeClose();
      }
      // moveToClosed();
      moveToStopped();
      try {
        doInit();
      } catch (Exception e) {
        // ParameterizedMessage parameterizedMessage =
        //    new ParameterizedMessage(
        //     "close connection exception caught on transport layer [{}], disconnecting from
        // relevant node44");
        // LOG.error(parameterizedMessage, e);
      } finally {
        for (LifecycleListener listener : listeners) {
          listener.afterClose();
        }
      }
    }
  }

  /**
   * 做初始化
   *
   * @throws Exception 异常
   */
  protected abstract void doInit() throws Exception;

  @Override
  public void destroy() throws LifecycleException {
    synchronized (lifecycle) {
      if (started()) {
        stop();
      }
      if (!canMoveToClosed()) {
        return;
      }
      for (LifecycleListener listener : listeners) {
        listener.beforeClose();
      }
      moveToClosed();
      try {
        doDestroy();
      } catch (Exception e) {
        //  ParameterizedMessage parameterizedMessage =
        //   new ParameterizedMessage(
        //     "close connection exception caught on transport layer [{}], disconnecting from
        // relevant node55");
        // LOG.error(parameterizedMessage, e);
      } finally {
        for (LifecycleListener listener : listeners) {
          listener.afterClose();
        }
      }
    }
  }

  public boolean initialized() {
    return moduleLifecycleState == ModuleLifecycleState.INITIALIZED;
  }

  public boolean started() {
    return moduleLifecycleState == ModuleLifecycleState.STARTED;
  }

  /**
   * 做破坏
   *
   * @throws Exception 异常
   */
  protected abstract void doDestroy() throws Exception;

  public boolean canMoveToStarted() {
    if (moduleLifecycleState == ModuleLifecycleState.INITIALIZED
        || moduleLifecycleState == ModuleLifecycleState.STOPPED) {
      return true;
    }
    if (moduleLifecycleState == ModuleLifecycleState.STARTED) {
      return false;
    }
    if (moduleLifecycleState == ModuleLifecycleState.CLOSED) {
      throw new IllegalStateException("Can't move to started state when closed");
    }
    throw new IllegalStateException("Can't move to started with unknown state");
  }

  public synchronized boolean moveToStarted() {
    if (moduleLifecycleState == ModuleLifecycleState.INITIALIZED
        || moduleLifecycleState == ModuleLifecycleState.STOPPED) {
      moduleLifecycleState = ModuleLifecycleState.STARTED;
      return true;
    }
    if (moduleLifecycleState == ModuleLifecycleState.STARTED) {
      return false;
    }
    if (moduleLifecycleState == ModuleLifecycleState.CLOSED) {
      throw new IllegalStateException("Can't move to started state when closed");
    }
    throw new IllegalStateException("Can't move to started with unknown state");
  }

  public boolean canMoveToStopped() {
    if (moduleLifecycleState == ModuleLifecycleState.STARTED) {
      return true;
    }
    if (moduleLifecycleState == ModuleLifecycleState.INITIALIZED
        || moduleLifecycleState == ModuleLifecycleState.STOPPED) {
      return false;
    }
    if (moduleLifecycleState == ModuleLifecycleState.CLOSED) {
      throw new IllegalStateException("Can't move to stopped state when closed");
    }
    throw new IllegalStateException("Can't move to stopped with unknown state");
  }

  public synchronized boolean moveToStopped() {
    if (moduleLifecycleState == ModuleLifecycleState.STARTED) {
      moduleLifecycleState = ModuleLifecycleState.STOPPED;
      return true;
    }
    if (moduleLifecycleState == ModuleLifecycleState.INITIALIZED
        || moduleLifecycleState == ModuleLifecycleState.STOPPED) {
      return false;
    }
    if (moduleLifecycleState == ModuleLifecycleState.CLOSED) {
      throw new IllegalStateException("Can't move to stopped state when closed");
    }
    throw new IllegalStateException("Can't move to stopped with unknown state");
  }

  public boolean canMoveToClosed() {
    if (moduleLifecycleState == ModuleLifecycleState.CLOSED) {
      return false;
    }
    if (moduleLifecycleState == ModuleLifecycleState.STARTED) {
      throw new IllegalStateException("Can't move to closed before moving to stopped mode");
    }
    return true;
  }

  public synchronized boolean moveToClosed() {
    if (moduleLifecycleState == ModuleLifecycleState.CLOSED) {
      return false;
    }
    if (moduleLifecycleState == ModuleLifecycleState.STARTED) {
      throw new IllegalStateException("Can't move to closed before moving to stopped mode");
    }
    moduleLifecycleState = ModuleLifecycleState.CLOSED;
    return true;
  }

  @Override
  public String toString() {
    return moduleLifecycleState.toString();
  }

  @Override
  public LifecycleState getState() {

    return null;
  }
}
