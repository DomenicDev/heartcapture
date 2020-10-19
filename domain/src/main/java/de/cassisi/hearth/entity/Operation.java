package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.time.LocalDate;

/**
 * An operation defines a concrete surgery covering all important meta information.
 */
@Getter
@AllArgsConstructor
public final class Operation {

    private final Long id;
    @NonNull private final LocalDate date;
    @NonNull private final String roomNr;
    private final boolean nirsDataAvailable;
    private final boolean infusionDataAvailable;
    private final boolean anesthesiaDataAvailable;
    private final boolean hlmDataAvailable;
    private final boolean locked;

}
