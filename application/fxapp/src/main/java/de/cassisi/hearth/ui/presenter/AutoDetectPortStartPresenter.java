package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.view.recording.RecordingViewModel;
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
