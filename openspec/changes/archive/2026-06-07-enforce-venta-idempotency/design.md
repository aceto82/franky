## Context

`POST /api/ventas` tiene idempotencia implementada vía header `Idempotency-Key`, pero es optativa: si el cliente omite el header, la venta se crea sin idempotencia. En un sistema transaccional de ventas, la consistencia no debería depender del cliente. Se requiere que toda venta se cree con idempotencia obligatoria, forzando la presencia de la clave en todos los niveles (HTTP, servicio, base de datos).

Estado actual:
- Controller: `@RequestHeader(required = false)`
- Service: `if (key != null && !key.isBlank()) { ... } else { crearSinIdempotencia() }`
- Entity: `@Column(unique = true)` pero nullable
- `MissingRequestHeaderException` no está manejada en `GlobalExceptionHandler`

## Goals / Non-Goals

**Goals:**
- `Idempotency-Key` es requerido en `POST /api/ventas` — sin excepción
- Si el header falta o está vacío → `400 Bad Request`
- La columna `idempotency_key` pasa a `NOT NULL` con migración de datos existentes
- Defensive check en service (no confiar solo en controller)
- Manejo limpio de errores via `GlobalExceptionHandler`

**Non-Goals:**
- Validar formato de la clave (se acepta cualquier string no vacío — UUID, nanoid, etc.)
- Implementar expiración de claves (sigue siendo no-goal)
- Idempotencia en otros endpoints (PUT, DELETE, GET)
- Cache distribuida para idempotency keys (sigue siendo DB como source of truth)

## Decisions

| Decisión | Opción elegida | Alternativas | Razón |
|---|---|---|---|
| Enforce en controller | `required = true` en `@RequestHeader` | Interceptor, Filter | Spring nativo, simple, consistente |
| Manejo de missing header | Handler en `GlobalExceptionHandler` para `MissingRequestHeaderException` | Dejar el default de Spring | El default de Spring expone detalle interno; queremos un 400 consistente con el resto de la API |
| Enforce en service | Validación al inicio de `CrearVenta`: `if (key == null \|\| key.isBlank()) throw IdempotencyKeyRequeridaException` | Confiar solo en controller | Defensivo — si alguien llama al service por otro camino, sigue protegido |
| Blanks y empty strings | Rechazados con 400 (mismo que missing) | Tratarlos como "sin clave" | Sería inconsistente con "obligatorio". Si es obligatorio, aplica a cualquier forma de "no clave" |
| NOT NULL en DB | Backfill con UUIDs + alter column | Dejar nullable + check en app | La DB debe reflejar la constraint. Backfill evita errores en producción |
| Migración de datos | Script SQL manual ejecutado antes del deploy | Flyway/Liquibase | El proyecto usa `ddl-auto=update`, no hay migraciones. Script SQL único es suficiente para el volumen actual. Si crece, se migra a Flyway |
| Response code para retry | `200 OK` (se mantiene) | `409 Conflict` | No cambia — misma decisión que la implementación original |

## Risks / Trade-offs

- **[Breaking change]** Clientes que no envían el header dejan de funcionar. **Mitigación**: documentar en changelog, actualizar Swagger, coordenar con consumidores conocidos.
- **[Riesgo medio]** Backfill de NULLs: si hay muchas ventas sin key, el script puede tomar tiempo. **Mitigación**: generar UUIDs en lote, probar en staging primero.
- **[Riesgo bajo]** `ddl-auto=update` puede no respetar el cambio a NOT NULL si ya hay datos. **Mitigación**: ejecutar el script SQL manualmente, no confiar en Hibernate.
- **[Trade-off]** No validar formato de clave significa que el cliente puede enviar "a" como clave. Aceptable: la unique constraint sigue protegiendo contra dupes. Si se necesita validación de formato en el futuro, se agrega como validación separada.

## Migration Plan

1. **Backfill**: Ejecutar SQL para asignar UUIDs a ventas existentes sin key:
   ```sql
   UPDATE ventas SET idempotency_key = gen_random_uuid()::text
   WHERE idempotency_key IS NULL;
   ```
   Alternativa para H2 (tests): `random_uuid()` en vez de `gen_random_uuid()`.

2. **NOT NULL**: Alterar columna:
   ```sql
   ALTER TABLE ventas ALTER COLUMN idempotency_key SET NOT NULL;
   ```

3. **Deploy**: Actualizar controller, service, GlobalExceptionHandler, tests.

4. **Rollback**: Revertir código + revertir NOT NULL + dejar NULLs. Sin pérdida de datos — las claves generadas simplemente pasan a ser opcionales de nuevo.
