## 1. Add JaCoCo Plugin to pom.xml

- [x] 1.1 Add jacoco-maven-plugin to `<build><plugins>` with `prepare-agent` execution
- [x] 1.2 Add `report` execution bound to the `verify` phase
- [x] 1.3 Add `check` execution with minimum 80% line coverage rule

## 2. Add coverage command to run.sh

- [x] 2.1 Add `cmd_coverage()` function and menu option for coverage report
- [x] 2.2 Add `coverage` CLI subcommand to run.sh

## 3. Verify Coverage

- [x] 3.1 Run `./run.sh coverage` and confirm build succeeds
- [x] 3.2 Open `target/site/jacoco/index.html` and verify the report is generated
