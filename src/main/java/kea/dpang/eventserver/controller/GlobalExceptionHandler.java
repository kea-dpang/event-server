package kea.dpang.eventserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import kea.dpang.eventserver.exception.EventNotFoundException;
import kea.dpang.eventserver.base.ErrorResponse;
import kea.dpang.eventserver.exception.EventTargetItemNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEventNotFoundException(HttpServletRequest request, EventNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getClass().getSimpleName(), request.getRequestURI(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventTargetItemNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEventTargetItemNotFoundException(HttpServletRequest request, EventTargetItemNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), ex.getClass().getSimpleName(), request.getRequestURI(), LocalDateTime.now());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
