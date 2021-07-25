package org.jdkstack.jdklog.logging.core.handler;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;
import org.jdkstack.jdklog.logging.api.metainfo.Record;

/**
 * This is a method description.
 *
 * <p>BufferedWriter.
 *
 * @author admin
 */
public class FileHandlerV2 extends AbstractFileHandler {

  /** . */
  private BufferedWriter bufferedWriter;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public FileHandlerV2() {
    super("");
    this.open();
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param prefix prefix.
   * @author admin
   */
  public FileHandlerV2(final String prefix) {
    super(prefix);
    this.open();
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param size .
   * @author admin
   */
  @Override
  public final void process(final int size) {
    try {
      boolean flag = false;
      // 获取一批数据,写入磁盘.
      for (int i = 0; i < size; i++) {
        // 10, TimeUnit.MILLISECONDS,不等待.
        final Record logRecord = this.fileQueue.poll();
        // 如果数量不够,导致从队列获取空对象.
        if (null != logRecord && null != this.bufferedWriter) {
          final String format = this.formatter.format(logRecord);
          this.bufferedWriter.write(format);
          flag = true;
        }
      }
      if (flag) {
        // 刷新一次IO磁盘.
        this.bufferedWriter.flush();
      }
    } catch (final Exception e) {
      //
      throw new StudyJuliRuntimeException(e);
    }
  }

  /**
   * 创建BufferedWriter客户端.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  private void open() {
    this.writeLock.lock();
    try {
      final Path path = this.logFilePath.toPath();
      this.bufferedWriter =
          Files.newBufferedWriter(
              path,
              StandardCharsets.UTF_8,
              StandardOpenOption.CREATE,
              StandardOpenOption.WRITE,
              StandardOpenOption.APPEND);
      this.bufferedWriter.write("");
    } catch (final Exception e) {
      // 如何任何阶段发生了异常,主动关闭所有IO资源.
      this.closeIo();
      throw new StudyJuliRuntimeException(e);
    } finally {
      this.writeLock.unlock();
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  private void closeIo() {
    this.writeLock.lock();
    try {
      // 尝试关闭buffered writer流.
      if (null != this.bufferedWriter) {
        this.bufferedWriter.write("");
        this.bufferedWriter.flush();
        this.bufferedWriter.close();
        this.bufferedWriter = null;
      }
    } catch (final Exception e) {
      //
      throw new StudyJuliRuntimeException(e);
    } finally {
      this.writeLock.unlock();
    }
  }

  @Override
  public void doOpen() {
    this.open();
  }

  @Override
  public void doClose() {
    this.closeIo();
  }
}
