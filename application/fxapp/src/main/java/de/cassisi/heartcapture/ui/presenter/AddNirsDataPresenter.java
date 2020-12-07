package de.cassisi.heartcapture.ui.presenter;

import de.cassisi.heartcapture.ui.view.recording.RecordingViewModel;
import de.cassisi.heartcapture.usecase.AddNirsData;
import org.springframework.stereotype.Component;

@Component
public class AddNirsDataPresenter extends UseCasePresenter<AddNirsData.OutputData> {

    private final RecordingViewModel viewModel;

    public AddNirsDataPresenter(RecordingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(AddNirsData.OutputData outputData) {
        boolean saved = outputData.saved;

        if (saved) {
            viewModel.getNirsLeftValue().setValue(outputData.left);
            viewModel.getNirsRightValue().setValue(outputData.right);
        }
    }

}
