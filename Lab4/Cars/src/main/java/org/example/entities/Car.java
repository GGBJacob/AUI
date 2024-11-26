package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="cars")
public class Car implements Comparable<Car>, Serializable {

    @Id
    @Column(name="car_ID")
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(name="car_model")
    String model;

    @Column(name="car_hp")
    int horsePower;

    @ManyToOne
    @JoinColumn(name = "car_brand")
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
