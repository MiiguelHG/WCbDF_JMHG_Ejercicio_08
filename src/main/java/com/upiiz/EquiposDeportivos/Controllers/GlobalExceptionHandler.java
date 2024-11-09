package com.upiiz.EquiposDeportivos.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerNotFound(NoHandlerFoundException e){
        String message = "Error 404: Ruta solicitada no encontrada";
        return ResponseEntity.status(404).body(message);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handlerMethodNotAllowed(HttpRequestMethodNotSupportedException e){
        String message = "Error 405: Método no permitido para la ruta solicitada";
        return ResponseEntity.status(405).body(message);
    }
}
