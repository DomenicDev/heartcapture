package de.cassisi.hearth.ui.operation;

import com.google.common.eventbus.EventBus;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTreeTableView;
import de.cassisi.hearth.ui.data.PerfusionUIData;
import de.cassisi.hearth.ui.enums.MessageType;
import de.cassisi.hearth.ui.event.*;
import de.cassisi.hearth.ui.preference.UserPreference;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.kordamp.ikonli.javafx.FontIcon;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

@Component
public class OperationOverview implements FxmlView<OperationOverviewViewModel>, Initializable {

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
    private Pane infusionContainer;
    @FXML
    private JFXTreeTableView<PerfusionUIData> infusionTableView;
    @FXML
    private TreeTableColumn<PerfusionUIData, String> infusionNameColumn;
    @FXML
    private TreeTableColumn<PerfusionUIData, Number> infusionRateColumn;

    // HLM FILE READER BUTTON
    @FXML
    private Button readHLMFileButton;
    private final FileChooser hlmFileChooser = new FileChooser();

    // REPORT GENERATOR
    @FXML
    private Button generateReportButton;

    private final Preferences preferences = UserPreference.getInstance().getPreferences();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleLabel.textProperty().bind(viewModel.titleLabel());
        operationDatePicker.valueProperty().bind(viewModel.dateProperty());
        roomTextField.textProperty().bind(viewModel.roomProperty());
        startRecordingButton.disableProperty().bind(viewModel.getStartRecordingButtonDisableProperty());
        stopRecordingButton.disableProperty().bind(viewModel.getStopRecordingButtonDisableProperty());

        startRecordingButton.setOnAction(event -> startRecording());
        stopRecordingButton.setOnAction(event -> stopRecording());

        initAutoDetection();

        initStatusInformation();
        initFileChooser();
        initRecordingSettings();

        // setup live data presenters
        initRecordingGauges();

        // READ HLM FILE BUTTON
        readHLMFileButton.setOnAction(event -> {
            File hlmFile = hlmFileChooser.showOpenDialog(getWindow());
            if (hlmFile != null) {
                eventBus.post(new AddHlmFileToOperationEvent(viewModel.idProperty().get(), hlmFile));
            }
        });

        // REPORT GENERATOR
        generateReportButton.setOnAction(event -> post(new GenerateReportEvent(getOperationId())));
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
        hlmFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("HLM-Excel-File (*.xlsx)", "*.xlsx"));
    }

    private void initRecordingSettings() {
        bisSerialPortComboBox.itemsProperty().bind(viewModel.getSerialPorts());
        nirsSerialPortComboBox.itemsProperty().bind(viewModel.getSerialPorts());
        infusionSerialPortComboBox.itemsProperty().bind(viewModel.getSerialPorts());

        bisSerialCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> preferences.putBoolean("BIS_SERIAL_ENABLED", newValue));
        nirsSerialCheckBox.selectedProperty().addListener(((observable, oldValue, newValue) -> preferences.putBoolean("NIRS_SERIAL_ENABLED", newValue)));
        infusionSerialCheckBox.selectedProperty().addListener(((observable, oldValue, newValue) -> preferences.putBoolean("INFUSION_SERIAL_ENABLED", newValue)));

        bisSerialPortComboBox.valueProperty().addListener((observable, oldValue, newValue) -> preferences.put("BIS_SERIAL_PORT_NAME", newValue));
        nirsSerialPortComboBox.valueProperty().addListener((observable, oldValue, newValue) -> preferences.put("NIRS_SERIAL_PORT_NAME", newValue));
        infusionSerialPortComboBox.valueProperty().addListener((observable, oldValue, newValue) -> preferences.put("INFUSION_SERIAL_PORT_NAME", newValue));

        boolean bisSerialEnabled = preferences.getBoolean("BIS_SERIAL_ENABLED", true);
        boolean nirsSerialEnabled = preferences.getBoolean("NIRS_SERIAL_ENABLED", true);
        boolean infusionSerialEnabled = preferences.getBoolean("INFUSION_SERIAL_ENABLED", true);

        String bisSerialPortName = preferences.get("BIS_SERIAL_PORT_NAME", null);
        String nirsSerialPortName = preferences.get("NIRS_SERIAL_PORT_NAME", null);
        String infusionSerialPortName = preferences.get("INFUSION_SERIAL_PORT_NAME", null);

        bisSerialPortComboBox.valueProperty().bindBidirectional(viewModel.getBisSerialPort());
        nirsSerialPortComboBox.valueProperty().bindBidirectional(viewModel.getNirsSerialPort());
        infusionSerialPortComboBox.valueProperty().bindBidirectional(viewModel.getInfusionSerialPort());

        refreshComPorts();
        setRecordingSettings(
                bisSerialEnabled,nirsSerialEnabled,infusionSerialEnabled,
                bisSerialPortName, nirsSerialPortName, infusionSerialPortName
        );

    }

    private void refreshComPorts() {
        post(new RefreshSerialPortEvent());
    }

    private void setRecordingSettings(boolean bis, boolean nirs, boolean infusion, String bisSerialPortName, String nirsSerialPortName, String infusionSerialPortName) {
        bisSerialCheckBox.setSelected(bis);
        nirsSerialCheckBox.setSelected(nirs);
        infusionSerialCheckBox.setSelected(infusion);

        bisSerialPortComboBox.valueProperty().setValue(bisSerialPortName);
        nirsSerialPortComboBox.valueProperty().setValue(nirsSerialPortName);
        infusionSerialPortComboBox.valueProperty().setValue(infusionSerialPortName);
    }

    private void initRecordingGauges() {
        // init NIRS gauges
        Gauge nirsLeftValueGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.TILE_SPARK_LINE)
                .minValue(0)
                .maxValue(100)
                //      .prefSize(250, 250)
                .animated(true)
                .build();
        Gauge nirsRightValueGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.TILE_SPARK_LINE)
                .averageVisible(true)
                .minValue(0)
                //    .prefSize(250, 250)
                .maxValue(100)
                .animated(true)
                .build();

        nirsLeftValueGauge.valueProperty().bind(viewModel.nirsLeftValue());
        nirsRightValueGauge.valueProperty().bind(viewModel.nirsRightValue());

        nirsContainer.getChildren().add(nirsLeftValueGauge);
        nirsContainer.getChildren().add(nirsRightValueGauge);

        // init BIS Gauges
        Gauge bisGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.DASHBOARD)
                .minValue(0)
                .maxValue(100)
                .animated(true)
                .build();
        bisGauge.valueProperty().bind(viewModel.bisValue());
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
        return viewModel.idProperty().get();
    }

    private void post(Object event) {
        this.eventBus.post(event);
    }

    private Window getWindow() {
        return readHLMFileButton.getScene().getWindow();
    }

    private void postWarningMessage(String message) {
        post(new ShowMessageEvent(message, MessageType.WARNING));
    }
}
