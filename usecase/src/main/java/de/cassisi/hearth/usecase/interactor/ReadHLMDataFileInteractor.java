package de.cassisi.hearth.usecase.interactor;

import de.cassisi.hearth.entity.HLMData;
import de.cassisi.hearth.usecase.ReadHLMDataFile;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.port.HLMFileReader;
import de.cassisi.hearth.usecase.port.ReadHLMDataFileRepository;

import java.io.File;

public class ReadHLMDataFileInteractor implements ReadHLMDataFile {

    private ReadHLMDataFileRepository repository;
    private HLMFileReader hlmFileReader;

    public ReadHLMDataFileInteractor(ReadHLMDataFileRepository repository, HLMFileReader hlmFileReader) {
        this.repository = repository;
        this.hlmFileReader = hlmFileReader;
    }

    @Override
    public void execute(InputData input, OutputHandler<OutputData> outputHandler) {
        // get input data
        long operationId  = input.operationId;
        File file = input.hlmFile;

        // validate file etc. todo
        // ...


        // convert file to hlm data
        HLMData hlmData = hlmFileReader.readHLMFile(file);

        // save hlm data to repository
        repository.saveHLMData(operationId, hlmData);

        // callback output handler
        OutputData outputData = new OutputData();
        outputHandler.handle(outputData);
    }
}
