package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;

public interface LockOperationRepository {

    Operation setLocked(long operationId, boolean locked) throws OperationNotFoundException;

}
