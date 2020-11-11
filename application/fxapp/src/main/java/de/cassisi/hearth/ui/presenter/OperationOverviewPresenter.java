package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.hearth.ui.utils.PresenterUtils;
import org.springframework.stereotype.Component;

import static de.cassisi.hearth.usecase.FindOperation.OutputData;

@Component
public class OperationOverviewPresenter extends UseCasePresenter<OutputData> {

    private final OperationOverviewViewModel viewModel;

    public OperationOverviewPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(OutputData outputData) {
        PresenterUtils.present(viewModel, outputData.operationData);
    }

}
