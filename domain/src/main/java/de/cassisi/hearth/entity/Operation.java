package de.cassisi.hearth.entity;

import de.cassisi.hearth.entity.ids.OperationId;

import java.time.LocalDate;

/**
 * A session defines a concrete operation.
 */
public final class Operation {

    private final Long id;
    private final LocalDate date;
    private final String roomNr;
    private final boolean nirsDataAvailable;
    private final boolean infusionDataAvailable;
    private final boolean anesthesiaDataAvailable;
    private final boolean hlmDataAvailable;
    private final boolean locked;

    public Operation(Long id, LocalDate date, String roomNr, boolean nirsDataAvailable, boolean infusionDataAvailable, boolean anesthesiaDataAvailable, boolean hlmDataAvailable, boolean locked) {
        this.id = id;
        this.date = date;
        this.roomNr = roomNr;
        this.nirsDataAvailable = nirsDataAvailable;
        this.infusionDataAvailable = infusionDataAvailable;
        this.anesthesiaDataAvailable = anesthesiaDataAvailable;
        this.hlmDataAvailable = hlmDataAvailable;
        this.locked = locked;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getRoomNr() {
        return roomNr;
    }

    public boolean isNirsDataAvailable() {
        return nirsDataAvailable;
    }

    public boolean isInfusionDataAvailable() {
        return infusionDataAvailable;
    }

    public boolean isAnesthesiaDataAvailable() {
        return anesthesiaDataAvailable;
    }

    public boolean isHlmDataAvailable() {
        return hlmDataAvailable;
    }

    public boolean isLocked() {
        return locked;
    }

}
