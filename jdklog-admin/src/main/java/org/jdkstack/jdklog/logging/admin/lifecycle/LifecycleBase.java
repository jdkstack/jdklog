package org.jdkstack.jdklog.logging.admin.lifecycle;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class LifecycleBase implements Lifecycle {

  private final List<LifecycleListener> lifecycleListeners = new CopyOnWriteArrayList<>();

  private volatile LifecycleState state = LifecycleState.NEW;

  @Override
  public void addLifecycleListener(LifecycleListener listener) {
    lifecycleListeners.add(listener);
  }

  @Override
  public LifecycleListener[] findLifecycleListeners() {
    return lifecycleListeners.toArray(new LifecycleListener[0]);
  }

  @Override
  public void removeLifecycleListener(LifecycleListener listener) {
    lifecycleListeners.remove(listener);
  }

  protected void fireLifecycleEvent(String type, Object data) {
    LifecycleEvent event = new LifecycleEvent(this, type, data);
    for (LifecycleListener listener : lifecycleListeners) {
      listener.lifecycleEvent(event);
    }
  }

  @Override
  public final synchronized void init() throws LifecycleException {
    if (!state.equals(LifecycleState.NEW)) {
      // invalidTransition(Lifecycle.BEFORE_INIT_EVENT);
    }
    try {
      setStateInternal(LifecycleState.INITIALIZING, null, false);
      initInternal();
      setStateInternal(LifecycleState.INITIALIZED, null, false);
    } catch (Throwable t) {
      // handleSubClassException(t, "lifecycleBase.initFail", toString());
    }
  }

  protected abstract void initInternal() throws LifecycleException;

  @Override
  public void close() throws LifecycleException {}

  @Override
  public final synchronized void start() throws LifecycleException {

    if (LifecycleState.STARTING_PREP.equals(state)
        || LifecycleState.STARTING.equals(state)
        || LifecycleState.STARTED.equals(state)) {
      return;
    }

    if (state.equals(LifecycleState.NEW)) {
      init();
    } else if (state.equals(LifecycleState.FAILED)) {
      stop();
    } else if (!state.equals(LifecycleState.INITIALIZED) && !state.equals(LifecycleState.STOPPED)) {
      // invalidTransition(Lifecycle.BEFORE_START_EVENT);
    }

    try {
      setStateInternal(LifecycleState.STARTING_PREP, null, false);
      startInternal();
      if (state.equals(LifecycleState.FAILED)) {
        // This is a 'controlled' failure. The component put itself into the
        // FAILED state so call stop() to complete the clean-up.
        stop();
      } else if (!state.equals(LifecycleState.STARTING)) {
        // Shouldn't be necessary but acts as a check that sub-classes are
        // doing what they are supposed to.
        // invalidTransition(Lifecycle.AFTER_START_EVENT);
      } else {
        setStateInternal(LifecycleState.STARTED, null, false);
      }
    } catch (Throwable t) {
      // This is an 'uncontrolled' failure so put the component into the
      // FAILED state and throw an exception.
      // handleSubClassException(t, "lifecycleBase.startFail", toString());
    }
  }

  protected abstract void startInternal() throws LifecycleException;

  @Override
  public final synchronized void stop() throws LifecycleException {

    if (LifecycleState.STOPPING_PREP.equals(state)
        || LifecycleState.STOPPING.equals(state)
        || LifecycleState.STOPPED.equals(state)) {
      return;
    }

    if (state.equals(LifecycleState.NEW)) {
      state = LifecycleState.STOPPED;
      return;
    }

    if (!state.equals(LifecycleState.STARTED) && !state.equals(LifecycleState.FAILED)) {
      // invalidTransition(Lifecycle.BEFORE_STOP_EVENT);
    }

    try {
      if (state.equals(LifecycleState.FAILED)) {
        // fireLifecycleEvent(BEFORE_STOP_EVENT, null);
      } else {
        setStateInternal(LifecycleState.STOPPING_PREP, null, false);
      }
      stopInternal();
      if (!state.equals(LifecycleState.STOPPING) && !state.equals(LifecycleState.FAILED)) {
        // invalidTransition(Lifecycle.AFTER_STOP_EVENT);
      }
      setStateInternal(LifecycleState.STOPPED, null, false);
    } catch (Throwable t) {
      // handleSubClassException(t, "lifecycleBase.stopFail", toString());
    }
  }

  protected abstract void stopInternal() throws LifecycleException;

  @Override
  public final synchronized void destroy() throws LifecycleException {
    if (LifecycleState.FAILED.equals(state)) {
      try {
        // Triggers clean-up
        stop();
      } catch (LifecycleException e) {
      }
    }

    if (LifecycleState.DESTROYING.equals(state) || LifecycleState.DESTROYED.equals(state)) {
      return;
    }

    if (!state.equals(LifecycleState.STOPPED)
        && !state.equals(LifecycleState.FAILED)
        && !state.equals(LifecycleState.NEW)
        && !state.equals(LifecycleState.INITIALIZED)) {
      // invalidTransition(Lifecycle.BEFORE_DESTROY_EVENT);
    }
    try {
      setStateInternal(LifecycleState.DESTROYING, null, false);
      destroyInternal();
      setStateInternal(LifecycleState.DESTROYED, null, false);
    } catch (Throwable t) {
      // handleSubClassException(t, "lifecycleBase.destroyFail", toString());
    }
  }

  protected abstract void destroyInternal() throws LifecycleException;

  public LifecycleState getState() {
    return state;
  }

  protected synchronized void setState(LifecycleState state) throws LifecycleException {
    setStateInternal(state, null, true);
  }

  public String getStateName() {
    return getState().toString();
  }

  protected synchronized void setState(LifecycleState state, Object data)
      throws LifecycleException {
    setStateInternal(state, data, true);
  }

  private synchronized void setStateInternal(LifecycleState state, Object data, boolean check)
      throws LifecycleException {
    if (check) {

      if (state == null) {
        // invalidTransition("null");
        return;
      }
      if (!(state == LifecycleState.FAILED
          || (this.state == LifecycleState.STARTING_PREP && state == LifecycleState.STARTING)
          || (this.state == LifecycleState.STOPPING_PREP && state == LifecycleState.STOPPING)
          || (this.state == LifecycleState.FAILED && state == LifecycleState.STOPPING))) {
        // invalidTransition(state.name());
      }
    }
    this.state = state;
    String lifecycleEvent = state.getLifecycleEvent();
    if (lifecycleEvent != null) {
      fireLifecycleEvent(lifecycleEvent, data);
    }
  }
}
