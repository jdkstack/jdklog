package org.jdkstack.jdklog.logging.admin.plugin;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * 标准插件executor类装入器
 *
 * @author admin
 */
public class StandardPluginExecutorClassLoader extends URLClassLoader {
  /**
   * 标准插件executor类装入器
   *
   * @param classLoaderName 类装入器的名字
   * @param urls url
   * @param parent 父
   */
  public StandardPluginExecutorClassLoader(final String classLoaderName, final URL[] urls, final ClassLoader parent) {
    super(classLoaderName, urls, parent);
  }
}
