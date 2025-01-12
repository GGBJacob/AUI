CREATE TABLE IF NOT EXISTS cars (
    car_hp INTEGER NOT NULL,
    brand_id UUID NOT NULL,
    car_id UUID NOT NULL,
    car_model VARCHAR(255) NOT NULL,
    PRIMARY KEY (car_id)
);