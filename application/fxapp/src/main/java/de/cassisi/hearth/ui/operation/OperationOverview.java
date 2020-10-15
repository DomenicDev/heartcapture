package de.cassisi.hearth.ui.operation;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class OperationOverview implements FxmlView<OperationOverviewViewModel>, Initializable {

    @InjectViewModel
    private OperationOverviewViewModel viewModel;

    @FXML private Label titleLabel;
    @FXML private DatePicker operationDatePicker;
    @FXML private TextField roomTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleLabel.textProperty().bind(viewModel.titleLabel());
        operationDatePicker.valueProperty().bind(viewModel.dateProperty());
        roomTextField.textProperty().bind(viewModel.roomProperty());
    }

}
