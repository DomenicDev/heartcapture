package de.cassisi.hearth.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class CompleteOperationDataDTO {

    private final long id;
    private final LocalDate date;
    private final String room;
    private final boolean nirsDataAvailable;
    private final boolean bisDataAvailable;
    private final boolean infusionDataAvailable;
    private final boolean hlmDataAvailable;

    private final List<NirsDataDTO> nirsData;
    private final List<BISDataDTO> bisData;

}
