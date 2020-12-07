package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.AnesthesiaData;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;

public interface AddAnesthesiaDataRepository {

    boolean isLocked(long operationId) throws OperationNotFoundException;

    void addAnesthesiaData(long operationId, AnesthesiaData anesthesiaData) throws OperationNotFoundException;

}
