package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.FindOperation;
import de.cassisi.heartcapture.usecase.dto.SimpleOperationData;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.port.FindOperationRepository;
import de.cassisi.heartcapture.usecase.util.DTOConverter;
import de.cassisi.heartcapture.usecase.validator.InputValidator;
import de.cassisi.heartcapture.entity.Operation;

public class FindOperationInteractor implements FindOperation {

    private final FindOperationRepository findOperationRepository;

    public FindOperationInteractor(FindOperationRepository findOperationRepository) {
        this.findOperationRepository = findOperationRepository;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        long id = input.operationId;

        // validate input
        InputValidator.checkIdPositive(id);

        // get operation data
        Operation operation = findOperationRepository.findOperationById(id);

        // repackage for presentation
        SimpleOperationData operationData = DTOConverter.toSimpleOperationData(operation);

        OutputData outputData = new OutputData();
        outputData.operationData = operationData;

        // callback output handler
        outputHandler.handle(outputData);
    }
}
