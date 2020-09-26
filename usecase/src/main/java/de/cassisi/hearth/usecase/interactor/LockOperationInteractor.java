package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.LockOperation;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.LockOperationRepository;
import de.cassisi.hearth.usecase.util.DTOConverter;

public class LockOperationInteractor implements LockOperation {

    private LockOperationRepository lockOperationRepository;

    public LockOperationInteractor(LockOperationRepository lockOperationRepository) {
        this.lockOperationRepository = lockOperationRepository;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        long operationId = input.operationId;

        // load operation
        Operation operation = lockOperationRepository.findById(operationId);

        // lock operation by setting attribute
        // the following could be done easier by using a builder pattern with a from(operation) method
        Operation operationToSave = new Operation(
                operation.getId(),
                operation.getDate(),
                operation.getRoomNr(),
                operation.isNirsDataAvailable(),
                operation.isInfusionDataAvailable(),
                operation.isAnesthesiaDataAvailable(),
                operation.isHlmDataAvailable(),
                true);

        // save updated operation
        Operation savedOperation = lockOperationRepository.save(operationToSave);

        // prepare data for presentation and callback output handler
        OutputData outputData = new OutputData();
        outputData.operationData = DTOConverter.toSimpleOperationData(savedOperation);
        outputHandler.handle(outputData);
    }
}
