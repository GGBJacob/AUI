package org.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cars")
public class Car implements Comparable<Car>, Serializable {

    @Id
    @Column(name = "car_ID")
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(name = "car_model", nullable = false)
    private String model;

    @Column(name = "car_hp", nullable = false)
    private int horsePower;

    @Column(name = "brand_id", nullable = false)
    private UUID brandId;

    @Override
    public int compareTo(Car o) {
        if (this.model.compareTo(o.model) != 0)
            return this.model.compareTo(o.model);
        return Integer.compare(o.horsePower, this.horsePower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, horsePower, brandId);
    }

    @Override
    public String toString() {
        return "Car(model=" + model + ", horsePower=" + horsePower + ", brandId=" + brandId + ")";
    }
}
