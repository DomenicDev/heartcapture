package de.cassisi.hearth.ui.operation;

import de.cassisi.hearth.ui.data.PerfusionUIData;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Paint;
import lombok.Getter;
import org.kordamp.ikonli.Ikon;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Component
public class OperationOverviewViewModel implements ViewModel {

    private final LongProperty idProperty = new SimpleLongProperty();
    private final ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>();
    private final StringProperty roomProperty = new SimpleStringProperty();
    private final StringProperty titleLabel = new SimpleStringProperty();


    // STATUS
    private final ObjectProperty<Ikon> nirsAvailableIconCode = new SimpleObjectProperty<>();
    private final ObjectProperty<Ikon> bisAvailableIconCode = new SimpleObjectProperty<>();
    private final ObjectProperty<Ikon> infusionAvailableIconCode = new SimpleObjectProperty<>();
    private final ObjectProperty<Ikon> hlmAvailableIconCode = new SimpleObjectProperty<>();

    private final ObjectProperty<Paint> nirsAvailableIconColor = new SimpleObjectProperty<>();
    private final ObjectProperty<Paint> bisAvailableIconColor = new SimpleObjectProperty<>();
    private final ObjectProperty<Paint> infusionAvailableIconColor = new SimpleObjectProperty<>();
    private final ObjectProperty<Paint> hlmAvailableIconColor = new SimpleObjectProperty<>();


    /*
    LIVE RECORDING
     */
    private final IntegerProperty nirsLeftValue = new SimpleIntegerProperty();
    private final IntegerProperty nirsRightValue = new SimpleIntegerProperty();
    private final DoubleProperty bisProperty = new SimpleDoubleProperty();
    private final ObjectProperty<TreeItem<PerfusionUIData>> infusionData = new SimpleObjectProperty<>();

    private final BooleanProperty startRecordingButtonDisableProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty stopRecordingButtonDisableProperty = new SimpleBooleanProperty(true);

    private final ObjectProperty<ObservableList<String>> serialPorts = new SimpleObjectProperty<>(FXCollections.observableArrayList());

    private final BooleanProperty bisSerialEnabled = new SimpleBooleanProperty();
    private final BooleanProperty nirsSerialEnabled = new SimpleBooleanProperty();
    private final BooleanProperty infusionSerialEnabled = new SimpleBooleanProperty();

    private final StringProperty bisSerialPort = new SimpleStringProperty();
    private final StringProperty nirsSerialPort = new SimpleStringProperty();
    private final StringProperty infusionSerialPort = new SimpleStringProperty();

    private final BooleanProperty autoDetectProgressBarVisible = new SimpleBooleanProperty(false);


    //***************************//
    //      ACCESS METHODS       //

    public ObjectProperty<LocalDate> dateProperty() {
        return dateProperty;
    }

    public StringProperty roomProperty() {
        return roomProperty;
    }

    public LongProperty idProperty() {
        return idProperty;
    }

    public StringProperty titleLabel() {
        return titleLabel;
    }

    public IntegerProperty nirsLeftValue() {
        return this.nirsLeftValue;
    }

    public IntegerProperty nirsRightValue() {
        return this.nirsRightValue;
    }

    public DoubleProperty bisValue() {
        return this.bisProperty;
    }



}
