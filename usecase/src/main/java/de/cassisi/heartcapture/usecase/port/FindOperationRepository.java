package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.Operation;

public interface FindOperationRepository {

    Operation findOperationById(long id);

}
