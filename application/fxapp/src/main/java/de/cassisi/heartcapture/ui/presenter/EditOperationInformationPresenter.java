package de.cassisi.heartcapture.ui.presenter;

import de.cassisi.heartcapture.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.heartcapture.ui.utils.PresenterUtils;
import org.springframework.stereotype.Component;

import static de.cassisi.heartcapture.usecase.EditOperationInformation.OutputData;

@Component
public class EditOperationInformationPresenter extends UseCasePresenter<OutputData> {

    private final OperationOverviewViewModel viewModel;

    public EditOperationInformationPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void runOnUI(OutputData data) {
        PresenterUtils.present(data.operationData, viewModel);
    }
}
