package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.repository.OperationRepository;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.port.LockOperationRepository;
import de.cassisi.hearth.util.DBConverter;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public class LockOperationJpaRepository implements LockOperationRepository {

    private final OperationRepository operationRepository;

    public LockOperationJpaRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    @Transactional
    public Operation setLocked(long operationId, boolean locked) throws OperationNotFoundException {
        OperationDB operationDB = operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));
        operationDB.setLocked(locked);
        OperationDB savedOperation = operationRepository.save(operationDB);
        return DBConverter.convert(savedOperation);
    }
}
