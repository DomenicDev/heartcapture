package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.Operation;

public interface LockOperationRepository {

    Operation findById(long id);

    Operation save(Operation operation);

}
