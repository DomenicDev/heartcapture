package de.cassisi.heartcapture.port;

import de.cassisi.heartcapture.entity.Operation;
import de.cassisi.heartcapture.port.repository.OperationRepository;
import de.cassisi.heartcapture.repository.model.OperationDB;
import de.cassisi.heartcapture.usecase.port.CreateOperationRepository;
import de.cassisi.heartcapture.port.util.OperationConverter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CreateOperationJpaRepository implements CreateOperationRepository {

    private final OperationRepository operationRepository;

    public CreateOperationJpaRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public Operation createOperation(Operation operationToSave) {
        OperationDB operationDB = OperationConverter.toOperationDB(operationToSave);
        OperationDB savedEntity = operationRepository.save(operationDB);
        return OperationConverter.toOperation(savedEntity);
    }
}
