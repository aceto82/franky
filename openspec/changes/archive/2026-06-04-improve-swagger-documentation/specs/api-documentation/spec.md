## ADDED Requirements

### Requirement: Auth endpoint has Swagger documentation
The `POST /api/auth/login` endpoint SHALL include `@Operation` with summary and description, and `@ApiResponse` annotations for 200 and 401 status codes.

#### Scenario: Auth login shows summary and description in Swagger UI
- **WHEN** a consumer views the Swagger UI
- **THEN** the `POST /api/auth/login` endpoint shows a human-readable summary and description

#### Scenario: Auth login response codes are documented
- **WHEN** a consumer views the Swagger UI
- **THEN** the `POST /api/auth/login` endpoint shows possible HTTP 200 and 401 responses

### Requirement: Product endpoints have Swagger documentation
The `ProductoController` endpoints SHALL include `@Operation` with summary and description, and `@ApiResponse` annotations covering all relevant status codes per endpoint.

#### Scenario: GET /api/productos shows list description
- **WHEN** a consumer views the Swagger UI
- **THEN** the `GET /api/productos` endpoint shows a description indicating it returns all registered products

#### Scenario: GET /api/productos response codes documented
- **WHEN** a consumer views the Swagger UI
- **THEN** the `GET /api/productos` endpoint shows possible HTTP 200, 401, and 500 responses

#### Scenario: POST /api/productos shows creation description
- **WHEN** a consumer views the Swagger UI
- **THEN** the `POST /api/productos` endpoint shows a description indicating it creates a new product

#### Scenario: POST /api/productos response codes documented
- **WHEN** a consumer views the Swagger UI
- **THEN** the `POST /api/productos` endpoint shows possible HTTP 201, 400, 401, and 403 responses

#### Scenario: PUT /api/productos/{id} shows update description
- **WHEN** a consumer views the Swagger UI
- **THEN** the `PUT /api/productos/{id}` endpoint shows a description indicating it updates an existing product

#### Scenario: PUT /api/productos/{id} response codes documented
- **WHEN** a consumer views the Swagger UI
- **THEN** the `PUT /api/productos/{id}` endpoint shows possible HTTP 200, 400, 401, 403, and 404 responses

#### Scenario: DELETE /api/productos/{id} shows delete description
- **WHEN** a consumer views the Swagger UI
- **THEN** the `DELETE /api/productos/{id}` endpoint shows a description indicating it performs a soft-delete

#### Scenario: DELETE /api/productos/{id} response codes documented
- **WHEN** a consumer views the Swagger UI
- **THEN** the `DELETE /api/productos/{id}` endpoint shows possible HTTP 204, 401, and 403 responses

### Requirement: Sucursal endpoints have Swagger documentation
The `SucursalController` endpoints SHALL include `@Operation` with summary and description, and `@ApiResponse` annotations covering all relevant status codes per endpoint.

#### Scenario: GET /api/sucursales shows list description
- **WHEN** a consumer views the Swagger UI
- **THEN** the `GET /api/sucursales` endpoint shows a description indicating it returns all registered sucursales

#### Scenario: POST /api/sucursales shows creation description
- **WHEN** a consumer views the Swagger UI
- **THEN** the `POST /api/sucursales` endpoint shows a description indicating it creates a new sucursal

#### Scenario: PUT /api/sucursales/{id} shows update description
- **WHEN** a consumer views the Swagger UI
- **THEN** the `PUT /api/sucursales/{id}` endpoint shows a description indicating it updates an existing sucursal

#### Scenario: DELETE /api/sucursales/{id} shows delete description
- **WHEN** a consumer views the Swagger UI
- **THEN** the `DELETE /api/sucursales/{id}` endpoint shows a description indicating it performs a soft-delete

#### Scenario: Sucursal endpoint response codes documented
- **WHEN** a consumer views the Swagger UI
- **THEN** each sucursal endpoint shows appropriate HTTP response codes (200, 201, 204, 400, 401, 403, 404)

### Requirement: Venta endpoints have Swagger documentation
The `VentaController` endpoints SHALL include `@Operation` with summary and description, and `@ApiResponse` annotations covering all relevant status codes per endpoint.

#### Scenario: POST /api/ventas shows creation description
- **WHEN** a consumer views the Swagger UI
- **THEN** the `POST /api/ventas` endpoint shows a description indicating it creates a new sale with product details

#### Scenario: POST /api/ventas response codes documented
- **WHEN** a consumer views the Swagger UI
- **THEN** the `POST /api/ventas` endpoint shows possible HTTP 201, 400, 401, and 403 responses

#### Scenario: GET /api/ventas shows filtering description
- **WHEN** a consumer views the Swagger UI
- **THEN** the `GET /api/ventas` endpoint shows a description indicating it filters sales by sucursal and date

#### Scenario: GET /api/ventas query params documented
- **WHEN** a consumer views the Swagger UI
- **THEN** the `sucursalId` and `fecha` query parameters show descriptions

#### Scenario: DELETE /api/ventas/{id} shows cancel description
- **WHEN** a consumer views the Swagger UI
- **THEN** the `DELETE /api/ventas/{id}` endpoint shows a description indicating it cancels a sale (soft-delete)

### Requirement: Estadistica endpoint has Swagger documentation
The `EstadisticaController` endpoint SHALL include `@Operation` with summary and description.

#### Scenario: GET /api/estadisticas/producto-mas-vendido shows description
- **WHEN** a consumer views the Swagger UI
- **THEN** the `GET /api/estadisticas/producto-mas-vendido` endpoint shows a description indicating it returns the best-selling product

### Requirement: Request and response DTOs have field-level descriptions
DTOs used in request bodies and responses SHALL include `@Schema` annotations with `description` on the class and key fields.

#### Scenario: ProductoRequestDto fields are documented
- **WHEN** a consumer views a POST/PUT product schema in Swagger UI
- **THEN** field descriptions appear for nombre, precio, and categoria

#### Scenario: ProductoResponseDto fields are documented
- **WHEN** a consumer views a product response schema in Swagger UI
- **THEN** field descriptions appear for id, nombre, precio, categoria, and estado

#### Scenario: SucursalRequestDto fields are documented
- **WHEN** a consumer views a POST/PUT sucursal schema in Swagger UI
- **THEN** field descriptions appear for nombre and direccion

#### Scenario: VentaRequestDto fields are documented
- **WHEN** a consumer views the create venta schema in Swagger UI
- **THEN** field descriptions appear for sucursalId and detalle

#### Scenario: LoginRequestDto fields are documented
- **WHEN** a consumer views the login schema in Swagger UI
- **THEN** field descriptions appear for username and password
