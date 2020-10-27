package de.cassisi.hearth.ui.operation;

import de.cassisi.hearth.ui.data.PerfusionUIData;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Component
public class OperationOverviewViewModel implements ViewModel {

    private final LongProperty idProperty = new SimpleLongProperty();
    private final ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>();
    private final StringProperty roomProperty = new SimpleStringProperty();
    private final StringProperty titleLabel = new SimpleStringProperty();
    private final ObjectProperty<ObservableList<PerfusionUIData>> perfusionData = new SimpleObjectProperty<>();

    /*
    LIVE RECORDING
     */
    private final IntegerProperty nirsLeftValue = new SimpleIntegerProperty();
    private final IntegerProperty nirsRightValue = new SimpleIntegerProperty();
    private final DoubleProperty bisProperty = new SimpleDoubleProperty();

    private final BooleanProperty startRecordingButtonDisableProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty stopRecordingButtonDisableProperty = new SimpleBooleanProperty(true);


    //***************************//
    //      ACCESS METHODS       //

    public ObjectProperty<LocalDate> dateProperty() {
        return dateProperty;
    }

    public StringProperty roomProperty() {
        return roomProperty;
    }

    public LongProperty idProperty() {
        titleLabel.setValue(String.valueOf(idProperty.get()));
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

    public ObjectProperty<ObservableList<PerfusionUIData>> infusionList() {
        return this.perfusionData;
    }

}
