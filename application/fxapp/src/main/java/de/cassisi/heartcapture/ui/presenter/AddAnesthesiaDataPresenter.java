package de.cassisi.heartcapture.ui.presenter;

import de.cassisi.heartcapture.ui.view.recording.RecordingViewModel;
import de.cassisi.heartcapture.usecase.AddAnesthesiaData;
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
