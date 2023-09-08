CREATE TABLE persons (
    id uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    surname VARCHAR(255) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    birth DATE NOT NULL,
    stack VARCHAR(255)[]
);


CREATE INDEX idx_persons_id ON persons (id);