package de.cassisi.hearth.ui.dashboard;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.stereotype.Component;

@Component
public final class DashboardViewModel implements ViewModel {

    public StringProperty testMessage = new SimpleStringProperty();

}
