## Why

The project currently only has integration tests (AuthIntegrationTest) and a context-load test. All service-layer logic — CRUD operations, business rules, edge cases — is untested. This means regressions can slip through undetected and the code has no safety net for refactoring.

## What Changes

- ADD: Unit tests for `ProductoService` covering all CRUD operations
- ADD: Unit tests for `SucursalService` covering all CRUD operations
- ADD: Unit tests for `VentaService` covering crear, obtener, and borrar
- ADD: Unit tests for `AuthService` covering login success and failure
- ADD: Unit tests for `EstadisticaService` covering best-selling product calculation
- ADD: Unit tests for `JwtTokenProvider` covering token generation and validation
- ADD: Unit tests for all mappers (ProductoMapper, SucursalMapper, VentaMapper, UsuarioMapper)

## Capabilities

### New Capabilities
- `unit-test-coverage`: Service-layer and utility unit tests using Mockito + JUnit 5

### Modified Capabilities
- (none)

## Impact

- New test classes under `src/test/java/com/superrrr/franky/{producto,sucursal,venta,estadistica,auth}/service/`
- New test classes under `src/test/java/com/superrrr/franky/auth/service/` for JwtTokenProvider
- New test classes under `src/test/java/com/superrrr/franky/{producto,sucursal,venta,auth}/mapper/`
- No new production dependencies
