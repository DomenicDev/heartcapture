package de.cassisi.heartcapture.usecase.port;

import de.cassisi.heartcapture.entity.HLMData;
import de.cassisi.heartcapture.entity.Operation;

public interface ReadHLMDataFileRepository {

    void saveHLMData(long operationId, HLMData data);

    Operation getOperation(long operationId);

    boolean isLocked(long operationId);

}
