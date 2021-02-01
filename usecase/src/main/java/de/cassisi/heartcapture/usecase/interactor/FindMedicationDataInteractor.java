package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.entity.PreMedicationData;
import de.cassisi.heartcapture.usecase.FindMedicationData;
import de.cassisi.heartcapture.usecase.dto.PreMedicationDataDTO;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.port.FindMedicationDataRepository;
import de.cassisi.heartcapture.usecase.util.DTOConverter;
import de.cassisi.heartcapture.usecase.validator.InputValidator;
import lombok.NonNull;

public class FindMedicationDataInteractor implements FindMedicationData {

    private final FindMedicationDataRepository repository;

    public FindMedicationDataInteractor(FindMedicationDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(@NonNull InputData input, @NonNull OutputHandler<PreMedicationDataDTO> outputHandler) throws OperationNotFoundException {
        // extract input data
        long operationId = input.operationId;

        // validate input
        InputValidator.checkNotNegative(operationId);

        // find pre medication data
        PreMedicationData preMedicationData = repository.findPreMedicationData(operationId);

        // convert to dto and call output handler
        PreMedicationDataDTO output = DTOConverter.convert(operationId, preMedicationData);
        outputHandler.handle(output);
    }
}
