package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.repository.OperationRepository;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.port.FindOperationRepository;
import org.springframework.stereotype.Repository;


@Repository
public class FindOperationJpaRepository implements FindOperationRepository {

    private OperationRepository operationRepository;

    public FindOperationJpaRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public Operation findOperationById(long id) {
        OperationDB dbOperation = operationRepository.findById(id).orElseThrow(() -> new OperationNotFoundException(id));
        return convert(dbOperation);
    }

    private Operation convert(OperationDB operationDB) {
        return new Operation(
                operationDB.getId(),
                operationDB.getDate(),
                operationDB.getRoomNr(),
                operationDB.isNirsDataAvailable(),
                operationDB.isInfusionDataAvailable(),
                operationDB.isAnesthesiaDataAvailable(),
                operationDB.isHlmDataAvailable(),
                operationDB.isLocked()
        );
    }
}
