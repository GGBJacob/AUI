package org.example;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CarDto implements Comparable<CarDto> {
    String model;
    int horsePower;
    String brand;

    @Override
    public int compareTo(CarDto o) {
        if(this.model.compareTo(o.model) != 0)
            return this.model.compareTo(o.model);
        if (o.horsePower > this.horsePower)
            return 1;
        else
            return -1;
    }
}
