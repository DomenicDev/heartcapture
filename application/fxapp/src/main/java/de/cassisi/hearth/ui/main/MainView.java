package de.cassisi.hearth.ui.main;

import com.google.common.eventbus.EventBus;
import com.jfoenix.controls.JFXButton;
import de.cassisi.hearth.ui.dashboard.DashboardView;
import de.cassisi.hearth.ui.dashboard.DashboardViewModel;
import de.cassisi.hearth.ui.operation.OperationOverview;
import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.ui.operation.overview.OperationView;
import de.cassisi.hearth.ui.operation.overview.OperationViewViewModel;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
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
    private ViewTuple<OperationView, OperationViewViewModel> operationViewTuple;

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
        operationViewTuple = FluentViewLoader.fxmlView(OperationView.class).load();

        initButtons();

        showDashboard();
    }

    private void initButtons() {
        this.dashboardButton.setOnAction(event -> showDashboard());
        this.operationOverviewButton.setOnAction(event -> showOperationView());
        this.settingsButton.setOnAction(event -> {});
    }

    public void showDashboard() {
        mainContent.getChildren().clear();
        mainContent.getChildren().add(dashboardViewTuple.getView());
        dashboardViewTuple.getCodeBehind().refreshTableData();
    }

    public void showOperationOverview() {
        mainContent.getChildren().clear();
        mainContent.getChildren().add(operationOverviewTuple.getView());
    }

    public void showOperationView() {
        show(operationViewTuple);
        operationViewTuple.getCodeBehind().refreshData();
    }

    private void show(ViewTuple<?, ?> tuple) {
        Parent view = tuple.getView();
        VBox.setVgrow(view, Priority.ALWAYS);
        mainContent.getChildren().clear();
        mainContent.getChildren().add(tuple.getView());
    }
}
