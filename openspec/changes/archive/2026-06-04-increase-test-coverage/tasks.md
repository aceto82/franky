## 1. Update JaCoCo thresholds

- [x] 1.1 Bump LINE minimum from 0.80 to 0.85 in pom.xml
- [x] 1.2 Add BRANCH counter with minimum 0.75 in pom.xml

## 2. Write controller tests

- [x] 2.1 Create AuthControllerTest with login/error scenarios
- [x] 2.2 Create ProductoControllerTest with CRUD and auth scenarios
- [x] 2.3 Create SucursalControllerTest with CRUD scenarios
- [x] 2.4 Create VentaControllerTest with create/list/delete scenarios
- [x] 2.5 Create EstadisticaControllerTest with stats endpoint scenarios

## 3. Write security filter tests

- [x] 3.1 Create JwtAuthenticationFilterTest with valid/invalid/missing/expired token scenarios

## 4. Write global exception handler tests

- [x] 4.1 Create GlobalExceptionHandlerTest with validation error, not-found, and generic exception scenarios

## 5. Write bootstrap tests

- [x] 5.1 Create DataInitializerTest verifying admin user is seeded
- [x] 5.2 Create SecurityConfigTest verifying endpoint access rules

## 6. Verify and validate

- [x] 6.1 Run `mvn clean verify` and confirm LINE >= 85% and BRANCH >= 75%
- [x] 6.2 Confirm all tests pass
