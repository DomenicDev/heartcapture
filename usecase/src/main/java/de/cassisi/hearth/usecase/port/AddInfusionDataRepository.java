package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;

public interface AddInfusionDataRepository {

    void addInfusionDataToOperation(long operationId, InfusionData infusionData) throws OperationNotFoundException;

    boolean isLocked(long operationId) throws OperationNotFoundException;
}
