## Context

The API is a CRUD supermarket system (productos, sucursales, ventas) with no security layer. Every endpoint is public. The project uses Spring Boot 4, Spring MVC, Spring Data JPA, and PostgreSQL. It follows a package-by-feature pattern. All existing entities use soft-delete via `Estado{Feature}` enums. No auth or user infrastructure exists today.

## Goals / Non-Goals

**Goals:**
- Authenticate requests via JWT bearer tokens
- Allow any authenticated user to access GET endpoints (productos, sucursales, ventas)
- Restrict POST, PUT, DELETE endpoints by role
- Define two roles: `ADMIN` (full access) and `USER` (read-only + own context)
- Store users in the database with hashed passwords (bcrypt)
- Provide `POST /api/auth/login` that returns a JWT on valid credentials
- Follow existing project conventions: package-by-feature, soft-delete, Lombok, hand-written mappers

**Non-Goals:**
- OAuth2 / social login
- Refresh token rotation or token revocation
- User registration endpoint (users are seeded or created via DB)
- Fine-grained resource-level permissions (e.g., "only creator can delete")
- Rate limiting or brute-force protection

## Decisions

1. **JWT over Session-based auth** — Stateless, no server-side session store, works well with REST APIs. `jjwt` library (io.jsonwebtoken) is the de facto standard for JWT in Java.

2. **Two roles: ADMIN and USER** — Simplicity. ADMIN can mutate everything; USER can read everything. Additional granularity can be added later (e.g., `GERENTE`, `CAJERO`) by extending the `Rol` enum.

3. **`Usuario` entity under `auth/` feature** — Consistent with existing package-by-feature convention. Soft-delete via `EstadoUsuario` enum.

4. **Custom `JwtAuthenticationFilter` extending `OncePerRequestFilter`** — Extracts token from `Authorization: Bearer <token>`, validates it, sets `SecurityContextHolder`. Standard Spring Security pattern.

5. **Spring Security method-level security (`@PreAuthorize`)** — Annotations on controller methods rather than request-matcher-based config. More explicit, follows the Single Responsibility Principle, and makes permissions visible at the endpoint definition.

6. **bcrypt for password hashing** — Spring Security's built-in `BCryptPasswordEncoder`. Industry standard, no external dependencies needed.

7. **`Usuario` includes `username`, `password`, `rol`** — Minimal user model. `password` is the bcrypt hash. `rol` is an enum `Rol { ADMIN, USER }`.

8. **`Rol` is a simple enum, not a separate entity** — For two roles, a JPA attribute on `Usuario` is simpler and sufficient. If roles grow complex later, refactor to `@ManyToMany`.

## Risks / Trade-offs

- **[Security] JWT secret in application.properties** → Use env-var (`JWT_SECRET`) in production, keep a default only for dev
- **[Compatibility] All existing tests will break** → Add test security config with a test JWT generator and `@WithMockUser`-style annotations or a `@TestUser` annotation
- **[Maintenance] Token expiration requires re-login** → Start with 24h expiration; if UX is poor, add refresh tokens later (non-goal now)
- **[Data] No registration endpoint** → Initial users must be seeded via `data.sql` or a `CommandLineRunner`. Document this for ops
