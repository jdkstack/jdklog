package org.jdkstack.jdklog.logging.admin.http.entity;

import java.util.HashMap;
import java.util.Map;
import org.jdkstack.jdklog.logging.admin.http.status.HttpStatus;

/**
 * Http 响应实体类
 *
 * @author admin
 */
public final class ResponseEntity<T> {
  private HttpStatus status;

  private T body;

  private String mimetype;

  private String fileName;

  private Map<String, String> headers = new HashMap<>(16);

  public ResponseEntity() {}

  public ResponseEntity(final HttpStatus status) {
    this.status = status;
  }

  public ResponseEntity(final HttpStatus status, final T body) {
    this.status = status;
    this.body = body;
  }

  public ResponseEntity(final HttpStatus status, final Map<String, String> headers, final T body) {
    this.status = status;
    this.headers = headers;
    this.body = body;
  }

  public ResponseEntity(
      final HttpStatus status,
      final Map<String, String> headers,
      final T body,
      final String mimetype) {
    this.status = status;
    this.headers = headers;
    this.body = body;
    this.mimetype = mimetype;
  }

  public ResponseEntity(
      final HttpStatus status,
      final Map<String, String> headers,
      final T body,
      final String mimetype,
      final String fileName) {
    this.status = status;
    this.headers = headers;
    this.body = body;
    this.mimetype = mimetype;
    this.fileName = fileName;
  }

  public static ResponseBuilder status(final HttpStatus status) {
    return new ResponseBuilder(status);
  }

  public static ResponseBuilder ok() {
    return ResponseEntity.status(HttpStatus.OK);
  }

  public static <T> ResponseEntity<T> ok(final T body) {
    final ResponseBuilder builder = ResponseEntity.ok();
    return builder.build(body);
  }

  public static <T> ResponseEntity<T> ok(final T body, final String mimetype) {
    final ResponseBuilder builder = ResponseEntity.ok();
    return builder.build(body, mimetype);
  }

  public static <T> ResponseEntity<T> ok(
      final T body, final String mimetype, final String fileName) {
    final ResponseBuilder builder = ResponseEntity.ok();
    return builder.build(body, mimetype, fileName);
  }

  public static ResponseBuilder created() {
    return ResponseEntity.status(HttpStatus.CREATED);
  }

  public static <T> ResponseEntity<T> created(final T body) {
    final ResponseBuilder builder = ResponseEntity.created();
    return builder.build(body);
  }

  public static ResponseBuilder noContent() {
    return ResponseEntity.status(HttpStatus.NO_CONTENT);
  }

  public static <T> ResponseEntity<T> noContent(final T body) {
    final ResponseBuilder builder = ResponseEntity.noContent();
    return builder.build(body);
  }

  public static ResponseBuilder notFound() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND);
  }

  public static <T> ResponseEntity<T> notFound(final T body) {
    final ResponseBuilder builder = ResponseEntity.notFound();
    return builder.build(body);
  }

  public static ResponseBuilder internalServerError() {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR);
  }

  public static <T> ResponseEntity<T> internalServerError(final T body) {
    final ResponseBuilder builder = ResponseEntity.internalServerError();
    return builder.build(body);
  }

  public HttpStatus getStatus() {
    return status;
  }

  public void setStatus(final HttpStatus status) {
    this.status = status;
  }

  public T getBody() {
    return body;
  }

  public void setBody(final T body) {
    this.body = body;
  }

  public String getMimetype() {
    return mimetype;
  }

  public String getFileName() {
    return fileName;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public ResponseEntity<T> headers(final Map<String, String> headers) {
    this.headers = headers;
    return this;
  }

  public static class ResponseBuilder {

    private final HttpStatus status;

    private Map<String, String> headers;

    public ResponseBuilder(final HttpStatus status) {
      this.status = status;
    }

    public ResponseBuilder headers(final Map<String, String> headers) {
      this.headers = headers;
      return this;
    }

    public <T> ResponseEntity<T> build() {
      return this.build(null);
    }

    public <T> ResponseEntity<T> build(final T body) {
      return new ResponseEntity<>(status, headers, body);
    }

    public <T> ResponseEntity<T> build(final T body, final String mimetype) {
      return new ResponseEntity<>(status, headers, body, mimetype);
    }

    public <T> ResponseEntity<T> build(final T body, final String mimetype, final String fileName) {
      return new ResponseEntity<>(status, headers, body, mimetype, fileName);
    }
  }
}
