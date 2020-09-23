package de.cassisi.hearth.usecase.impl;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.CreateOperation;
import de.cassisi.hearth.usecase.dto.SessionCreateRequest;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.CreateOperationRepository;
import de.cassisi.hearth.usecase.validator.SessionCreateRequestValidator;

import java.time.LocalDate;

public class CreateOperationInteractor implements CreateOperation {

    private CreateOperationRepository operationRepository;

    public CreateOperationInteractor(CreateOperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        // extract and validate input
        LocalDate date = input.localDate;
        String room = input.room;

        // create new operation entity based on input values
        Operation operationToSave = new Operation(
                null,
                date,
                room,
                false,
                false,
                false,
                false,
                false);

        // persist operation and gather result
        Operation savedOperation = operationRepository.createOperation(operationToSave);

        //generate output
        OutputData data = new OutputData();
        data.result = "Successfully generated entity with id: " + savedOperation.getId() + "!";
        outputHandler.handle(data);
    }
}
