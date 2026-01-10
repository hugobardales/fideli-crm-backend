CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(255),
    loyalty_points INTEGER DEFAULT 0
);

CREATE TABLE interactions (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(255) NOT NULL,
    notes TEXT,
    date TIMESTAMP,
    customer_id BIGINT NOT NULL,
    CONSTRAINT fk_customer
    FOREIGN KEY(customer_id)
    REFERENCES customers(id)
);

CREATE INDEX idx_customers_email ON customers(email);
