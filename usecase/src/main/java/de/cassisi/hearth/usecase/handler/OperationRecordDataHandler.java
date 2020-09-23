package de.cassisi.hearth.usecase.handler;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.entity.NIRSData;
import de.cassisi.hearth.entity.ids.OperationId;

public interface OperationRecordDataHandler {

    void handle(OperationId operationId, InfusionData infusionData);

    void handle(OperationId operationId, AnesthesiaData anesthesiaData);

    void handle(OperationId operationId, NIRSData nirsData);

}
