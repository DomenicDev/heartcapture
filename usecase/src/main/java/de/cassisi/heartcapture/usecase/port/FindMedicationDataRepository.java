package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.PreMedicationData;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;

public interface FindMedicationDataRepository {

    PreMedicationData findPreMedicationData(long operationId) throws OperationNotFoundException;

}
