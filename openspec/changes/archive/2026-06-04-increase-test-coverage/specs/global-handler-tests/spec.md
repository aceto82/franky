## ADDED Requirements

### Requirement: GlobalExceptionHandler handles validation errors
The handler SHALL return 400 Bad Request with field error details when a `MethodArgumentNotValidException` occurs.

#### Scenario: Validation error returns 400 with field errors
- **WHEN** a controller method decorated with `@Valid` receives invalid input
- **THEN** the response SHALL be 400 Bad Request with a body containing field-level error messages

### Requirement: GlobalExceptionHandler handles not-found exceptions
The handler SHALL return 404 Not Found when a domain-specific not-found exception is thrown (ProductoNoEncontradoException, SucursalNoEncontradoException, VentaNoEncontradaException).

#### Scenario: Producto not found returns 404
- **WHEN** a service method throws `ProductoNoEncontradoException`
- **THEN** the response SHALL be 404 Not Found with an appropriate error message

#### Scenario: Sucursal not found returns 404
- **WHEN** a service method throws `SucursalNoEncontradoException`
- **THEN** the response SHALL be 404 Not Found with an appropriate error message

#### Scenario: Venta not found returns 404
- **WHEN** a service method throws `VentaNoEncontradaException`
- **THEN** the response SHALL be 404 Not Found with an appropriate error message

### Requirement: GlobalExceptionHandler handles generic exceptions
The handler SHALL return 500 Internal Server Error for unhandled exceptions.

#### Scenario: Generic exception returns 500
- **WHEN** a controller or service throws an unhandled `RuntimeException`
- **THEN** the response SHALL be 500 Internal Server Error with a generic error message
