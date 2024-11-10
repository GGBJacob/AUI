package org.example;

import java.io.Serializable;
import java.util.List;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class Brand implements Serializable {
    String name;
    int issueYear;
    List<Car> cars;
}
