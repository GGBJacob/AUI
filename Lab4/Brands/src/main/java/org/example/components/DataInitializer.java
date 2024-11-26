package org.example.components;

import jakarta.annotation.PostConstruct;
import org.example.entities.Brand;
import org.example.repositories.CarBrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataInitializer {

    private final CarBrandsRepository brandsRepository;

    @Autowired
    public DataInitializer(CarBrandsRepository brandsRepository) {
        this.brandsRepository = brandsRepository;
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

        brandsRepository.saveAllAndFlush(exampleBrands.values());
        System.out.println("Example items loaded!");
    }
}
