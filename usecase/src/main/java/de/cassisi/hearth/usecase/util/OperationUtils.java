package de.cassisi.hearth.usecase.util;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.exception.OperationLockException;

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
