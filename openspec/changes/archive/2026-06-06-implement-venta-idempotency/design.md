## Context

`POST /api/ventas` actualmente crea una nueva venta en cada solicitud. No hay ningún mecanismo para detectar reintentos: si el cliente envía la misma petición dos veces (timeout, error de red, retry automático), se genera una venta duplicada con productos, cantidades y sucursal idénticos. Esto es un problema de consistencia de datos.

## Goals / Non-Goals

**Goals:**
- Hacer que `POST /api/ventas` sea idempotente mediante un header `Idempotency-Key`
- Clientes que reintenten la misma solicitud (misma clave) obtengan la misma respuesta
- Garantizar consistencia incluso en race conditions

**Non-Goals:**
- Implementar idempotencia en otros endpoints
- Implementar expiración automática de claves de idempotencia (se manejará en futuro si es necesario)
- Validar el formato de la clave (se acepta cualquier string no vacío)

## Decisions

| Decisión | Opción elegida | Alternativas | Razón |
|---|---|---|---|
| Almacenamiento de clave | Columna `idempotency_key` nullable en tabla `ventas` con unique constraint | Tabla separada de idempotencia | Simplicidad: la clave vive con el recurso. Unique constraint nullable permite claves solo cuando se usan |
| Header vs body | Header `Idempotency-Key` | Campo en `VentaRequestDto` | Estándar de la industria (Stripe, PayPal). Separa concerns de transporte del payload del negocio |
| Respuesta para clave repetida | `200 OK` con el recurso existente | `409 Conflict`, `422` | El recurso ya existe y es válido, no hay conflicto. `200` permite al cliente obtener el resultado original |
| Manejo de race condition | Catch `DataIntegrityViolationException` y retornar recurso existente | Lock pesimista, advisory lock | Simplicidad y bajo acoplamiento. La unique constraint es la fuente de verdad definitiva |
| Formato de clave | UUID generado por el cliente | Timestamp + nonce | UUID es el estándar, fácil de generar en cualquier lenguaje |
| Expiración de claves | No implementado (fase futura) | TTL con cleanup programado | Scope mínimo: evitar duplicados inmediatos. En producción real se agregaría |

## Risks / Trade-offs

- [Riesgo bajo] Cliente envía clave inválida (vacía, nula) → El servicio ignora la clave y crea sin idempotencia (comportamiento actual). El cliente sigue siendo responsable de enviar una clave válida.
- [Riesgo medio] La unique constraint no funciona con valores null en PostgreSQL (múltiples nulls no violan la constraint) → Es el comportamiento deseado: solo las ventas con clave deben ser idempotentes.
