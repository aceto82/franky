## 1. Controller Change

- [ ] 1.1 Change `@PreAuthorize` on `VentaController.crearVenta()` from `hasAuthority('ROLE_ADMIN')` to `hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')`

## 2. Test Updates

- [ ] 2.1 Add test in `AuthIntegrationTest` verifying USER role can create a venta (expects 201)
- [ ] 2.2 Verify existing `createProducto_WithUserRole_Returns403` still passes (USER still blocked from productos)

## 3. Verify

- [ ] 3.1 Run all tests: `./mvnw test -Dspring.profiles.active=h2` — all pass
- [ ] 3.2 Confirm compilation: `./mvnw compile -q` — no errors
