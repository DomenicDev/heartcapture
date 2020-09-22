package de.cassisi.hearth.usecase;

import de.cassisi.hearth.dto.RecordParams;
import de.cassisi.hearth.dto.RecordSession;
import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.entity.InfusionData;
import de.cassisi.hearth.entity.NIRSData;
import de.cassisi.hearth.handler.OperationRecordDataHandler;
import de.cassisi.hearth.identifier.OperationId;
import de.cassisi.hearth.port.RecordingManager;

public class StartRecording {

    private RecordingManager recordingManager;
    private OperationRecordDataHandler operationRecordDataHandler;

    public StartRecording(RecordingManager recordingManager, OperationRecordDataHandler operationRecordDataHandler) {
        this.recordingManager = recordingManager;
        this.operationRecordDataHandler = operationRecordDataHandler;
    }

    public void startReoording(OperationId operationId, RecordParams recordParams) {
        RecordSession recordSession = recordingManager.setup(recordParams);
        recordSession.setNirsDataListener(data -> handle(operationId, data));
        recordSession.setInfusionDataListener(data -> handle(operationId,data));
        recordSession.setAnesthesiaDataListener(data -> handle(operationId, data));

        recordingManager.startRecording(recordSession);
    }

    private void handle(OperationId operationId, InfusionData infusionData) {
        operationRecordDataHandler.handle(operationId, infusionData);
    }

    private void handle(OperationId operationId, NIRSData nirsData) {
        operationRecordDataHandler.handle(operationId, nirsData);
    }

    private void handle(OperationId operationId, AnesthesiaData anesthesiaData) {
        operationRecordDataHandler.handle(operationId, anesthesiaData);
    }

}
