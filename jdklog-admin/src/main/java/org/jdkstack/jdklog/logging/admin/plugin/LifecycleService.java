package org.jdkstack.jdklog.logging.admin.plugin;

import java.util.HashMap;
import java.util.Map;
import org.jdkstack.jdklog.logging.admin.lifecycle.Lifecycle;
import org.jdkstack.jdklog.logging.admin.lifecycle.LifecycleException;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * <p>
 *
 * @author admin
 * @version 2020-09-04 10:21
 * @since 2020-09-04 10:21:00
 */
public class LifecycleService {

  private static final LifecycleService instance = new LifecycleService();
  /** 存储每一个生命周期组件,初始化之前的对象 */
  private static final Map<String, Lifecycle> lifecycleServices = new HashMap<>();

  /** 插件服务 */
  private LifecycleService() {}

  /**
   * .
   *
   * @return LifecycleService 生命周期
   */
  public static LifecycleService getInstance() {
    return LifecycleService.instance;
  }

  /**
   * 把生命周期
   *
   * @param lifecycleServiceName 生命周期服务名称
   * @param lifecycle 生命周期
   */
  public void putLifecycle(final String lifecycleServiceName, final Lifecycle lifecycle) {
    LifecycleService.lifecycleServices.put(lifecycleServiceName, lifecycle);
  }

  /**
   * 删除生命周期
   *
   * @param lifecycleServiceName 生命周期服务名称
   */
  public void removeLifecycle(final String lifecycleServiceName) {
    LifecycleService.lifecycleServices.remove(lifecycleServiceName);
  }

  /**
   * 初始化
   *
   * @throws LifecycleException 生命周期异常
   */
  public void init() throws LifecycleException {
    for (final Map.Entry<String, Lifecycle> entry : LifecycleService.lifecycleServices.entrySet()) {
      final Lifecycle lifecycle = entry.getValue();
      lifecycle.init();
    }
  }

  /**
   * 开始
   *
   * @throws LifecycleException 生命周期异常
   */
  public void start() throws LifecycleException {
    for (final Map.Entry<String, Lifecycle> entry : LifecycleService.lifecycleServices.entrySet()) {
      final Lifecycle lifecycle = entry.getValue();
      lifecycle.start();
    }
  }
}
