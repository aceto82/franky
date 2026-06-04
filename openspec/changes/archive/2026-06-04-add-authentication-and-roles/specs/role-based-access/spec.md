## ADDED Requirements

### Requirement: ADMIN role can create, update, and delete resources
Users with `ADMIN` role SHALL be allowed to execute POST, PUT, and DELETE operations on all endpoints (`/api/productos/*`, `/api/sucursales/*`, `/api/ventas/*`).

#### Scenario: ADMIN creates a producto
- **WHEN** an authenticated user with `ADMIN` role sends a POST request to `/api/productos` with valid body
- **THEN** the system responds with HTTP 201 Created and the created producto

#### Scenario: ADMIN updates a sucursal
- **WHEN** an authenticated user with `ADMIN` role sends a PUT request to `/api/sucursales/{id}` with valid body
- **THEN** the system responds with HTTP 200 and the updated sucursal

#### Scenario: ADMIN deletes a venta
- **WHEN** an authenticated user with `ADMIN` role sends a DELETE request to `/api/ventas/{id}`
- **THEN** the system responds with HTTP 204 No Content and the venta is soft-deleted

### Requirement: USER role cannot create, update, or delete resources
Users with `USER` role SHALL receive HTTP 403 Forbidden when attempting POST, PUT, or DELETE operations on any endpoint. This restriction SHALL NOT prevent access to GET endpoints.

#### Scenario: USER cannot create a producto
- **WHEN** an authenticated user with `USER` role sends a POST request to `/api/productos` with valid body
- **THEN** the system responds with HTTP 403 Forbidden

#### Scenario: USER cannot update a sucursal
- **WHEN** an authenticated user with `USER` role sends a PUT request to `/api/sucursales/{id}` with valid body
- **THEN** the system responds with HTTP 403 Forbidden

#### Scenario: USER cannot delete a venta
- **WHEN** an authenticated user with `USER` role sends a DELETE request to `/api/ventas/{id}`
- **THEN** the system responds with HTTP 403 Forbidden

#### Scenario: USER can still read productos
- **WHEN** an authenticated user with `USER` role sends a GET request to `/api/productos`
- **THEN** the system responds with HTTP 200 with the list of productos

### Requirement: Unauthenticated requests to mutation endpoints are rejected with 401
Requests without a valid JWT to POST, PUT, DELETE endpoints SHALL receive HTTP 401 (not 403), consistent with the authentication layer rejecting them before role checks occur.

#### Scenario: Unauthenticated POST is rejected
- **WHEN** a request without a valid JWT sends a POST to `/api/productos`
- **THEN** the system responds with HTTP 401 Unauthorized

#### Scenario: Unauthenticated DELETE is rejected
- **WHEN** a request without a valid JWT sends a DELETE to `/api/ventas/{id}`
- **THEN** the system responds with HTTP 401 Unauthorized

### Requirement: Rol enum defines ADMIN and USER values
The system SHALL define a `Rol` enum with values `ADMIN` and `USER`. This enum SHALL be used in the `Usuario` entity to determine authorization.

#### Scenario: Usuario has an associated role
- **WHEN** a `Usuario` is loaded from the database
- **THEN** its `rol` field SHALL be one of `ADMIN` or `USER`
