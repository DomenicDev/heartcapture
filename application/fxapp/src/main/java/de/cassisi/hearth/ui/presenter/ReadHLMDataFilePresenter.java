package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.hearth.ui.utils.PresenterUtils;
import org.springframework.stereotype.Component;

import static de.cassisi.hearth.usecase.ReadHLMDataFile.OutputData;

@Component
public class ReadHLMDataFilePresenter extends UseCasePresenter<OutputData> {

    private final OperationOverviewViewModel viewModel;

    public ReadHLMDataFilePresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(OutputData outputData) {
        PresenterUtils.present(outputData.simpleOperationData, viewModel);
    }

}
