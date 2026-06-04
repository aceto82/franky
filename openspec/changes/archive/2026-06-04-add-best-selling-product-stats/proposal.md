## Why

Business needs visibility into which products sell the most to make inventory and pricing decisions. Currently there is no endpoint to query sales statistics.

## What Changes

- NEW: `GET /api/estadisticas/producto-mas-vendido` returns the product with the highest total quantity sold across all ventas
- NEW: Feature package `estadistica/` under the standard sub-package structure
- The calculation aggregates `DetalleVenta.cantidad` grouped by `Producto` using Java Streams
- Accessible to any authenticated user (both ADMIN and USER roles)

## Capabilities

### New Capabilities
- `product-statistics`: Read-only statistics about product sales, starting with best-selling product

### Modified Capabilities
- (none — new capability only)

## Impact

- New feature package: `src/main/java/com/superrrr/franky/estadistica/` with `controller/`, `service/`, `dto/`
- New repository method or query to fetch all ventas with their detalles for in-memory stream processing
- Security: `@PreAuthorize("isAuthenticated()")` on the new controller
- No new dependencies, no schema changes
