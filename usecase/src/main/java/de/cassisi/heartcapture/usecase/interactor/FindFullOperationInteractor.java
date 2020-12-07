package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.FindFullOperation;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.port.FindFullOperationRepository;
import de.cassisi.heartcapture.usecase.util.DTOConverter;
import de.cassisi.heartcapture.usecase.dto.CompleteOperationDataDTO;
import de.cassisi.heartcapture.usecase.validator.InputValidator;

public class FindFullOperationInteractor implements FindFullOperation {

    private final FindFullOperationRepository repository;

    public FindFullOperationInteractor(FindFullOperationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(InputData input, OutputHandler<CompleteOperationDataDTO> outputHandler) {
        long operationId = input.operationId;

        // validate input
        InputValidator.checkIdPositive(operationId);

        // find operation data
        FindFullOperationRepository.ResultData data = repository.getOperationData(operationId);

        // call output handler
        CompleteOperationDataDTO result = DTOConverter.toFullOperationData(data);
        outputHandler.handle(result);
    }
}
