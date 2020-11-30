package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;

import java.time.LocalDate;

public interface EditOperationInformationRepository {

    boolean isLocked(long operationId) throws OperationNotFoundException;

    Operation saveOperationInformation(long operationId, LocalDate operationDate, String operationRoom) throws OperationNotFoundException;

}
