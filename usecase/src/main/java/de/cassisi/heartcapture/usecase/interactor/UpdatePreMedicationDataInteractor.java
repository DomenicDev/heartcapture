package de.cassisi.heartcapture.usecase.interactor;

import de.cassisi.heartcapture.entity.PreMedicationData;
import de.cassisi.heartcapture.usecase.UpdatePreMedicationData;
import de.cassisi.heartcapture.usecase.exception.OperationLockException;
import de.cassisi.heartcapture.usecase.exception.OperationNotFoundException;
import de.cassisi.heartcapture.usecase.output.OutputHandler;
import de.cassisi.heartcapture.usecase.port.UpdatePreHlmMedicationDataRepository;
import de.cassisi.heartcapture.usecase.validator.InputValidator;
import lombok.NonNull;

public class UpdatePreMedicationDataInteractor implements UpdatePreMedicationData {

    private final UpdatePreHlmMedicationDataRepository repository;

    public UpdatePreMedicationDataInteractor(UpdatePreHlmMedicationDataRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(@NonNull InputData input, @NonNull OutputHandler<OutputData> outputHandler) throws OperationNotFoundException, OperationLockException {
        // extract input
        double suprareninPreOperation = input.suprareninPreOperation;
        double norepinephrinPreOperation = input.norepinephrinPreOperation;
        double vasopressinPreOperation = input.vasopressinPreOperation;
        double milrinonPreOperation = input.milrinonPreOperation;
        double ntgPreOperation = input.ntgPreOperation;
        double simdaxPreOperation = input.simdaxPreOperation;
        double heparinPreOperation = input.heparinPreOperation;

        double suprareninPreHlm = input.suprareninPreHlm;
        double norepinephrinPreHlm = input.norepinephrinPreHlm;
        double vasopressinPreHlm = input.vasopressinPreHlm;
        double milrinonPreHlm = input.milrinonPreHlm;
        double ntgPreHlm = input.ntgPreHlm;
        double simdaxPreHlm = input.simdaxPreHlm;

        long operationId = input.operationId;

        // validate input
        InputValidator.checkNotNegative(suprareninPreOperation);
        InputValidator.checkNotNegative(norepinephrinPreOperation);
        InputValidator.checkNotNegative(vasopressinPreOperation);
        InputValidator.checkNotNegative(milrinonPreOperation);
        InputValidator.checkNotNegative(ntgPreOperation);
        InputValidator.checkNotNegative(simdaxPreOperation);
        InputValidator.checkNotNegative(heparinPreOperation);

        InputValidator.checkNotNegative(suprareninPreHlm);
        InputValidator.checkNotNegative(norepinephrinPreHlm);
        InputValidator.checkNotNegative(vasopressinPreHlm);
        InputValidator.checkNotNegative(milrinonPreHlm);
        InputValidator.checkNotNegative(ntgPreHlm);
        InputValidator.checkNotNegative(simdaxPreHlm);

        // create entity object
        PreMedicationData data = new PreMedicationData(
                suprareninPreOperation,
                norepinephrinPreOperation,
                vasopressinPreOperation,
                milrinonPreOperation,
                ntgPreOperation,
                simdaxPreOperation,
                heparinPreOperation,

                suprareninPreHlm,
                norepinephrinPreHlm,
                vasopressinPreHlm,
                milrinonPreHlm,
                ntgPreHlm,
                simdaxPreHlm
        );

        // save in repository
        repository.updatePreHlmMedicationData(operationId, data);

        // call output handler
        OutputData outputData = new OutputData();
        outputHandler.handle(outputData);
    }
}
