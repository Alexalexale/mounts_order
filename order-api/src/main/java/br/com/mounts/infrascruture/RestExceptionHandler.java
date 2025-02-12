package br.com.mounts.infrascruture;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import br.com.mounts.infrascruture.exception.OrderNotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  /** Trata exceções lançadas por conta de recursos não encontrados. */
  @ExceptionHandler({OrderNotFoundException.class})
  protected ResponseEntity<ProblemDetail> handleNotFoundException(final RuntimeException ex) {
    final var response = ProblemDetail.forStatusAndDetail(NOT_FOUND, ex.getMessage());
    return ResponseEntity.status(NOT_FOUND).body(response);
  }
}
