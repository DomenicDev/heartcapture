package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.AddAnesthesiaData;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.port.AddAnesthesiaDataRepository;
import de.cassisi.heartcapture.usecase.util.OperationUtils;
import de.cassisi.heartcapture.usecase.validator.InputValidator;
import de.cassisi.heartcapture.entity.AnesthesiaData;

import java.time.LocalDateTime;

public class AddAnesthesiaDataInteractor implements AddAnesthesiaData {

    private final AddAnesthesiaDataRepository repository;

    public AddAnesthesiaDataInteractor(AddAnesthesiaDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        // extract input data
        long operationId = input.operationId;
        LocalDateTime timestamp = input.timestamp;
        double depthOfAnesthesia = input.depthOfAnesthesia;

        // validate input
        InputValidator.checkIdPositive(operationId);
        InputValidator.checkTimestamp(timestamp);
        InputValidator.checkDepthOfAnesthesia(depthOfAnesthesia);

        // check if operation is locked
        OperationUtils.throwIfLocked(operationId, repository.isLocked(operationId));

        // create anesthesia entity
        AnesthesiaData anesthesiaData = new AnesthesiaData(timestamp, depthOfAnesthesia);
        repository.addAnesthesiaData(operationId, anesthesiaData);

        // callback output handler
        OutputData data = new OutputData();
        data.operationId = operationId;
        data.depthOfAnesthesia = depthOfAnesthesia;
        data.timestamp = timestamp;
        outputHandler.handle(data);
    }
}
