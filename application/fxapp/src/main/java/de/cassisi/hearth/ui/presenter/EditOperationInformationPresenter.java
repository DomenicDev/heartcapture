package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.utils.PresenterUtils;
import de.cassisi.hearth.ui.view.operation.OperationOverviewViewModel;
import org.springframework.stereotype.Component;

import static de.cassisi.hearth.usecase.EditOperationInformation.OutputData;

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
