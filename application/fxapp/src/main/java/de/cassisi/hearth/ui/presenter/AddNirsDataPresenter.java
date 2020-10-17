package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.usecase.AddNirsData;
import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

@Component
public class AddNirsDataPresenter implements OutputHandler<AddNirsData.OutputData> {

    private final OperationOverviewViewModel viewModel;

    public AddNirsDataPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void handle(AddNirsData.OutputData outputData) {
        boolean saved = outputData.saved;

        if (saved) {
            viewModel.nirsLeftValue().setValue(outputData.left);
            viewModel.nirsRightValue().setValue(outputData.right);
        }
    }
}
