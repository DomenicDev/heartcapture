package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import de.cassisi.hearth.usecase.template.UseCaseTemplate;

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

}
