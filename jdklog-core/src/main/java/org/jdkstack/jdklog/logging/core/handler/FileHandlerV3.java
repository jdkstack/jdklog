package org.jdkstack.jdklog.logging.core.handler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;
import org.jdkstack.jdklog.logging.api.metainfo.Record;

/**
 * This is a method description.
 *
 * <p>FileChannel.
 *
 * @author admin
 */
public class FileHandlerV3 extends AbstractFileHandler {
  /** . */
  private FileChannel writer;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public FileHandlerV3() {
    super("");
    // 开始创建文件流,用于日志写入.
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
  public FileHandlerV3(final String prefix) {
    super(prefix);
    // 开始创建文件流,用于日志写入.
    this.open();
  }

  /**
   * .
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
        // 非阻塞方法获取队列元素.
        final Record logRecord = this.fileQueue.poll();
        // 如果数量不够,导致从队列获取空对象.
        if (null != logRecord && null != this.writer) {
          // 写入缓存(如果在publish方法中先格式化,则性能下降30%,消费端瓶颈取决于磁盘IO,生产端速度达不到最大,并发不够).
          final String format = this.formatter.format(logRecord);
          this.writer.write(ByteBuffer.wrap(format.getBytes(StandardCharsets.UTF_8)));
          // 设置不为空的标志.
          flag = true;
        }
      }
      // 如果缓存中由数据,刷新一次.
      if (flag) {
        // 刷新一次IO磁盘.
        this.writer.force(true);
      }
    } catch (final Exception e) {
      // ignore Exception.
      throw new StudyJuliRuntimeException(e);
    }
  }

  private void open() {
    this.writeLock.lock();
    try {
      final Path path = this.logFilePath.toPath();
      this.writer =
          (FileChannel)
              Files.newByteChannel(
                  path,
                  StandardOpenOption.CREATE,
                  StandardOpenOption.WRITE,
                  StandardOpenOption.APPEND);
      // 尝试写入一个空"".
      this.writer.write(ByteBuffer.allocate(0));
    } catch (final Exception e) {
      // 如何任何阶段发生了异常,主动关闭所有IO资源.
      this.closeIo();
      throw new StudyJuliRuntimeException(e);
    } finally {
      this.writeLock.unlock();
    }
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  private void closeIo() {
    this.writeLock.lock();
    try {
      this.writerClose();
    } catch (final IOException e) {
      throw new StudyJuliRuntimeException("关闭IO异常.", e);
    } finally {
      this.writeLock.unlock();
    }
  }

  private void writerClose() throws IOException {
    // 尝试关闭print writer流.
    if (null != this.writer) {
      this.writer.write(ByteBuffer.allocate(0));
      this.writer.force(true);
      this.writer.close();
      this.writer = null;
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
