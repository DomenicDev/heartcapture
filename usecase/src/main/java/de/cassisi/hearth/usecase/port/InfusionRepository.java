package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.entity.ids.OperationId;

import java.util.List;

public interface InfusionRepository {

    void addInfusionDataToOperation(OperationId operationId, InfusionData infusionData);

    List<InfusionData> getInfusionDataFromOperation(OperationId operationId);

}
