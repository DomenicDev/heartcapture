package de.cassisi.hearth.ui.operation;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OperationOverviewViewModel implements ViewModel {

    private final LongProperty idProperty = new SimpleLongProperty();
    private final ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>();
    private final StringProperty roomProperty = new SimpleStringProperty();
    private final StringProperty titleLabel = new SimpleStringProperty();

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

}
