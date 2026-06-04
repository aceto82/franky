## 1. Repository

- [x] 1.1 Add `findAllByEstadoVentaNot(EstadoVenta estadoVenta)` to `VentaRepository` (fetch join on `detalles` and `detalles.producto` to avoid N+1)

## 2. DTO

- [x] 2.1 Create `ProductoMasVendidoDto` in `estadistica/dto/` with producto fields (id, nombre, categoria, precio) plus `totalVendido` (Long)

## 3. Service

- [x] 3.1 Create `EstadisticaService` in `estadistica/service/` with `obtenerProductoMasVendido()` that:
  - Loads all active ventas with detalles via the repository
  - Uses Java Streams to group `DetalleVenta` by `Producto`, sum `cantidad`, find max
  - Returns `ProductoMasVendidoDto` or throws a custom exception if no data

## 4. Controller

- [x] 4.1 Create `EstadisticaController` in `estadistica/controller/` with `GET /api/estadisticas/producto-mas-vendido`
- [x] 4.2 Add `@PreAuthorize("isAuthenticated()")` on the endpoint method

## 5. Exception Handling

- [x] 5.1 Create `EstadisticaNoEncontradaException` in `estadistica/exception/`
- [x] 5.2 Add handler for `EstadisticaNoEncontradaException` in `GlobalExceptionHandler` returning 404

## 6. Tests

- [x] 6.1 Add integration test in `AuthIntegrationTest` verifying `GET /api/estadisticas/producto-mas-vendido` returns 200 with valid token
- [x] 6.2 Add integration test verifying the endpoint returns 401 without token

## 7. Verify

- [x] 7.1 Run all tests: `./mvnw test -Dspring.profiles.active=h2` — all pass (11/11)
- [x] 7.2 Confirm compilation: `./mvnw compile -q` — no errors
