package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.AnesthesiaData;
import de.cassisi.hearth.usecase.AddAnesthesiaData;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.AddAnesthesiaDataRepository;
import de.cassisi.hearth.usecase.validator.InputValidator;

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
