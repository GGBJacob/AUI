// BrandsService.java
package org.example.services;

import org.example.entities.Brand;
import org.example.repositories.CarBrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandsService {

    private final CarBrandsRepository brandsRepository;

    @Autowired
    public BrandsService(CarBrandsRepository brandsRepository) {
        this.brandsRepository = brandsRepository;
    }

    public List<Brand> findAll() {
        return brandsRepository.findAll();
    }

    public Optional<Brand> findById(UUID id) {
        return brandsRepository.findById(id);
    }

    public Brand save(Brand brand) {
        return brandsRepository.save(brand);
    }

    public void deleteById(UUID id) {
        brandsRepository.deleteById(id);
    }

}
