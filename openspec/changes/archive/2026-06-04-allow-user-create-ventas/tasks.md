## 1. Controller Change

- [x] 1.1 Change `@PreAuthorize` on `VentaController.crearVenta()` from `hasAuthority('ROLE_ADMIN')` to `hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')`

## 2. Test Updates

- [x] 2.1 Add test in `AuthIntegrationTest` verifying USER role can create a venta (expects 201)
- [x] 2.2 Verify existing `createProducto_WithUserRole_Returns403` still passes (USER still blocked from productos)
- [x] 3.1 Run all tests: `./mvnw test -Dspring.profiles.active=h2` — all pass
- [x] 3.2 Confirm compilation: `./mvnw compile -q` — no errors
