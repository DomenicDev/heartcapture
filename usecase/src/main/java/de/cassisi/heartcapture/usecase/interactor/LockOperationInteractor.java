package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.LockOperation;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.port.LockOperationRepository;
import de.cassisi.heartcapture.usecase.util.DTOConverter;
import de.cassisi.heartcapture.entity.Operation;

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
