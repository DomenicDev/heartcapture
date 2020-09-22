package de.cassisi.hearth.usecase;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.identifier.OperationId;
import de.cassisi.hearth.port.OperationRepository;

public class LockOperation {

    private OperationRepository operationRepository;

    public LockOperation(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public void lockOperation(OperationId operationId) {
        // make some validation beforehand?
        Operation operation = operationRepository.findById(operationId);

        // the following could be done easier by using a builder pattern with a from(operation) method
        Operation operationToSave = new Operation(
                operationId.getId(),
                operation.getDate(),
                operation.getRoomNr(),
                operation.isNirsDataAvailable(),
                operation.isInfusionDataAvailable(),
                operation.isAnesthesiaDataAvailable(),
                operation.isHlmDataAvailable(),
                true);

        // update entry in repository
        operationRepository.update(operationToSave);
    }

}
