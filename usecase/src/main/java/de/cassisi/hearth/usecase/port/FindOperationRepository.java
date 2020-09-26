package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.Operation;

public interface FindOperationRepository {

    Operation findOperationById(long id);

}
