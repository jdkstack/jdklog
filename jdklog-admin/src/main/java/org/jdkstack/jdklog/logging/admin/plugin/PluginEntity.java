package org.jdkstack.jdklog.logging.admin.plugin;

import java.net.URLClassLoader;

/**
 * 插件的实体
 *
 * @author admin
 */
public class PluginEntity {
  private String pluginName;
  private URLClassLoader classLoader;
  private PluginMeta pluginMeta;
  private Plugin plugin;
  private PluginStatus pluginsStatus;

  public String getPluginName() {
    return this.pluginName;
  }

  public void setPluginName(final String pluginName) {
    this.pluginName = pluginName;
  }

  public URLClassLoader getClassLoader() {
    return this.classLoader;
  }

  public void setClassLoader(final URLClassLoader classLoader) {
    this.classLoader = classLoader;
  }

  public PluginMeta getPluginMeta() {
    return this.pluginMeta;
  }

  public void setPluginMeta(final PluginMeta pluginMeta) {
    this.pluginMeta = pluginMeta;
  }

  public Plugin getPlugin() {
    return this.plugin;
  }

  public void setPlugin(final Plugin plugin) {
    this.plugin = plugin;
  }

  public PluginStatus getPluginsStatus() {
    return this.pluginsStatus;
  }

  public void setPluginsStatus(final PluginStatus pluginsStatus) {
    this.pluginsStatus = pluginsStatus;
  }
}
