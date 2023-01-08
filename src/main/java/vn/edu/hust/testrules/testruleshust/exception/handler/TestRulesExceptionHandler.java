package vn.edu.hust.testrules.testruleshust.exception.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.exception.response.ErrorResponse;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class TestRulesExceptionHandler extends ResponseEntityExceptionHandler {

  private final String RESULT_NG = "ng";

  private final MessageSource messageSource;

  @ExceptionHandler({ServiceException.class})
  public ResponseEntity<Object> handleServiceException(ServiceException exception) {

    log.error("log error is here service exception");

    if ("question_duplicate_text_and_image".equals(exception.getCauseId())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().result(RESULT_NG).errorMessage("question_duplicate_text_and_image").build());
    }

    if ("question_duplicate_text".equals(exception.getCauseId())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().result(RESULT_NG).errorMessage("question_duplicate_text").build());
    }

    if ("history_not_found".equals(exception.getCauseId())) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().result(RESULT_NG).errorMessage("history_not_found").build());
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().result(RESULT_NG).errorMessage("question_duplicate").build());
  }

  // This is demo
  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleRuntimeException(Exception exception, WebRequest request) {

    // log
    log.error("log error is here");

    ErrorResponse response =
        ErrorResponse.builder()
            .result(RESULT_NG)
            .errorMessage("This is error 500")
            .build();

    return super.handleExceptionInternal(
        exception, response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }
}
