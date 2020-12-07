package de.cassisi.heartcapture.usecase;

import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.exception.MissingHlmFileException;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.exception.ReportGenerationException;
import de.cassisi.heartcapture.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import static de.cassisi.heartcapture.usecase.GenerateReport.*;

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
