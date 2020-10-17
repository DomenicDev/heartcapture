package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.usecase.FindOperation;
import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

import static de.cassisi.hearth.usecase.FindOperation.*;

@Component
public class OperationOverviewPresenter implements OutputHandler<OutputData> {

    private final OperationOverviewViewModel viewModel;

    public OperationOverviewPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void handle(OutputData outputData) {
        SimpleOperationData data = outputData.operationData;
        viewModel.idProperty().setValue(data.getId());
        viewModel.roomProperty().setValue(data.getRoom());
        viewModel.dateProperty().setValue(data.getDate());
        viewModel.titleLabel().setValue("Operation #" + data.getId());
    }
}
