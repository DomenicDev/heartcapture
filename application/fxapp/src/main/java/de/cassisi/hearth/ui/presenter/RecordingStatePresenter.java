package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import javafx.application.Platform;
import org.springframework.stereotype.Component;

@Component
public class RecordingStatePresenter {

    private final OperationOverviewViewModel viewModel;

    public RecordingStatePresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void handleStarted() {
        Platform.runLater(() -> {
            viewModel.getStartRecordingButtonDisableProperty().set(true);
            viewModel.getStopRecordingButtonDisableProperty().set(false);
        });
    }

    public void handleStopped() {
        Platform.runLater(() -> {
            viewModel.getStartRecordingButtonDisableProperty().set(false);
            viewModel.getStopRecordingButtonDisableProperty().set(true);
        });
    }
}
