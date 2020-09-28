package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public final class AnesthesiaData {

    private final LocalDateTime timestamp;
    private final double depthOfAnesthesia;

}
