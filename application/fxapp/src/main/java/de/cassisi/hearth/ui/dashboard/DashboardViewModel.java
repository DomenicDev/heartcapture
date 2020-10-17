package de.cassisi.hearth.ui.dashboard;

import de.cassisi.hearth.ui.data.LatestOperationTableData;
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.springframework.stereotype.Component;

@Component
public final class DashboardViewModel implements ViewModel {

    public StringProperty testMessage = new SimpleStringProperty();

    private final ObjectProperty<ObservableList<LatestOperationTableData>> latestOperationData = new SimpleObjectProperty<>();

    public ObjectProperty<ObservableList<LatestOperationTableData>> latestOperationData() {
        return this.latestOperationData;
    }

}
