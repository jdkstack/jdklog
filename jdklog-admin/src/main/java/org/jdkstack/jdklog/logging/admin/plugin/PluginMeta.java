package org.jdkstack.jdklog.logging.admin.plugin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/**
 * 插件元数据
 *
 * @author admin
 */
public class PluginMeta {
  public static final String PLUGINPROPERTIES = "plugin-descriptor.properties";
  public static final String PLUGINPOLICY = "plugin-security.policy";
  private String name;
  private String description;
  private String version;
  private String className;
  private int type;

  public PluginMeta(final String name, final String description, final String version, final String className, final int type) {
    this.name = name;
    this.description = description;
    this.version = version;
    this.className = className;
    this.type = type;
  }

  public static PluginMeta getPluginMeta(final Path pluginDirectory) throws IOException {
    final String pluginDirName = pluginDirectory.getFileName().toString();
    final Path pluginDescriptorFile = pluginDirectory.resolve(PluginMeta.PLUGINPROPERTIES);
    final Properties props = new Properties();
    props.load(Files.newBufferedReader(pluginDescriptorFile, StandardCharsets.UTF_8));
    final String description = props.getProperty("plugin.description", "");
    final String version = props.getProperty("plugin.version", "");
    final int type = Integer.parseInt(props.getProperty("plugin.type", "1"));
    final String javaVersion = props.getProperty("java.version", "11");
    assert "11".equals(javaVersion) : "jdk 版本错误,必须是jdk11";
    final String className = props.getProperty("plugin.className", "");
    return new PluginMeta(pluginDirName, description, version, className, type);
  }

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(final String description) {
    this.description = description;
  }

  public String getVersion() {
    return this.version;
  }

  public void setVersion(final String version) {
    this.version = version;
  }

  public String getClassName() {
    return this.className;
  }

  public void setClassName(final String className) {
    this.className = className;
  }

  public int getType() {
    return this.type;
  }

  public void setType(final int type) {
    this.type = type;
  }
}
