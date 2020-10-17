package de.cassisi.hearth.ui.operation;

import com.google.common.eventbus.EventBus;
import de.cassisi.hearth.ui.event.AddHlmFileToOperationEvent;
import de.cassisi.hearth.ui.event.GenerateReportEvent;
import de.cassisi.hearth.ui.event.StartRecordingEvent;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import eu.hansolo.medusa.Gauge;
import eu.hansolo.medusa.GaugeBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
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
    private Pane nirsContainer;

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

        startRecordingButton.setOnAction(event -> eventBus.post(new StartRecordingEvent(viewModel.idProperty().get())));

        // setup live data presenters
        Gauge nirsLeftValueGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN)
                .minValue(0)
                .maxValue(100)
                .animated(true)
                .build();
        Gauge nirsRightValueGauge = GaugeBuilder.create()
                .skinType(Gauge.SkinType.MODERN)
                .minValue(0)
                .maxValue(100)
                .animated(true)
                .build();

        nirsLeftValueGauge.valueProperty().bind(viewModel.nirsLeftValue());
        nirsRightValueGauge.valueProperty().bind(viewModel.nirsRightValue());

        nirsContainer.getChildren().add(nirsLeftValueGauge);
        nirsContainer.getChildren().add(nirsRightValueGauge);

        // READ HLM FILE BUTTON
        readHLMFileButton.setOnAction(event -> {
            // todo add extension filter
            File hlmFile = hlmFileChooser.showOpenDialog(getWindow());
            if (hlmFile != null) {
                System.out.println("SELECTED FILE: " + hlmFile.toURI());
                eventBus.post(new AddHlmFileToOperationEvent(viewModel.idProperty().get(), hlmFile));
            }
        });

        // REPORT GENERATOR
        generateReportButton.setOnAction(event -> post(new GenerateReportEvent(getOperationId())));
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
