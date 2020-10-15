package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.usecase.CreateOperation;
import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CreateOperationPresenter implements OutputHandler<CreateOperation.OutputData> {

    private final OperationOverviewViewModel viewModel;

    public CreateOperationPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void handle(CreateOperation.OutputData outputData) {
        viewModel.idProperty().setValue(outputData.id);
        viewModel.dateProperty().setValue(LocalDate.now());
        viewModel.roomProperty().setValue(outputData.room);
    }
}
