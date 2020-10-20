package de.cassisi.hearth.ui.operation;

import com.google.common.eventbus.EventBus;
import de.cassisi.hearth.ui.data.PerfusionUIData;
import de.cassisi.hearth.ui.event.AddHlmFileToOperationEvent;
import de.cassisi.hearth.ui.event.GenerateReportEvent;
import de.cassisi.hearth.ui.event.StartRecordingEvent;
import de.cassisi.hearth.ui.event.StopRecordingEvent;
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
import javafx.util.Callback;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class OperationOverview implements FxmlView<OperationOverviewViewModel>, Initializable {

    private final EventBus eventBus = EventBusProvider.getEventBus();

    @InjectViewModel
    private OperationOverviewViewModel viewModel;

    @FXML private Label titleLabel;
    @FXML private DatePicker operationDatePicker;
    @FXML private TextField roomTextField;

    // RECORDING
    @FXML
    private Button startRecordingButton;
    @FXML
    private Button stopRecordingButton;
    @FXML
    private Pane nirsContainer;
    @FXML
    private Pane bisContainer;
    @FXML
    private Pane infusionContainer;
    @FXML
    private ListView<PerfusionUIData> infusionListView;

    // HLM FILE READER BUTTON
    @FXML
    private Button readHLMFileButton;
    private final FileChooser hlmFileChooser = new FileChooser();

    // REPORT GENERATOR
    @FXML
    private Button generateReportButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleLabel.textProperty().bind(viewModel.titleLabel());
        operationDatePicker.valueProperty().bind(viewModel.dateProperty());
        roomTextField.textProperty().bind(viewModel.roomProperty());

        startRecordingButton.setOnAction(event -> post(new StartRecordingEvent(viewModel.idProperty().get())));
        stopRecordingButton.setOnAction(event -> post(new StopRecordingEvent()));

        // setup live data presenters
       initRecordingGauges();

        // READ HLM FILE BUTTON
        readHLMFileButton.setOnAction(event -> {
            // todo add extension filter
            File hlmFile = hlmFileChooser.showOpenDialog(getWindow());
            if (hlmFile != null) {
                eventBus.post(new AddHlmFileToOperationEvent(viewModel.idProperty().get(), hlmFile));
            }
        });

        // REPORT GENERATOR
        generateReportButton.setOnAction(event -> post(new GenerateReportEvent(getOperationId())));
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
        infusionListView.itemsProperty().bind(viewModel.infusionList());
        infusionListView.setCellFactory(new Callback<ListView<PerfusionUIData>, ListCell<PerfusionUIData>>() {
            @Override
            public ListCell<PerfusionUIData> call(ListView<PerfusionUIData> param) {
                ListCell<PerfusionUIData> cell = new ListCell<>() {
                    @Override
                    protected void updateItem(PerfusionUIData item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName() + " --> " + item.getRate());
                        }
                    }
                };
                return cell;
            }
        });
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

}
