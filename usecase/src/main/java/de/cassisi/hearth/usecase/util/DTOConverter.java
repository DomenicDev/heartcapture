package de.cassisi.hearth.usecase.util;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.entity.ReportData;
import de.cassisi.hearth.usecase.dto.BISDataDTO;
import de.cassisi.hearth.usecase.dto.CompleteOperationDataDTO;
import de.cassisi.hearth.usecase.dto.NirsDataDTO;
import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import de.cassisi.hearth.usecase.port.FindFullOperationRepository;

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
                operation.isHlmDataAvailable()
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
}
