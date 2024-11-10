package org.example;

import lombok.*;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@Builder
public class Car implements Comparable<Car>, Serializable {
    String model;
    int horsePower;
    Brand brand;

    @Override
    public int compareTo(Car o) {
        if(this.model.compareTo(o.model) != 0)
            return this.model.compareTo(o.model);
        if (o.horsePower > this.horsePower)
            return 1;
        else
            return -1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, horsePower, brand.name);
    }

    @Override
    public String toString()
    {
        return "Car(model=" + model + ", horsePower=" + horsePower + ", brand=" + brand.name + ")";
    }
}
