package org.example.controllers;

import org.example.entities.Car;
import org.example.entities.CarDTO;
import org.example.services.BrandsService;
import org.example.services.CarsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@RestController
public class CarController {
    private final CarsService carsService;
    private final BrandsService brandsService;

    public CarController(CarsService carsService, BrandsService brandsService) {
        this.carsService = carsService;
        this.brandsService = brandsService;
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarDTO>> listAllCars(@RequestParam(required = false) String sort) {
        List<CarDTO> resultList = new ArrayList<>();
        for (Car car : carsService.findAll())
        {
            CarDTO carDTO = new CarDTO(car.getModel(), car.getHorsePower(), car.getBrand().getName());
            resultList.add(carDTO);
        }

        if (sort == null || sort.isEmpty())
            return new ResponseEntity<>(resultList, HttpStatus.OK);

        if (sort.equalsIgnoreCase("model"))
            resultList.sort(Comparator.comparing(CarDTO::getModel));
        else if (sort.equalsIgnoreCase("hp") || sort.equalsIgnoreCase("horsepower"))
            resultList.sort(Comparator.comparing(CarDTO::getHorsePower));
        else if (sort.equalsIgnoreCase("brand"))
            resultList.sort(Comparator.comparing(CarDTO::getBrand));

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @GetMapping("/cars/{uuid}")
    public ResponseEntity<CarDTO> getCar(@PathVariable UUID uuid) {
        var car = carsService.findById(uuid);
        return car.map(carEntity -> new ResponseEntity<>(CarDTO.from(carEntity), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/cars/add/{uuid}")
    public ResponseEntity<CarDTO> addCar(@PathVariable UUID uuid, @RequestParam String model, @RequestParam String horsePower, @RequestParam String brandName) {

        if (carsService.findById(uuid).isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var brand = brandsService.findByName(brandName).stream().findFirst().orElse(null);
        if (brand == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        var car = Car.builder()
                .model(model)
                .horsePower(Integer.parseInt(horsePower))
                .brand(brand)
                .build();

        carsService.save(car);

        return new ResponseEntity<>(CarDTO.from(car), HttpStatus.CREATED);
    }

    @PutMapping("/cars/{uuid}")
    public ResponseEntity<CarDTO> editCar(@PathVariable UUID uuid, @RequestBody CarDTO carDTO) {
        var car = carsService.findById(uuid);
        if (car.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var brand = brandsService.findByName(carDTO.getBrand()).stream().findFirst().orElse(null);
        if (brand == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var foundCar = car.get();
        foundCar.setModel(carDTO.getModel());
        foundCar.setHorsePower(carDTO.getHorsePower());
        foundCar.setBrand(brand);

        carsService.save(foundCar);

        return new ResponseEntity<>(CarDTO.from(foundCar), HttpStatus.OK); // Kod 200 OK
    }
}
