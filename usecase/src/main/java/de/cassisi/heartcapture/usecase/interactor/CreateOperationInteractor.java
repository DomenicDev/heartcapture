package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.CreateOperation;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.port.CreateOperationRepository;
import de.cassisi.heartcapture.usecase.util.DTOConverter;
import de.cassisi.heartcapture.usecase.validator.InputValidator;
import de.cassisi.heartcapture.entity.Operation;

import java.time.LocalDate;

public class CreateOperationInteractor implements CreateOperation {

    private final CreateOperationRepository operationRepository;

    public CreateOperationInteractor(CreateOperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        // extract and validate input
        LocalDate date = input.localDate;
        String room = input.room;

        InputValidator.checkNotNull(date, "operation name");
        InputValidator.checkNotNull(room, "operation room");

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
        data.operationData = DTOConverter.toFullOperationData(savedOperation);
        outputHandler.handle(data);
    }
}
