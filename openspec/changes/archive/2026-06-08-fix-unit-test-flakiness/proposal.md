## Why

`SecurityConfigTest.adminEndpoint_WithUserRole_Returns403` falla porque no mockea `UserDetailsService`. El test depende de que el usuario "user" exista en la BD, pero `AuthIntegrationTest` lo borra en su `@BeforeEach` (`usuarioRepository.deleteAll()`). Como Spring cachea el contexto entre tests, `DataInitializer` no se vuelve a ejecutar para reponerlo. El resultado: 1 test falla intermitentemente según el orden de ejecución.

## What Changes

- **Fix**: Agregar `@MockitoBean` de `UserDetailsService` en `SecurityConfigTest`, siguiendo el mismo patrón que los otros 5 controller tests
- **Fix**: Eliminar la dependencia de base de datos real en `SecurityConfigTest` — es un test de configuración de seguridad, no de integración con datos
- **Oportunidad**: Revisar si `AuthIntegrationTest` debería limitar su limpieza a solo los datos que crea, no a todas las tablas

## Capabilities

### New Capabilities

Ninguna. Esto es un fix, no una nueva funcionalidad.

### Modified Capabilities

Ninguna. No cambian requirements del sistema, solo la infraestructura de tests.

## Impact

- `SecurityConfigTest.java`: agregar `@MockitoBean UserDetailsService` + mock en `@BeforeEach`
- `AuthIntegrationTest.java` (opcional): restringir `deleteAll()` a solo las entidades que crea
- **No hay cambios en código de producción**
- Tests: de 96 → 96 pasando
