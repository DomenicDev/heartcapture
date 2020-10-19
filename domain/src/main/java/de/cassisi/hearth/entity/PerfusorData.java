package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * This class contains information about just one perfusor.
 * It is used in combination with the class {@link InfusionData}.
 */
@Getter
@AllArgsConstructor
public final class PerfusorData {

    @NonNull private final String name;
    private final double rate;

}
