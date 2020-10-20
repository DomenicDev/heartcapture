package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.usecase.AddAnesthesiaData;
import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

@Component
public class AddAnesthesiaDataPresenter implements OutputHandler<AddAnesthesiaData.OutputData> {

    private final OperationOverviewViewModel viewModel;

    public AddAnesthesiaDataPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void handle(AddAnesthesiaData.OutputData outputData) {
        viewModel.bisValue().set(outputData.depthOfAnesthesia);
    }

}
