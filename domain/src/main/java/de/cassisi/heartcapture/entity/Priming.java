package de.cassisi.heartcapture.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@Builder
@EqualsAndHashCode
@AllArgsConstructor
public final class Priming {

    private final String text;
    private final Integer amount;
    private final String unit;

}
