package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import de.cassisi.hearth.usecase.exception.OperationLockException;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.exception.ReadHLMFileException;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import java.io.File;

import static de.cassisi.hearth.usecase.ReadHLMDataFile.*;

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
