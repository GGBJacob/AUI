CREATE TABLE IF NOT EXISTS brand_cars (
    brand_brand_id UUID NOT NULL,
    cars UUID,
    FOREIGN KEY (brand_brand_id) REFERENCES brands(brand_id)
);