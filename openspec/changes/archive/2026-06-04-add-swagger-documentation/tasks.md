## 1. Dependency and config

- [x] 1.1 Add springdoc-openapi-starter-webmvc-ui v3.0.3 dependency to pom.xml
- [x] 1.2 Create `OpenApiConfig` with API info metadata (title, version, description, contact)

## 2. Security scheme

- [x] 2.1 Add JWT Bearer security scheme and global security requirement to `OpenApiConfig`

## 3. Verify

- [x] 3.1 Run `mvn test -Dspring.profiles.active=h2` and confirm all tests pass
- [x] 3.2 Start the app and verify Swagger UI loads at `/swagger-ui.html` and OpenAPI spec at `/v3/api-docs`
