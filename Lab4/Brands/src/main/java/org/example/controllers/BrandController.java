package org.example.controllers;

import org.example.entities.Brand;
import org.example.entities.BrandDTO;
import org.example.entities.CarDTO;
import org.example.services.BrandsService;
import org.example.services.CarsService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
public class BrandController {

    private final BrandsService brandsService;
    private final CarsService carsService;

    public BrandController(BrandsService brandsService, CarsService carsService) {
        this.brandsService = brandsService;
        this.carsService = carsService;
    }

    @GetMapping("/brands")
    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        var brandsDTO = brandsService.findAll().stream().map(BrandDTO::from).toList();
        if (brandsDTO.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(brandsDTO, HttpStatus.OK);
    }

//    @GetMapping("/brands/{uuid}/cars")
//    public ResponseEntity<List<CarDTO>> getCars(@PathVariable UUID uuid, @RequestParam(required = false) String sort) {
//        var brand = brandsService.findById(uuid);
//        if (brand.isEmpty())
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//
//        List<CarDTO> foundCars = new java.util.ArrayList<>(carsService.findByBrand(brand.get()).stream().map(CarDTO::from).toList());
//
//        if(sort == null || sort.isEmpty())
//            return new ResponseEntity<>(foundCars, HttpStatus.OK);
//        else if (sort.equalsIgnoreCase("hp") || sort.equalsIgnoreCase("horsePower"))
//            foundCars.sort(Comparator.comparing(CarDTO::getHorsePower));
//        else if (sort.equalsIgnoreCase("model"))
//            foundCars.sort(Comparator.comparing(CarDTO::getModel));
//        return new ResponseEntity<>(foundCars, HttpStatus.OK);
//
//    }

    @GetMapping("/brands/{name}/cars")
    public ResponseEntity<List<CarDTO>> getCars(@PathVariable String name, @RequestParam(required = false) String sort) {
        var brand = brandsService.findByName(name);
        if (brand.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        List<CarDTO> foundCars = new java.util.ArrayList<>(carsService.findByBrand(brand.get(0)).stream().map(CarDTO::from).toList());

        if (foundCars.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if(sort == null || sort.isEmpty())
            return new ResponseEntity<>(foundCars, HttpStatus.OK);
        else if (sort.equalsIgnoreCase("hp") || sort.equalsIgnoreCase("horsePower"))
            foundCars.sort(Comparator.comparing(CarDTO::getHorsePower));
        else if (sort.equalsIgnoreCase("model"))
            foundCars.sort(Comparator.comparing(CarDTO::getModel));
        return new ResponseEntity<>(foundCars, HttpStatus.OK);
    }

    @PutMapping("/brands/{uuid}")
    public ResponseEntity<CarDTO> updateBrand(@PathVariable UUID uuid, @RequestBody BrandDTO brandDTO) {
        var brand = brandsService.findById(uuid);
        if (brand.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var newBrand = brand.get();
        newBrand.setName(brandDTO.getName());
        newBrand.setIssueYear(brandDTO.getIssueYear());
        brandsService.save(brand.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/brands")
    public ResponseEntity<BrandDTO> createBrand(@RequestBody BrandDTO brandDTO) {
        if(!brandsService.findByName(brandDTO.getName()).isEmpty())
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        var brand = Brand.builder()
                .name(brandDTO.getName())
                .issueYear(brandDTO.getIssueYear())
                .cars(new ArrayList<>())
                .build();
        brandsService.save(brand);
        return new ResponseEntity<>(brandDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/brands/{uuid}")
    public ResponseEntity<Void> deleteBrand(@PathVariable UUID uuid) {
        var brand = brandsService.findById(uuid).stream().findFirst();
        if (brand.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        brandsService.delete(brand.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
