## Why

The current role model is too restrictive for the Ventas feature. A USER role employee should be able to register sales at the point of sale, even if they can't create products or modify master data. Blocking venta creation for USER blocks the core business flow.

## What Changes

- CHANGE: `VentaController.crearVenta()` now allows both `ROLE_ADMIN` and `ROLE_USER`
- KEEP: `VentaController.borrarVenta()` remains `ROLE_ADMIN` only
- KEEP: all other mutation endpoints (productos, sucursales) remain `ROLE_ADMIN` only
- KEEP: GET endpoints remain accessible to any authenticated user

## Capabilities

### New Capabilities
*(None — no new capabilities introduced)*

### Modified Capabilities
- `role-based-access`: The USER role requirement changes — USER can now create ventas (POST /api/ventas)

## Impact

- **`VentaController.java`**: change `@PreAuthorize` from `hasAuthority('ROLE_ADMIN')` to `hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')` on `crearVenta()` only
- **`AuthIntegrationTest`**: update `createProducto_WithUserRole_Returns403` → create a similar test for ventas verifying 201 instead of 403
- **No new dependencies, no schema changes, no new entities**
