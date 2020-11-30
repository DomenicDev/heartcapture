package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;

public interface AddAnesthesiaDataRepository {

    boolean isLocked(long operationId) throws OperationNotFoundException;

    void addAnesthesiaData(long operationId, AnesthesiaData anesthesiaData) throws OperationNotFoundException;

}
