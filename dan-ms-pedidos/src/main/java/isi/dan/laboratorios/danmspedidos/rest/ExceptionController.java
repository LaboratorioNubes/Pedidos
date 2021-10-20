package isi.dan.laboratorios.danmspedidos.rest;

import isi.dan.laboratorios.danmspedidos.exceptions.ApiError;
import isi.dan.laboratorios.danmspedidos.exceptions.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ApiException.class)
    ResponseEntity<Error> handleGlobalExceptions(ApiException e) {
        return new ResponseEntity(e.getError(), e.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ApiException> handleValidationExceptions(MethodArgumentNotValidException e) {
        ApiError apiError = new ApiError("MethodArgumentNotValidException", e.getBindingResult().getFieldError().getDefaultMessage());
        ApiException error = new ApiException(apiError, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
