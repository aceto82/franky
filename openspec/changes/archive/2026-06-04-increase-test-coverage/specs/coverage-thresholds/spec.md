## ADDED Requirements

### Requirement: JaCoCo LINE coverage minimum 85%
The build SHALL fail if JaCoCo LINE coverage ratio falls below 0.85.

#### Scenario: Build passes with adequate LINE coverage
- **WHEN** `mvn clean verify` is run and LINE coverage is >= 85%
- **THEN** the build SHALL succeed with no JaCoCo check violation

#### Scenario: Build fails with insufficient LINE coverage
- **WHEN** `mvn clean verify` is run and LINE coverage is < 85%
- **THEN** the build SHALL fail with a JaCoCo check violation message

### Requirement: JaCoCo BRANCH coverage minimum 75%
The build SHALL fail if JaCoCo BRANCH coverage ratio falls below 0.75.

#### Scenario: Build passes with adequate BRANCH coverage
- **WHEN** `mvn clean verify` is run and BRANCH coverage is >= 75%
- **THEN** the build SHALL succeed with no JaCoCo check violation

#### Scenario: Build fails with insufficient BRANCH coverage
- **WHEN** `mvn clean verify` is run and BRANCH coverage is < 75%
- **THEN** the build SHALL fail with a JaCoCo check violation message
