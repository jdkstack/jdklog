package org.jdkstack.jdklog.logging.admin.plugin;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import org.jdkstack.jdklog.logging.admin.lifecycle.LifecycleException;

/**
 * 插件服务
 *
 * @author admin
 */
public final class PluginServices {
  /** 保存所有插件的元数据和实例 */
  private static final Map<String, PluginServiceLifecycle> pluginServiceLifecycles =
      new HashMap<>();

  private static final PluginServices instance = new PluginServices();
  private static Path pluginHomeDirectory;

  /** 插件服务 */
  private PluginServices() {
    // 读取资源国际化文件,测试
    // ResourceBundleManager rbm = ResourceBundleManager.getInstance();
    // System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + rbm.getString(PluginServices.class,
    // Locale.US, "simpleServerAuthConfig.noModules"));
  }

  /** 获得实例 */
  public static PluginServices getInstance() {
    return PluginServices.instance;
  }

  public static PluginServices getInstance(final Path pluginHomeDir) {
    PluginServices.pluginHomeDirectory = pluginHomeDir;
    return PluginServices.instance;
  }

  /** 加载所有插件 */
  public void loadPlugins() throws LifecycleException {
    try (final DirectoryStream<Path> paths =
        Files.newDirectoryStream(PluginServices.pluginHomeDirectory)) {
      for (final Path pluginParentDirectory : paths) {
        // 插件的种类目录
        final String pluginParentDirectoryName = pluginParentDirectory.getFileName().toString();
        // 创建一个插件的服务
        final PluginServiceLifecycle pluginServiceLifecycle =
            new PluginServiceLifecycle(pluginParentDirectory);
        pluginServiceLifecycle.init();
        // 保存这个插件种类的服务
        PluginServices.pluginServiceLifecycles.put(
            pluginParentDirectoryName, pluginServiceLifecycle);
      }
    } catch (final IOException e) {
      //
    }
  }

  public Map<String,PluginServiceLifecycle> getPluginServiceLifecycles() {
    return PluginServices.pluginServiceLifecycles;
  }
}
