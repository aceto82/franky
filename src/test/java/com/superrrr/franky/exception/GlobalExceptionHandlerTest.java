package com.superrrr.franky.exception;

import com.superrrr.franky.auth.exception.CredencialesInvalidasException;
import com.superrrr.franky.estadistica.exception.EstadisticaNoEncontradaException;
import com.superrrr.franky.producto.exception.ProductoNoEncontradoException;
import com.superrrr.franky.sucursal.exception.SucursalNoEncontradoException;
import com.superrrr.franky.venta.exception.VentaNoEncontradaException;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleValidationException_ShouldReturn400WithFieldErrors() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);

        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("obj", "nombre", "Nombre es requerido"),
                new FieldError("obj", "precio", "Precio debe ser positivo")
        ));

        ResponseEntity<Map<String, String>> result = handler.handleValidationException(ex);

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Nombre es requerido", result.getBody().get("nombre"));
        assertEquals("Precio debe ser positivo", result.getBody().get("precio"));
    }

    @Test
    void handleProductoNoEncontrado_ShouldReturn400() {
        ProductoNoEncontradoException ex = new ProductoNoEncontradoException("no encontrado");

        ResponseEntity<Map<String, String>> result = handler.handleProductoNoEncontrado(ex);

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Producto no encontrado", result.getBody().get("mensaje"));
    }

    @Test
    void handleSucursalNoEncontrado_ShouldReturn400() {
        SucursalNoEncontradoException ex = new SucursalNoEncontradoException("no encontrada");

        ResponseEntity<Map<String, String>> result = handler.handleSucursalNoEncontrado(ex);

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Sucursal no encontrada", result.getBody().get("mensaje"));
    }

    @Test
    void handleVentaNoEncontrada_ShouldReturn400() {
        VentaNoEncontradaException ex = new VentaNoEncontradaException("no encontrada");

        ResponseEntity<Map<String, String>> result = handler.handleVentaNoEncontrada(ex);

        assertEquals(400, result.getStatusCode().value());
        assertEquals("Venta no encontrada", result.getBody().get("mensaje"));
    }

    @Test
    void handleEstadisticaNoEncontrada_ShouldReturn404() {
        EstadisticaNoEncontradaException ex = new EstadisticaNoEncontradaException("no encontrada");

        ResponseEntity<Map<String, String>> result = handler.handleEstadisticaNoEncontrada(ex);

        assertEquals(404, result.getStatusCode().value());
        assertEquals("Estadistica no encontrada", result.getBody().get("mensaje"));
    }

    @Test
    void handleCredencialesInvalidas_ShouldReturn401() {
        CredencialesInvalidasException ex = new CredencialesInvalidasException("credenciales invalidas");

        ResponseEntity<Map<String, String>> result = handler.handleCredencialesInvalidas(ex);

        assertEquals(401, result.getStatusCode().value());
        assertEquals("Credenciales invalidas", result.getBody().get("mensaje"));
    }
}
