## 1. Fix SecurityConfigTest

- [x] 1.1 Add `@MockitoBean` for `UserDetailsService` in `SecurityConfigTest`
- [x] 1.2 Add `@BeforeEach` setup that mocks `userDetailsService.loadUserByUsername("user")` returning a `User` with `ROLE_USER` authority
- [x] 1.3 Run tests and verify all 96 pass
