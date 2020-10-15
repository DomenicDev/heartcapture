package de.cassisi.hearth.ui.dashboard;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.cassisi.hearth.ui.event.AddNirsDataEvent;
import de.cassisi.hearth.ui.event.CreateOperationEvent;
import de.cassisi.hearth.ui.event.OpenNewCreateOperationWindow;
import de.cassisi.hearth.ui.event.ShowOperationView;
import de.cassisi.hearth.ui.operation.OperationOverview;
import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

@Component
public class DashboardView implements FxmlView<DashboardViewModel>, Initializable {

    private final EventBus eventBus = EventBusProvider.getEventBus();

    @InjectViewModel
    private DashboardViewModel dashboardViewModel;

    @FXML
    private Button clickButton;

    @FXML
    private Button createOperationButton;

    @FXML
    private Label testLabel;

    @FXML
    private Button createOperation;

    @FXML
    private VBox mainContent;

    private ViewTuple<OperationOverview, OperationOverviewViewModel> operationOverviewTuple;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.eventBus.register(this);

        operationOverviewTuple = FluentViewLoader.fxmlView(OperationOverview.class).load();

        clickButton.setOnAction((event) -> eventBus.post(new AddNirsDataEvent(LocalDateTime.now(), 2, 3, 1L)));
        createOperation.setOnAction(event -> eventBus.post(new OpenNewCreateOperationWindow(getWindow())));
        createOperationButton.setOnAction(event -> eventBus.post(new CreateOperationEvent(LocalDate.now(), "Raum 7")));
        testLabel.textProperty().bind(dashboardViewModel.testMessage);
    }

    private Window getWindow() {
        return clickButton.getScene().getWindow();
    }

    @Subscribe
    public void handle(ShowOperationView event) {
        mainContent.getChildren().clear();
        mainContent.getChildren().add(operationOverviewTuple.getView());
    }

}
