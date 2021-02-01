package de.cassisi.heartcapture.port;

import de.cassisi.heartcapture.entity.PreMedicationData;
import de.cassisi.heartcapture.port.repository.OperationRepository;
import de.cassisi.heartcapture.port.util.DBConverter;
import de.cassisi.heartcapture.repository.model.OperationDB;
import de.cassisi.heartcapture.repository.model.PreMedicationDataDB;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.port.FindMedicationDataRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class FindMedicationJpaRepository implements FindMedicationDataRepository {

    private final OperationRepository operationRepository;

    public FindMedicationJpaRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public PreMedicationData findPreMedicationData(long operationId) throws OperationNotFoundException {
        OperationDB operationDB = operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));
        PreMedicationDataDB data = operationDB.getPreMedicationData();
        return DBConverter.convert(data);
    }
}
