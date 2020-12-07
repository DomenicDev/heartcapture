package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.AddNirsData;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.port.AddNirsDataRepository;
import de.cassisi.heartcapture.usecase.util.OperationUtils;
import de.cassisi.heartcapture.usecase.validator.InputValidator;
import de.cassisi.heartcapture.entity.NIRSData;

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

        // check if operation is locked
        OperationUtils.throwIfLocked(operationId, repository.isLocked(operationId));

        // repackage data
        NIRSData nirsData = new NIRSData(timestamp, left, right);

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
