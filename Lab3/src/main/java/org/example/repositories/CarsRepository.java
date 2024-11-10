package org.example.repositories;

import org.example.entities.Brand;
import org.example.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CarsRepository extends JpaRepository<Car, UUID> {
    List<Car> findByBrand(Brand brand);
    List<Car> findByHorsePower(Integer horsePower);
    List<Car> findByModel(String model);
}
