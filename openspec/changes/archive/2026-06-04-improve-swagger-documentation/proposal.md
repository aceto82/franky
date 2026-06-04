## Why

The Swagger UI currently shows auto-generated endpoint names and empty descriptions, making it difficult for API consumers to understand each endpoint's purpose, request format, and response codes. Adding `@Operation`, `@ApiResponse`, and `@Schema` annotations across all controllers will produce a self-documenting, professional-grade API reference.

## What Changes

- Add `@Operation` with `summary` and `description` to every endpoint in all controllers
- Add `@ApiResponse` annotations documenting possible HTTP status codes (200, 201, 400, 401, 403, 404, 500)
- Add `@Schema` annotations to request/response DTOs where descriptions are missing
- Add `@Parameter` annotations for path/query parameters
- Apply annotations to `AuthController`, `ProductoController`, `SucursalController`, `VentaController`, and `EstadisticaController`
- No behavioral changes — documentation-only

## Capabilities

### New Capabilities
- `api-documentation`: Swagger/OpenAPI annotations (`@Operation`, `@ApiResponse`, `@Schema`) covering all REST endpoints, DTOs, and parameters

### Modified Capabilities
<!-- None — first spec-driven change, no existing specs -->

## Impact

- All 5 controller classes: add import statements and method-level annotations
- Selected DTO classes: add `@Schema` annotations
- No runtime dependencies, no API contract changes, no DB changes
