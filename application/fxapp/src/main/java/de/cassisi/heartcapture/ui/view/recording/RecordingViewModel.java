package de.cassisi.heartcapture.ui.view.recording;

import de.cassisi.heartcapture.ui.view.operation.PerfusionUIData;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class RecordingViewModel implements ViewModel {

    private final LongProperty idProperty = new SimpleLongProperty();

    private final IntegerProperty nirsLeftValue = new SimpleIntegerProperty();
    private final IntegerProperty nirsRightValue = new SimpleIntegerProperty();
    private final DoubleProperty bisProperty = new SimpleDoubleProperty();
    private final ObjectProperty<TreeItem<PerfusionUIData>> infusionData = new SimpleObjectProperty<>();

    private final BooleanProperty startRecordingButtonDisableProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty stopRecordingButtonDisableProperty = new SimpleBooleanProperty(true);

    private final ObjectProperty<ObservableList<String>> serialPorts = new SimpleObjectProperty<>(FXCollections.observableArrayList());

    private final BooleanProperty bisSerialEnabled = new SimpleBooleanProperty(false);
    private final BooleanProperty nirsSerialEnabled = new SimpleBooleanProperty(false);
    private final BooleanProperty infusionSerialEnabled = new SimpleBooleanProperty(false);

    private final StringProperty bisSerialPort = new SimpleStringProperty();
    private final StringProperty nirsSerialPort = new SimpleStringProperty();
    private final StringProperty infusionSerialPort = new SimpleStringProperty();

    private final BooleanProperty autoDetectProgressBarVisible = new SimpleBooleanProperty(false);

    public RecordingViewModel() {

    }
}
