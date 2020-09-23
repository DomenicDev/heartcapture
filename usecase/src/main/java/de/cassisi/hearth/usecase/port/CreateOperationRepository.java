package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.Operation;

public interface CreateOperationRepository {

    Operation createOperation(Operation operationToSave);

}
