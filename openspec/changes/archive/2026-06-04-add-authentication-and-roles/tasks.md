## 1. Dependencies and Configuration

- [x] 1.1 Add `spring-boot-starter-security` and `jjwt-api`, `jjwt-impl`, `jjwt-jackson` to `pom.xml`
- [x] 1.2 Add JWT configuration properties (`jwt.secret`, `jwt.expiration`) to `application.properties` and `application-h2.properties`
- [x] 1.3 Add a `CommandLineRunner` bean to seed initial users (`DataInitializer`) — one ADMIN, one USER for development
- [x] 2.1 Create `auth/` sub-packages: `entity`, `enums`, `repositories`, `service`, `controller`, `dto`, `exception`, `mapper`, `validation`
- [x] 2.2 Create `Rol` enum in `auth/enums/` with `ADMIN` and `USER` values
- [x] 2.3 Create `EstadoUsuario` enum in `auth/enums/` with `ACTIVO`, `INACTIVO`, `ELIMINADO`
- [x] 2.4 Create `Usuario` entity in `auth/entity/` with fields `id`, `username` (unique), `password`, `rol`, `estadoUsuario`; use soft-delete pattern
- [x] 2.5 Create `UsuarioRepository` in `auth/repositories/` extending `JpaRepository` with `findByUsernameAndEstadoUsuarioNot(String username, EstadoUsuario estado)`
- [x] 2.6 Create `LoginRequestDto` and `LoginResponseDto` in `auth/dto/`
- [x] 2.7 Create `UsuarioMapper` in `auth/mapper/` with static methods (hand-written, following project convention)

## 3. JWT Infrastructure

- [x] 3.1 Create `JwtTokenProvider` in `auth/service/` with methods: `generateToken(Usuario)`, `validateToken(String)`, `getUsernameFromToken(String)`, `getRolFromToken(String)`
- [x] 3.2 Create `JwtAuthenticationFilter` extending `OncePerRequestFilter` that extracts `Bearer` token, validates it, and sets `SecurityContextHolder`
- [x] 3.3 Create `CustomUserDetailsService` implementing `UserDetailsService` that loads `Usuario` by username
- [x] 3.4 Create `SecurityConfig` class with `@Configuration` and `@EnableMethodSecurity`; define `SecurityFilterChain` bean that permits `/api/auth/login` and requires auth for everything else; register `JwtAuthenticationFilter`; declare `BCryptPasswordEncoder` bean

## 4. Authentication Endpoint

- [x] 4.1 Create `AuthService` in `auth/service/` with `login(LoginRequestDto)` method that validates credentials and returns a JWT
- [x] 4.2 Create `AuthController` in `auth/controller/` with `POST /api/auth/login` endpoint
- [x] 4.3 Create `CredencialesInvalidasException` in `auth/exception/`
- [x] 4.4 Add `CredencialesInvalidasException` handling to `GlobalExceptionHandler`

## 5. Authorization on Existing Controllers

- [x] 5.1 Add `@PreAuthorize("hasAuthority('ROLE_ADMIN')")` to POST, PUT, DELETE methods in `ProductoController`
- [x] 5.2 Add `@PreAuthorize("hasAuthority('ROLE_ADMIN')")` to POST, PUT, DELETE methods in `SucursalController`
- [x] 5.3 Add `@PreAuthorize("hasAuthority('ROLE_ADMIN')")` to POST, DELETE methods in `VentaController`
- [x] 5.4 Add `@PreAuthorize("isAuthenticated()")` to GET endpoints in `ProductoController`, `SucursalController`, and `VentaController` (or rely on global config)

## 6. Test Updates

- [x] 6.1 Create `TestSecurityConfig` in test sources with a `@TestConfiguration` that provides a mock `UserDetailsService` and a test JWT generator
- [x] 6.2 Create a test helper to generate valid JWTs for integration tests (`TestJwtHelper`)
- [x] 6.3 Create `AuthIntegrationTest` with authenticated requests (createProducto with admin, getProductos with token)
- [x] 6.4 `AuthIntegrationTest` verifies 401 for unauthenticated and 403 for `USER` role trying mutations

## 7. Data Seeding

- [x] 7.1 Create `DataInitializer` (`CommandLineRunner`) that creates ADMIN and USER users if they don't exist
- [x] 7.2 Seeder uses `@Profile("!production")` — only runs in dev/test profiles
