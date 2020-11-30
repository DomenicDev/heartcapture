package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.exception.MissingHlmFileException;
import de.cassisi.hearth.usecase.exception.OperationNotFoundException;
import de.cassisi.hearth.usecase.exception.ReportGenerationException;
import de.cassisi.hearth.usecase.output.OutputHandler;
import de.cassisi.hearth.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import java.io.File;

import static de.cassisi.hearth.usecase.GenerateReport.*;

public interface GenerateReport extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public long operationId;
    }

    class OutputData {
        public byte[] reportFile;
    }

    @Override
    void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler) throws
            OperationNotFoundException,
            MissingHlmFileException,
            ReportGenerationException;
}
