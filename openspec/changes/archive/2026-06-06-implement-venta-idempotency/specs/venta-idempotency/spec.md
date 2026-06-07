## ADDED Requirements

### Requirement: Venta creation supports idempotency key

The system SHALL support an optional `Idempotency-Key` header in `POST /api/ventas` to ensure that duplicate requests with the same key do not create duplicate sales.

#### Scenario: First request with Idempotency-Key creates venta
- **WHEN** a client sends `POST /api/ventas` with a valid `Idempotency-Key` header
- **THEN** the system SHALL create the venta and return `201 Created`
- **AND** the system SHALL store the idempotency key associated with the created venta

#### Scenario: Retry with same Idempotency-Key returns existing venta
- **WHEN** a client sends `POST /api/ventas` with the same `Idempotency-Key` used in a previous successful request
- **THEN** the system SHALL NOT create a duplicate venta
- **AND** the system SHALL return `200 OK` with the original venta data

#### Scenario: Request without Idempotency-Key creates normally
- **WHEN** a client sends `POST /api/ventas` without an `Idempotency-Key` header
- **THEN** the system SHALL create the venta as before (no idempotency check)

#### Scenario: Concurrent requests with same Idempotency-Key
- **WHEN** two concurrent requests arrive with the same `Idempotency-Key`
- **THEN** exactly one venta SHALL be created
- **AND** both requests SHALL receive the same venta response

#### Scenario: Empty or blank Idempotency-Key is ignored
- **WHEN** a client sends `POST /api/ventas` with an empty or blank `Idempotency-Key` header
- **THEN** the system SHALL treat it as if no header was provided
- **AND** the system SHALL create the venta normally (no idempotency check)
