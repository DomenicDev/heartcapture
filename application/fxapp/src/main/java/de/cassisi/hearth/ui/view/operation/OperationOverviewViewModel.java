package de.cassisi.hearth.ui.view.operation;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Paint;
import lombok.Getter;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.Ikonli;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Component
public class OperationOverviewViewModel implements ViewModel {

    private final LongProperty idProperty = new SimpleLongProperty();
    private final ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>();
    private final StringProperty roomProperty = new SimpleStringProperty();
    private final StringProperty titleLabel = new SimpleStringProperty();
    private final BooleanProperty generalInformationPaneDisabledProperty = new SimpleBooleanProperty(false);

    // STATUS
    private final ObjectProperty<Ikon> nirsAvailableIconCode = new SimpleObjectProperty<>();
    private final ObjectProperty<Ikon> bisAvailableIconCode = new SimpleObjectProperty<>();
    private final ObjectProperty<Ikon> infusionAvailableIconCode = new SimpleObjectProperty<>();
    private final ObjectProperty<Ikon> hlmAvailableIconCode = new SimpleObjectProperty<>();

    private final ObjectProperty<Paint> nirsAvailableIconColor = new SimpleObjectProperty<>();
    private final ObjectProperty<Paint> bisAvailableIconColor = new SimpleObjectProperty<>();
    private final ObjectProperty<Paint> infusionAvailableIconColor = new SimpleObjectProperty<>();
    private final ObjectProperty<Paint> hlmAvailableIconColor = new SimpleObjectProperty<>();

    // LOCK STATUS
    private final ObjectProperty<Ikon> lockFontIconCode = new SimpleObjectProperty<>(Ikonli.NONE);
    private final BooleanProperty operationLockedProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty newRecordingButtonDisabledProperty = new SimpleBooleanProperty(false);
    private final BooleanProperty readHLMFileButtonDisabledProperty = new SimpleBooleanProperty(false);

    // LIVE DATA
    private final ObjectProperty<ObservableList<XYChart.Series<String, Integer>>> nirsChartData = new SimpleObjectProperty<>(FXCollections.emptyObservableList());
    private final ObjectProperty<ObservableList<XYChart.Series<String, Integer>>> bisChartData = new SimpleObjectProperty<>(FXCollections.emptyObservableList());

    // ACTIONS
    private final BooleanProperty generateReportButtonDisableProperty = new SimpleBooleanProperty(true);

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


}
