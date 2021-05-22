package org.jdkstack.jdklog.logging.admin.plugin;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jdkstack.jdklog.logging.admin.exception.ServerRuntimeException;
import org.jdkstack.jdklog.logging.admin.lifecycle.module.AbstractModuleLifecycle;

/**
 * 插件服务生命周期
 *
 * @author admin
 */
public class PluginServiceLifecycle extends AbstractModuleLifecycle implements PluginService {
  /** 用来保存正常的插件信息(成功的) */
  protected static Map<String, PluginEntity> pluginEntitys = new ConcurrentHashMap<>();
  /** 用来保存不正常的插件信息(失败的) */
  protected static Map<String, PluginEntity> abnormalPluginEntitys = new ConcurrentHashMap<>();
  /** 扫描具体插件目录下的所有的jar文件,例如:/bank/*.jar */
  private static final String fileExtension = "*.jar";
  /** /{服务的home目录,从System中获取}/plugin/{插件的类型目录,例如:rest}/{具体插件的目录,例如:bank} */
  private final Path pluginParentDir;

  public PluginServiceLifecycle(final Path pluginParentDirectory) {
    // 具体插件的上一层目录/rest
    this.pluginParentDir = pluginParentDirectory;
  }

  /**
   * 做初始化
   *
   * @throws IOException ioexception
   */
  @Override
  protected void doInit() throws IOException {
    // 具体的插件目录/bank
    try (final DirectoryStream<Path> paths = Files.newDirectoryStream(this.pluginParentDir)) {
      for (final Path pluginTargetDir : paths) {
        // 获取/bank/*.properties插件的配置文件
        final PluginMeta pluginMeta = PluginMeta.getPluginMeta(pluginTargetDir);
        // 从配置文件中得到插件名称
        final String pluginName = pluginMeta.getName();
        // 从配置文件中得到插件全限定名
        final String pluginClassName = pluginMeta.getClassName();
        // 获取所有的jar文件,并转换成URL格式
        final List<URL> urls = this.jars2Urls(pluginTargetDir);
        final StandardPluginExecutorClassLoader classLoader = this.getClassLoader(pluginName, urls);
        final Class<? extends Plugin> classObj = this.getClass(pluginClassName, classLoader);
        if (classObj == null) {
          throw new ServerRuntimeException("无法获取类ClassNotFoundException");
        }
        final Constructor<? extends Plugin> constructor = this.getConstructor(classObj);
        if (constructor == null) {
          throw new ServerRuntimeException("反射调用异常");
        }
        // 利用构造器对象,实例化具体的实例对象
        final Plugin plugin = this.getPlugin(constructor);
        if (plugin == null) {
          throw new ServerRuntimeException("plugin类反射异常");
        }
        // 创建一个新的实体类
        final PluginEntity newPluginEntity = new PluginEntity();
        // 设置插件信息
        newPluginEntity.setPluginMeta(pluginMeta);
        // 设置classloader
        newPluginEntity.setClassLoader(classLoader);
        // 设置插件对象
        newPluginEntity.setPlugin(plugin);
        // 从配置文件中得到插件信息
        final PluginEntity pluginEntity = PluginServiceLifecycle.pluginEntitys.get(pluginName);
        if (pluginEntity != null) {
          // 设置插件目前的状态是CLOSE
          newPluginEntity.setPluginsStatus(PluginStatus.CLOSE);
          // 保存到异常插件列表
          PluginServiceLifecycle.abnormalPluginEntitys.put(pluginName, pluginEntity);
        } else {
          // 设置插件目前的状态是OPEN
          newPluginEntity.setPluginsStatus(PluginStatus.OPEN);
          // 保存到正常插件列表
          PluginServiceLifecycle.pluginEntitys.put(pluginName, pluginEntity);
        }
      }
    }
  }

  /**
   * 得到类装入器
   *
   * @param pluginName 插件名称
   * @param urlsList url列表
   * @return {@link StandardPluginExecutorClassLoader}
   */
  private StandardPluginExecutorClassLoader getClassLoader(final String pluginName, final List<URL> urlsList) {
    // 获取自定义的类加载器,加载所有URL格式的jar文件
    final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    final URL[] urls = urlsList.toArray(new URL[0]);
    return new StandardPluginExecutorClassLoader(pluginName, urls, contextClassLoader);
  }

  /**
   * 把插件
   *
   * @param constructor 构造函数
   * @return {@link Plugin}
   */
  private Plugin getPlugin(final Constructor<? extends Plugin> constructor) {
    Plugin plugin = null;
    try {
      plugin = constructor.newInstance();
    } catch (final InstantiationException e) {

    } catch (final IllegalAccessException e) {

    } catch (final InvocationTargetException e) {

    }
    return plugin;
  }

  /**
   * 把构造函数
   *
   * @param classObj 类obj
   * @return {@link Constructor<? extends Plugin >}
   */
  private Constructor<? extends Plugin> getConstructor(final Class<? extends Plugin> classObj) {
    // 得到Class对象后,利用构造器进行反射,获取具体的构造器对象
    Constructor<? extends Plugin> constructor = null;
    try {
      constructor = classObj.getConstructor();
    } catch (final NoSuchMethodException e) {

    }
    return constructor;
  }

  /**
   * gata类
   *
   * @param pluginClassName 插件类名
   * @param specl specl
   * @return {@link Class<? extends Plugin >}
   */
  private Class<? extends Plugin> getClass(
      final String pluginClassName, final StandardPluginExecutorClassLoader specl) {
    // 从加载器中获取字节码Class对象,并检查是否继承于Plugin对象
    Class<? extends Plugin> classObj = null;
    try {
      classObj = specl.loadClass(pluginClassName).asSubclass(Plugin.class);
    } catch (final ClassNotFoundException e) {

    }
    return classObj;
  }

  /**
   * jar -> url
   *
   * @param pluginTargetDir 插件目标dir
   * @return {@link List<URL>}* @throws IOException ioexception
   */
  private List<URL> jars2Urls(final Path pluginTargetDir) throws IOException {
    final List<URL> urls = new ArrayList<>();
    try (final DirectoryStream<Path> paths = Files.newDirectoryStream(pluginTargetDir, PluginServiceLifecycle.fileExtension)) {
      for (final Path jar : paths) {
        urls.add(jar.toRealPath().toUri().toURL());
      }
    }
    return urls;
  }

  @Override
  public void doStart() throws Exception {
    System.out.println();
  }

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

  /** 获取正常的插件列表 */
  public Map<String, PluginEntity> getPluginEntitys() {
    return PluginServiceLifecycle.pluginEntitys;
  }
}
