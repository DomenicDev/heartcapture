package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.utils.PresenterUtils;
import de.cassisi.hearth.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.hearth.usecase.dto.CompleteOperationDataDTO;
import org.springframework.stereotype.Component;

@Component
public class OperationViewPresenter extends UseCasePresenter<CompleteOperationDataDTO> {

    private final OperationOverviewViewModel viewModel;

    public OperationViewPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void runOnUI(CompleteOperationDataDTO data) {
        PresenterUtils.present(data, viewModel);
    }
}
