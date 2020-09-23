package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.repository.OperationJpaRepository;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.port.CreateOperationRepository;
import de.cassisi.hearth.util.OperationConverter;
import org.springframework.stereotype.Component;

@Component
public class CreateOperationJpaRepository implements CreateOperationRepository {

    private final OperationJpaRepository operationJpaRepository;

public CreateOperationJpaRepository(OperationJpaRepository operationJpaRepository) {
        this.operationJpaRepository = operationJpaRepository;
    }

    @Override
    public Operation createOperation(Operation operationToSave) {
        OperationDB operationDB = OperationConverter.toOperationDB(operationToSave);
        OperationDB savedEntity = operationJpaRepository.save(operationDB);
        return OperationConverter.toOperation(savedEntity);
    }
}
