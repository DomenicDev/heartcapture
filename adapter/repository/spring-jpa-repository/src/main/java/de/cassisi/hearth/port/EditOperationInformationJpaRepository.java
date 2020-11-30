package de.cassisi.hearth.port;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.repository.OperationRepository;
import de.cassisi.hearth.repository.model.OperationDB;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.port.EditOperationInformationRepository;
import de.cassisi.hearth.util.DBConverter;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Repository
public class EditOperationInformationJpaRepository implements EditOperationInformationRepository {

    private final OperationRepository operationRepository;

    public EditOperationInformationJpaRepository(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public boolean isLocked(long operationId) throws OperationNotFoundException {
        return operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId)).isLocked();
    }

    @Override
    @Transactional
    public Operation saveOperationInformation(long operationId, LocalDate operationDate, String operationRoom) throws OperationNotFoundException {
        OperationDB operationDB = operationRepository.findById(operationId).orElseThrow(() -> new OperationNotFoundException(operationId));
        operationDB.setDate(operationDate);
        operationDB.setRoomNr(operationRoom);
        OperationDB updatedOperation = operationRepository.save(operationDB);
        return DBConverter.convert(updatedOperation);
    }
}
