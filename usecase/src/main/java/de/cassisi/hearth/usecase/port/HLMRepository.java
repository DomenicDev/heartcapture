package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.HLMData;
import de.cassisi.hearth.entity.ids.OperationId;

public interface HLMRepository {

    void store(HLMData hlmData);

    HLMData findByOperation(OperationId operationId);
}
