package de.cassisi.hearth.usecase.handler.impl;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.entity.NIRSData;
import de.cassisi.hearth.usecase.handler.OperationRecordDataHandler;
import de.cassisi.hearth.entity.ids.OperationId;
import de.cassisi.hearth.usecase.port.OperationRepository;

public class DatabaseWriterOperationRecordHandler implements OperationRecordDataHandler {

    private OperationRepository operationRepository;

    public DatabaseWriterOperationRecordHandler(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public void handle(OperationId operationId, InfusionData infusionData) {
        operationRepository.addInfusionData(operationId, infusionData);
    }

    @Override
    public void handle(OperationId operationId, AnesthesiaData anesthesiaData) {
        operationRepository.addAnesthesiaData(operationId, anesthesiaData);
    }

    @Override
    public void handle(OperationId operationId, NIRSData nirsData) {
        operationRepository.addNIRSData(operationId, nirsData);
    }
}
