## ADDED Requirements

### Requirement: OpenAPI spec endpoint
The system SHALL expose an OpenAPI 3.1 specification at `/v3/api-docs` (JSON) and `/v3/api-docs.yaml` (YAML) that describes all REST endpoints, request/response schemas, and security requirements.

#### Scenario: Access JSON OpenAPI spec
- **WHEN** a GET request is made to `/v3/api-docs`
- **THEN** the response is a valid OpenAPI 3.1 JSON document listing all endpoints

#### Scenario: Access YAML OpenAPI spec
- **WHEN** a GET request is made to `/v3/api-docs.yaml`
- **THEN** the response is a valid OpenAPI 3.1 YAML document

### Requirement: Swagger UI
The system SHALL serve the Swagger UI at `/swagger-ui.html` for interactive API exploration.

#### Scenario: Swagger UI loads
- **WHEN** a browser GET request is made to `/swagger-ui.html`
- **THEN** the Swagger UI page is rendered with all endpoints listed

### Requirement: API info metadata
The OpenAPI spec SHALL include API title, version, description, and contact information in its info block.

#### Scenario: Info block contains metadata
- **WHEN** the OpenAPI spec is fetched
- **THEN** the `info` section contains title "Franky API", a version, a description, and contact

### Requirement: JWT Bearer security scheme
The OpenAPI spec SHALL declare a Bearer JWT security scheme so that secured endpoints show a lock icon in Swagger UI. Public endpoints (like login) SHALL NOT require the security scheme.

#### Scenario: Secured endpoint shows auth requirement
- **WHEN** viewing a secured endpoint in the OpenAPI spec (e.g., POST /api/productos)
- **THEN** it SHALL reference the Bearer JWT security scheme

#### Scenario: Public endpoint shows no auth requirement
- **WHEN** viewing a public endpoint in the OpenAPI spec (e.g., POST /api/auth/login)
- **THEN** it SHALL NOT reference the Bearer JWT security scheme
