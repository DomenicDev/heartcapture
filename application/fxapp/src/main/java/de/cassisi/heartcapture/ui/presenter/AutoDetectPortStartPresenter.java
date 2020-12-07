package de.cassisi.heartcapture.ui.presenter;

import de.cassisi.heartcapture.ui.view.recording.RecordingViewModel;
import org.springframework.stereotype.Component;

@Component
public class AutoDetectPortStartPresenter extends FXPresenter<Void> {

    private final RecordingViewModel viewModel;

    public AutoDetectPortStartPresenter(RecordingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void runOnUI(Void data) {
        viewModel.getAutoDetectProgressBarVisible().setValue(true);
    }
}
