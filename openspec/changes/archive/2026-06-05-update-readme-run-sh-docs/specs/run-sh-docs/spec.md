## ADDED Requirements

### Requirement: Document run.sh in README

The system SHALL document the `run.sh` script in the project's README.md, including its interactive menu mode and all CLI commands, so that developers can discover and use the script without external guidance.

#### Scenario: README contains run.sh section
- **WHEN** a developer reads README.md
- **THEN** there SHALL be a section titled "Script de utilidad (run.sh)" that explains how to use the script

#### Scenario: Interactive menu is documented
- **WHEN** a developer reads the run.sh section
- **THEN** the menu output with options 1-9 and 0 SHALL be shown or summarized

#### Scenario: CLI commands are documented in a table
- **WHEN** a developer reads the run.sh section
- **THEN** all CLI commands SHALL be listed in a table with columns: Comando, Descripción
- **AND** the table SHALL include at minimum: `dc:up`, `dc:down`, `dc:status`, `dev`, `test`, `compile`, `clean`, `clean-test`, `coverage`

#### Scenario: Quick start uses run.sh
- **WHEN** a developer reads the Quick Start section
- **THEN** the instructions SHALL use `./run.sh` commands instead of manual docker/mvn commands
- **AND** the flow SHALL show: `./run.sh dc:up` → `./run.sh dev` for the basic development workflow

#### Scenario: Typical workflow example
- **WHEN** a developer reads the run.sh section
- **THEN** there SHALL be at least one typical workflow example (e.g., arrancar DB → iniciar app, o clean → coverage)
