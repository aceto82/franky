## Context

The project has 5 REST controllers (Producto, Sucursal, Venta, Auth, Estadistica) with no API documentation. Adding springdoc-openapi provides automatic OpenAPI 3.1 spec generation from Spring annotations with zero manual endpoint annotation effort. The project uses Spring Boot 4.0.6 with `spring-boot-starter-webmvc`.

## Goals / Non-Goals

**Goals:**
- `springdoc-openapi-starter-webmvc-ui` v3.x dependency added
- OpenAPI spec auto-generated at `/v3/api-docs` and `/v3/api-docs.yaml`
- Swagger UI at `/swagger-ui.html`
- Custom `OpenApiConfig` bean with API info metadata and JWT Bearer security scheme
- All endpoints auto-documented (no `@Operation` annotations needed)
- Existing tests continue to pass

**Non-Goals:**
- Manual `@Operation` or `@ApiResponse` annotations (springdoc auto-discovers from Spring annotations)
- Custom grouping or tagging of endpoints (default springdoc behavior is sufficient)
- Scalar UI (only Swagger UI — can be added later)
- Authentication on the Swagger UI page itself

## Decisions

1. **springdoc-openapi 3.x over manual OpenAPI generation** — springdoc auto-discovers endpoints from `@RestController`, `@RequestMapping`, `@RequestParam`, etc. Zero boilerplate. The 3.x line supports Spring Boot 4.x.
2. **Single OpenApiConfig class** — Keeps configuration centralized. The bean defines info metadata and registers a Bearer JWT security scheme with global `securityRequirement` so all endpoints show the padlock. Public endpoints (login) are excluded automatically because they have no Spring Security constraint.
3. **No `@SecurityScheme` annotation on controllers** — The scheme is declared globally in `OpenApiConfig` via `SecurityScheme` + `SecurityRequirement` in the `OpenAPI` bean.
4. **No `springdoc.api-docs.enabled=false` override needed** — Default springdoc behavior is fine. The spec is accessible without authentication (matching the public nature of documentation).

## Risks / Trade-offs

- **[Boot 4 compatibility] springdoc 3.x is still maturing** — There is a known issue with springdoc 3.0.0 and Boot 4.0 (Jackson `ObjectNode` conflict). Using 3.0.3 mitigates this.
- **[Security] Swagger UI exposed without auth** — The documentation endpoint is public. This is acceptable for a dev/QA API; for production, consider restricting via `springdoc.api-docs.enabled=false` or adding path-based security.
