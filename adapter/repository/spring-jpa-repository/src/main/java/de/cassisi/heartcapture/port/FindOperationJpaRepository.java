package de.cassisi.heartcapture.port;

import de.cassisi.heartcapture.entity.Operation;
import de.cassisi.heartcapture.port.repository.OperationRepository;
import de.cassisi.heartcapture.repository.model.OperationDB;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.port.FindOperationRepository;
import de.cassisi.heartcapture.port.util.DBConverter;
import org.springframework.stereotype.Repository;


@Repository
public class FindOperationJpaRepository implements FindOperationRepository {

    private final OperationRepository operationRepository;

    public FindOperationJpaRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Operation findOperationById(long id) {
        OperationDB dbOperation = operationRepository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));
        return DBConverter.convert(dbOperation);
    }


}
