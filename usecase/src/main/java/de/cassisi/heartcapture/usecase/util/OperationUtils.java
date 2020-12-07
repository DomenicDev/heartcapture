package de.cassisi.heartcapture.usecase.util;

import de.cassisi.heartcapture.entity.Operation;
import de.cassisi.heartcapture.usecase.exception.OperationLockException;

public final class OperationUtils {

    public static void throwIfLocked(Operation operation) throws OperationLockException {
        throwIfLocked(operation.getId(), operation.isLocked());
    }

    public static void throwIfLocked(long operationId, boolean locked) throws OperationLockException {
        if (locked) {
            throw new OperationLockException(operationId);
        }
    }

}
