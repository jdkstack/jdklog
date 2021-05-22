package org.jdkstack.jdklog.logging.admin.plugin.app;

import java.util.HashMap;
import java.util.Map;
import org.jdkstack.jdklog.logging.admin.plugin.AbstractAppServiceModuleLifecycle;

/**
 * web app服务模块的生命周期
 *
 * @author admin
 */
public final class AppServiceModuleLifecycle extends AbstractAppServiceModuleLifecycle {
  private static final AppServiceModuleLifecycle instance = new AppServiceModuleLifecycle();
  private final Map<String, String> fullyQualifiedNames = new HashMap<>();
  private final Map<String, String> webAppNames = new HashMap<>();
  private final Map<String, String> htmlFiles = new HashMap<>();
  private final Map<String, String> cssFiles = new HashMap<>();
  private final Map<String, String> jsFiles = new HashMap<>();
  private final Map<String, String> imageFiles = new HashMap<>();

  private AppServiceModuleLifecycle() {}

  public static AppServiceModuleLifecycle getInstance() {
    return AppServiceModuleLifecycle.instance;
  }

  @Override
  public void doInit() throws Exception {
    this.webAppNames.put("ROOT", "/ROOT");
    this.webAppNames.put("bi", "/bi");
    this.webAppNames.put("docs", "/docs");
    this.webAppNames.put("host-manager", "/host-manager");
    this.webAppNames.put("manager", "/manager");
  }

  @Override
  public void doStart() {}

  @Override
  protected void doStop() {
    System.out.println();
  }

  @Override
  protected void doClose() {
    System.out.println();
  }

  @Override
  protected void doDestroy() {
    System.out.println();
  }

  public Map<String, String> getWebAppNames() {
    return this.webAppNames;
  }
}
