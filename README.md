# Franky — API de gestión de supermercado

API REST para administrar productos, sucursales y ventas de un supermercado.

## Tecnologías

- Java 21, Spring Boot 4.0.6, Maven
- PostgreSQL, Spring Data JPA, Hibernate
- Lombok, Jakarta Validation

## Requisitos previos

- JDK 21
- PostgreSQL en funcionamiento
- Base de datos creada (por ejemplo `franky`)

## Configuración

Variables de entorno requeridas:

| Variable | Descripción |
|---|---|
| `DB_URL` | JDBC URL (ej. `jdbc:postgresql://localhost:5432/franky`) |
| `DB_USER` | Usuario de base de datos |
| `DB_PASS` | Contraseña del usuario |

Hibernate usa `ddl-auto=update`: las tablas se crean/actualizan automáticamente al iniciar.

## Ejecución

```bash
export DB_URL=jdbc:postgresql://localhost:5432/franky DB_USER=... DB_PASS=...
./mvnw spring-boot:run
```

## Tests

Los tests también requieren la base de datos (no hay perfil H2 configurado).

```bash
./mvnw test
./mvnw test -Dtest=FrankyApplicationTests
```

## Arquitectura

Layered estándar: `Controller` → `Service` → `Repository` (JPA) → `Entity`

| Dominio | Paquete |
|---|---|
| Productos | `controller.ProductoController`, `service.ProductoService`, `repositories.ProductoRepository` |
| Sucursales | `controller.SucursalController`, `service.SucursalService`, `repositories.SucursalRepository` |
| Ventas | `controller.VentaController`, `service.VentaService`, `repositories.{Venta,DetalleVenta}Repository` |

## API

### Productos

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/productos` | Listar productos |
| POST | `/api/productos` | Crear producto |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminación lógica |

### Sucursales

| Método | Ruta | Descripción |
|---|---|---|
| GET | `/api/sucursales` | Listar sucursales |
| POST | `/api/sucursales` | Crear sucursal |
| PUT | `/api/sucursales/{id}` | Actualizar sucursal |
| DELETE | `/api/sucursales/{id}` | Eliminación lógica |

### Ventas

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/ventas` | Crear venta con detalle de productos |
| GET | `/api/ventas` | Filtrar por `sucursalId` y `fecha` (query params) |
| DELETE | `/api/ventas/{id}` | Eliminación lógica |

## Convenciones del proyecto

- **Eliminación lógica**: todas las entidades usan un enum `Estado{*} { ACTIVO, INACTIVO, ELIMINADO }`. Las consultas filtran excluyendo `ELIMINADO`. No se ejecutan hard deletes.
- **Validación por grupos**: los DTOs de creación usan grupos de validación extras (`CrearProductoGrupoValidacion`, `CrearSucursalGrupoValidacion`) para requerir campos obligatorios. Las actualizaciones usan solo `Default.class`.
- **Mappers**: clases estáticas escritas a mano (sin MapStruct).
- **Lombok**: `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor` en entidades y DTOs.
