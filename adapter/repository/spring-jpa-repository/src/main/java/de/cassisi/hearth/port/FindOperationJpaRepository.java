package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.repository.OperationRepository;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.port.FindOperationRepository;

import java.util.Optional;

public class FindOperationJpaRepository implements FindOperationRepository {

    private OperationRepository operationRepository;

    public FindOperationJpaRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Operation findOperationById(long id) {
        Optional<OperationDB> optOperationDB = operationRepository.findById(id);
        if (optOperationDB.isPresent()) {
            OperationDB dvOperation = optOperationDB.get();
            Operation operationEntity = null;
            return operationEntity;
        } else {
            throw new OperationNotFoundException(id);
        }
    }
}
