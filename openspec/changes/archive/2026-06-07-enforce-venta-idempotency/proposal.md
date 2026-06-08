## Why

`POST /api/ventas` acepta el header `Idempotency-Key` como opcional. Si el cliente no lo envía, la venta se crea sin idempotencia, dejando abierta la posibilidad de duplicados por retries. En un sistema de ventas donde la consistencia es crítica, toda creación debe ser idempotente — no debería ser decisión del cliente.

## What Changes

- **BREAKING**: `Idempotency-Key` pasa de opcional a **requerido** en `POST /api/ventas`
- **BREAKING**: Si el cliente omite el header, la API responde con `400 Bad Request`
- La columna `idempotency_key` en `ventas` pasa de nullable a `NOT NULL` (requiere migración de datos existentes)
- El service valida la key al inicio y lanza excepción si es null/blank
- Se agrega la excepción `IdempotencyKeyRequeridaException` y su handler en `GlobalExceptionHandler`

## Capabilities

### New Capabilities

- `venta-idempotency`: Define el comportamiento obligatorio de idempotencia para `POST /api/ventas`, incluyendo validación de presencia del header, respuesta ante ausencia, y garantía de consistencia en la base de datos.

### Modified Capabilities

Ninguna. Espec principal no existente aún en `openspec/specs/`.

## Impact

- **Controller**: `required = false` → `required = true` en `@RequestHeader`
- **Service**: validación al inicio de `CrearVenta`, lanza excepción si key es null/blank
- **Entity**: `@Column(nullable = false)` en `idempotencyKey`
- **Base de datos**: migración de datos: backfill de UUIDs para ventas existentes con key null, luego alter column a NOT NULL
- **GlobalExceptionHandler**: nuevo handler para `MissingRequestHeaderException` y `IdempotencyKeyRequeridaException`
- **Tests**: se actualizan tests de controller y service que omiten el header
- **OpenSpec**: invalida 2 escenarios de la spec archivada (`implement-venta-idempotency`)
