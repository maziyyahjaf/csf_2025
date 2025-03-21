package vttp.batch5.csf.assessment.server.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import vttp.batch5.csf.assessment.server.exceptions.ErrorSavingOrderSql;
import vttp.batch5.csf.assessment.server.exceptions.InvalidPasswordException;
import vttp.batch5.csf.assessment.server.exceptions.InvalidUsernameException;
import vttp.batch5.csf.assessment.server.exceptions.PaymentFailureException;
import vttp.batch5.csf.assessment.server.models.ErrorMessage;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorMessage> handleInvalidPasswordException(InvalidPasswordException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(401, ex.getMessage(), LocalDateTime.now(), request.getRequestURI());
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(errorMessage);
    }

    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<ErrorMessage> handleInvalidUsernameException(InvalidUsernameException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(401, ex.getMessage(), LocalDateTime.now(), request.getRequestURI());
        return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(errorMessage);
    }
    
    @ExceptionHandler(PaymentFailureException.class)
    public ResponseEntity<ErrorMessage> handlePaymentFailureException(PaymentFailureException ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(500, ex.getMessage(), LocalDateTime.now(), request.getRequestURI());
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(errorMessage);
    }

    @ExceptionHandler(ErrorSavingOrderSql.class)
    public ResponseEntity<ErrorMessage> handleErrorSavingOrderSqlException(ErrorSavingOrderSql ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(500, ex.getMessage(), LocalDateTime.now(), request.getRequestURI());
        return ResponseEntity.status(HttpStatusCode.valueOf(500)).body(errorMessage);
    }
    

    
}
