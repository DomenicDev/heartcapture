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
            public double rate;

            public PerfusorInput() {
            }
            public PerfusorInput(String name, double rate) {
                this.name = name;
                this.rate = rate;
            }
        }
    }

    class OutputData {

    }

}
