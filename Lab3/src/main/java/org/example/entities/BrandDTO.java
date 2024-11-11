package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private String name;
    private int issueYear;
    private List<CarDTO> cars;

    public static BrandDTO from(Brand brand) {
        return new BrandDTO(brand.getName(), brand.getIssueYear(), brand.getCars().stream().map(CarDTO::from).collect(Collectors.toList()));
    }
}
