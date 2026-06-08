-- Make idempotency_key NOT NULL after backfill
ALTER TABLE ventas ALTER COLUMN idempotency_key SET NOT NULL;
