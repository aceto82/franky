## Why

The project has unit tests but no way to measure coverage. Without JaCoCo, developers cannot identify untested code paths, track coverage trends, or enforce minimum coverage standards. Adding JaCoCo enables `./mvnw verify` to produce an HTML coverage report and optionally fail the build if coverage drops below a threshold.

## What Changes

- Add `jacoco-maven-plugin` to `<build><plugins>` in `pom.xml`
- Configure a `prepare-agent` execution to capture coverage data during tests
- Configure a `report` execution to generate the HTML report at `target/site/jacoco/`
- Optionally configure a `check` execution with a minimum coverage rule (e.g., 80% line coverage)
- No changes to application code, tests, or runtime dependencies

## Capabilities

### New Capabilities
- `coverage`: JaCoCo coverage measurement, HTML report generation, and optional coverage enforcement

### Modified Capabilities
<!-- None — no existing specs to modify -->

## Impact

- `pom.xml`: add jacoco-maven-plugin with prepare-agent, report, and optional check goals
- `target/site/jacoco/`: generated report (git-ignored)
- No runtime impact, no API changes, no DB changes
