package isi.dan.laboratorios.danmspedidos.rest;

import isi.dan.laboratorios.danmspedidos.exceptions.ApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(ApiException.class)
    ResponseEntity<Error> handleGlobalExceptions(ApiException e) {
        return new ResponseEntity(e.getError(), e.getStatus());
    }
}
