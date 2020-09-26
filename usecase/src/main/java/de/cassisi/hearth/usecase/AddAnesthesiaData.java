package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.template.UseCaseTemplate;

import java.time.LocalDateTime;

import static de.cassisi.hearth.usecase.AddAnesthesiaData.*;

public interface AddAnesthesiaData extends UseCaseTemplate<InputData, OutputData> {

    class InputData {
        public long operationId;
        public LocalDateTime timestamp;
        public double depthOfAnesthesia;
    }

    class OutputData {

    }

}
