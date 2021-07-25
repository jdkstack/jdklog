package org.jdkstack.jdklog.examples.handler;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import org.jdkstack.jdklog.logging.api.exception.StudyJuliRuntimeException;
import org.jdkstack.jdklog.logging.api.metainfo.Record;
import org.jdkstack.jdklog.logging.core.handler.AbstractFileHandler;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public class ConsoleHandler extends AbstractFileHandler {
  /** . */
  private PrintWriter consoleWriter;
  /** . */
  private OutputStreamWriter streamWriter;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  public ConsoleHandler() {
    super("");
    // 开始创建文件流,用于日志写入.
    this.open();
  }

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param prefix prefix.
   * @author admin
   */
  public ConsoleHandler(final String prefix) {
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
        if (null != logRecord && null != this.consoleWriter) {
          // 写入缓存(如果在publish方法中先格式化,则性能下降30%,消费端瓶颈取决于磁盘IO,生产端速度达不到最大,并发不够).
          final String format = this.formatter.format(logRecord);
          this.consoleWriter.write(format);
          // 设置不为空的标志.
          flag = true;
        }
      }
      // 如果缓存中由数据,刷新一次.
      if (flag) {
        // 刷新一次IO磁盘.
        this.consoleWriter.flush();
      }
    } catch (final Exception e) {
      // ignore Exception.
      throw new StudyJuliRuntimeException(e);
    }
  }

  private void open() {
    this.writeLock.lock();
    try {
      // 创建一个输出流,使用UTF-8 编码.
      final PrintStream err = System.err;
      this.streamWriter = new OutputStreamWriter(err, StandardCharsets.UTF_8);
      // 创建一个PrintWriter,启动自动刷新.
      this.consoleWriter = new PrintWriter(this.streamWriter, true);
      // 尝试写入一个空"".
      this.consoleWriter.write("");
    } catch (final Exception e) {
      // 如何任何阶段发生了异常,主动关闭所有IO资源.
      this.closeIo();
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
      // 尝试关闭stream writer流.
      if (null != this.streamWriter) {
        try {
          this.streamWriter.flush();
          this.streamWriter.close();
        } catch (final IOException e) {
          throw new StudyJuliRuntimeException(e);
        }
      }
      // 尝试关闭print writer流.
      if (null != this.consoleWriter) {
        this.consoleWriter.write("");
        this.consoleWriter.flush();
        this.consoleWriter.close();
        this.consoleWriter = null;
      }
    } finally {
      this.writeLock.unlock();
    }
  }
}
