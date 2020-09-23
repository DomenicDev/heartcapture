package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.dto.SessionCreateRequest;
import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.port.CreateOperationRepository;
import de.cassisi.hearth.usecase.validator.SessionCreateRequestValidator;

public class CreateOperationOld {

    private CreateOperationRepository operationRepository;

    public CreateOperationOld(CreateOperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public Operation createOperation(SessionCreateRequest request) {
        SessionCreateRequestValidator.validate(request);
        Operation operationToSave = new Operation(
                null,
                request.getDate(),
                request.getRoom(),
                false,
                false,
                false,
                false,
                false);

        return operationRepository.createOperation(operationToSave);
    }

}
