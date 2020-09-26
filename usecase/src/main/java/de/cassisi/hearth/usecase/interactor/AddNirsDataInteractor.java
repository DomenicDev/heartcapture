package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.NIRSData;
import de.cassisi.hearth.usecase.AddNirsData;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.AddNirsDataRepository;

import java.time.LocalDateTime;

public class AddNirsDataInteractor implements AddNirsData {

    private AddNirsDataRepository repository;

    public AddNirsDataInteractor(AddNirsDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        // extract input data
        long operationId = input.operationId;
        double left = input.leftSaturation;
        double right = input.rightSaturation;
        LocalDateTime timestamp = input.timestamp;
        // validate input data (todo)

        // repackage data
        NIRSData nirsData = new NIRSData(left, right, timestamp);

        // save in repository
        repository.addNirsDataToOperation(operationId, nirsData);

        // callback output handler
        OutputData outputData = new OutputData();
        outputData.saved = true;
        outputHandler.handle(outputData);
    }
}
