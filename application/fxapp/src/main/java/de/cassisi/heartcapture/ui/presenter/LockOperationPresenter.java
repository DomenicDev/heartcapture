package de.cassisi.heartcapture.ui.presenter;

import de.cassisi.heartcapture.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.heartcapture.ui.utils.PresenterUtils;
import org.springframework.stereotype.Component;

import static de.cassisi.heartcapture.usecase.LockOperation.OutputData;

@Component
public class LockOperationPresenter extends UseCasePresenter<OutputData> {

    private final OperationOverviewViewModel viewModel;

    public LockOperationPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void runOnUI(OutputData data) {
        PresenterUtils.present(data.operationData, viewModel);
    }
}
