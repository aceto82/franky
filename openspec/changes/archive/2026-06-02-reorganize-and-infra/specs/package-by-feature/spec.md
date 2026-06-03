## ADDED Requirements

### Requirement: Code organized by feature package
The system SHALL organize Java source code by domain feature rather than by technical layer. Each domain (producto, sucursal, venta) SHALL have its own package containing all related classes: controller, service, repository, entity, DTOs, mapper, enum, exception, and validation groups. Only cross-cutting code (FrankyApplication, GlobalExceptionHandler) SHALL remain in the root `com.superrrr.franky` package.

#### Scenario: Producto domain is self-contained
- **WHEN** inspecting `src/main/java/com/superrrr/franky/producto/`
- **THEN** the following sub-packages SHALL exist with their respective classes:
  - `controller/` → ProductoController
  - `service/` → ProductoService
  - `repositories/` → ProductoRepository
  - `entity/` → Producto entity
  - `enums/` → EstadoProducto
  - `mapper/` → ProductoMapper
  - `dto/` → ProductoRequestDto, ProductoResponseDto
  - `exception/` → ProductoNoEncontradoException
  - `validation/` → CrearProductoGrupoValidacion

#### Scenario: Sucursal domain is self-contained
- **WHEN** inspecting `src/main/java/com/superrrr/franky/sucursal/`
- **THEN** the following sub-packages SHALL exist with their respective classes:
  - `controller/` → SucursalController
  - `service/` → SucursalService
  - `repositories/` → SucursalRepository
  - `entity/` → Sucursal entity
  - `enums/` → EstadoSucursal
  - `mapper/` → SucursalMapper
  - `dto/` → SucursalRequestDto, SucursalResponseDto
  - `exception/` → SucursalNoEncontradoException
  - `validation/` → CrearSucursalGrupoValidacion

#### Scenario: Venta domain is self-contained
- **WHEN** inspecting `src/main/java/com/superrrr/franky/venta/`
- **THEN** the following sub-packages SHALL exist with their respective classes:
  - `controller/` → VentaController
  - `service/` → VentaService
  - `repositories/` → VentaRepository, DetalleVentaRepository
  - `entity/` → Venta entity, DetalleVenta entity
  - `enums/` → EstadoVenta
  - `mapper/` → VentaMapper, DetalleVentaMapper
  - `dto/` → VentaRequestDto, VentaResponseDto, VentaFiltrosDto, DetalleVentaRequestDto, DetalleVentaResponseDto
  - `exception/` → VentaNoEncontradaException

#### Scenario: Application compiles after reorganization
- **WHEN** running `./mvnw compile -q`
- **THEN** it SHALL complete successfully with no compilation errors

#### Scenario: Tests pass after reorganization
- **WHEN** running `./mvnw test -Dspring.profiles.active=h2 -q`
- **THEN** all tests SHALL pass

### Requirement: No behavior changes introduced
The reorganization SHALL only relocate files and update imports/package declarations. No business logic, method signatures, or API behavior SHALL be modified.

#### Scenario: All endpoints respond identically
- **WHEN** sending requests to all API endpoints before and after the change
- **THEN** responses SHALL have the same status codes, same field names, and same data types
