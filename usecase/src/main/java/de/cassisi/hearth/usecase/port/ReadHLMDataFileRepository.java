package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.HLMData;
import de.cassisi.hearth.entity.Operation;

public interface ReadHLMDataFileRepository {

    void saveHLMData(long operationId, HLMData data);

    Operation getOperation(long operationId);
}
