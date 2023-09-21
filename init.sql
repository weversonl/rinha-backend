CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE TABLE persons (
    id uuid PRIMARY KEY,
    surname VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    birth DATE NOT NULL,
    stack VARCHAR(255)[],
    terms TEXT GENERATED ALWAYS AS (
            name || surname || stack
    ) STORED
);

CREATE INDEX IF NOT EXISTS idx_persons_concatenated_text_trgm
ON persons
USING GIST (terms gist_trgm_ops);