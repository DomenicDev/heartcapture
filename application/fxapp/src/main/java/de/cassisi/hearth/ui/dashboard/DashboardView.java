package de.cassisi.hearth.ui.dashboard;

import com.google.common.eventbus.EventBus;
import de.cassisi.hearth.ui.event.AddNirsDataEvent;
import de.cassisi.hearth.ui.event.CreateOperationEvent;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Component
public class DashboardView implements FxmlView<MyViewModel>, Initializable {

    private final EventBus eventBus = EventBusProvider.getEventBus();

    @InjectViewModel
    private MyViewModel myViewModel;

    @FXML
    private Button clickButton;

    @FXML
    private Button createOperationButton;

    @FXML
    private Label testLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clickButton.setOnAction((event) -> eventBus.post(new AddNirsDataEvent(LocalDateTime.now(), 2, 3, 1L)));
        createOperationButton.setOnAction(event -> eventBus.post(new CreateOperationEvent(LocalDate.now(), "Raum 7")));
        testLabel.textProperty().bind(myViewModel.testMessage);

    }
}
