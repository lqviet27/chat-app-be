package vn.bt.spring.chatappbe.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import vn.bt.spring.chatappbe.Payload.Response.ErrorDetail;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetail> userExceptionHandler(UserException e, WebRequest request) {
        ErrorDetail err = new ErrorDetail(1, e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> otherExceptionHandler(Exception e, WebRequest request) {
        ErrorDetail err = new ErrorDetail(1, e.getMessage(), request.getDescription(false), LocalDateTime.now());
        return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);

       }
}
