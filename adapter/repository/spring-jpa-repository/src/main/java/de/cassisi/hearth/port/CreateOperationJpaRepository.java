package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.repository.OperationRepository;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.port.CreateOperationRepository;
import de.cassisi.hearth.util.OperationConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class CreateOperationJpaRepository implements CreateOperationRepository {

    private final OperationRepository operationRepository;

    public CreateOperationJpaRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Operation createOperation(Operation operationToSave) {
        OperationDB operationDB = OperationConverter.toOperationDB(operationToSave);
        OperationDB savedEntity = operationRepository.save(operationDB);
        return OperationConverter.toOperation(savedEntity);
    }
}
