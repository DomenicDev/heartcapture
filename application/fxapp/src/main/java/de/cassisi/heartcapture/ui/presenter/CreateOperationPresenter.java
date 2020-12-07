package de.cassisi.heartcapture.ui.presenter;

import de.cassisi.heartcapture.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.heartcapture.ui.utils.PresenterUtils;
import de.cassisi.heartcapture.usecase.CreateOperation;
import org.springframework.stereotype.Component;


@Component
public class CreateOperationPresenter extends UseCasePresenter<CreateOperation.OutputData> {

    private final OperationOverviewViewModel viewModel;

    public CreateOperationPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(CreateOperation.OutputData outputData) {
        PresenterUtils.present(outputData.operationData, viewModel);
    }

}
