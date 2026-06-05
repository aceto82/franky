# Franky — API de gestión de supermercado

API REST para administrar productos, sucursales y ventas de un supermercado.

## Tecnologías

- Java 21, Spring Boot 4.0.6, Maven
- PostgreSQL, Spring Data JPA, Hibernate
- Lombok, Jakarta Validation

## Requisitos previos

- JDK 21
- Docker (para PostgreSQL) o PostgreSQL instalado

## Inicio rápido

```bash
# 1. Iniciar PostgreSQL
./run.sh dc:up

# 2. Iniciar la aplicación en modo desarrollo
./run.sh dev
```

## Script de utilidad (run.sh)

El proyecto incluye `run.sh`, un script que centraliza todas las tareas comunes (base de datos, compilación, tests, cobertura). Soporta dos modos de uso:

### Modo interactivo (menú)

Ejecutar sin argumentos muestra un menú con opciones numeradas:

```
╔══════════════════════════════════════╗
║  franky - Menu de opciones          ║
╚══════════════════════════════════════╝

  1)  Arrancar PostgreSQL (docker compose)
  2)  Iniciar aplicacion (dev)
  3)  Ejecutar tests (H2)
  4)  Compilar
  5)  Clean + compilar
  6)  Clean + tests (H2)
  7)  Coverage report (JaCoCo)
  8)  Estado de containers
  9)  Detener PostgreSQL
  0)  Salir
```

### Modo CLI (argumentos)

| Comando | Descripción |
|---|---|
| `dc:up` | Arrancar PostgreSQL con Docker Compose |
| `dc:down` | Detener PostgreSQL |
| `dc:status` | Mostrar estado de los contenedores |
| `dev` | Iniciar aplicación en modo desarrollo |
| `test` | Ejecutar tests con H2 en memoria |
| `compile` | Compilar el proyecto |
| `clean` | Limpiar y compilar desde cero |
| `clean-test` | Limpiar, compilar y ejecutar tests |
| `coverage` | Ejecutar tests y generar reporte de cobertura (JaCoCo) |

### Ejemplos de uso

```bash
# Flujo completo: arrancar DB, compilar, ejecutar tests
./run.sh dc:up
./run.sh clean-test

# Generar reporte de cobertura
./run.sh coverage

# Solo compilar rápido
./run.sh compile
```

## Tests

Usan H2 en memoria — no requieren PostgreSQL:

```bash
./mvnw test -Dspring.profiles.active=h2
```

## API

### 📖 Documentación interactiva (Swagger)

Una vez ejecutando la aplicación:

| Recurso | URL |
|---|---|
| Swagger UI | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) |
| OpenAPI JSON | [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) |

Los endpoints protegidos muestran un candado en Swagger UI. Usar el botón **Authorize** e ingresar un token JWT obtenido de `/api/auth/login`.

### Autenticación

| Método | Ruta | Descripción | Auth |
|---|---|---|---|
| POST | `/api/auth/login` | Iniciar sesión (devuelve JWT) | Público |

### Productos

| Método | Ruta | Descripción | Auth |
|---|---|---|---|
| GET | `/api/productos` | Listar productos | Cualquier rol |
| POST | `/api/productos` | Crear producto | ADMIN |
| PUT | `/api/productos/{id}` | Actualizar producto | ADMIN |
| DELETE | `/api/productos/{id}` | Eliminación lógica | ADMIN |

### Sucursales

| Método | Ruta | Descripción | Auth |
|---|---|---|---|
| GET | `/api/sucursales` | Listar sucursales | Cualquier rol |
| POST | `/api/sucursales` | Crear sucursal | ADMIN |
| PUT | `/api/sucursales/{id}` | Actualizar sucursal | ADMIN |
| DELETE | `/api/sucursales/{id}` | Eliminación lógica | ADMIN |

### Ventas

| Método | Ruta | Descripción | Auth |
|---|---|---|---|
| POST | `/api/ventas` | Crear venta con detalle de productos | ADMIN o USER |
| GET | `/api/ventas` | Filtrar por `sucursalId` y `fecha` (query params) | ADMIN |
| DELETE | `/api/ventas/{id}` | Eliminación lógica | ADMIN |

### Estadísticas

| Método | Ruta | Descripción | Auth |
|---|---|---|---|
| GET | `/api/estadisticas/producto-mas-vendido` | Producto más vendido (por cantidad total) | Cualquier rol autenticado |

## Arquitectura

Package-by-feature con sub-packages por tipo de artefacto:

| Feature | Paquetes |
|---|---|
| `producto/` | `controller`, `service`, `repositories`, `entity`, `enums`, `mapper`, `dto`, `exception`, `validation` |
| `sucursal/` | `controller`, `service`, `repositories`, `entity`, `enums`, `mapper`, `dto`, `exception`, `validation` |
| `venta/` | `controller`, `service`, `repositories`, `entity`, `enums`, `mapper`, `dto`, `exception` |
| `auth/` | `controller`, `service`, `repositories`, `entity`, `enums`, `mapper`, `dto`, `exception` |
| `estadistica/` | `controller`, `service`, `dto`, `exception` |

Cross-cutting: `config/`, `exception/GlobalExceptionHandler`.

## Convenciones del proyecto

- **Eliminación lógica**: todas las entidades usan un enum `Estado{*} { ACTIVO, INACTIVO, ELIMINADO }`. Las consultas filtran excluyendo `ELIMINADO`. No se ejecutan hard deletes.
- **Validación por grupos**: los DTOs de creación usan grupos de validación extras (`CrearProductoGrupoValidacion`, `CrearSucursalGrupoValidacion`) para requerir campos obligatorios. Las actualizaciones usan solo `Default.class`.
- **Mappers**: clases estáticas escritas a mano (sin MapStruct).
- **Lombok**: `@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor` en entidades y DTOs.
