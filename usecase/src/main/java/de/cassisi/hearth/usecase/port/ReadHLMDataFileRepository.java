package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.HLMData;

public interface ReadHLMDataFileRepository {

    void saveHLMData(long operationId, HLMData data);

}
