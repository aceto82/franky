# Franky — Spring Boot 4 / Java 21 / PostgreSQL

## Quick start

```bash
# Start PostgreSQL (Docker Compose)
docker compose up -d

# Set credentials (match docker-compose.yml)
export DB_URL=jdbc:postgresql://localhost:5432/franky DB_USER=franky DB_PASS=franky
./mvnw spring-boot:run
```

Tests use H2 in-memory (no PostgreSQL needed):

```bash
./mvnw test -Dspring.profiles.active=h2
```

## Architecture

Package-by-feature with sub-packages by artifact nature:

| Feature | Sub-packages |
|---|---|
| `producto/` | `controller`, `service`, `repositories`, `entity`, `enums`, `mapper`, `dto`, `exception`, `validation` |
| `sucursal/` | `controller`, `service`, `repositories`, `entity`, `enums`, `mapper`, `dto`, `exception`, `validation` |
| `venta/` | `controller`, `service`, `repositories`, `entity`, `enums`, `mapper`, `dto`, `exception` |

Cross-cutting: `exception/GlobalExceptionHandler` stays at root level.

Base package: `com.superrrr.franky`

## Conventions

- **Soft delete**: all entities use `Estado{*} { ACTIVO, INACTIVO, ELIMINADO }`. Repositories query with `findBy*AndEstado*Not(..., Estado*.ELIMINADO)`. Never hard-delete.
- **Validation groups**: creation requires extra constraints via `CrearProductoGrupoValidacion` / `CrearSucursalGrupoValidacion` marker interfaces (in `validation/` sub-package). Update uses only `Default.class`.
- **Mappers**: hand-written static methods (`VentaMapper.toDTO`, etc.). No MapStruct.
- **Lombok**: `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor` on entities and DTOs.
- **DB config**: env-var-based (`DB_URL`, `DB_USER`, `DB_PASS`), PostgreSQL dialect, `ddl-auto=update`.

## Endpoints

| Method | Path | Notes |
|---|---|---|
| GET | `/api/productos` | |
| POST | `/api/productos` | |
| PUT | `/api/productos/{id}` | |
| DELETE | `/api/productos/{id}` | soft delete |
| GET | `/api/sucursales` | |
| POST | `/api/sucursales` | |
| PUT | `/api/sucursales/{id}` | |
| DELETE | `/api/sucursales/{id}` | soft delete |
| POST | `/api/ventas` | body includes `detalle` list |
| GET | `/api/ventas` | query params: `sucursalId`, `fecha` |
| DELETE | `/api/ventas/{id}` | soft delete |

## Gotchas

- `spring-boot-starter-webmvc` (not `spring-boot-starter-web`) — Boot 4 naming.
- Devtools dependency is present; might cause restarts during dev.
- Only one test (`FrankyApplicationTests`, context load). Add real tests for service/controller layers.
- `mvn clean` is required after package moves to avoid stale class conflicts.
