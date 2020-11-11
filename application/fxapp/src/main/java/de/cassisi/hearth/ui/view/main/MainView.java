package de.cassisi.hearth.ui.view.main;

import de.cassisi.hearth.ui.view.BaseView;
import de.cassisi.hearth.ui.view.dashboard.DashboardView;
import de.cassisi.hearth.ui.view.dashboard.DashboardViewModel;
import de.cassisi.hearth.ui.view.operation.OperationOverview;
import de.cassisi.hearth.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.hearth.ui.view.operation.overview.OperationView;
import de.cassisi.hearth.ui.view.operation.overview.OperationViewViewModel;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainView extends BaseView implements FxmlView<MainViewModel>, Initializable {

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
