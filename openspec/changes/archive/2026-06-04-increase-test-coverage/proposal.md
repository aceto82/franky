## Why

Current JaCoCo coverage is around 80% line coverage with no branch coverage threshold. We need to raise test coverage to catch more logic bugs (especially branching conditions) and ensure the codebase is well-tested across all layers.

## What Changes

- Update JaCoCo `check` rule: LINE minimum from 0.80 to 0.85, add BRANCH minimum of 0.75
- Write tests for uncovered areas: controllers, JwtAuthenticationFilter, GlobalExceptionHandler, DataInitializer, SecurityConfig
- Existing service/mapper tests should largely stay as-is (minor additions if needed)

## Capabilities

### New Capabilities

- `coverage-thresholds`: Update pom.xml JaCoCo check configuration with higher LINE and new BRANCH minimums
- `controller-tests`: Add MockMvc-based controller tests for all 5 REST controllers (Auth, Producto, Sucursal, Venta, Estadistica)
- `security-filter-tests`: Add unit tests for JwtAuthenticationFilter covering valid/invalid/missing token scenarios
- `global-handler-tests`: Add tests for GlobalExceptionHandler covering validation errors, not-found exceptions, and generic exceptions
- `bootstrap-tests`: Add tests for DataInitializer (seeded users) and SecurityConfig (HTTP security rules)

### Modified Capabilities

*(none — no existing specs to modify)*

## Impact

- `pom.xml`: JaCoCo check thresholds updated
- `src/test/java/com/superrrr/franky/`: 5 new test classes for controllers, 1 for JwtAuthenticationFilter, 1 for GlobalExceptionHandler, 1 for DataInitializer, 1 for SecurityConfig
- Build will fail if coverage drops below new thresholds
