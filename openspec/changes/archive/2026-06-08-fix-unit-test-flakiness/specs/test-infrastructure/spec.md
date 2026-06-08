### Requirement: Tests are independent and order-resistant

The test suite SHALL produce the same results regardless of test execution order. No test SHALL depend on side effects from other tests.

#### Scenario: SecurityConfigTest passes when run after AuthIntegrationTest

- **WHEN** `SecurityConfigTest` runs after a test that clears the database
- **THEN** it SHALL still pass
- **AND** it SHALL NOT require a specific user to exist in the database
