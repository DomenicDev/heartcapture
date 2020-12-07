package de.cassisi.heartcapture.ui.presenter;

import de.cassisi.heartcapture.ui.utils.PresenterUtils;
import de.cassisi.heartcapture.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.heartcapture.usecase.dto.CompleteOperationDataDTO;
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
