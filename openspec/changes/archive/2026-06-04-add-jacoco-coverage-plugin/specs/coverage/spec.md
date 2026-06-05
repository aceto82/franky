## ADDED Requirements

### Requirement: JaCoCo plugin is configured in pom.xml
The `pom.xml` SHALL include `jacoco-maven-plugin` in `<build><plugins>` with `prepare-agent`, `report`, and `check` executions.

#### Scenario: Build runs with JaCoCo prepared agent
- **WHEN** running `./mvnw test -Dspring.profiles.active=h2`
- **THEN** JaCoCo instruments the classes and writes execution data to `target/jacoco.exec`

#### Scenario: Coverage report is generated
- **WHEN** running `./mvnw verify -Dspring.profiles.active=h2`
- **THEN** an HTML coverage report SHALL be generated at `target/site/jacoco/index.html`

### Requirement: Coverage threshold is enforced
The JaCoCo `check` execution SHALL enforce a minimum line coverage of 80% across the project.

#### Scenario: Build fails below coverage threshold
- **WHEN** running `./mvnw verify -Dspring.profiles.active=h2`
- **AND** line coverage is below 80%
- **THEN** the build SHALL fail with a coverage violation error

#### Scenario: Build succeeds above coverage threshold
- **WHEN** running `./mvnw verify -Dspring.profiles.active=h2`
- **AND** line coverage is at or above 80%
- **THEN** the build SHALL succeed and the report SHALL be generated
