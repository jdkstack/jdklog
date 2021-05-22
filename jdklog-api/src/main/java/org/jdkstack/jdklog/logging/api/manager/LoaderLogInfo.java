package org.jdkstack.jdklog.logging.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import org.jdkstack.jdklog.logging.api.handler.Handler;
import org.jdkstack.jdklog.logging.api.logger.Logger;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
public interface LoaderLogInfo {

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return Logger .
   * @author admin
   */
  Logger getRootLogger();

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return Map .
   * @author admin
   */
  Map<String, Handler> getHandlers();

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @param value value.
   * @author admin
   */
  void setProperty(String key, String value);

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param inputStream inputStream.
   * @throws IOException IOException.
   * @author admin
   */
  void load(InputStream inputStream) throws IOException;

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @return boolean boolean.
   * @author admin
   */
  boolean isEmpty();

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param loggerName loggerName.
   * @return boolean boolean.
   * @author admin
   */
  boolean containsKey(String loggerName);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name name.
   * @return Logger Logger.
   * @author admin
   */
  Logger get(String name);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param name name.
   * @param logger logger.
   * @author admin
   */
  void put(String name, Logger logger);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handler handler.
   * @param handlerName handlerName.
   * @author admin
   */
  void putIfAbsent(String handlerName, Handler handler);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @param handlerName handlerName.
   * @return Handler Handler.
   * @author admin
   */
  Handler getHandler(String handlerName);

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @param defaultValue value.
   * @return String .
   * @author admin
   */
  String getProperty(String key, String defaultValue);

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param key key.
   * @return String .
   * @author admin
   */
  String getProperty(String key);

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param handler handler.
   * @author admin
   */
  void addHandler(Handler handler);

  /**
   * .
   *
   * <p>Another description after blank line.
   *
   * @param levelName .
   * @author admin
   */
  void setLevel(String levelName);
}
