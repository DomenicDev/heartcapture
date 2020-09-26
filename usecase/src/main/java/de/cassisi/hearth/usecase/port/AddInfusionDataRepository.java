package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.InfusionData;

public interface AddInfusionDataRepository {

    void addInfusionDataToOperation(long operationId, InfusionData infusionData);

}
