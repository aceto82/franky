## 1. Global Exception Handler

- [x] 1.1 Add handler for `MissingRequestHeaderException` in `GlobalExceptionHandler` returning `400 Bad Request` with mensaje claro
- [x] 1.2 Create `IdempotencyKeyRequeridaException` in `venta/exception/` package
- [x] 1.3 Add handler for `IdempotencyKeyRequeridaException` in `GlobalExceptionHandler` returning `400 Bad Request`

## 2. Service Layer

- [x] 2.1 Add validation at start of `VentaService.CrearVenta()`: reject null or blank `idempotencyKey` throwing `IdempotencyKeyRequeridaException`
- [x] 2.2 Remove the branch that calls `crearNuevaVenta(ventaRequestDto, null)` — always use the provided key path

## 3. Controller Layer

- [x] 3.1 Change `@RequestHeader(value = "Idempotency-Key", required = false)` to `required = true` in `VentaController.crearVenta()`
- [x] 3.2 Remove the null/blank guard in the controller (already handled by `required = true` + service validation)
- [x] 3.3 Update Swagger/OpenAPI `@Operation` and `@ApiResponse` docs to reflect that header is now required

## 4. Data Model

- [x] 4.1 Change `@Column(name = "idempotency_key", unique = true)` to add `nullable = false` in `Venta.java`
- [x] 4.2 Create SQL migration script for backfill: `UPDATE ventas SET idempotency_key = gen_random_uuid()::text WHERE idempotency_key IS NULL`
- [x] 4.3 Create SQL migration script for NOT NULL: `ALTER TABLE ventas ALTER COLUMN idempotency_key SET NOT NULL`

## 5. Tests — Service

- [x] 5.1 Update `CrearVenta_WithoutIdempotencyKey_ShouldCreateNormally` → rename and adapt to verify `IdempotencyKeyRequeridaException` is thrown when key is null
- [x] 5.2 Update `CrearVenta_WithBlankIdempotencyKey_ShouldCreateNormally` → adapt to verify exception is thrown when key is blank
- [x] 5.3 Verify existing idempotency tests still pass (first request, retry, concurrent)

## 6. Tests — Controller

- [x] 6.1 Update `crearVenta_ShouldReturn201` to include `Idempotency-Key` header
- [x] 6.2 Add test `crearVenta_WithoutIdempotencyKey_ShouldReturn400` verifying missing header returns 400
- [x] 6.3 Add test `crearVenta_WithBlankIdempotencyKey_ShouldReturn400` verifying blank header returns 400
