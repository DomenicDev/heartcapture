package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.FindOperation;
import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.FindOperationRepository;
import de.cassisi.hearth.usecase.validator.InputValidator;

public class FindOperationInteractor implements FindOperation {

    private FindOperationRepository findOperationRepository;

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
        SimpleOperationData operationData = new SimpleOperationData(operation.getId(), operation.getDate(), operation.getRoomNr());

        OutputData outputData = new OutputData();
        outputData.operationData = operationData;

        // callback output handler
        outputHandler.handle(outputData);
    }
}
