## ADDED Requirements

### Requirement: Idempotency-Key header is required for venta creation

The system SHALL require the `Idempotency-Key` header in `POST /api/ventas`. Requests without a valid, non-blank key SHALL be rejected.

The system SHALL store the idempotency key in a `NOT NULL` column with a unique constraint to prevent duplicates at the database level.

#### Scenario: Request with valid key creates venta

- **WHEN** a client sends `POST /api/ventas` with a non-blank `Idempotency-Key` header that has not been used before
- **THEN** the system SHALL create the venta and return `201 Created`
- **AND** the system SHALL store the idempotency key associated with the created venta

#### Scenario: Retry with same key returns existing venta

- **WHEN** a client sends `POST /api/ventas` with the same `Idempotency-Key` used in a previous successful request
- **THEN** the system SHALL NOT create a duplicate venta
- **AND** the system SHALL return `200 OK` with the original venta data

#### Scenario: Missing Idempotency-Key header is rejected

- **WHEN** a client sends `POST /api/ventas` without an `Idempotency-Key` header
- **THEN** the system SHALL reject the request
- **AND** the system SHALL return `400 Bad Request`

#### Scenario: Blank Idempotency-Key header is rejected

- **WHEN** a client sends `POST /api/ventas` with an empty or blank `Idempotency-Key` header
- **THEN** the system SHALL reject the request
- **AND** the system SHALL return `400 Bad Request`

#### Scenario: Concurrent requests with same key

- **WHEN** two concurrent requests arrive with the same `Idempotency-Key`
- **THEN** exactly one venta SHALL be created
- **AND** both requests SHALL receive the same venta response

#### Scenario: Backfill of existing ventas without idempotency key

- **WHEN** the system is migrated from optional to mandatory idempotency
- **THEN** all existing ventas with a NULL `idempotency_key` SHALL receive an auto-generated UUID
- **AND** the column SHALL be altered to `NOT NULL`
