package org.example.controllers;

import org.example.entities.Car;
import org.example.entities.CarDTO;
import org.example.services.CarsService;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarsService carsService;

    public CarController(CarsService carsService) {
        this.carsService = carsService;
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> listAllCars(@RequestParam(required = false) String sort) {
        List<Car> carList = carsService.findAll();

        if (carList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<CarDTO> resultList = new ArrayList<>();
        for (Car car : carList) {
            CarDTO carDTO = new CarDTO(car.getModel(), car.getHorsePower(), car.getBrandId().toString());
            resultList.add(carDTO);
        }

        if (sort == null || sort.isEmpty())
            return new ResponseEntity<>(resultList, HttpStatus.OK);

        if (sort.equalsIgnoreCase("model"))
            resultList.sort(Comparator.comparing(CarDTO::getModel));
        else if (sort.equalsIgnoreCase("hp") || sort.equalsIgnoreCase("horsepower"))
            resultList.sort(Comparator.comparing(CarDTO::getHorsePower));

        return new ResponseEntity<>(resultList, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CarDTO> getCar(@PathVariable UUID uuid) {
        Optional<Car> car = carsService.findById(uuid);
        return car.map(c -> new ResponseEntity<>(CarDTO.from(c), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/add")
    public ResponseEntity<CarDTO> addCar(@RequestBody CarDTO carDTO) {

        // Sprawdzanie, czy marka o podanym UUID istnieje w aplikacji "brands"
        String brandUrl = "http://localhost:8081/brands/" + carDTO.getBrandId();
        RestTemplate restTemplate = new RestTemplate();

        // Wykonanie zapytania GET do aplikacji "brands" w celu sprawdzenia, czy marka istnieje
        ResponseEntity<Void> brandResponse = restTemplate.exchange(brandUrl, HttpMethod.GET, null, Void.class);

        if (brandResponse.getStatusCode() == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        Car car = Car.builder()
                .model(carDTO.getModel())
                .horsePower(carDTO.getHorsePower())
                .brandId(UUID.fromString(carDTO.getBrandId()))
                .build();

        carsService.save(car);

        return new ResponseEntity<>(CarDTO.from(car), HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CarDTO> editCar(@PathVariable UUID uuid, @RequestBody CarDTO carDTO) {
        Optional<Car> carOpt = carsService.findById(uuid);
        if (carOpt.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Car car = carOpt.get();
        car.setModel(carDTO.getModel());
        car.setHorsePower(carDTO.getHorsePower());
        car.setBrandId(UUID.fromString(carDTO.getBrandId()));

        carsService.save(car);

        return new ResponseEntity<>(CarDTO.from(car), HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID uuid) {
        Optional<Car> car = carsService.findById(uuid);
        if (car.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        carsService.delete(car.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/byBrand/{brandId}")
    public ResponseEntity<Void> deleteCarsByBrand(@PathVariable UUID brandId) {
        carsService.deleteAllByBrandId(brandId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
