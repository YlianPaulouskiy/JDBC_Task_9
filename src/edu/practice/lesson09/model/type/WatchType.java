package edu.practice.lesson09.model.type;

import lombok.EqualsAndHashCode;
import lombok.Getter;


@Getter
public enum WatchType {

    QUARTZ("Кварц"),
    MECHANICAL("Mех");

    private final String type;

    WatchType(String type) {
        this.type = type;
    }

}
