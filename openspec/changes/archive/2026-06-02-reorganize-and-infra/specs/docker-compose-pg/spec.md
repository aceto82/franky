## ADDED Requirements

### Requirement: Docker Compose provides PostgreSQL
The repository SHALL include a `docker-compose.yml` file at the project root that starts a PostgreSQL 16 service. The service SHALL use a named volume for data persistence and expose port 5432. Credentials SHALL match the application's default environment variables.

#### Scenario: Service starts successfully
- **WHEN** running `docker compose up -d`
- **THEN** PostgreSQL SHALL be available on localhost:5432, and the `franky` database SHALL be created

#### Scenario: Data persists across restarts
- **WHEN** running `docker compose down` and then `docker compose up -d`
- **THEN** previously created data SHALL still be present

#### Scenario: Application connects to containerized database
- **WHEN** setting `DB_URL=jdbc:postgresql://localhost:5432/franky`, `DB_USER=franky`, `DB_PASS=franky` and running `./mvnw spring-boot:run`
- **THEN** the application SHALL start without connection errors
