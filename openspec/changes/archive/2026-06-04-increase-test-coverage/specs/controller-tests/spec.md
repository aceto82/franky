## ADDED Requirements

### Requirement: AuthController endpoints are tested
The AuthController SHALL have tests covering successful login, invalid credentials, and missing credentials scenarios.

#### Scenario: POST /api/auth/login returns 200 with token
- **WHEN** a POST request is sent to `/api/auth/login` with valid credentials
- **THEN** the response SHALL be 200 OK and contain a non-empty token

#### Scenario: POST /api/auth/login returns 401 with invalid credentials
- **WHEN** a POST request is sent to `/api/auth/login` with invalid password
- **THEN** the response SHALL be 401 Unauthorized

### Requirement: ProductoController endpoints are tested
The ProductoController SHALL have tests covering CRUD operations via HTTP.

#### Scenario: GET /api/productos returns 200
- **WHEN** a GET request is sent to `/api/productos` with a valid token
- **THEN** the response SHALL be 200 OK with a list of products

#### Scenario: POST /api/productos returns 201
- **WHEN** a POST request is sent to `/api/productos` with a valid admin token and valid body
- **THEN** the response SHALL be 201 Created

#### Scenario: PUT /api/productos/{id} returns 200
- **WHEN** a PUT request is sent to `/api/productos/{id}` with a valid admin token and valid body
- **THEN** the response SHALL be 200 OK with the updated product

#### Scenario: DELETE /api/productos/{id} returns 204
- **WHEN** a DELETE request is sent to `/api/productos/{id}` with a valid admin token
- **THEN** the response SHALL be 204 No Content

#### Scenario: GET /api/productos returns 401 without token
- **WHEN** a GET request is sent to `/api/productos` without a token
- **THEN** the response SHALL be 401 Unauthorized

### Requirement: SucursalController endpoints are tested
The SucursalController SHALL have tests covering CRUD operations via HTTP.

#### Scenario: GET /api/sucursales returns 200
- **WHEN** a GET request is sent to `/api/sucursales` with a valid token
- **THEN** the response SHALL be 200 OK with a list of sucursales

#### Scenario: POST /api/sucursales returns 201
- **WHEN** a POST request is sent to `/api/sucursales` with a valid admin token and valid body
- **THEN** the response SHALL be 201 Created

#### Scenario: PUT /api/sucursales/{id} returns 200
- **WHEN** a PUT request is sent to `/api/sucursales/{id}` with a valid admin token and valid body
- **THEN** the response SHALL be 200 OK

#### Scenario: DELETE /api/sucursales/{id} returns 204
- **WHEN** a DELETE request is sent to `/api/sucursales/{id}` with a valid admin token
- **THEN** the response SHALL be 204 No Content

### Requirement: VentaController endpoints are tested
The VentaController SHALL have tests covering sale creation and listing.

#### Scenario: POST /api/ventas returns 201
- **WHEN** a POST request is sent to `/api/ventas` with a valid token and valid body
- **THEN** the response SHALL be 201 Created

#### Scenario: GET /api/ventas returns 200
- **WHEN** a GET request is sent to `/api/ventas` with a valid token
- **THEN** the response SHALL be 200 OK with a list of ventas

#### Scenario: DELETE /api/ventas/{id} returns 204
- **WHEN** a DELETE request is sent to `/api/ventas/{id}` with a valid token
- **THEN** the response SHALL be 204 No Content

### Requirement: EstadisticaController endpoints are tested
The EstadisticaController SHALL have tests covering the product-mas-vendido endpoint.

#### Scenario: GET /api/estadisticas/producto-mas-vendido returns 200
- **WHEN** a GET request is sent to `/api/estadisticas/producto-mas-vendido` with a valid token
- **THEN** the response SHALL be 200 OK with statistics data

#### Scenario: GET /api/estadisticas/producto-mas-vendido returns 401 without token
- **WHEN** a GET request is sent to `/api/estadisticas/producto-mas-vendido` without a token
- **THEN** the response SHALL be 401 Unauthorized
