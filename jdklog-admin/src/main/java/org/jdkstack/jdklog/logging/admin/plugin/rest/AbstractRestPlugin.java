package org.jdkstack.jdklog.logging.admin.plugin.rest;


import org.jdkstack.jdklog.logging.admin.plugin.AbstractPlugin;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * <p>
 *
 * @author admin
 * @version 2020-10-04 10:12
 * @since 2020-10-04 10:12:00
 */
public abstract class AbstractRestPlugin extends AbstractPlugin implements RestPlugin {

  public AbstractRestPlugin(final String name, final String description) {
    super(name, description);
  }
}
