package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.EditOperationInformation;
import de.cassisi.hearth.usecase.exception.InputValidationException;
import de.cassisi.hearth.usecase.exception.OperationLockException;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.EditOperationInformationRepository;
import de.cassisi.hearth.usecase.util.DTOConverter;
import de.cassisi.hearth.usecase.util.OperationUtils;
import de.cassisi.hearth.usecase.validator.InputValidator;
import lombok.NonNull;

import java.time.LocalDate;

public class EditOperationInformationInteractor implements EditOperationInformation {

    private final EditOperationInformationRepository repository;

    public EditOperationInformationInteractor(EditOperationInformationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler) throws InputValidationException, OperationNotFoundException, OperationLockException {
        // extract input data
        long operationId = input.operationId;
        LocalDate operationDate = input.operationDate;
        String operationRoom = input.operationRoom;

        // validate input data
        InputValidator.checkIdPositive(operationId);
        InputValidator.checkNotNull(operationDate, "operation date");
        InputValidator.checkNotNullOrBlank(operationRoom);

        // check if locked
        OperationUtils.throwIfLocked(operationId, repository.isLocked(operationId));

        // save changes to operation
        Operation updatedOperation = repository.saveOperationInformation(operationId, operationDate, operationRoom);

        // call output handler
        OutputData outputData = new OutputData();
        outputData.operationData = DTOConverter.toSimpleOperationData(updatedOperation);
        outputHandler.handle(outputData);
    }
}
