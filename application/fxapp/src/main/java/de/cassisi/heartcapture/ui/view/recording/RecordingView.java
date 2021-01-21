package de.cassisi.heartcapture.ui.view.recording;

import com.jfoenix.controls.JFXTreeTableView;
import de.cassisi.heartcapture.ui.event.*;
import de.cassisi.heartcapture.ui.view.operation.PerfusionUIData;
import de.cassisi.heartcapture.ui.view.BaseView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class RecordingView extends BaseView implements FxmlView<RecordingViewModel>, Initializable {

    @InjectViewModel
    private RecordingViewModel viewModel;

   

    // Start / Stop
    @FXML
    private Button startRecordingButton;
    @FXML
    private Button stopRecordingButton;

    // Gauges
    @FXML
    private Pane nirsContainer;
    @FXML
    private Pane bisContainer;
    @FXML
    private JFXTreeTableView<PerfusionUIData> infusionTableView;
    @FXML
    private TreeTableColumn<PerfusionUIData, String> infusionNameColumn;
    @FXML
    private TreeTableColumn<PerfusionUIData, Number> infusionRateColumn;

    // RECORDING
    //Settings
    @FXML
    private CheckBox bisSerialCheckBox;

    @FXML
    private CheckBox nirsSerialCheckBox;

    @FXML
    private CheckBox infusionSerialCheckBox;

    @FXML
    private ComboBox<String> bisSerialPortComboBox;

    @FXML
    private ComboBox<String> nirsSerialPortComboBox;

    @FXML
    private ComboBox<String> infusionSerialPortComboBox;

    @FXML
    private Button autoDetectButton;

    @FXML
    private ProgressBar autoDetectProgressBar;

    // MEDICATION BUTTON
    @FXML
    private Button addMedicationButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initAutoDetection();
        initRecordingButtons();
        initRecordingGauges();
        initRecordingSettings();
        refreshComPorts();
        initMedication();
    }

    private void initMedication() {
        addMedicationButton.setOnAction(event -> post(new ShowEditMedicationDialogEvent(getWindow(), getOperationId())));
    }

    private void initRecordingButtons() {
        startRecordingButton.disableProperty().bind(viewModel.getStartRecordingButtonDisableProperty());
        stopRecordingButton.disableProperty().bind(viewModel.getStopRecordingButtonDisableProperty());

        startRecordingButton.setOnAction(event -> startRecording());
        stopRecordingButton.setOnAction(event -> stopRecording());
    }

    private void initAutoDetection() {
        autoDetectButton.setOnAction(event -> post(new AutoDetectEvent(bisEnabled(), nirsEnabled(), infusionEnabled())));
        autoDetectProgressBar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        autoDetectProgressBar.visibleProperty().bindBidirectional(viewModel.getAutoDetectProgressBarVisible());
    }

    private boolean bisEnabled() {
        return this.bisSerialCheckBox.isSelected();
    }

    private boolean nirsEnabled() {
        return this.nirsSerialCheckBox.isSelected();
    }

    private boolean infusionEnabled() {
        return this.infusionSerialCheckBox.isSelected();
    }

    private void initRecordingSettings() {
        bisSerialPortComboBox.itemsProperty().bind(viewModel.getSerialPorts());
        nirsSerialPortComboBox.itemsProperty().bind(viewModel.getSerialPorts());
        infusionSerialPortComboBox.itemsProperty().bind(viewModel.getSerialPorts());

        bisSerialCheckBox.selectedProperty().bindBidirectional(viewModel.getBisSerialEnabled());
        nirsSerialCheckBox.selectedProperty().bindBidirectional(viewModel.getNirsSerialEnabled());
        infusionSerialCheckBox.selectedProperty().bindBidirectional(viewModel.getInfusionSerialEnabled());

        bisSerialPortComboBox.valueProperty().bindBidirectional(viewModel.getBisSerialPort());
        nirsSerialPortComboBox.valueProperty().bindBidirectional(viewModel.getNirsSerialPort());
        infusionSerialPortComboBox.valueProperty().bindBidirectional(viewModel.getInfusionSerialPort());
    }

    private void refreshComPorts() {
        post(new RefreshSerialPortEvent());
    }

    private void initRecordingGauges() {
        // init NIRS gauges

        Gauge nirsLeftValueGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.TILE_SPARK_LINE)
                .title(getString("ui.live_recording.nirs_left.title"))
                .minValue(0)
                .maxValue(100)
                .animated(true)
                .build();
        Gauge nirsRightValueGauge = GaugeBuilder.create()
                .title(getString("ui.live_recording.nirs_right.title"))
                .skinType(Gauge.SkinType.TILE_SPARK_LINE)
                .averageVisible(true)
                .minValue(0)
                .maxValue(100)
                .animated(true)
                .build();


        nirsLeftValueGauge.valueProperty().bind(viewModel.getNirsLeftValue());
        nirsRightValueGauge.valueProperty().bind(viewModel.getNirsRightValue());

        nirsContainer.getChildren().add(nirsLeftValueGauge);
        nirsContainer.getChildren().add(nirsRightValueGauge);

        Gauge bisGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.TILE_SPARK_LINE)
                .title("BIS")
                .minValue(0)
                .maxValue(100)
                .animated(true)
                .build();
        bisGauge.valueProperty().bind(viewModel.getBisProperty());
        bisContainer.getChildren().add(bisGauge);

        // init infusion view
        infusionTableView.rootProperty().bind(viewModel.getInfusionData());
        infusionTableView.setShowRoot(false);

        infusionNameColumn.setCellValueFactory(param -> param.getValue().getValue().getName());
        infusionRateColumn.setCellValueFactory(param -> param.getValue().getValue().getRate());
    }

    private void startRecording() {
        boolean useBisSerial = bisSerialCheckBox.isSelected();
        boolean useNirsSerial = nirsSerialCheckBox.isSelected();
        boolean useInfusionSerial = infusionSerialCheckBox.isSelected();

        String bisSerialPort = bisSerialPortComboBox.getValue();
        if (useBisSerial && bisSerialPort == null) {
            postWarningMessage("ui.message.warning_select_bis_serial_port");
            return;
        }

        String nirsSerialPort = nirsSerialPortComboBox.getValue();
        if (useNirsSerial && nirsSerialPort == null) {
            postWarningMessage("ui.message.warning_select_nirs_serial_port");
            return;
        }

        String infusionSerialPort = infusionSerialPortComboBox.getValue();
        if (useInfusionSerial && infusionSerialPort == null) {
            postWarningMessage("ui.message.warning_select_infusion_serial_port");
            return;
        }

        post(new StartRecordingEvent(
                getOperationId(),
                useBisSerial,
                useNirsSerial,
                useInfusionSerial,
                bisSerialPort,
                nirsSerialPort,
                infusionSerialPort));
    }

    private void stopRecording() {
        post(new StopRecordingEvent(getOperationId()));
    }

    private long getOperationId() {
        return viewModel.getIdProperty().get();
    }

    public void handleCloseRequest(WindowEvent event) {
        if (isRecordingRunning()) {
            postWarningMessage("ui.message_warning_close_on_recording_running");
            event.consume();
        }
    }

    private boolean isRecordingRunning() {
        return startRecordingButton.isDisabled();
    }

    @Override
    protected Window getWindow() {
        return addMedicationButton.getScene().getWindow();
    }
}
