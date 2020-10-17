package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.NIRSData;
import de.cassisi.hearth.usecase.AddNirsData;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.AddNirsDataRepository;
import de.cassisi.hearth.usecase.validator.InputValidator;

import java.time.LocalDateTime;

public class AddNirsDataInteractor implements AddNirsData {

    private final AddNirsDataRepository repository;

    public AddNirsDataInteractor(AddNirsDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        // extract input data
        long operationId = input.operationId;
        int left = input.leftSaturation;
        int  right = input.rightSaturation;
        LocalDateTime timestamp = input.timestamp;

        // validate input data
        InputValidator.checkIdPositive(operationId);
        InputValidator.checkTimestamp(timestamp);
        InputValidator.checkNirsSaturation(left);
        InputValidator.checkNirsSaturation(right);

        // repackage data
        NIRSData nirsData = new NIRSData(left, right, timestamp);

        // save in repository
        repository.addNirsDataToOperation(operationId, nirsData);

        // callback output handler
        OutputData outputData = new OutputData();
        outputData.saved = true;
        outputData.left = left;
        outputData.right = right;
        outputHandler.handle(outputData);
    }
}
