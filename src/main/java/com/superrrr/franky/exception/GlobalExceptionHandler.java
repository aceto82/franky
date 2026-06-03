package com.superrrr.franky.exception;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(
            GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ProductoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleProductoNoEncontrado(ProductoNoEncontradoException ex){
        log.warn("Producto no encontrado {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("mensaje", "Producto no encontrado");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(SucursalNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> handleSucursalNoEncontrado(SucursalNoEncontradoException ex){
        log.warn("Sucursal no encontrada {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("mensaje", "Sucursal no encontrada");
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(VentaNoEncontradaException.class)
    public ResponseEntity<Map<String, String>> handleVentaNoEncontrada(VentaNoEncontradaException ex){
        log.warn("Venta no encontrada {}", ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        errors.put("mensaje", "Venta no encontrada");
        return ResponseEntity.badRequest().body(errors);
    }
}
