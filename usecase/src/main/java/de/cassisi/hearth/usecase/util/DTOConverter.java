package de.cassisi.hearth.usecase.util;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.dto.SimpleOperationData;

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

}
