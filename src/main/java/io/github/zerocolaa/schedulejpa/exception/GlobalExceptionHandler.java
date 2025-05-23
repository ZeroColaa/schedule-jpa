package io.github.zerocolaa.schedulejpa.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + " : " + f.getDefaultMessage())
                .findFirst()
                .orElse("검증 실패");
        return ResponseEntity.badRequest()
                .body(new ErrorResponse("VALIDATION_ERROR", msg));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleStatus(ResponseStatusException ex) {
        return ResponseEntity.status(ex.getStatusCode())
                .body(new ErrorResponse("ERROR", ex.getReason()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleEtc(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(new ErrorResponse("SERVER_ERROR", "서버 내부 오류"));
    }
}
