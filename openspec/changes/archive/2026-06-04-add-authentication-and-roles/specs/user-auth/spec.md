## ADDED Requirements

### Requirement: User can authenticate with username and password
The system SHALL provide a `POST /api/auth/login` endpoint that accepts `username` and `password` in the request body. On successful authentication, the system SHALL return a JSON object with a `token` field containing a signed JWT.

#### Scenario: Successful login with valid credentials
- **WHEN** a user sends a POST request to `/api/auth/login` with valid `username` and `password`
- **THEN** the system responds with HTTP 200 and a JSON body containing a `token` field with a non-empty JWT string

#### Scenario: Login with invalid password
- **WHEN** a user sends a POST request to `/api/auth/login` with a valid `username` but incorrect `password`
- **THEN** the system responds with HTTP 401 Unauthorized

#### Scenario: Login with non-existent username
- **WHEN** a user sends a POST request to `/api/auth/login` with a `username` that does not exist in the system
- **THEN** the system responds with HTTP 401 Unauthorized

### Requirement: Authenticated requests include JWT in Authorization header
The system SHALL accept JWT tokens via the `Authorization` header using the `Bearer <token>` scheme. The system SHALL validate the token's signature, expiration, and format on every protected request.

#### Scenario: Request with valid JWT token
- **WHEN** a client sends a request to any protected endpoint with a valid `Authorization: Bearer <token>` header
- **THEN** the system processes the request normally and returns the expected response

#### Scenario: Request with expired JWT token
- **WHEN** a client sends a request with an expired JWT token
- **THEN** the system responds with HTTP 401 Unauthorized

#### Scenario: Request with malformed JWT token
- **WHEN** a client sends a request with a malformed or invalid-signed `Authorization: Bearer <token>` header
- **THEN** the system responds with HTTP 401 Unauthorized

#### Scenario: Request without Authorization header
- **WHEN** a client sends a request to a protected endpoint without an `Authorization` header
- **THEN** the system responds with HTTP 401 Unauthorized

### Requirement: Usuario entity stores authentication credentials
The system SHALL store users in a `usuarios` table with fields: `id`, `username` (unique), `password` (bcrypt hash), `rol`, and `estado_usuario` (for soft-delete). The `password` field SHALL never be exposed in API responses.

#### Scenario: Password is hashed before storage
- **WHEN** a `Usuario` is created with a raw password
- **THEN** the `password` field in the database SHALL contain a bcrypt hash, not the raw password

#### Scenario: Password is never returned in responses
- **WHEN** any endpoint returns data related to a user
- **THEN** the `password` field SHALL NOT be present in the response

### Requirement: JWT token contains user identity and role
The JWT SHALL contain at minimum the `sub` (username), `rol`, and `exp` (expiration) claims. The system SHALL extract these claims to authenticate and authorize every request.

#### Scenario: Token contains required claims
- **WHEN** a JWT is issued after successful login
- **THEN** the decoded token SHALL contain `sub`, `rol`, and `exp` claims with non-null values

### Requirement: Existing GET endpoints require authentication
All existing GET endpoints (`GET /api/productos`, `GET /api/sucursales`, `GET /api/ventas`) SHALL require a valid JWT token. Any authenticated user (regardless of role) SHALL be able to access them.

#### Scenario: Authenticated user can access GET productos
- **WHEN** an authenticated user sends a GET request to `/api/productos`
- **THEN** the system returns HTTP 200 with the list of productos

#### Scenario: Unauthenticated request to GET productos is rejected
- **WHEN** a request without a valid JWT is sent to `GET /api/productos`
- **THEN** the system responds with HTTP 401
