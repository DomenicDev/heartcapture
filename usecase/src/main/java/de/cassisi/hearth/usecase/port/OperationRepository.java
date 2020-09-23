package de.cassisi.hearth.usecase.port;

import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.entity.ids.OperationId;

public interface OperationRepository {

    Operation createSession(Operation operationToSave);

    Operation findById(OperationId id);

    void addNIRSData(OperationId operationId, NIRSData nirsData);

    void addAnesthesiaData(OperationId operationId, AnesthesiaData anesthesiaData);

    void addInfusionData(OperationId operationId, InfusionData infusionData);

    void addHLMData(OperationId operationId, HLMData hlmData);

    Operation update(Operation operation);

}
