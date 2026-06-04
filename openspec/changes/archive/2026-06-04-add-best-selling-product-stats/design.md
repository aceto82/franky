## Context

A `GET /api/estadisticas/producto-mas-vendido` endpoint is needed to find the product with the highest total quantity sold. The data already exists in `DetalleVenta.cantidad` and `DetalleVenta.producto`. The calculation runs in-memory using Java Streams over all active ventas.

## Goals / Non-Goals

**Goals:**
- New `estadistica/` package following the project's feature-based structure
- Single endpoint returning the best-selling product with total quantity sold
- Calculation via Java Streams (grouping by Producto, summing cantidad, finding max)
- Accessible to any authenticated user

**Non-Goals:**
- No pagination, filtering, or date ranges for this initial version
- No caching layer — the query loads all active ventas each time
- No new database aggregates or stored procedures

## Decisions

1. **In-memory Streams over JPQL aggregate** — The requirement explicitly calls for Java Streams. Load all active ventas with their detalles via a single JPQL fetch join, then stream-group-sum-max in Java. Trade-off: works for small-to-medium datasets; a dedicated aggregate query would scale better.
2. **New `estadistica` package** — Placing the statistics endpoint in its own feature package (`controller/`, `service/`, `dto/`) follows the project convention and keeps concerns separated from `venta/` and `producto/`.
3. **DTO with producto info + totalVendido** — The response includes the `Producto` fields plus a computed `totalVendido` (sum of `cantidad` across all detalles for that product).
4. **`@PreAuthorize("isAuthenticated()")`** — Since this is a read-only analytics endpoint, any authenticated user (ADMIN or USER) can access it, matching the existing GET endpoint policy.

## Risks / Trade-offs

- **[Performance] Loading all ventas into memory** — For small-to-medium datasets this is fine. If the database grows large, this should switch to a JPQL aggregate query or a materialized view. The Streams approach is a requirement, so this is an accepted trade-off.
- **[Tie handling] Two products with equal total quantity** — The implementation returns the first product encountered after sorting by totalVendido descending. This is documented behavior; if a specific tie-breaker is needed later, it can be added.
