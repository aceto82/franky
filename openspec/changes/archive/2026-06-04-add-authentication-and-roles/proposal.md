## Why

The API currently has no security — every endpoint (productos, sucursales, ventas) is fully public. To expose this system beyond local development, we need authentication to verify who is making requests and authorization to control who can create, update, or delete data.

## What Changes

- Add `spring-boot-starter-security` dependency
- Create `Usuario` entity with roles and soft-delete (matching existing entity conventions)
- Add JWT-based authentication endpoint (`POST /api/auth/login`)
- Protect all existing endpoints: GET requires authentication, POST/PUT/DELETE require specific roles
- Add `Rol` enum with granular permissions (e.g., `ADMIN`, `USER`, etc.)
- Create `auth/` feature package with standard sub-packages
- Update existing tests to include authentication where needed

## Capabilities

### New Capabilities
- `user-auth`: JWT-based authentication — users can register and log in to receive a token
- `role-based-access`: Role-based authorization enforced on mutation endpoints (POST, PUT, DELETE)

### Modified Capabilities
*(No existing capabilities to modify — no specs exist yet)*

## Impact

- **`pom.xml`**: add `spring-boot-starter-security` and `jjwt` (or equivalent JWT library)
- **New feature**: `com.superrrr.franky.auth/` with full sub-package structure
- **Existing features**: Producto, Sucursal, Venta controllers need security annotations
- **Tests**: all existing tests will fail without authentication tokens — need to add test security configuration
- **`application.properties`**: may need JWT secret and expiration config
