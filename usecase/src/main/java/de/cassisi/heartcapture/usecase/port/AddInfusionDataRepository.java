package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.InfusionData;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;

public interface AddInfusionDataRepository {

    void addInfusionDataToOperation(long operationId, InfusionData infusionData) throws OperationNotFoundException;

    boolean isLocked(long operationId) throws OperationNotFoundException;
}
