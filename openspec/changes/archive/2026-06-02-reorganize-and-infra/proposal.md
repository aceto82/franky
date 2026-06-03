## Why

The current package structure organizes code by technical layer (`controller/`, `service/`, `repository/`, `model/`), which scatters each domain across multiple packages. This makes it harder to navigate, maintain, and reason about a single feature. Additionally, there is no local development infrastructure — developers must manually install and configure PostgreSQL, and tests cannot run without a live database.

## What Changes

- Reorganize Java source from layer-based packages to feature-based packages (Producto, Sucursal, Venta), keeping only truly shared code (FrankyApplication, enums, exception handler, validation groups) at the root
- Add `docker-compose.yml` with PostgreSQL service for local development
- Add H2 database profile for tests so they can run without an external PostgreSQL instance

## Capabilities

### New Capabilities

- `package-by-feature`: Move all per-domain code (controller, service, repository, entity, DTO, mapper, enum, exception) into cohesive feature packages
- `docker-compose-pg`: Docker Compose setup for local PostgreSQL development
- `h2-test-profile`: Spring profile using H2 in-memory database for running tests independently

### Modified Capabilities

*None — no existing specs to modify.*

## Impact

- `src/main/java/com/superrrr/franky/` — all packages restructured
- `src/test/java/com/superrrr/franky/` — test imports updated to match new package locations
- `pom.xml` — add H2 dependency for test scope
- `src/main/resources/application.properties` — adjust if needed for profile support
- New files: `docker-compose.yml`, `src/main/resources/application-test.properties` or `application-h2.properties`
