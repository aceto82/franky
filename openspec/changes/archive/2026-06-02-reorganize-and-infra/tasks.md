## 1. Infrastructure — Docker Compose

- [x] 1.1 Create `docker-compose.yml` at project root with PostgreSQL 16 service, named volume, and port 5432
- [x] 1.2 Add `pgdata` named volume entry to `docker-compose.yml`

## 2. Infrastructure — H2 Test Profile

- [x] 2.1 Add `com.h2database:h2` dependency with scope `test` in `pom.xml`
- [x] 2.2 Create `src/main/resources/application-h2.properties` with H2 in-memory datasource config
- [x] 2.3 Verify: run `./mvnw test -Dspring.profiles.active=h2` passes

## 3. Reorganize — Producto domain

- [x] 3.1 Create package `com.superrrr.franky.producto` and move all Producto classes (controller, service, repository, entity, DTOs, mapper, enum, exception, validation group)
- [x] 3.2 Update package declarations and imports in moved files
- [x] 3.3 Run `./mvnw compile -q` to verify

## 4. Reorganize — Sucursal domain

- [x] 4.1 Create package `com.superrrr.franky.sucursal` and move all Sucursal classes (controller, service, repository, entity, DTOs, mapper, enum, exception, validation group)
- [x] 4.2 Update package declarations and imports in moved files
- [x] 4.3 Run `./mvnw compile -q` to verify

## 5. Reorganize — Venta domain

- [x] 5.1 Create package `com.superrrr.franky.venta` and move all Venta and DetalleVenta classes (controller, service, repositories, entities, DTOs, mappers, enum, exception)
- [x] 5.2 Update package declarations and imports in moved files
- [x] 5.3 Run `./mvnw compile -q` to verify

## 6. Verify final state

- [x] 6.1 Run `./mvnw compile -q` — no errors
- [x] 6.2 Run `./mvnw test -Dspring.profiles.active=h2` — all tests pass
- [x] 6.3 Delete empty packages: `controller/`, `service/`, `repositories/`, `model/`, `dto/`, `mapper/`, `enums/` (only if truly empty)
