## Context

`SecurityConfigTest` verifica reglas de acceso a endpoints según roles. Actualmente levanta el contexto Spring completo sin mockear `UserDetailsService`. Cuando el `JwtAuthenticationFilter` procesa un token JWT válido, intenta cargar el usuario desde la BD real. El problema: `AuthIntegrationTest` (que ejecuta `@BeforeEach` con `deleteAll()` sobre todos los repositorios) comparte el mismo contexto cacheado, dejando la BD sin datos. `DataInitializer` no se re-ejecuta porque `CommandLineRunner` corre solo una vez por ciclo de vida del contexto.

Los otros 5 controller tests (`ProductoControllerTest`, `SucursalControllerTest`, `VentaControllerTest`, `EstadisticaControllerTest`, `AuthControllerTest`) ya resuelven esto mockeando `UserDetailsService` con `@MockitoBean`.

## Goals / Non-Goals

**Goals:**
- `SecurityConfigTest` pasa independientemente del orden de ejecución de tests
- Mismo patrón que los otros controller tests (`@MockitoBean`)

**Non-Goals:**
- Refactorizar la suite de tests completa
- Cambiar el comportamiento de `AuthIntegrationTest.deleteAll()`
- Agregar nuevos tests

## Decisions

| Decisión | Opción elegida | Alternativas | Razón |
|---|---|---|---|
| Cómo mockear | `@MockitoBean` en `UserDetailsService` | Seed user en `@BeforeEach` | Consistencia: los otros 5 controller tests ya usan este patrón. Seed user requeriría lógica de setup adicional y replica la dependencia de BD |

## Risks / Trade-offs

- **[Riesgo bajo]** `@MockitoBean` reemplaza el `UserDetailsService` real solo para esta clase de test. El mock es mínimalista (devuelve un `User` con `ROLE_USER`). No afecta otros tests.
- **[Trade-off]** El test ya no verifica que el flujo completo (token → filter → DB → authorities) funcione. Pero ese no es el objetivo de `SecurityConfigTest` — es un test de configuración de rutas, no de integración del filter.
- **[No aplica]** No hay migración, deploy, ni rollback — solo cambios de test.
