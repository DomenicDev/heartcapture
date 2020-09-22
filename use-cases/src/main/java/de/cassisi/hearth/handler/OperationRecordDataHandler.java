package de.cassisi.hearth.handler;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.entity.NIRSData;
import de.cassisi.hearth.identifier.OperationId;

public interface OperationRecordDataHandler {

    void handle(OperationId operationId, InfusionData infusionData);

    void handle(OperationId operationId, AnesthesiaData anesthesiaData);

    void handle(OperationId operationId, NIRSData nirsData);

}
