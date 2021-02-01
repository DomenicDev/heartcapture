package de.cassisi.heartcapture.ui.presenter;

import de.cassisi.heartcapture.ui.view.premedication.PreMedicationViewModel;
import de.cassisi.heartcapture.usecase.dto.PreMedicationDataDTO;
import org.springframework.stereotype.Component;

@Component
public class FindMedicationDataPresenter extends UseCasePresenter<PreMedicationDataDTO> {

    private final PreMedicationViewModel viewModel;

    public FindMedicationDataPresenter(PreMedicationViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void runOnUI(PreMedicationDataDTO data) {
        // operation id
        viewModel.getOperationIdProperty().set(data.getOperationId());

        // pre operation
        viewModel.getSuprareninPreOperationProperty().set(data.getSuprareninPreOperation());
        viewModel.getNorepinephrinPreOperationProperty().set(data.getNorepinephrinPreOperation());
        viewModel.getVasopressinPreOperationProperty().set(data.getVasopressinPreOperation());
        viewModel.getMilrinonPreOperationProperty().set(data.getMilrinonPreOperation());
        viewModel.getNtgPreOperationProperty().set(data.getNtgPreOperation());
        viewModel.getSimdaxPreOperationProperty().set(data.getSimdaxPreOperation());
        viewModel.getHeparinPreOperationProperty().set(data.getHeparinPreOperation());

        // pre hlm
        viewModel.getSuprareninPreHlmProperty().set(data.getSuprareninPreHlm());
        viewModel.getNorepinephrinPreHlmProperty().set(data.getNorepinephrinPreHlm());
        viewModel.getVasopressinPreHlmProperty().set(data.getVasopressinPreHlm());
        viewModel.getMilrinonPreHlmProperty().set(data.getMilrinonPreHlm());
        viewModel.getNtgPreHlmProperty().set(data.getNtgPreHlm());
        viewModel.getSimdaxPreHlmProperty().set(data.getSimdaxPreHlm());
    }
}
