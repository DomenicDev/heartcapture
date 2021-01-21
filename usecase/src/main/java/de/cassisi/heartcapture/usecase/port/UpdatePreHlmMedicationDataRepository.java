package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.PreMedicationData;
import de.cassisi.heartcapture.usecase.exception.OperationLockException;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;

public interface UpdatePreHlmMedicationDataRepository {

    void updatePreHlmMedicationData(long operationId, PreMedicationData data) throws OperationNotFoundException, OperationLockException;

}
