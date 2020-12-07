package de.cassisi.heartcapture.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class SimpleOperationData {

    private final long id;
    private final LocalDate date;
    private final String room;
    private final boolean nirsDataAvailable;
    private final boolean bisDataAvailable;
    private final boolean infusionDataAvailable;
    private final boolean hlmDataAvailable;
    private final boolean locked;

}
