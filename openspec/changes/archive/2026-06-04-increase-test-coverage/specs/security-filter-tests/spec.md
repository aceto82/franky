## ADDED Requirements

### Requirement: JwtAuthenticationFilter handles valid token
The filter SHALL set the SecurityContext when a valid JWT token is provided.

#### Scenario: Valid token sets authentication
- **WHEN** a request with a valid Bearer token is processed
- **THEN** the SecurityContext SHALL contain an authenticated UsernamePasswordAuthenticationToken

### Requirement: JwtAuthenticationFilter handles invalid token
The filter SHALL NOT set authentication when an invalid JWT token is provided.

#### Scenario: Invalid token does not set authentication
- **WHEN** a request with an invalid or malformed Bearer token is processed
- **THEN** the SecurityContext SHALL remain empty

#### Scenario: Malformed token (no Bearer prefix) does not set authentication
- **WHEN** a request without the "Bearer " prefix in the Authorization header is processed
- **THEN** the SecurityContext SHALL remain empty

### Requirement: JwtAuthenticationFilter handles missing token
The filter SHALL continue the filter chain when no Authorization header is present.

#### Scenario: Missing Authorization header
- **WHEN** a request without an Authorization header is processed
- **THEN** the filter chain SHALL proceed without setting authentication

### Requirement: JwtAuthenticationFilter handles expired token
The filter SHALL NOT set authentication when an expired JWT token is provided.

#### Scenario: Expired token does not set authentication
- **WHEN** a request with an expired Bearer token is processed
- **THEN** the SecurityContext SHALL remain empty
