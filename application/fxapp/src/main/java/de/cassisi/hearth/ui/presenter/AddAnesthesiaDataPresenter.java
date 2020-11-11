package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.hearth.usecase.AddAnesthesiaData;
import org.springframework.stereotype.Component;

@Component
public class AddAnesthesiaDataPresenter extends UseCasePresenter<AddAnesthesiaData.OutputData> {

    private final OperationOverviewViewModel viewModel;

    public AddAnesthesiaDataPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(AddAnesthesiaData.OutputData outputData) {
        viewModel.bisValue().set(outputData.depthOfAnesthesia);
    }

}
