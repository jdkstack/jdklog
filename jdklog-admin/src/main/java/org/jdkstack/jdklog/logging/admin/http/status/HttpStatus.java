package org.jdkstack.jdklog.logging.admin.http.status;

/**
 * http状态
 *
 * @author admin
 */
public enum HttpStatus {
  /** ok */
  OK(200, "OK"),
  /** 创建成功 */
  CREATED(201, "Created"),
  NO_CONTENT(204, "No Content"),
  BAD_REQUEST(400, "Bad Request"),
  UNAUTHORIZED(401, "Unauthorized"),
  FORBIDDEN(403, "Forbidden"),
  NOT_FOUND(404, "Not Found"),
  METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
  NOT_ACCEPTABLE(406, "Not Acceptable"),
  UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
  INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
  SERVICE_UNAVAILABLE(503, "Service Unavailable"),
  HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version not supported");
  private final int value;
  private final String reasonPhrase;

  HttpStatus(final int value, final String reasonPhrase) {
    this.value = value;
    this.reasonPhrase = reasonPhrase;
  }

  public int value() {
    return value;
  }

  public String getReasonPhrase() {
    return reasonPhrase;
  }
}
