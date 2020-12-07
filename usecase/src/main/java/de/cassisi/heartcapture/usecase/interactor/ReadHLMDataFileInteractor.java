package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.usecase.ReadHLMDataFile;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.port.HLMFileReader;
import de.cassisi.heartcapture.usecase.port.ReadHLMDataFileRepository;
import de.cassisi.heartcapture.usecase.util.DTOConverter;
import de.cassisi.heartcapture.usecase.util.OperationUtils;
import de.cassisi.heartcapture.usecase.validator.InputValidator;
import de.cassisi.heartcapture.entity.HLMData;
import de.cassisi.heartcapture.entity.Operation;

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
