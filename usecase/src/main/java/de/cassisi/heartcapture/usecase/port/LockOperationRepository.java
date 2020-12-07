package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.Operation;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;

public interface LockOperationRepository {

    Operation setLocked(long operationId, boolean locked) throws OperationNotFoundException;

}
