package de.cassisi.hearth.ui.view.dashboard;

import com.jfoenix.controls.JFXTreeTableView;
import de.cassisi.hearth.ui.event.OpenNewCreateOperationWindow;
import de.cassisi.hearth.ui.event.OpenOperationOverviewEvent;
import de.cassisi.hearth.ui.event.RefreshDashboardStatisticEvent;
import de.cassisi.hearth.ui.event.RefreshLatestOperationDataEvent;
import de.cassisi.hearth.ui.view.BaseView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.stage.Window;
import javafx.util.Callback;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DashboardView extends BaseView implements FxmlView<DashboardViewModel>, Initializable {

    @InjectViewModel
    private DashboardViewModel viewModel;

    @FXML
    private Button createOperation;

    @FXML
    private PieChart operationPieChart;

    @FXML
    private BarChart<String, Integer> operationBarChart;

    @FXML
    private Label numberOfOperationsLabel;

    @FXML
    private Label numberOfOpenOperationsLabel;

    @FXML
    private Label numberOfIncompleteOperationsLabel;

    @FXML
    private JFXTreeTableView<LatestOperation> lastOperationsTableView;

    @FXML
    private TreeTableColumn<LatestOperation, Number> operationIdColumn;

    @FXML
    private TreeTableColumn<LatestOperation, String> operationDateColumn;

    @FXML
    private TreeTableColumn<LatestOperation, String> operationRoomColumn;

    @FXML
    private TreeTableColumn<LatestOperation, String> operationDetailsColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        createOperation.setOnAction(event -> post(new OpenNewCreateOperationWindow(getWindow())));

        initStatisticLabels();
        initPieChart();
        initOperationLineChart();
        initRecentOperationsTableView();

        // init table data
        refreshTableData();
    }

    private void initStatisticLabels() {
        numberOfOperationsLabel.textProperty().bind(viewModel.getNumberOfOperationsProperty().asString());
        numberOfIncompleteOperationsLabel.textProperty().bind(viewModel.getNumberOfIncompleteOperationsProperty().asString());
        numberOfOpenOperationsLabel.textProperty().bind(viewModel.getNumberOfOpenOperationsProperty().asString());
    }

    private void initRecentOperationsTableView() {
        operationIdColumn.setCellValueFactory(param -> param.getValue().getValue().getOperationId());
        operationDateColumn.setCellValueFactory(param -> param.getValue().getValue().getOperationDate());
        operationRoomColumn.setCellValueFactory(param -> param.getValue().getValue().getOperationRoom());

        operationDetailsColumn.setCellFactory(new Callback<>() {

            @Override
            public TreeTableCell<LatestOperation, String> call(TreeTableColumn<LatestOperation, String> param) {
                return new TreeTableCell<>() {

                    private final Hyperlink detailsLink = new Hyperlink(getString("ui.label.open"));

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            detailsLink.setOnAction(event -> {
                                TreeItem<LatestOperation> operation = getTreeTableView().getTreeItem(getIndex());
                                long operationId = operation.getValue().getOperationId().get();
                                post(new OpenOperationOverviewEvent(operationId));
                            });
                            setGraphic(detailsLink);
                            setText(null);
                        }
                    }
                };
            }
        });

        // double click to open operation view
        lastOperationsTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<LatestOperation> selectedItem = lastOperationsTableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    long operationId = selectedItem.getValue().getOperationId().get();
                    post(new OpenOperationOverviewEvent(operationId));
                }
            }
        });

        lastOperationsTableView.rootProperty().bind(viewModel.getLatestOperation());
        lastOperationsTableView.setShowRoot(false);
    }

    private void initOperationLineChart() {
        operationBarChart.setTitle(getString("ui.dashboard.operation_line_chart.title"));
        operationBarChart.setLegendVisible(false);
        operationBarChart.dataProperty().bind(viewModel.getOperationChartProperty());
    }

    private void initPieChart() {
        operationPieChart.dataProperty().bind(viewModel.getOperationAgeDistributionProperty());
        operationPieChart.setTitle(getString("ui.dashboard.operation_pie_chart.title"));
    }

    public void refreshTableData() {
        post(new RefreshLatestOperationDataEvent(true, 20));
        post(new RefreshDashboardStatisticEvent());
    }

    @Override
    public Window getWindow() {
        return createOperation.getScene().getWindow();
    }

}
