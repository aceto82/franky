## Context

Proyecto Spring Boot 4 / Java 21 con tres dominios (Producto, Sucursal, Venta) actualmente organizados por capa técnica (`controller/`, `service/`, `repositories/`, `model/`, `dto/`, `mapper/`, `enums/`, `exception/`). Sin infraestructura de desarrollo local ni perfil de base de datos para tests.

## Goals / Non-Goals

**Goals:**
- Reorganizar cada dominio en un paquete autocontenido: `producto/`, `sucursal/`, `venta/`
- Proveer `docker-compose.yml` con PostgreSQL para desarrollo local
- Agregar perfil H2 para que los tests funcionen sin PostgreSQL externo

**Non-Goals:**
- No modificar lógica de negocio, endpoints, ni comportamiento de la API
- No agregar CI/CD
- No cambiar nombres de clases, métodos o firmas — solo mover archivos y ajustar imports

## Decisions

### 1. Estructura target: paquete-por-feature

Cada dominio agrupa todo su código en un subpaquete, con subdirectorios por naturaleza del artefacto:

```
com.superrrr.franky/
├── FrankyApplication.java
├── exception/
│   └── GlobalExceptionHandler.java
├── producto/
│   ├── controller/   → ProductoController.java
│   ├── service/      → ProductoService.java
│   ├── repositories/ → ProductoRepository.java
│   ├── entity/       → Producto.java
│   ├── enums/        → EstadoProducto.java
│   ├── mapper/       → ProductoMapper.java
│   ├── dto/          → Producto{Request,Response}Dto.java
│   ├── exception/    → ProductoNoEncontradoException.java
│   └── validation/   → CrearProductoGrupoValidacion.java
├── sucursal/                    ← análogo a producto
│   └── {controller,service,repositories,entity,enums,mapper,dto,exception,validation}/
└── venta/                       ← análogo, incluye DetalleVenta*
    └── {controller,service,repositories,entity,enums,mapper,dto,exception}/
```

**Qué se queda en el paquete raíz:**
- `FrankyApplication.java` — punto de entrada
- `exception/GlobalExceptionHandler.java` — cross-cutting, maneja excepciones de todos los dominios

**Alternativa considerada:** dejar una carpeta `shared/` o `common/` para GlobalExceptionHandler. Se descarta porque un solo archivo no justifica un paquete extra; se queda en `exception/` bajo la raíz.

### 2. Docker Compose para PostgreSQL

Servicio único de PostgreSQL 16 con variables de entorno para `DB_URL`, `DB_USER`, `DB_PASS`. Mapeo de puerto estándar `5432:5432`. Volumen nombrado para persistencia.

```yaml
services:
  db:
    image: postgres:16-alpine
    environment:
      POSTGRES_DB: franky
      POSTGRES_USER: franky
      POSTGRES_PASSWORD: franky
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data
```

### 3. Perfil H2 para tests

Se crea `application-h2.properties` con datasource H2 en memoria. Las tests se ejecutan activando el perfil `h2`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect
```

En `pom.xml` se agrega dependencia H2 con scope `test`.

**Alternativa considerada:** usar Testcontainers para arrancar PostgreSQL real en tests. Se descarta porque (a) agrega complejidad y dependencia Docker en el ciclo de test, (b) el perfil H2 es más simple y suficiente para el alcance actual del proyecto.

## Risks / Trade-offs

- **[Risk]** Package-by-feature cambia imports en todas las clases del proyecto y archivos de test → **Mitigation:** mover archivos uno por uno, verificar compilación con `./mvnw compile` después de cada dominio
- **[Risk]** H2 no es 100% compatible con PostgreSQL (funciones, dialecto) → **Mitigation:** las queries del proyecto son JPQL básicas sin funciones PG específicas; H2Dialect cubre el caso
- **[Risk]** `ddl-auto=update` con H2 puede crear esquemas ligeramente distintos → **Mitigation:** aceptable para tests de integración; el DDL real se controla con PostgreSQL en producción/desarrollo
