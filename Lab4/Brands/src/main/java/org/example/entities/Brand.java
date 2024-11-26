package org.example.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="brands")
public class Brand implements Serializable {

    @Id
    @Column(name="brand_ID")
    @Builder.Default
    private UUID id = UUID.randomUUID();

    @Column(name="brand_name")
    String name;

    @Column(name="issue_year")
    int issueYear;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Car> cars = new ArrayList<>();
}
