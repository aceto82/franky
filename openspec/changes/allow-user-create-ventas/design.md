## Context

The Ventas feature currently requires `ROLE_ADMIN` for both creating and deleting sales. The rest of the role model (`ROLE_ADMIN` for all mutations, `ROLE_USER` read-only) stays intact. This is a single-annotation change in `VentaController`.

## Goals / Non-Goals

**Goals:**
- Allow both `ROLE_ADMIN` and `ROLE_USER` to create ventas via `POST /api/ventas`
- Keep `DELETE /api/ventas/{id}` restricted to `ROLE_ADMIN`

**Non-Goals:**
- Changes to productos, sucursales, or any other feature
- Changes to GET endpoint permissions
- Changes to the data model or service logic

## Decisions

1. **`hasAnyAuthority` over separate method** — Using `@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")` on `crearVenta()` is simpler than adding a custom permission evaluator or splitting the method. Single-line change.

2. **No new role** — Adding a `CAJERO` or `VENDEDOR` role would be overkill for one endpoint. USER is sufficient for now; a new role can be added later if more granularity is needed.

## Risks / Trade-offs

- **[Business] USER can create ventas but not productos** — This is intentional. POS employees need to ring up sales but shouldn't modify inventory master data.
- **[Test] Existing `createProducto_WithUserRole_Returns403` must stay** — That test verifies productos, not ventas. Add a new test for ventas with USER role expecting 201.
