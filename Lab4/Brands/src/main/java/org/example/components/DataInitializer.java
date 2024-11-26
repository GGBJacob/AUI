package org.example.components;

import jakarta.annotation.PostConstruct;
import org.example.entities.Brand;
import org.example.repositories.CarBrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
                        .issueYear(1945)
                        .cars(new ArrayList<>() {{
                            add(UUID.fromString("4d6f04e6-44fb-4d7f-911a-e378b8f5ac6e"));
                            add(UUID.fromString("73a33a96-d821-4f1e-9109-4c78db0df1f5"));
                            add(UUID.fromString("ea46dd4e-b1c6-481b-88e9-df6e5ac0e7de"));
                        }}),
                Brand.builder()
                        .id(UUID.fromString("f47ac10b-58cc-4372-a567-0e02b2c3d479"))
                        .name("Audi")
                        .issueYear(1930)
                        .cars(new ArrayList<>() {{
                            add(UUID.fromString("7d6f2e2c-b3e4-4fdd-8f5c-f5b9be0d70b9"));
                            add(UUID.fromString("3fa85f64-5717-4562-b3fc-2c963f66afa6"));
                            add(UUID.fromString("d3486ae9-9d1b-403b-b338-4c889c44ec3a"));
                        }}),
                Brand.builder()
                        .id(UUID.fromString("9fd93d44-3b70-4d7a-8363-5b73a676f185"))
                        .name("Tesla")
                        .issueYear(1990)
                        .cars(new ArrayList<>() {{
                            add(UUID.fromString("12b3b517-2ff3-4f48-b6fc-987fdc7fbac9"));
                            add(UUID.fromString("bd1a244e-18c3-4b21-8ae3-e2b4ff84f1c7"));
                            add(UUID.fromString("8f2c565a-4316-4a5b-b4e8-5fbb2147427a"));
                        }})
        )
                .map(Brand.BrandBuilder::build).collect(Collectors.toMap(Brand::getName, x -> x));

        brandsRepository.saveAllAndFlush(exampleBrands.values());
        System.out.println("Example items loaded!");
    }
}
