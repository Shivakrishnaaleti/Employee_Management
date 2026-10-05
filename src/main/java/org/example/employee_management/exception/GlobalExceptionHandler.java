package org.example.employee_management.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request){

        ErrorResponse response = new ErrorResponse(LocalDateTime.now(),String.valueOf(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND.getReasonPhrase(),ex.getMessage(),request.getRequestURI(), List.of());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);



    }


    @ExceptionHandler({DuplicateResourceException.class, ResourceConflictException.class})
    public ResponseEntity<ErrorResponse> handleConflict(RuntimeException ex, HttpServletRequest request){

        ErrorResponse response = new ErrorResponse();

        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(String.valueOf(HttpStatus.CONFLICT.value()));
        response.setError(HttpStatus.CONFLICT.getReasonPhrase());
        response.setMessage(ex.getMessage());
        response.setPath(request.getRequestURI());
        response.setErrors(List.of());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);



    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request){

        List<String> errors = new ArrayList<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()){
            String errorMessage = error.getField() + " : " + error.getDefaultMessage();

            errors.add(errorMessage);
        }

        ErrorResponse response = new ErrorResponse();

        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        response.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        response.setMessage("Validation Error...");
        response.setPath(request.getRequestURI());
        response.setErrors(errors);

        return new  ResponseEntity<>(response, HttpStatus.BAD_REQUEST);


    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        List<String> errors = ex.getConstraintViolations().stream()
                .map(this::formatConstraintViolation)
                .toList();

        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation Error",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity.badRequest().body(response);
    }

    private String formatConstraintViolation(ConstraintViolation<?> violation) {
        return violation.getPropertyPath() + " : " + violation.getMessage();
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request){

        ErrorResponse response = new ErrorResponse();

        response.setTimeStamp(LocalDateTime.now());
        response.setStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        response.setError(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        response.setMessage("An Unexpected error occurred..");
        response.setPath(request.getRequestURI());
        response.setErrors(List.of());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);


    }
}
