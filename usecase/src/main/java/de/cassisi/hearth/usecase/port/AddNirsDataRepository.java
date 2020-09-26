package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.NIRSData;

public interface AddNirsDataRepository {

    void addNirsDataToOperation(long operationId, NIRSData nirsData);

}
