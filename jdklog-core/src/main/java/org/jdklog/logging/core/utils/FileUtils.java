package org.jdklog.logging.core.utils;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.jdklog.logging.api.metainfo.Constants;
import org.jdklog.logging.api.strategy.Strategy;
import org.jdklog.logging.core.strategy.DeleteStrategy;
import org.jdklog.logging.core.strategy.GzipCompressStrategy;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public final class FileUtils {
  /** . */
  private static final Strategy GZIPCOMPRESSSTRATEGY = new GzipCompressStrategy();
  /** . */
  private static final LinkedHashMap<String, File> LOGFILES = new LinkedHashMap<>(16);

  private FileUtils() {
    //
  }

  /**
   * 进行文件切换的瞬间,必须进行背压控制,否则内存溢出.
   *
   * <p>Another description after blank line.
   *
   * <p>需要优化处理.
   *
   * @author admin
   */
  public static void strategy() {
    // 获取要压缩和删除的文件.
    final int size = LOGFILES.size();
    if (Constants.LOOP_COUNT < size) {
      final int temp = size - Constants.LOOP_COUNT;
      final Set<Map.Entry<String, File>> entries = LOGFILES.entrySet();
      final Iterator<Map.Entry<String, File>> iterator = entries.iterator();
      for (int i = 0; i < temp; i++) {
        final Map.Entry<String, File> next = iterator.next();
        execute(next);
      }
    }
  }

  /**
   * 进行文件切换的瞬间,必须进行背压控制,否则内存溢出.
   *
   * <p>Another description after blank line.
   *
   * <p>需要优化处理.
   *
   * @param next next.
   * @author admin
   */
  public static void execute(final Map.Entry<String, File> next) {
    final String key = next.getKey();
    final File source = LOGFILES.get(key);
    final String path = source.getAbsolutePath();
    final String filePath = path + ".gz";
    final File destination = new File(filePath);
    // 执行压缩.
    GZIPCOMPRESSSTRATEGY.execute(source, destination);
    // 执行删除.
    DeleteStrategy.delete(source);
    // 删除条目.
    LOGFILES.remove(key);
  }

  /**
   * 进行文件切换的瞬间,必须进行背压控制,否则内存溢出.
   *
   * <p>Another description after blank line.
   *
   * <p>需要优化处理.
   *
   * @param file file.
   * @param logFileName logFileName.
   * @author admin
   */
  public static void putIfAbsent(final String logFileName, final File file) {
    LOGFILES.putIfAbsent(logFileName, file);
  }
}
