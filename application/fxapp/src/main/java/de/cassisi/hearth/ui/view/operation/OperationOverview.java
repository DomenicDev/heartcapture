package de.cassisi.hearth.ui.view.operation;

import com.google.common.eventbus.EventBus;
import de.cassisi.hearth.ui.event.AddHlmFileToOperationEvent;
import de.cassisi.hearth.ui.event.GenerateReportEvent;
import de.cassisi.hearth.ui.event.OpenRecordingDialogEvent;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.cassisi.hearth.ui.view.BaseView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class OperationOverview extends BaseView implements FxmlView<OperationOverviewViewModel>, Initializable {

    private final EventBus eventBus = EventBusProvider.getEventBus();

    @InjectViewModel
    private OperationOverviewViewModel viewModel;

    @FXML
    private Label titleLabel;
    @FXML
    private DatePicker operationDatePicker;
    @FXML
    private TextField roomTextField;

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
        initCharts();
        initGenerateReportButton();
    }

    private void initOperationInformation() {
        titleLabel.textProperty().bind(viewModel.titleLabel());
        operationDatePicker.valueProperty().bind(viewModel.dateProperty());
        roomTextField.textProperty().bind(viewModel.roomProperty());
    }

    private void initGenerateReportButton() {
        generateReportButton.disableProperty().bind(viewModel.getGenerateReportButtonDisableProperty());
        generateReportButton.setOnAction(event -> post(new GenerateReportEvent(getOperationId())));
    }

    private void initCharts() {
        initNirsChart();
        initBisChart();
    }

    private void initBisChart() {
        bisChart.dataProperty().bind(viewModel.getBisChartData());
        bisChart.setCreateSymbols(false);
        bisChart.getXAxis().setTickLabelsVisible(false);
    }

    private void initNirsChart() {
        nirsChart.setCreateSymbols(false);
        nirsChart.setLegendSide(Side.RIGHT);
        nirsChart.getXAxis().setTickLabelsVisible(false);
        nirsChart.getXAxis().setOpacity(0);
        nirsChart.getXAxis().setTickMarkVisible(false);
        nirsChart.dataProperty().bind(viewModel.getNirsChartData());
    }

    private void initNewRecordingButton() {
        newRecordingButton.setOnAction(event -> post(new OpenRecordingDialogEvent(getWindow(), getOperationId())));
    }

    private void initReadHlmFileButton() {
        readHLMFileButton.setOnAction(event -> {
            File hlmFile = hlmFileChooser.showOpenDialog(getWindow());
            if (hlmFile != null) {
                eventBus.post(new AddHlmFileToOperationEvent(viewModel.idProperty().get(), hlmFile));
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
