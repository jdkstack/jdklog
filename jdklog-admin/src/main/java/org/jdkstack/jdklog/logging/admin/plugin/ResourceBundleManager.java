package org.jdkstack.jdklog.logging.admin.plugin;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class ResourceBundleManager {
  private static final Map<String, Map<Locale, ResourceBundle>> resourceBundleManager =
      new HashMap<>();
  private static final ResourceBundleManager instance = new ResourceBundleManager();

  static {
    final Map<Locale, ResourceBundle> resourceBundles = new HashMap<>();
    final String packageName = "org.memory";
    final String bundleName = packageName + ".test";
    final ResourceBundle us = ResourceBundle.getBundle(bundleName, Locale.US);
    final ResourceBundle def = ResourceBundle.getBundle(bundleName, Locale.getDefault());
    resourceBundles.put(Locale.US, us);
    resourceBundles.put(Locale.getDefault(), def);
    resourceBundles.put(Locale.SIMPLIFIED_CHINESE, def);
    ResourceBundleManager.resourceBundleManager.put(packageName, resourceBundles);
  }

  public static ResourceBundleManager getInstance() {
    return ResourceBundleManager.instance;
  }

  public static ResourceBundleManager getInstance(final Class<?> clazz) {
    final String packageName = clazz.getPackage().getName();
    final Map<Locale, ResourceBundle> localeResourceBundleMap = ResourceBundleManager.resourceBundleManager.get(packageName);
    if (localeResourceBundleMap == null) {
      final Map<Locale, ResourceBundle> resourceBundles = new HashMap<>();
      final String bundleName = packageName + ".test";
      final ResourceBundle us = ResourceBundle.getBundle(bundleName, Locale.US);
      final ResourceBundle def = ResourceBundle.getBundle(bundleName, Locale.getDefault());
      resourceBundles.put(Locale.US, us);
      resourceBundles.put(Locale.getDefault(), def);
      resourceBundles.put(Locale.SIMPLIFIED_CHINESE, def);
      ResourceBundleManager.resourceBundleManager.put(packageName, resourceBundles);
    }
    return ResourceBundleManager.instance;
  }

  public String getString(final Class<?> clazz, final String key) {
    final Locale defaultLocale = Locale.getDefault();
    return this.getString(clazz, defaultLocale, key);
  }

  public String getString(final Class<?> clazz, final Locale locale, final String key) {
    final String packageName = clazz.getPackage().getName();
    final Locale defaultLocale = Locale.getDefault();
    final Map<Locale, ResourceBundle> localeResourceBundleMap = ResourceBundleManager.resourceBundleManager.get(packageName);
    ResourceBundle resourceBundle = null;
    if (locale == null) {
      resourceBundle = localeResourceBundleMap.get(defaultLocale);
    } else {
      resourceBundle = localeResourceBundleMap.get(locale);
    }
    return resourceBundle.getString(key);
  }

  public String getString(final Class<?> clazz, final String key, final Object... args) {
    final Locale defaultLocale = Locale.getDefault();
    return this.getString(clazz, defaultLocale, key, args);
  }

  public String getString(final Class<?> clazz, final Locale locale, final String key, final Object... args) {
    String value = this.getString(clazz, locale, key);
    if (value != null) {
      final MessageFormat temp = new MessageFormat(value);
      temp.setLocale(locale);
      value = temp.format(args);
    }
    return value;
  }
}
