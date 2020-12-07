package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.NIRSData;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;

public interface AddNirsDataRepository {

    boolean isLocked(long operationId) throws OperationNotFoundException;

    void addNirsDataToOperation(long operationId, NIRSData nirsData) throws OperationNotFoundException;

}
