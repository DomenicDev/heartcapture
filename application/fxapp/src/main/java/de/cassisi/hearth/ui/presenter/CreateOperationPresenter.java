package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.ui.utils.PresenterUtils;
import de.cassisi.hearth.usecase.CreateOperation;
import org.springframework.stereotype.Component;


@Component
public class CreateOperationPresenter extends UseCasePresenter<CreateOperation.OutputData> {

    private final OperationOverviewViewModel viewModel;

    public CreateOperationPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(CreateOperation.OutputData outputData) {
        PresenterUtils.present(viewModel, outputData.operationData);
    }

}
