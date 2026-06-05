## Context

The project has 13 existing tests (service, mapper, and 1 integration test) achieving roughly 80% line coverage with no branch coverage enforcement. The main uncovered areas are the 5 REST controllers, JwtAuthenticationFilter, GlobalExceptionHandler, DataInitializer, and SecurityConfig. The existing test infrastructure uses JUnit 5 + Mockito for unit tests and Spring Boot's MockMvc with `@SpringBootTest` for integration tests, all run against H2 in-memory with `h2` profile.

## Goals / Non-Goals

**Goals:**
- Increase LINE coverage minimum from 0.80 to 0.85
- Introduce BRANCH coverage minimum of 0.75
- Add tests for all untested production classes: controllers, JwtAuthenticationFilter, GlobalExceptionHandler, DataInitializer, SecurityConfig

**Non-Goals:**
- Tests for Lombok-generated methods (getters/setters/builders) on entities, DTOs, or enums
- Tests for repository interfaces (covered by integration tests)
- Refactoring production code purely for testability
- Adding UI or end-to-end tests

## Decisions

1. **Controller tests via MockMvc standalone setup** — use `@WebMvcTest` or standalone MockMvc setup with mocked services. This avoids loading the full Spring context for each controller test, keeping tests fast. Each controller test mocks its service dependency and focuses on HTTP mapping, validation, and response codes.

2. **JwtAuthenticationFilter as parameterized unit test** — the filter has clear branches (valid token, invalid token, missing token, expired token). Use JUnit 5 `@ParameterizedTest` for clean coverage of each branch.

3. **GlobalExceptionHandler via MockMvc** — test by calling a controller endpoint that triggers each exception type (validation error, not-found, generic). Create a simple test controller or simulate via `@WebMvcTest` with a mocked endpoint.

4. **DataInitializer via `@SpringBootTest`** — run the full context and verify the seeded admin user exists in the database. This mirrors the existing integration test pattern.

5. **SecurityConfig via `@WebMvcTest`** — test that unauthenticated requests to secured endpoints return 401 and public endpoints (login) are accessible.

6. **JaCoCo BRANCH counter** — add `<counter>BRANCH</counter>` as a separate limit inside the same BUNDLE rule. This catches missed if/else branches and ternary conditions.

## Risks / Trade-offs

- **POM change may break the build** if current coverage is already below 85% line or 75% branch → Mitigation: run coverage check before committing to verify thresholds are achievable
- **Branch coverage is harder to satisfy** than line coverage → Mitigation: prioritize tests that exercise both true/false paths in conditionals
- **SecurityConfig tests can be brittle** if security rules change → Mitigation: test at the HTTP endpoint level, not the config bean level
