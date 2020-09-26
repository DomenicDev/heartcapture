package de.cassisi.hearth.usecase.util;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.dto.SimpleOperationData;

public final class DTOConverter {

    public static SimpleOperationData toSimpleOperationData(Operation operation) {
        SimpleOperationData simpleOperationData = new SimpleOperationData();
        simpleOperationData.id = operation.getId();
        simpleOperationData.localDate = operation.getDate();
        simpleOperationData.rommBr = operation.getRoomNr();
        return simpleOperationData;
    }

}
