package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.usecase.CreateOperation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CreateOperationPresenter extends FXPresenter<CreateOperation.OutputData> {

    private final OperationOverviewViewModel viewModel;

    public CreateOperationPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(CreateOperation.OutputData outputData) {
        viewModel.idProperty().setValue(outputData.id);
        viewModel.dateProperty().setValue(LocalDate.now());
        viewModel.roomProperty().setValue(outputData.room);
    }

}
