package de.cassisi.hearth.util;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.repository.model.OperationDB;

public final class OperationConverter {

    public static Operation toOperation(OperationDB operationDB) {
        return new Operation(
                operationDB.getId(),
                operationDB.getDate(),
                operationDB.getRoomNr(),
                operationDB.isNirsDataAvailable(),
                operationDB.isInfusionDataAvailable(),
                operationDB.isAnesthesiaDataAvailable(),
                operationDB.isHlmDataAvailable(),
                operationDB.isLocked());
    }

    public static OperationDB toOperationDB(Operation operation) {
        return OperationDB.builder()
                .id(operation.getId())
                .date(operation.getDate())
                .roomNr(operation.getRoomNr())
                .nirsDataAvailable(operation.isNirsDataAvailable())
                .infusionDataAvailable(operation.isInfusionDataAvailable())
                .anesthesiaDataAvailable(operation.isAnesthesiaDataAvailable())
                .hlmDataAvailable(operation.isHlmDataAvailable())
                .locked(operation.isLocked())
                .build();
    }

}
