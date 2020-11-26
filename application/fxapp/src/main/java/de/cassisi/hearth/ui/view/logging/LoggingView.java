package de.cassisi.hearth.ui.view.logging;

import de.cassisi.hearth.ui.event.RefreshLoggingDataEvent;
import de.cassisi.hearth.ui.view.BaseView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class LoggingView extends BaseView implements FxmlView<LoggingViewModel>, Initializable {

    @InjectViewModel
    private LoggingViewModel viewModel;

    @FXML
    private TextArea loggingTextArea;

    @FXML
    private Button refreshButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggingTextArea.setEditable(false);
        loggingTextArea.textProperty().bind(viewModel.getLoggingTextProperty());

        refreshButton.setOnAction(event -> refresh());
        refresh();
    }

    private void refresh() {
        post(new RefreshLoggingDataEvent());
    }
}
