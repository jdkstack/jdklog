package org.jdkstack.jdklog.logging.admin.plugin;

/**
 * 不带字段的抽象类应转换为接口
 *
 * @author admin
 */
public interface Plugin {

  /**
   * 的名字 The name of the plugin.
   *
   * @return {@link String}
   */
  String name();

  /**
   * 描述 The description of the plugin.
   *
   * @return {@link String}
   */
  String description();
}
