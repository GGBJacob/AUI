package org.example.controllers;

import org.example.entities.Brand;
import org.example.entities.BrandDTO;
import org.example.services.BrandsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandsService brandsService;

    public BrandController(BrandsService brandsService) {
        this.brandsService = brandsService;
    }

    @GetMapping
    public ResponseEntity<List<Brand>> getAllBrands() {
        var brands = brandsService.findAll().stream().toList();
        if (brands.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(brands, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<Void> getBrand(@PathVariable UUID uuid) {
        var brand = brandsService.findById(uuid);
        return brand.map(value -> new ResponseEntity<Void>(HttpStatus.FOUND))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PutMapping("/{brandId}")
    public ResponseEntity<BrandDTO> updateBrand(@PathVariable UUID brandId, @RequestBody BrandDTO brandDTO) {
        var brand = brandsService.findById(brandId);
        if (brand.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        brand.get().setIssueYear(brandDTO.getIssueYear());
        brandsService.save(brand.get());

        return new ResponseEntity<>(brandDTO, HttpStatus.OK);
    }

    @PostMapping("/{uuid}/{caruuid}")
    public ResponseEntity<Void> addCar(@PathVariable UUID uuid, @PathVariable UUID caruuid)
    {
        var brand = brandsService.findById(uuid);

        if (brand.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        brand.get().getCars().add(caruuid);
        brandsService.save(brand.get());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO) {
        if (!brandsService.findByName(brandDTO.getName()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        var brand = Brand.builder()
                .name(brandDTO.getName())
                .issueYear(brandDTO.getIssueYear())
                .build();
        brandsService.save(brand);
        return new ResponseEntity<>(brandDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable UUID uuid) {
        var brand = brandsService.findById(uuid).stream().findFirst();
        if (brand.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Notify Car Management about deletion
        notifyCarManagementAboutDeletion(uuid);

        brandsService.delete(brand.get());
        System.out.println("Deleted Brand: " + brand.get().getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void notifyCarManagementAboutDeletion(UUID brandId) {
        String carManagementUrl = "http://localhost:8082/cars/byBrand/" + brandId;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(carManagementUrl);
    }
}
