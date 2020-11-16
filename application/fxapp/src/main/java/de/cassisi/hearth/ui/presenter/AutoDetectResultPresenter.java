package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.tools.recorder.detect.AutoPortDetector;
import de.cassisi.hearth.tools.recorder.detect.DetectionResult;
import de.cassisi.hearth.ui.view.recording.RecordingViewModel;
import org.springframework.stereotype.Component;

@Component
public class AutoDetectResultPresenter extends FXPresenter<DetectionResult> {

    private final RecordingViewModel viewModel;

    public AutoDetectResultPresenter(RecordingViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void runOnUI(DetectionResult data) {
        if (data.containsKey(AutoPortDetector.BIS_SERIAL)) {
            String bisPort = data.get(AutoPortDetector.BIS_SERIAL);
            if (bisPort != null) {
                viewModel.getBisSerialPort().set(bisPort);
            }
        }

        if (data.containsKey(AutoPortDetector.NIRS_SERIAL)) {
            String nirsPort = data.get(AutoPortDetector.NIRS_SERIAL);
            if (nirsPort != null) {
                viewModel.getNirsSerialPort().set(nirsPort);
            }
        }

        if (data.containsKey(AutoPortDetector.INFUSION_SERIAL)) {
            String infPort = data.get(AutoPortDetector.INFUSION_SERIAL);
            if (infPort != null) {
                viewModel.getInfusionSerialPort().set(infPort);
            }
        }

        viewModel.getAutoDetectProgressBarVisible().setValue(false);
    }

}
