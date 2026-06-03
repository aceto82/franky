## ADDED Requirements

### Requirement: H2 in-memory profile for tests
The application SHALL provide a Spring profile (`h2`) that configures an H2 in-memory database. The profile SHALL be defined in `application-h2.properties`. Tests SHALL be able to run without a running PostgreSQL instance by activating this profile.

#### Scenario: Profile activates H2 datasource
- **WHEN** the `h2` profile is active
- **THEN** the datasource SHALL use `jdbc:h2:mem:testdb` with driver `org.h2.Driver` and H2Dialect

#### Scenario: Tests pass with H2 profile
- **WHEN** running `./mvnw test -Dspring.profiles.active=h2`
- **THEN** all tests SHALL pass

### Requirement: H2 dependency in test scope
The `pom.xml` SHALL include `com.h2database:h2` with scope `test`.

#### Scenario: H2 jar is available during tests
- **WHEN** running tests
- **THEN** the H2 driver SHALL be on the classpath
