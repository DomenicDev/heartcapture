package de.cassisi.hearth.usecase;

import de.cassisi.hearth.dto.SessionCreateRequest;
import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.port.OperationRepository;
import de.cassisi.hearth.validator.SessionCreateRequestValidator;

public class CreateSession {

    private OperationRepository operationRepository;

    public CreateSession(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public Operation createSession(SessionCreateRequest request) {
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

        return operationRepository.createSession(operationToSave);
    }

}
