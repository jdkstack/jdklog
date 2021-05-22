package org.jdkstack.jdklog.logging.admin.lifecycle.server;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;
import org.jdkstack.jdklog.logging.admin.lifecycle.LifecycleEvent;
import org.jdkstack.jdklog.logging.admin.lifecycle.LifecycleException;
import org.jdkstack.jdklog.logging.admin.lifecycle.LifecycleListener;
import org.jdkstack.jdklog.logging.admin.lifecycle.LifecycleState;

/**
 * 抽象类必须有默认的方法实现和字段,否则用接口
 *
 * @author admin
 */
public abstract class AbstractServerLifecycle implements ServerLifecycle {

  private static final Logger LOG = null;
  protected Object lifecycle = new Object();
  /** 当前组件的生命周期状态 */
  // protected LifecycleState state = LifecycleState.INITIALIZED;
  protected ServerLifecycleState state = ServerLifecycleState.NEW;
  /** 存储所有的监听器 */
  protected List<LifecycleListener> listeners = new CopyOnWriteArrayList<>();

  @Override
  public LifecycleListener[] findLifecycleListeners() {
    return listeners.toArray(new LifecycleListener[0]);
  }

  @Override
  public ServerLifecycleState getServerLifecycleState() {
    return null;
  }

  @Override
  public String getStateName() {
    return null;
  }

  @Override
  public void addLifecycleListener(LifecycleListener listener) {
    listeners.add(listener);
  }

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
  @Override
  public void fireLifecycleEvent(String type, Object data) {
    LifecycleEvent event = new LifecycleEvent(this, type, data);
    for (LifecycleListener listener : listeners) {
      listener.lifecycleEvent(event);
    }
  }

  @Override
  public void start() throws LifecycleException {
    synchronized (lifecycle) {
      if (ServerLifecycleState.START_BEFORE.equals(state)
          || ServerLifecycleState.STARTED.equals(state)
          || ServerLifecycleState.START_AFTER.equals(state)) {
        return;
      }
      if (state.equals(ServerLifecycleState.NEW)) {
        init();
      } else if (state.equals(ServerLifecycleState.FAILED)) {
        stop();
      } else if (!state.equals(ServerLifecycleState.INITIALIZED)
          && !state.equals(ServerLifecycleState.STOPPED)) {
        throw new LifecycleException();
      }
      try {
        setLifecycleEvent(ServerLifecycleState.START_BEFORE);
        doStart();
        setLifecycleEvent(ServerLifecycleState.START_AFTER);
      } catch (Exception e) {
        // ParameterizedMessage pm = new ParameterizedMessage("启动失败");
        // LOG.error(pm, e);
        setLifecycleEvent(ServerLifecycleState.FAILED);
      }
      if (state.equals(ServerLifecycleState.FAILED)) {
        stop();
      } else if (!state.equals(ServerLifecycleState.START_AFTER)) {
        throw new LifecycleException("xxxxxxxxxxxxx");
      } else {
        setLifecycleEvent(ServerLifecycleState.STARTED);
      }
    }
  }

  /** 做开始 */
  public abstract void doStart();

  @Override
  public void stop() throws LifecycleException {
    synchronized (lifecycle) {
      if (ServerLifecycleState.START_BEFORE.equals(state)
          || ServerLifecycleState.STARTED.equals(state)
          || ServerLifecycleState.START_AFTER.equals(state)) {
        return;
      }
      if (state.equals(ServerLifecycleState.NEW)) {
        state = ServerLifecycleState.STOP_AFTER;
        return;
      }
      if (!state.equals(ServerLifecycleState.START_AFTER)
          && !state.equals(ServerLifecycleState.FAILED)) {
        throw new LifecycleException();
      }
      if (state.equals(ServerLifecycleState.FAILED)) {
        fireLifecycleEvent("before_stop", null);
      }
      try {
        setLifecycleEvent(ServerLifecycleState.STOP_BEFORE);
        doStop();
        setLifecycleEvent(ServerLifecycleState.STOP_AFTER);
      } catch (Exception e) {
        // ParameterizedMessage pm = new ParameterizedMessage("停止服务");
        // LOG.error(pm, e);
        setLifecycleEvent(ServerLifecycleState.FAILED);
      }
      if (!state.equals(ServerLifecycleState.STOP_AFTER)
          && !state.equals(ServerLifecycleState.FAILED)) {
        throw new LifecycleException();
      }
      setLifecycleEvent(ServerLifecycleState.STOPPED);
    }
  }

  /** 停止做 */
  public abstract void doStop();

  @Override
  public void init() throws LifecycleException {
    synchronized (lifecycle) {
      if (!state.equals(ServerLifecycleState.NEW)) {
        throw new LifecycleException();
      }
      try {
        setLifecycleEvent(ServerLifecycleState.INIT_BEFORE);
        doInit();
        setLifecycleEvent(ServerLifecycleState.INIT_AFTER);
      } catch (Exception e) {
        // ParameterizedMessage pm = new ParameterizedMessage("初始化状态异常");
        // LOG.error(pm, e);
        setLifecycleEvent(ServerLifecycleState.FAILED);
      }
      if (!state.equals(ServerLifecycleState.INIT_AFTER)) {
        throw new LifecycleException();
      }
      setLifecycleEvent(ServerLifecycleState.INITIALIZED);
    }
  }

  public void setLifecycleEvent(ServerLifecycleState state) {
    this.state = state;
    String lifecycleEvent = state.getLifecycleEvent();
    if (lifecycleEvent != null) {
      fireLifecycleEvent(lifecycleEvent, "空事件");
    }
  }

  public void setLifecycleEvent(ServerLifecycleState state, Object data) {
    this.state = state;
    String lifecycleEvent = state.getLifecycleEvent();
    if (lifecycleEvent != null) {
      fireLifecycleEvent(lifecycleEvent, data);
    }
  }

  /** 做初始化 */
  public abstract void doInit();

  @Override
  public void destroy() throws LifecycleException {
    synchronized (lifecycle) {
      if (ServerLifecycleState.FAILED.equals(state)) {
        stop();
      }
      if (ServerLifecycleState.DESTROY_BEFORE.equals(state)
          || ServerLifecycleState.DESTROY_AFTER.equals(state)) {
        return;
      }
      if (!state.equals(ServerLifecycleState.STOP_AFTER)
          && !state.equals(ServerLifecycleState.FAILED)
          && !state.equals(ServerLifecycleState.NEW)
          && !state.equals(ServerLifecycleState.DESTROY_AFTER)) {
        throw new LifecycleException();
      }
      setLifecycleEvent(ServerLifecycleState.DESTROY_BEFORE);
      try {
        doDestroy();
      } catch (Exception e) {
        // ParameterizedMessage pm = new ParameterizedMessage("销毁");
        // LOG.error(pm, e);
      }
      setLifecycleEvent(ServerLifecycleState.DESTROY_AFTER);
    }
  }

  /** 做破坏 */
  public abstract void doDestroy();

  @Override
  public void close() throws LifecycleException {
    synchronized (lifecycle) {
      try {
        doClose();
      } catch (Exception e) {
        // ParameterizedMessage pm = new ParameterizedMessage("关闭");
        // LOG.error(pm, e);
      }
    }
  }

  /** 做亲密的 */
  protected abstract void doClose();

  public ServerLifecycleState state() {
    return this.state;
  }

  /** 重写类的方法,默认打印类的地址 */
  @Override
  public String toString() {
    return state.toString();
  }

  @Override
  public LifecycleState getState() {
    return null;
  }
}
