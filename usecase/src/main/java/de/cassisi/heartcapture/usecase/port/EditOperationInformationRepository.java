package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.Operation;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;

import java.time.LocalDate;

public interface EditOperationInformationRepository {

    boolean isLocked(long operationId) throws OperationNotFoundException;

    Operation saveOperationInformation(long operationId, LocalDate operationDate, String operationRoom) throws OperationNotFoundException;

}
