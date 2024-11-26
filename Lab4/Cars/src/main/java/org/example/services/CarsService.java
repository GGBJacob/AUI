// CarsService.java
package org.example.services;

import org.example.entities.Car;
import org.example.repositories.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarsService {

    private final CarsRepository carsRepository;

    @Autowired
    public CarsService(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    public List<Car> findAll() {
        return carsRepository.findAll();
    }

    public Optional<Car> findById(UUID id) {
        return carsRepository.findById(id);
    }

    public Optional<Car> findByBrandId(UUID brandId) {return carsRepository.findById(brandId);}

    public Car save(Car car) {
        return carsRepository.save(car);
    }

    public void deleteById(UUID id) {
        carsRepository.deleteById(id);
    }

    public List<Car> findByModel(String model) { return carsRepository.findByModel(model);
    }

    public void delete(Car car) { carsRepository.delete(car);
    }

    public void deleteAllByBrandId(UUID brandId) {
        for (Car car : findAll()) {
            if (car.getBrandId() == brandId)
                carsRepository.delete(car);
        }
    }
}
