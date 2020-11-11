package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.hearth.ui.utils.PresenterUtils;
import org.springframework.stereotype.Component;

import java.util.List;

import static de.cassisi.hearth.usecase.AddInfusionData.OutputData;

@Component
public class AddInfusionDataPresenter extends UseCasePresenter<OutputData> {

    private final OperationOverviewViewModel viewModel;

    public AddInfusionDataPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(OutputData outputData) {
        List<OutputData.PerfusorData> perfusorData = outputData.data;
        viewModel.getInfusionData().setValue(PresenterUtils.convertInfusionData(perfusorData));
    }

}
