## 1. Auth Controller Documentation

- [x] 1.1 Add `@Operation` with summary and description to `POST /api/auth/login` in `AuthController`
- [x] 1.2 Add `@ApiResponse` annotations for 200 and 401 to `AuthController.login()`

## 2. Product Controller Documentation

- [x] 2.1 Add `@Operation` with summary and description to each endpoint in `ProductoController`
- [x] 2.2 Add `@ApiResponse` annotations to `ProductoController.obtenerListaProductos()` (200, 401, 500)
- [x] 2.3 Add `@ApiResponse` annotations to `ProductoController.crearProducto()` (201, 400, 401, 403)
- [x] 2.4 Add `@ApiResponse` annotations to `ProductoController.actualizarProducto()` (200, 400, 401, 403, 404)
- [x] 2.5 Add `@ApiResponse` annotations to `ProductoController.borrarProducto()` (204, 401, 403)
- [x] 2.6 Add `@Parameter` description annotation to `{id}` path variable in PUT and DELETE endpoints

## 3. Sucursal Controller Documentation

- [x] 3.1 Add `@Operation` with summary and description to each endpoint in `SucursalController`
- [x] 3.2 Add `@ApiResponse` annotations to `SucursalController.obtenerListaSucursales()` (200, 401, 500)
- [x] 3.3 Add `@ApiResponse` annotations to `SucursalController.crearSucursal()` (201, 400, 401, 403)
- [x] 3.4 Add `@ApiResponse` annotations to `SucursalController.actualizarSucursal()` (200, 400, 401, 403, 404)
- [x] 3.5 Add `@ApiResponse` annotations to `SucursalController.borrarSucursal()` (204, 401, 403)
- [x] 3.6 Add `@Parameter` description annotation to `{id}` path variable in PUT and DELETE endpoints

## 4. Venta Controller Documentation

- [x] 4.1 Add `@Operation` with summary and description to each endpoint in `VentaController`
- [x] 4.2 Add `@ApiResponse` annotations to `VentaController.crearVenta()` (201, 400, 401, 403)
- [x] 4.3 Add `@ApiResponse` annotations to `VentaController.obtenerVentasPorSucursalYFecha()` (200, 400, 401)
- [x] 4.4 Add `@ApiResponse` annotations to `VentaController.borrarVenta()` (204, 401, 403)
- [x] 4.5 Add `@Parameter` description to `{id}` path variable and query params `sucursalId`, `fecha`

## 5. Estadistica Controller Documentation

- [x] 5.1 Add `@Operation` with summary and description to `GET /api/estadisticas/producto-mas-vendido`
- [x] 5.2 Add `@ApiResponse` annotations for 200, 401, and 500

## 6. DTO Schema Documentation

- [x] 6.1 Add `@Schema` class and field annotations to `LoginRequestDto`
- [x] 6.2 Add `@Schema` class and field annotations to `ProductoRequestDto`
- [x] 6.3 Add `@Schema` class and field annotations to `ProductoResponseDto`
- [x] 6.4 Add `@Schema` class and field annotations to `SucursalRequestDto`
- [x] 6.5 Add `@Schema` class and field annotations to `VentaRequestDto`
