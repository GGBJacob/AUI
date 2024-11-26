package org.example.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private String model;
    private int horsePower;
    private String brand;

    public static CarDTO from(Car car) {
        return new CarDTO(car.getModel(), car.getHorsePower(), car.brand.getName());
    }
}
