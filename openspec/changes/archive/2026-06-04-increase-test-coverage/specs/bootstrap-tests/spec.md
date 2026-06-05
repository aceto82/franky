## ADDED Requirements

### Requirement: DataInitializer seeds admin user
The DataInitializer SHALL create a default admin user on application startup when no admin exists.

#### Scenario: Admin user is created on startup
- **WHEN** the application starts with an empty database
- **THEN** a user with username "admin" and rol ADMIN SHALL exist in the database

### Requirement: SecurityConfig enforces authentication
The SecurityConfig SHALL require authentication for all endpoints except `/api/auth/login` and Swagger UI paths.

#### Scenario: Public endpoint is accessible without token
- **WHEN** a GET request is sent to `/api/auth/login` without a token
- **THEN** the response SHALL NOT be 401 Unauthorized

#### Scenario: Secured endpoint is blocked without token
- **WHEN** a GET request is sent to `/api/productos` without a token
- **THEN** the response SHALL be 401 Unauthorized

#### Scenario: Secured POST is blocked without ADMIN role
- **WHEN** a POST request is sent to `/api/productos` with a valid USER token
- **THEN** the response SHALL be 403 Forbidden
