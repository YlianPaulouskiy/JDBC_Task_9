package edu.practice.lesson09.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class Manufacturer {

    private Long id;
    private String name;
    private String country;

}
