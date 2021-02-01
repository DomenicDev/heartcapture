package de.cassisi.heartcapture.usecase;

import de.cassisi.heartcapture.usecase.dto.PreMedicationDataDTO;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.template.UseCaseTemplate;
import lombok.NonNull;

public interface FindMedicationData extends UseCaseTemplate<FindMedicationData.InputData, PreMedicationDataDTO> {

    class InputData {
        public long operationId;
    }

    @Override
    void execute(@NonNull InputData input, @NonNull OutputHandler<PreMedicationDataDTO> outputHandler) throws OperationNotFoundException;
}
