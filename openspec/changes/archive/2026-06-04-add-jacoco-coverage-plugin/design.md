## Context

The project uses Maven with Spring Boot 4.0.6 and Java 21. Unit tests exist under `src/test/java/` and run with `./mvnw test -Dspring.profiles.active=h2`. No coverage tool is configured, so developers have no visibility into untested code paths.

## Goals / Non-Goals

**Goals:**
- Add JaCoCo Maven plugin to `pom.xml` with `prepare-agent` and `report` executions
- Generate HTML coverage report at `target/site/jacoco/index.html` after tests
- Add a `check` execution to enforce minimum 80% line coverage across the project
- Ensure `./mvnw verify` runs tests + generates report + checks coverage

**Non-Goals:**
- No changes to existing tests
- No changes to CI/CD configuration
- No branch coverage thresholds (line coverage only for now)
- No per-package or per-class exclusions unless needed (exclude Lombok-generated code)

## Decisions

| Decision | Choice | Rationale |
|---|---|---|
| Plugin | JaCoCo (`jacoco-maven-plugin`) | Industry standard for Java Maven projects, integrates with SonarQube, IntelliJ, and VS Code |
| Phase for report | `verify` | Ensures report is generated after tests but before install; standard convention |
| Coverage rule | 80% line coverage minimum | Reasonable baseline; prevents regression without blocking development |
| Exclusions | None initially | Can add exclusions for config classes if needed after first report |

## Risks / Trade-offs

- **[Low] Build time increase**: JaCoCo adds minimal overhead (~5-10%) to test execution
- **[Low] False negatives**: Lombok-generated methods (getters, setters, builders) are counted as uncovered unless excluded — may need to add exclusions
- **[Low] Flaky threshold**: 80% might be too high if untested areas exist — can adjust down after first run
