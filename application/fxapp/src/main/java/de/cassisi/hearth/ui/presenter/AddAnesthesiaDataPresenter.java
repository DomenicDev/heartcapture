package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.hearth.ui.view.recording.RecordingViewModel;
import de.cassisi.hearth.usecase.AddAnesthesiaData;
import org.springframework.stereotype.Component;

@Component
public class AddAnesthesiaDataPresenter extends UseCasePresenter<AddAnesthesiaData.OutputData> {

    private final RecordingViewModel viewModel;

    public AddAnesthesiaDataPresenter(RecordingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(AddAnesthesiaData.OutputData outputData) {
        viewModel.getBisProperty().set(outputData.depthOfAnesthesia);
    }

}
