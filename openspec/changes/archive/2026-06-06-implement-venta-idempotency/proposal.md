## Why

El endpoint `POST /api/ventas` no es idempotente: si el cliente envía la misma solicitud dos veces (por un timeout, error de red, o reintento automático), se crean ventas duplicadas. Esto puede generar cobros duplicados, inventario inconsistente, y datos corruptos. Se necesita un mecanismo de idempotencia para garantizar que una misma solicitud se procese una sola vez.

## What Changes

- Agregar columna `idempotency_key` a la entidad `Venta` (nullable, con unique constraint)
- Modificar `VentaController` para aceptar el header `Idempotency-Key`
- Modificar `VentaService.CrearVenta` para verificar la clave de idempotencia antes de crear
- Si la clave ya existe, devolver la venta existente en lugar de crear una nueva
- Manejar error de integridad para race conditions (unique constraint violation)
- Agregar documentación Swagger para el nuevo header

## Capabilities

### New Capabilities
- `venta-idempotency`: idempotencia en POST /api/ventas mediante Idempotency-Key header

### Modified Capabilities

<!-- No existing specs to modify -->

## Impact

- `Venta` entity: nuevo campo `idempotencyKey`
- `VentaController`: nuevo parámetro de header `Idempotency-Key`
- `VentaService`: lógica de idempotencia en `CrearVenta`
- `VentaRepository`: nuevo método `findByIdempotencyKey`
- Schema de BD: nueva columna con unique constraint
