package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private String model;
    private int horsePower;
    private String brandId;

    public static CarDTO from(Car car) {
        return new CarDTO(car.getModel(), car.getHorsePower(), car.getBrandId().toString());
    }
}
