package org.jdkstack.jdklog.logging.admin.http.entity;

/**
 * 用户
 *
 * @author admin
 */
public class User {
  private int id;
  private String name;

  public String getName() {
    return this.name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public int getId() {
    return this.id;
  }

  public void setId(final int id) {
    this.id = id;
  }
}
