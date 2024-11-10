package org.example.components;

import jakarta.annotation.PostConstruct;
import org.example.entities.Brand;
import org.example.entities.Car;
import org.example.repositories.CarBrandsRepository;
import org.example.repositories.CarsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataInitializer {

    private final CarBrandsRepository brandsRepository;
    private final CarsRepository carsRepository;

    @Autowired
    public DataInitializer(CarBrandsRepository brandsRepository, CarsRepository carsRepository) {
        this.brandsRepository = brandsRepository;
        this.carsRepository = carsRepository;
    }

    @PostConstruct
    public void init() {
        System.out.println("Adding example items to the database!");

        var exampleBrands = Stream.of(
                Brand.builder()
                        .id(UUID.fromString("550e8400-e29b-41d4-a716-446655440000"))
                        .name("BMW")
                        .issueYear(1945),
                Brand.builder()
                        .id(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"))
                        .name("Audi")
                        .issueYear(1930),
                Brand.builder()
                        .id(UUID.fromString("9fd93d44-3b70-4d7a-8363-5b73a676f185"))
                        .name("Tesla")
                        .issueYear(1990)
        )
                .map(Brand.BrandBuilder::build).collect(Collectors.toMap(Brand::getName, x -> x));


        var exampleCars = Stream.of(
                Car.builder()
                        .id(UUID.fromString("4d6f04e6-44fb-4d7f-911a-e378b8f5ac6e"))
                        .model("X1")
                        .horsePower(240)
                        .brand(exampleBrands.get("BMW")),
                Car.builder()
                        .id(UUID.fromString("73a33a96-d821-4f1e-9109-4c78db0df1f5"))
                        .model("M9")
                        .horsePower(390)
                        .brand(exampleBrands.get("BMW")),
                Car.builder()
                        .id(UUID.fromString("ea46dd4e-b1c6-481b-88e9-df6e5ac0e7de"))
                        .model("i7")
                        .horsePower(530)
                        .brand(exampleBrands.get("BMW")),
                Car.builder()
                        .id(UUID.fromString("7d6f2e2c-b3e4-4fdd-8f5c-f5b9be0d70b9"))
                        .model("A6")
                        .horsePower(472)
                        .brand(exampleBrands.get("Audi")),
                Car.builder()
                        .id(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"))
                        .model("Q5")
                        .horsePower(350)
                        .brand(exampleBrands.get("Audi")),
                Car.builder()
                        .id(UUID.fromString("d3486ae9-9d1b-403b-b338-4c889c44ec3a"))
                        .model("A3")
                        .horsePower(280)
                        .brand(exampleBrands.get("Audi")),
                Car.builder()
                        .id(UUID.fromString("12b3b517-2ff3-4f48-b6fc-987fdc7fbac9"))
                        .model("S")
                        .horsePower(500)
                        .brand(exampleBrands.get("Tesla")),
                Car.builder()
                        .id(UUID.fromString("bd1a244e-18c3-4b21-8ae3-e2b4ff84f1c7"))
                        .model("3")
                        .horsePower(350)
                        .brand(exampleBrands.get("Tesla")),
                Car.builder()
                        .id(UUID.fromString("8f2c565a-4316-4a5b-b4e8-5fbb2147427a"))
                        .model("X")
                        .horsePower(427)
                        .brand(exampleBrands.get("Tesla"))
        ).map(Car.CarBuilder::build).toList();

        brandsRepository.saveAllAndFlush(exampleBrands.values());

        carsRepository.saveAllAndFlush(exampleCars);
        System.out.println("Example items loaded!");
    }
}
