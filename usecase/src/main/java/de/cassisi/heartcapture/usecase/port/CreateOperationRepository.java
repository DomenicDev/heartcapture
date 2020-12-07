package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.Operation;

public interface CreateOperationRepository {

    Operation createOperation(Operation operationToSave);

}
