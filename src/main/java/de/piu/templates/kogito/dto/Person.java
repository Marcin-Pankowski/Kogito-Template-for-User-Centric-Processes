package de.piu.templates.kogito.dto;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Person {
    @NonNull private String name;
    private Integer age;
}
