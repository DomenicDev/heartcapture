package de.cassisi.heartcapture.usecase;

import de.cassisi.heartcapture.usecase.dto.SimpleOperationData;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.exception.OperationLockException;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.exception.ReadHLMFileException;
import de.cassisi.heartcapture.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import java.io.File;

import static de.cassisi.heartcapture.usecase.ReadHLMDataFile.*;

public interface ReadHLMDataFile extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public long operationId;
        public File hlmFile;
    }

    class OutputData {
        public SimpleOperationData simpleOperationData;
    }

    @Override
    void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler) throws
            OperationNotFoundException,
            ReadHLMFileException,
            OperationLockException;
}
