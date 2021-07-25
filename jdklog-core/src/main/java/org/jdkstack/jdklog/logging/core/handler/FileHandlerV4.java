package org.jdkstack.jdklog.logging.core.handler;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;
import org.jdkstack.jdklog.logging.api.metainfo.Constants;
import org.jdkstack.jdklog.logging.api.metainfo.Record;

/**
 * This is a method description.
 *
 * <p>BufferedOutputStream.
 *
 * @author admin
 */
public class FileHandlerV4 extends AbstractFileHandler {
  /** . */
  private BufferedOutputStream bufferedOutputStreamWriter;
  /** . */
  private FileOutputStream fileStream;

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public FileHandlerV4() {
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
  public FileHandlerV4(final String prefix) {
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
        if (null != logRecord && null != this.bufferedOutputStreamWriter) {
          // 写入缓存(如果在publish方法中先格式化,则性能下降30%,消费端瓶颈取决于磁盘IO,生产端速度达不到最大,并发不够).
          final String format = this.formatter.format(logRecord);
          this.bufferedOutputStreamWriter.write(format.getBytes(StandardCharsets.UTF_8));
          // 设置不为空的标志.
          flag = true;
        }
      }
      // 如果缓存中由数据,刷新一次.
      if (flag) {
        // 刷新一次IO磁盘.
        this.bufferedOutputStreamWriter.flush();
      }
    } catch (final Exception e) {
      // ignore Exception.
      throw new StudyJuliRuntimeException(e);
    }
  }

  private void open() {
    this.writeLock.lock();
    try {
      // java:S2093 这个严重问题,暂时无法解决,先忽略sonar的警告.因为文件不能关闭,需要长时间打开.但是sonar检测,需要关闭IO资源.
      this.fileStream = new FileOutputStream(this.logFilePath, true);
      // 创建一个buffered流,缓存大小默认8192.
      this.bufferedOutputStreamWriter =
          new BufferedOutputStream(this.fileStream, Constants.BATCH_BUF_SIZE);
      // 尝试写入一个空"".
      this.bufferedOutputStreamWriter.write("".getBytes(StandardCharsets.UTF_8));
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
      this.fileStreamClose();
      this.writerClose();
    } finally {
      this.writeLock.unlock();
    }
  }

  private void writerClose() {
    // 尝试关闭print writer流.
    if (null != this.bufferedOutputStreamWriter) {
      try {
        this.bufferedOutputStreamWriter.write("".getBytes(StandardCharsets.UTF_8));
        this.bufferedOutputStreamWriter.flush();
        this.bufferedOutputStreamWriter.close();
        this.bufferedOutputStreamWriter = null;
      } catch (final IOException e) {
        throw new StudyJuliRuntimeException(e);
      }
    }
  }

  private void fileStreamClose() {
    // 尝试关闭文件流.
    if (null != this.fileStream) {
      try {
        this.fileStream.flush();
        this.fileStream.close();
      } catch (final IOException e) {
        throw new StudyJuliRuntimeException(e);
      }
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
