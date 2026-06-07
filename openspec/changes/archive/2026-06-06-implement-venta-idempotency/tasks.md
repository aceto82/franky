## 1. Data model

- [x] 1.1 Add `idempotencyKey` field to `Venta` entity (nullable String, unique)
- [x] 1.2 Add `findByIdempotencyKey` method to `VentaRepository`

## 2. Service logic

- [x] 2.1 Modify `VentaService.CrearVenta` to accept `idempotencyKey` parameter
- [x] 2.2 Implement idempotency check: if key exists, return existing venta
- [x] 2.3 Handle `DataIntegrityViolationException` for concurrent requests with same key

## 3. Controller

- [x] 3.1 Extract `Idempotency-Key` header in `VentaController.crearVenta`
- [x] 3.2 Pass idempotency key to service and handle response status (200 vs 201)
- [x] 3.3 Add Swagger documentation for the new header

## 4. Tests

- [x] 4.1 Add test: first request with key creates venta (201)
- [x] 4.2 Add test: retry with same key returns existing venta (200)
- [x] 4.3 Add test: request without key creates normally
- [x] 4.4 Add test: empty key is ignored
