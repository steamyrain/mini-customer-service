ALTER TABLE transaction RENAME COLUMN created_date TO created_at;
ALTER TABLE transaction RENAME COLUMN updated_date TO updated_at;
ALTER TABLE transaction_item RENAME COLUMN created_date TO created_at;
ALTER TABLE transaction_item RENAME COLUMN updated_date TO updated_at;
