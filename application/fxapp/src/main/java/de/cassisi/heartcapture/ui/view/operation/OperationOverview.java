package de.cassisi.heartcapture.ui.view.operation;

import de.cassisi.heartcapture.ui.event.*;
import de.cassisi.heartcapture.ui.view.BaseView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
public class OperationOverview extends BaseView implements FxmlView<OperationOverviewViewModel>, Initializable {

    @InjectViewModel
    private OperationOverviewViewModel viewModel;


    @FXML
    private Label titleLabel;

    // GENERAL INFORMATION
    @FXML
    private Pane generalInformationPane;

    @FXML
    private DatePicker operationDatePicker;

    @FXML
    private TextField roomTextField;

    @FXML
    private Button saveOperationInformationButton;

    // STATUS
    @FXML
    private FontIcon nirsDataAvailableFontIcon;

    @FXML
    private FontIcon bisDataAvailableFontIcon;

    @FXML
    private FontIcon infusionDataAvailableFontIcon;

    @FXML
    private FontIcon hlmDataAvailableFontIcon;

    // HLM FILE READER BUTTON
    @FXML
    private Button readHLMFileButton;
    private final FileChooser hlmFileChooser = new FileChooser();

    // REPORT GENERATOR
    @FXML
    private Button generateReportButton;

    @FXML
    private Button newRecordingButton;

    // LOCK OPERATION
    @FXML
    private FontIcon lockFontIcon;

    @FXML
    private ToggleButton lockToggleButton;

    // CHARTS
    @FXML
    private LineChart<String, Integer> nirsChart;

    @FXML
    private AreaChart<String, Integer> bisChart;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initOperationInformation();
        initNewRecordingButton();
        initStatusInformation();
        initFileChooser();
        initReadHlmFileButton();
        initLockOperation();
        initCharts();
        initGenerateReportButton();
    }

    private void initLockOperation() {
        // FONT ICON
        lockFontIcon.iconCodeProperty().bind(viewModel.getLockFontIconCode());


        // TOGGLE BUTTON
        lockToggleButton.selectedProperty().bindBidirectional(viewModel.getOperationLockedProperty());
        lockToggleButton.setOnAction(event -> post(new LockOperationEvent(getOperationId(), lockToggleButton.isSelected())));
    }

    private void initOperationInformation() {
        generalInformationPane.disableProperty().bind(viewModel.getGeneralInformationPaneDisabledProperty());
        titleLabel.textProperty().bind(viewModel.titleLabel());
        operationDatePicker.valueProperty().bindBidirectional(viewModel.dateProperty());
        roomTextField.textProperty().bindBidirectional(viewModel.roomProperty());

        // SAVE BUTTON
        saveOperationInformationButton.setOnAction(event -> {
            LocalDate date = operationDatePicker.getValue();
            String room = roomTextField.getText();

            if (operationDatePicker == null || room == null || room.isBlank()) {
                return;
            }

            post(new EditOperationInformationEvent(getOperationId(), date, room));
        });
    }

    private void initGenerateReportButton() {
        generateReportButton.disableProperty().bind(viewModel.getGenerateReportButtonDisableProperty());
        generateReportButton.setOnAction(event -> post(new GenerateReportEvent(getOperationId(), getWindow())));
    }

    private void initCharts() {
        initNirsChart();
        initBisChart();
    }

    private void initBisChart() {
        bisChart.dataProperty().bind(viewModel.getBisChartData());
        bisChart.setCreateSymbols(false);
        bisChart.getXAxis().setTickLabelsVisible(true);
    }

    private void initNirsChart() {
        nirsChart.setCreateSymbols(false);
        nirsChart.setLegendSide(Side.TOP);
        nirsChart.getXAxis().setTickLabelsVisible(true);
        nirsChart.getXAxis().setOpacity(1);
        nirsChart.getXAxis().setTickMarkVisible(false);
        nirsChart.dataProperty().bind(viewModel.getNirsChartData());
    }

    private void initNewRecordingButton() {
        newRecordingButton.setOnAction(event -> post(new OpenRecordingDialogEvent(getWindow(), getOperationId())));
        newRecordingButton.disableProperty().bindBidirectional(viewModel.getNewRecordingButtonDisabledProperty());
    }

    private void initReadHlmFileButton() {
        readHLMFileButton.disableProperty().bindBidirectional(viewModel.getReadHLMFileButtonDisabledProperty());
        readHLMFileButton.setOnAction(event -> {
            File hlmFile = hlmFileChooser.showOpenDialog(getWindow());
            if (hlmFile != null) {
                post(new AddHlmFileToOperationEvent(viewModel.idProperty().get(), hlmFile, getWindow()));
            }
        });
    }

    private void initStatusInformation() {
        bisDataAvailableFontIcon.iconCodeProperty().bind(viewModel.getBisAvailableIconCode());
        nirsDataAvailableFontIcon.iconCodeProperty().bind(viewModel.getNirsAvailableIconCode());
        infusionDataAvailableFontIcon.iconCodeProperty().bind(viewModel.getInfusionAvailableIconCode());
        hlmDataAvailableFontIcon.iconCodeProperty().bind(viewModel.getHlmAvailableIconCode());

        bisDataAvailableFontIcon.iconColorProperty().bind(viewModel.getBisAvailableIconColor());
        nirsDataAvailableFontIcon.iconColorProperty().bind(viewModel.getNirsAvailableIconColor());
        infusionDataAvailableFontIcon.iconColorProperty().bind(viewModel.getInfusionAvailableIconColor());
        hlmDataAvailableFontIcon.iconColorProperty().bind(viewModel.getHlmAvailableIconColor());
    }

    private void initFileChooser() {
        hlmFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(getString("ui.operation_detail_view.hlm_file_chooser.hlm_file_extension"), "*.xlsx"));
    }

    private long getOperationId() {
        return viewModel.idProperty().get();
    }

    @Override
    protected Window getWindow() {
        return readHLMFileButton.getScene().getWindow();
    }

}
