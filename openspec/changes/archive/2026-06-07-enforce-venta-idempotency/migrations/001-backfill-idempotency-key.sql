-- Backfill existing ventas with auto-generated UUIDs
-- PostgreSQL: gen_random_uuid()
-- H2 (tests): random_uuid()
UPDATE ventas SET idempotency_key = gen_random_uuid()::text WHERE idempotency_key IS NULL;
