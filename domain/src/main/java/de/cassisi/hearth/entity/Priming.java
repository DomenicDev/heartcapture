package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Priming {

    private final String text;
    private final Integer amount;
    private final String unit;

}
