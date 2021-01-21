package de.cassisi.heartcapture.usecase;

import de.cassisi.heartcapture.usecase.exception.OperationLockException;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.template.UseCaseTemplate;
import lombok.NonNull;

import static de.cassisi.heartcapture.usecase.UpdatePreMedicationData.*;

public interface UpdatePreMedicationData extends UseCaseTemplate<InputData, OutputData> {
    
    class InputData {
        public long operationId;

        public double suprareninPreOperation;
        public double norepinephrinPreOperation;
        public double vasopressinPreOperation;
        public double milrinonPreOperation;
        public double ntgPreOperation;
        public double simdaxPreOperation;
        public double heparinPreOperation;

        public double suprareninPreHlm;
        public double norepinephrinPreHlm;
        public double vasopressinPreHlm;
        public double milrinonPreHlm;
        public double ntgPreHlm;
        public double simdaxPreHlm;
    }

    class OutputData {

    }

    @Override
    void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler) throws OperationNotFoundException, OperationLockException;
}
