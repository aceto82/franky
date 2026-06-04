## MODIFIED Requirements

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

#### Scenario: ADMIN creates a venta
- **WHEN** an authenticated user with `ADMIN` role sends a POST request to `/api/ventas` with valid body
- **THEN** the system responds with HTTP 201 Created and the created venta

### Requirement: USER role can read and create ventas
Users with `USER` role SHALL be able to access all GET endpoints. Additionally, USER SHALL be allowed to create ventas via `POST /api/ventas`. USER SHALL NOT be allowed to POST, PUT, or DELETE on productos or sucursales, nor DELETE ventas.

#### Scenario: USER creates a venta
- **WHEN** an authenticated user with `USER` role sends a POST request to `/api/ventas` with valid body
- **THEN** the system responds with HTTP 201 Created and the created venta

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
