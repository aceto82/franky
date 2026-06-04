## Why

The REST API has no discoverable documentation. Consumers (frontend, integration partners) have no way to know available endpoints, request/response schemas, or authentication requirements without reading source code. Swagger/OpenAPI provides interactive API docs at runtime.

## What Changes

- Add `springdoc-openapi-starter-webmvc-ui` dependency (v3.x, compatible with Boot 4)
- Add `OpenApiConfig` bean with app info (title, version, description, contact)
- Add JWT Bearer security scheme to OpenAPI config so endpoints show the padlock in Swagger UI
- Auto-documented endpoints via standard Spring annotations (no manual @Operation needed)
- Swagger UI at `/swagger-ui.html`, OpenAPI JSON at `/v3/api-docs`
- Passes existing integration tests (no endpoint changes)

## Capabilities

### New Capabilities
- `api-documentation`: OpenAPI 3.1 interactive documentation with Swagger UI, JWT security scheme, and endpoint descriptions for all REST controllers

### Modified Capabilities
*(none)*

## Impact

- New Maven dependency: `org.springdoc:springdoc-openapi-starter-webmvc-ui:3.0.3`
- New config class: `com.superrrr.franky.config.OpenApiConfig`
- No changes to existing controllers, services, or repositories
- All existing tests continue passing
