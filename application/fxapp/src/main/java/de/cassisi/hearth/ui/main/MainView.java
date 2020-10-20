package de.cassisi.hearth.ui.main;

import com.google.common.eventbus.EventBus;
import com.jfoenix.controls.JFXButton;
import de.cassisi.hearth.ui.dashboard.DashboardView;
import de.cassisi.hearth.ui.dashboard.DashboardViewModel;
import de.cassisi.hearth.ui.operation.OperationOverview;
import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainView implements FxmlView<MainViewModel>, Initializable {

    private final EventBus eventBus = EventBusProvider.getEventBus();

    @FXML
    private VBox mainContent;
    private ViewTuple<OperationOverview, OperationOverviewViewModel> operationOverviewTuple;
    private ViewTuple<DashboardView, DashboardViewModel> dashboardViewTuple;

    // BUTTONS
    @FXML
    private Button dashboardButton;
    @FXML
    private Button operationOverviewButton;
    @FXML
    private Button settingsButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        operationOverviewTuple = FluentViewLoader.fxmlView(OperationOverview.class).load();
        dashboardViewTuple = FluentViewLoader.fxmlView(DashboardView.class).load();


        initButtons();

        showDashboard();
    }

    private void initButtons() {
        this.dashboardButton.setOnAction(event -> showDashboard());
        this.operationOverviewButton.setOnAction(event -> showOperationOverview());
        this.settingsButton.setOnAction(event -> {});
    }

    public void showDashboard() {
        mainContent.getChildren().clear();
        mainContent.getChildren().add(dashboardViewTuple.getView());
    }

    public void showOperationOverview() {
        mainContent.getChildren().clear();
        mainContent.getChildren().add(operationOverviewTuple.getView());
    }
}
