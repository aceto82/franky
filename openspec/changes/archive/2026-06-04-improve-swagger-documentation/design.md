## Context

Swagger UI at `/swagger-ui.html` currently shows auto-generated endpoint names and empty descriptions — not useful for API consumers. The `springdoc-openapi-starter-webmvc-ui` dependency is already in `pom.xml` and an `OpenApiConfig` bean configures the API info and JWT security scheme. Only endpoint-level and DTO-level annotations are missing.

## Goals / Non-Goals

**Goals:**
- Add `@Operation` with `summary` and `description` to all controller methods
- Add `@ApiResponse` annotations documenting possible HTTP status codes per endpoint
- Add `@Schema` annotations to request/response DTOs for field-level descriptions
- Add `@Parameter` annotations for path and query parameters
- Cover all 5 controllers: Auth, Producto, Sucursal, Venta, Estadistica

**Non-Goals:**
- No behavioral or logic changes to endpoints
- No DTO restructuring or new fields
- No changes to entity, service, or repository layers

## Decisions

| Decision | Choice | Rationale |
|---|---|---|
| Annotation style | `@Operation`, `@ApiResponse`, `@Schema` from `io.swagger.v3.oas.annotations` | SpringDoc auto-picks these up; consistent with `OpenApiConfig` using the same model package |
| HTTP status codes per endpoint | Document all codes the controller can return (200, 201, 400, 401, 403, 404, 500) | Gives consumers a complete picture of possible responses |
| `@ApiResponse` grouping | One annotation per status code | Standard practice; avoids responseCode string in generic response |
| Auth on endpoints | Document via OpenApiConfig's security scheme (already set); add description mentioning required role | The lock icon in Swagger UI covers auth; description enriches the text |

## Risks / Trade-offs

- **[Low] Annotation verbosity**: Each endpoint gets several annotations. Minor source size increase, no runtime cost.
- **[Low] Stale descriptions**: Descriptions could drift from actual behavior, but this is a documentation concern — same as any doc.
