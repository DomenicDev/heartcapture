package de.cassisi.heartcapture.port;

import de.cassisi.heartcapture.entity.Operation;
import de.cassisi.heartcapture.port.repository.OperationRepository;
import de.cassisi.heartcapture.repository.model.OperationDB;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.port.LockOperationRepository;
import de.cassisi.heartcapture.port.util.DBConverter;
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
