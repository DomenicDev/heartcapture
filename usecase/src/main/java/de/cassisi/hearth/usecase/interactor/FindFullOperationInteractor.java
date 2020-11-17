package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.usecase.FindFullOperation;
import de.cassisi.hearth.usecase.dto.CompleteOperationDataDTO;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.FindFullOperationRepository;
import de.cassisi.hearth.usecase.util.DTOConverter;
import de.cassisi.hearth.usecase.validator.InputValidator;

import static de.cassisi.hearth.usecase.port.FindFullOperationRepository.ResultData;

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
        ResultData data = repository.getOperationData(operationId);

        // call output handler
        CompleteOperationDataDTO result = DTOConverter.toFullOperationData(data);
        outputHandler.handle(result);
    }
}
