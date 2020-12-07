package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.util.DTOConverter;
import de.cassisi.heartcapture.entity.Operation;
import de.cassisi.heartcapture.usecase.EditOperationInformation;
import de.cassisi.heartcapture.usecase.exception.InputValidationException;
import de.cassisi.heartcapture.usecase.exception.OperationLockException;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.port.EditOperationInformationRepository;
import de.cassisi.heartcapture.usecase.util.OperationUtils;
import de.cassisi.heartcapture.usecase.validator.InputValidator;
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
