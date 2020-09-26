package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.template.UseCaseTemplate;

import java.io.File;

import static de.cassisi.hearth.usecase.GenerateReport.*;

public interface GenerateReport extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public long operationId;
    }

    class OutputData {
        public File reportFile;
    }

}
