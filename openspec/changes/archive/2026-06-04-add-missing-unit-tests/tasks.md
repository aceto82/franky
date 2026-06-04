## 1. Mapper tests

- [x] 1.1 Add `ProductoMapperTest` covering `toDTO`, `toModel`, and `toResponseDto`
- [x] 1.2 Add `SucursalMapperTest` covering `toDTO`, `toModel`, and `toResponseDto`
- [x] 1.3 Add `VentaMapperTest` covering `toDTO` with detalles
- [x] 1.4 Add `DetalleVentaMapperTest` covering `toDTO`
- [x] 1.5 Add `UsuarioMapperTest` covering `loginToDto` and `toDto`

## 2. Auth service tests

- [x] 2.1 Add `JwtTokenProviderTest` covering `generateToken`, `validateToken` (valid/tampered), `getUsernameFromToken`, `getRolFromToken`
- [x] 2.2 Add `CustomUserDetailsServiceTest` covering user found and not found
- [x] 2.3 Add `AuthServiceTest` covering login success, wrong password, non-existent user

## 3. Domain service tests

- [x] 3.1 Add `ProductoServiceTest` covering CRUD, partial update, not-found exception
- [x] 3.2 Add `SucursalServiceTest` covering CRUD and not-found exception
- [x] 3.3 Add `VentaServiceTest` covering crear (success, sucursal not found, producto not found)
- [x] 3.4 Add `EstadisticaServiceTest` covering best-selling product and empty ventas

## 4. Verify

- [x] 4.1 Run `mvn test -Dspring.profiles.active=h2` and confirm all tests pass
