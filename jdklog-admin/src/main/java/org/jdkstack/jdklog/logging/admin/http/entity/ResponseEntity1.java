package org.jdkstack.jdklog.logging.admin.http.entity;

import java.util.HashMap;
import java.util.Map;
import org.jdkstack.jdklog.logging.admin.http.status.HttpStatus;

/**
 * 响应entity1
 *
 * @author admin
 */
public class ResponseEntity1<T> {
  private Map<String, String> headers = new HashMap<>(16);
  private HttpStatus status;
  private T body;

  public ResponseEntity1(final HttpStatus status, final Map<String, String> headers, final T body) {
    this.status = status;
    this.headers = headers;
    this.body = body;
  }

  public ResponseEntity1(final HttpStatus status) {
    this.status = status;
  }

  public ResponseEntity1(final T body) {
    this.body = body;
  }

  public ResponseEntity1(final HttpStatus status, final T body) {
    this.status = status;
    this.body = body;
  }
}
