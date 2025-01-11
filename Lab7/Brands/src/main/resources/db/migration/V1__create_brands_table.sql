CREATE TABLE IF NOT EXISTS brands(
    issue_year INTEGER,
    brand_id uuid NOT NULL ,
    brand_name VARCHAR(255),
    PRIMARY KEY (brand_id)
);


