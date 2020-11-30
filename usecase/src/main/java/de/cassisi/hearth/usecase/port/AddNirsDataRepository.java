package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.NIRSData;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;

public interface AddNirsDataRepository {

    boolean isLocked(long operationId) throws OperationNotFoundException;

    void addNirsDataToOperation(long operationId, NIRSData nirsData) throws OperationNotFoundException;

}
