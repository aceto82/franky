## Context

The project has 6 service classes, 5 mapper classes, and a JwtTokenProvider — all with zero unit tests. Only integration tests exist (AuthIntegrationTest). Service classes use `@Autowired` field injection and rely on Spring Data JPA repositories and other services as collaborators.

## Goals / Non-Goals

**Goals:**
- Unit tests for every service class using Mockito mocks for all collaborators
- Unit tests for every mapper covering toDTO, toModel, and edge cases
- Unit test for JwtTokenProvider covering token creation and validation
- Tests run with `@ExtendWith(MockitoExtension.class)` — no Spring context needed
- Minimum 80% line coverage on service and mapper classes

**Non-Goals:**
- Integration tests (already covered by AuthIntegrationTest)
- Controller layer tests (can be added later)
- Data layer tests (@DataJpaTest)
- HTML or UI tests

## Decisions

1. **Mockito over SpringBootTest** — Service tests use `@ExtendWith(MockitoExtension.class)` + `@InjectMocks`/`@Mock`. This keeps tests fast (no context loading) and focused on the service logic, not Spring wiring.
2. **Same package structure** — Test classes mirror the source package: `src/test/java/com/superrrr/franky/{feature}/service/` and `src/test/java/com/superrrr/franky/{feature}/mapper/`.
3. **Mapper tests as plain JUnit 5** — Mappers are static methods with no dependencies. They need no mocking, just JUnit assertions with `@Test`.
4. **JwtTokenProvider test uses reflection** — The `jwtSecret` and `jwtExpiration` fields are set via `@Value`. In tests, use `@BeforeEach` to set them via setter or ReflectionTestUtils.

## Risks / Trade-offs

- **[Fragility] Mockito tests may break if constructor injection is adopted** — If services switch to constructor injection, `@InjectMocks` still works but the test structure changes slightly. Acceptable trade-off for speed.
- **[Coverage gap] Stream-heavy methods in EstadisticaService are harder to mock** — The method builds a complex stream pipeline. The test verifies the final result, not intermediate steps. That's acceptable: the spec cares about output, not implementation.
- **[No integration test for mapper] Mapper tests are pure unit** — Mappers like `VentaMapper` need multiple entities (Venta, Sucursal, Producto, DetalleVenta) to build DTOs. Tests construct test objects inline, no mocking needed.
