package de.cassisi.hearth.ui.dashboard;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.jfoenix.controls.JFXTreeTableView;
import de.cassisi.hearth.ui.data.LatestOperationTableData;
import de.cassisi.hearth.ui.event.*;
import de.cassisi.hearth.ui.operation.OperationOverview;
import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

@Component
public class DashboardView implements FxmlView<DashboardViewModel>, Initializable {

    private final EventBus eventBus = EventBusProvider.getEventBus();

    @InjectViewModel
    private DashboardViewModel viewModel;

    @FXML
    private TableView<LatestOperationTableData> latestOperationTable;
    @FXML
    private TableColumn<LatestOperationTableData, Long> opNrCol;
    @FXML
    private TableColumn<LatestOperationTableData, LocalDate> opDateCol;
    @FXML
    private TableColumn<LatestOperationTableData, String> opRoomCol;

    @FXML
    private VBox mainContent;

    @FXML
    private Button createOperation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.eventBus.register(this);

        createOperation.setOnAction(event -> eventBus.post(new OpenNewCreateOperationWindow(getWindow())));

        // init table
        initLatestOperationTable();

        // init table data
        this.eventBus.post(new RefreshLatestOperationDataEvent(true, 20));
    }

    private Window getWindow() {
        return mainContent.getScene().getWindow();
    }

    private void initLatestOperationTable() {
        latestOperationTable.setEditable(false);

        opNrCol.setReorderable(false);
        opDateCol.setReorderable(false);
        opRoomCol.setReorderable(false);

        // init table
        opNrCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        opDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        opRoomCol.setCellValueFactory(new PropertyValueFactory<>("room"));
        latestOperationTable.itemsProperty().bind(viewModel.latestOperationData());
        latestOperationTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                LatestOperationTableData selected = latestOperationTable.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    eventBus.post(new OpenOperationOverviewEvent(selected.getId()));
                }
            }
        });
    }

}
