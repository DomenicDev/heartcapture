package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.repository.OperationRepository;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.port.FindOperationRepository;
import de.cassisi.hearth.util.DBConverter;
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
