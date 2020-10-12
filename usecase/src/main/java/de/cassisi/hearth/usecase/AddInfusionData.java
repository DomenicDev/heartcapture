package de.cassisi.hearth.usecase;

import de.cassisi.hearth.usecase.template.UseCaseTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static de.cassisi.hearth.usecase.AddInfusionData.*;

public interface AddInfusionData extends UseCaseTemplate<InputData, OutputData> {


    class InputData {
        public long operationId;
        public LocalDateTime timestamp;
        public List<PerfusorInput> infusionData = new ArrayList<>();

        public static class PerfusorInput {
            public String name;
            public int rate;

            public PerfusorInput() {
            }
            public PerfusorInput(String name, int rate) {
                this.name = name;
                this.rate = rate;
            }
        }
    }

    class OutputData {

    }

}
