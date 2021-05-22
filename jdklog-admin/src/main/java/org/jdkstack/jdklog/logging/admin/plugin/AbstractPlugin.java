package org.jdkstack.jdklog.logging.admin.plugin;

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * <p>
 *
 * @author admin
 * @version 2020-10-04 10:08
 * @since 2020-10-04 10:08:00
 */
public abstract class AbstractPlugin implements Plugin {
  private final String name;
  private final String description;

  public AbstractPlugin(final String name, final String description) {
    this.name = name;
    this.description = description;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public String description() {
    return this.description;
  }
}
