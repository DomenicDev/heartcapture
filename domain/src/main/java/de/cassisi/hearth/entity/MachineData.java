package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * Covers information about the used oxygenator, filter and cannulas.
 */
@Getter
@Builder
@AllArgsConstructor
public final class MachineData {

    private final String oxygenator;
    private final String haemoFil;
    private final String kanuelArt;
    private final String kanuelVen;
    private final String kanuelVen2;

}
