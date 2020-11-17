package de.cassisi.hearth.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CompleteOperationDataDTO {

    private final SimpleOperationData operationData;

    private final List<NirsDataDTO> nirsData;
    private final List<BISDataDTO> bisData;

}
