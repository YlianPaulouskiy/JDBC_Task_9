package edu.practice.lesson09.model;

import edu.practice.lesson09.model.type.WatchType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Watch {

    private Long id;
    private String brand;
    private WatchType type;
    private Double price;
    private Integer amount;
    private Manufacturer manufacturer;


}
