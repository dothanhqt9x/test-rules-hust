package vn.edu.hust.testrules.testruleshust.exception.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.edu.hust.testrules.testruleshust.exception.ServiceException;
import vn.edu.hust.testrules.testruleshust.exception.response.ErrorResponse;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class TestRulesExceptionHandler extends ResponseEntityExceptionHandler {

  private final String RESULT_NG = "ng";

  private final MessageSource messageSource;

  @ExceptionHandler({BadCredentialsException.class})
  public ResponseEntity<Object> handleBadCredentialsException() {

    log.error("log error is here BadCredentialsException exception");

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().result(RESULT_NG).errorMessage("Email hoặc mật khẩu không đúng").build());
  }

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

    if ("answer_duplicate".equals(exception.getCauseId())) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().result(RESULT_NG).errorMessage("answer_duplicate").build());
    }

    if ("question_answer_key_is_null".equals(exception.getCauseId())) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder().result(RESULT_NG).errorMessage("question_answer_key_is_null").build());
    }

    if ("Account already exists".equals(exception.getCauseId())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().result(RESULT_NG).errorMessage("Account already exists").build());
    }

    if ("Account already exists".equals(exception.getCauseId())) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().result(RESULT_NG).errorMessage("Tài khoản đã tồn tại").build());
    }

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().result(RESULT_NG).errorMessage("question_duplicate").build());
  }

//  @ExceptionHandler(MethodArgumentNotValidException.class)
//  public void handle400(MethodArgumentNotValidException ex) {
//
//    log.error("log error here 400");
//  }

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


  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers, HttpStatus status, WebRequest request) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().result(RESULT_NG).errorMessage(ex.getParameter().getParameterName()).build());
  }
}
