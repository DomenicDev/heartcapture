package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.LockOperation;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.LockOperationRepository;
import de.cassisi.hearth.usecase.util.DTOConverter;

public class LockOperationInteractor implements LockOperation {

    private final LockOperationRepository lockOperationRepository;

    public LockOperationInteractor(LockOperationRepository lockOperationRepository) {
        this.lockOperationRepository = lockOperationRepository;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        long operationId = input.operationId;
        boolean locked = input.locked;

        // load operation
        Operation operation = lockOperationRepository.setLocked(operationId, locked);

        // prepare data for presentation and callback output handler
        OutputData outputData = new OutputData();
        outputData.operationData = DTOConverter.toSimpleOperationData(operation);
        outputHandler.handle(outputData);
    }
}
