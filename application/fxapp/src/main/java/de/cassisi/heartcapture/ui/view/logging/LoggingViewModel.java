package de.cassisi.heartcapture.ui.view.logging;

import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class LoggingViewModel implements ViewModel {

    private final StringProperty loggingTextProperty = new SimpleStringProperty();

}
