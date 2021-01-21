package de.cassisi.heartcapture.port;

import de.cassisi.heartcapture.entity.PreMedicationData;
import de.cassisi.heartcapture.port.repository.OperationRepository;
import de.cassisi.heartcapture.repository.model.OperationDB;
import de.cassisi.heartcapture.repository.model.PreMedicationDataDB;
import de.cassisi.heartcapture.usecase.exception.OperationLockException;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.port.UpdatePreHlmMedicationDataRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class UpdatePreHlmMedicationDataJpaRepository implements UpdatePreHlmMedicationDataRepository {

    private final OperationRepository operationRepository;

    public UpdatePreHlmMedicationDataJpaRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public void updatePreHlmMedicationData(long operationId, PreMedicationData data) throws OperationNotFoundException, OperationLockException {
        // get operation to update
        OperationDB operationDB = operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));

        // check if locked
        if (operationDB.isLocked()) {
            throw new OperationLockException(operationId);
        }

        // update values
        PreMedicationDataDB preData = operationDB.getPreMedicationData();
        preData.setSuprareninPreOperation(data.getSuprareninPreOperation());
        preData.setNorepinephrinPreOperation(data.getNorepinephrinPreOperation());
        preData.setVasopressinPreOperation(data.getVasopressinPreOperation());
        preData.setMilrinonPreOperation(data.getMilrinonPreOperation());
        preData.setNtgPreOperation(data.getNtgPreOperation());
        preData.setSimdaxPreOperation(data.getSimdaxPreOperation());
        preData.setHeparinPreOperation(data.getHeparinPreOperation());
        preData.setSuprareninPreHlm(data.getSuprareninPreHlm());
        preData.setNorepinephrinPreHlm(data.getNorepinephrinPreHlm());
        preData.setVasopressinPreHlm(data.getVasopressinPreHlm());
        preData.setMilrinonPreHlm(data.getMilrinonPreHlm());
        preData.setNtgPreHlm(data.getNtgPreHlm());
        preData.setSimdaxPreHlm(data.getSimdaxPreHlm());

        // save updated entity
        operationRepository.save(operationDB);
    }
}
