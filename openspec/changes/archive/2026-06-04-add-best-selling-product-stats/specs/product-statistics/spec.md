## ADDED Requirements

### Requirement: Product statistics endpoint

The system SHALL expose a `GET /api/estadisticas/producto-mas-vendido` endpoint that returns the product with the highest total quantity sold across all ventas. The calculation SHALL be performed using Java Streams over all active ventas and their detalles. The endpoint SHALL be accessible to any authenticated user.

#### Scenario: Best-selling product found
- **WHEN** an authenticated user sends a GET request to `/api/estadisticas/producto-mas-vendido`
- **THEN** the system responds with HTTP 200 and a JSON body containing the product details and `totalVendido` representing the total quantity sold

#### Scenario: No sales exist
- **WHEN** an authenticated user sends a GET request to `/api/estadisticas/producto-mas-vendido` and no ventas exist in the system
- **THEN** the system responds with HTTP 404 Not Found

#### Scenario: Unauthenticated request is rejected
- **WHEN** a request without a valid JWT is sent to `GET /api/estadisticas/producto-mas-vendido`
- **THEN** the system responds with HTTP 401 Unauthorized
