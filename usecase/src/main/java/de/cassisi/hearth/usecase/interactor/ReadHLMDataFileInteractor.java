package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.HLMData;
import de.cassisi.hearth.entity.Operation;
import de.cassisi.hearth.usecase.ReadHLMDataFile;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.HLMFileReader;
import de.cassisi.hearth.usecase.port.ReadHLMDataFileRepository;
import de.cassisi.hearth.usecase.util.DTOConverter;
import de.cassisi.hearth.usecase.util.OperationUtils;
import de.cassisi.hearth.usecase.validator.InputValidator;

import java.io.File;

public class ReadHLMDataFileInteractor implements ReadHLMDataFile {

    private final ReadHLMDataFileRepository repository;
    private final HLMFileReader hlmFileReader;

    public ReadHLMDataFileInteractor(ReadHLMDataFileRepository repository, HLMFileReader hlmFileReader) {
        this.repository = repository;
        this.hlmFileReader = hlmFileReader;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        // get input data
        long operationId  = input.operationId;
        File file = input.hlmFile;

        // validate input
        InputValidator.checkIdPositive(operationId);
        InputValidator.checkNotNull(file, "HLM File");

        // check if locked
        OperationUtils.throwIfLocked(operationId, repository.isLocked(operationId));

        // convert file to hlm data
        HLMData hlmData = hlmFileReader.readHLMFile(file);

        // save hlm data to repository
        repository.saveHLMData(operationId, hlmData);

        Operation operation = repository.getOperation(operationId);

        // callback output handler
        OutputData outputData = new OutputData();
        outputData.simpleOperationData = DTOConverter.toSimpleOperationData(operation);
        outputHandler.handle(outputData);
    }
}
