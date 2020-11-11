package de.cassisi.hearth.ui.view.dashboard;

import com.google.common.eventbus.EventBus;
import com.jfoenix.controls.JFXTreeTableView;
import de.cassisi.hearth.ui.event.OpenNewCreateOperationWindow;
import de.cassisi.hearth.ui.event.OpenOperationOverviewEvent;
import de.cassisi.hearth.ui.event.RefreshLatestOperationDataEvent;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.cassisi.hearth.ui.view.BaseView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.stage.Window;
import javafx.util.Callback;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
public class DashboardView extends BaseView implements FxmlView<DashboardViewModel>, Initializable {

    private final EventBus eventBus = EventBusProvider.getEventBus();

    @InjectViewModel
    private DashboardViewModel viewModel;

    @FXML
    private Button createOperation;

    @FXML
    private PieChart operationPieChart;

    @FXML
    private LineChart<String, Integer> operationLineChart;

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
        this.eventBus.register(this);

        createOperation.setOnAction(event -> eventBus.post(new OpenNewCreateOperationWindow(getWindow())));

        initPieChart();

        initOperationLineChart();

        initRecentOperationsTableView();

        // init table data
        refreshTableData();
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
                                eventBus.post(new OpenOperationOverviewEvent(operationId));
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
                    eventBus.post(new OpenOperationOverviewEvent(operationId));
                }
            }
        });

        lastOperationsTableView.rootProperty().bind(viewModel.getLatestOperation());
        lastOperationsTableView.setShowRoot(false);
    }

    private void initOperationLineChart() {
        operationLineChart.setTitle(getString("ui.dashboard.operation_line_chart.title"));
        operationLineChart.setCreateSymbols(false);
        operationLineChart.setLegendVisible(false);

        //defining a series

        XYChart.Series<String, Integer> series = new XYChart.Series<>();


        series.getData().add(new XYChart.Data<>(LocalDate.now().toString(), 2));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(1).toString(), 3));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(2).toString(), 1));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(3).toString(), 4));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(4).toString(), 5));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(5).toString(), 2));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(6).toString(), 3));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(7).toString(), 2));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(8).toString(), 3));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(9).toString(), 3));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(10).toString(), 4));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(11).toString(), 2));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(12).toString(), 3));
        series.getData().add(new XYChart.Data<>(LocalDate.now().plusDays(13).toString(), 1));

        operationLineChart.getData().add(series);
    }

    private void initPieChart() {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("< 50", 30),
                        new PieChart.Data("50 - 59", 40),
                        new PieChart.Data("60 - 69", 69),
                        new PieChart.Data("70 - 79", 59),
                        new PieChart.Data("< 80", 30));
        operationPieChart.setData(pieChartData);
        operationPieChart.setTitle(getString("ui.dashboard.operation_pie_chart.title"));
    }

    public void refreshTableData() {
        this.eventBus.post(new RefreshLatestOperationDataEvent(true, 20));
    }

    @Override
    public Window getWindow() {
        return createOperation.getScene().getWindow();
    }

}
