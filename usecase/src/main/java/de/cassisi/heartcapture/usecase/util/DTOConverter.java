package de.cassisi.heartcapture.usecase.util;

import de.cassisi.heartcapture.entity.Operation;
import de.cassisi.heartcapture.entity.PreMedicationData;
import de.cassisi.heartcapture.usecase.dto.*;
import de.cassisi.heartcapture.usecase.port.FindFullOperationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class DTOConverter {

    public static SimpleOperationData toSimpleOperationData(Operation operation) {
        return new SimpleOperationData(
                operation.getId(),
                operation.getDate(),
                operation.getRoomNr(),
                operation.isNirsDataAvailable(),
                operation.isAnesthesiaDataAvailable(),
                operation.isInfusionDataAvailable(),
                operation.isHlmDataAvailable(),
                operation.isLocked()
        );
    }

    public static CompleteOperationDataDTO toFullOperationData(FindFullOperationRepository.ResultData data) {
        SimpleOperationData operationData = toSimpleOperationData(data.getOperation());
        List<BISDataDTO> bisData = data.getAnesthesiaData().stream().map(d -> new BISDataDTO(d.getTimestamp(), d.getDepthOfAnesthesia())).collect(Collectors.toList());
        List<NirsDataDTO> nirsData = data.getNirsData().stream().map(n -> new NirsDataDTO(n.getTimestamp(), n.getLeftSaturation(), n.getRightSaturation())).collect(Collectors.toList());

        return new CompleteOperationDataDTO(
                operationData,
                nirsData,
                bisData);
    }

    public static CompleteOperationDataDTO toFullOperationData(Operation operation) {
        return new CompleteOperationDataDTO(
                toSimpleOperationData(operation),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public static PreMedicationDataDTO convert(long operationId, PreMedicationData data) {
        return new PreMedicationDataDTO(
                operationId,
                data.getSuprareninPreOperation(),
                data.getNorepinephrinPreOperation(),
                data.getVasopressinPreOperation(),
                data.getMilrinonPreOperation(),
                data.getNtgPreOperation(),
                data.getSimdaxPreOperation(),
                data.getHeparinPreOperation(),
                data.getSuprareninPreHlm(),
                data.getNorepinephrinPreHlm(),
                data.getVasopressinPreHlm(),
                data.getMilrinonPreHlm(),
                data.getNtgPreHlm(),
                data.getSimdaxPreHlm()
        );
    }
}
