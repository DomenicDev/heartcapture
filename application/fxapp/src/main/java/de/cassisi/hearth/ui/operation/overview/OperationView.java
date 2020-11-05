package de.cassisi.hearth.ui.operation.overview;

import com.google.common.eventbus.EventBus;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableView;
import de.cassisi.hearth.ui.event.OpenOperationOverviewEvent;
import de.cassisi.hearth.ui.event.RefreshOperationViewDataEvent;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class OperationView implements FxmlView<OperationViewViewModel>, Initializable {

    private final EventBus eventBus = EventBusProvider.getEventBus();

    @InjectViewModel
    private OperationViewViewModel viewModel;

    @FXML
    private JFXTreeTableView<OperationTableData> operationTableView;

    @FXML
    private TreeTableColumn<OperationTableData, Number> operationIdColumn;

    @FXML
    private TreeTableColumn<OperationTableData, String> operationDateColumn;

    @FXML
    private TreeTableColumn<OperationTableData, String> operationRoomColumn;

    @FXML
    private TreeTableColumn<OperationTableData, Boolean> operationRecordingColumn;

    @FXML
    private TreeTableColumn<OperationTableData, Boolean> operationHlmColumn;

    @FXML
    private TreeTableColumn<OperationTableData, String> operationOpenColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        initOperationTable();

    }

    private void initOperationTable() {
        operationTableView.setShowRoot(false);
        operationTableView.rootProperty().bindBidirectional(viewModel.getOperationData());

        // init columns
        operationIdColumn.setCellValueFactory(param -> param.getValue().getValue().getId());
        operationDateColumn.setCellValueFactory(param -> param.getValue().getValue().getDate());
        operationRoomColumn.setCellValueFactory(param -> param.getValue().getValue().getRoom());
        operationRecordingColumn.setCellValueFactory(param -> param.getValue().getValue().getLiveDataAvailable());
        operationHlmColumn.setCellValueFactory(param -> param.getValue().getValue().getHlmDataAvailable());

        operationRecordingColumn.setCellFactory(new Callback<>() {
            @Override
            public TreeTableCell<OperationTableData, Boolean> call(TreeTableColumn<OperationTableData, Boolean> param) {
                return new TreeTableCell<>() {

                    private final CheckBox liveDataCheckBox = new JFXCheckBox("Verfügbar");

                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            liveDataCheckBox.setSelected(item);
                            setGraphic(liveDataCheckBox);
                            setText(null);
                        }
                    }
                };
            }
        });

        operationHlmColumn.setCellFactory(new Callback<TreeTableColumn<OperationTableData, Boolean>, TreeTableCell<OperationTableData, Boolean>>() {
            @Override
            public TreeTableCell<OperationTableData, Boolean> call(TreeTableColumn<OperationTableData, Boolean> param) {
                return new TreeTableCell<>() {

                    private final CheckBox dataAvailableCheckBox = new JFXCheckBox("Verfügbar");

                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            dataAvailableCheckBox.setSelected(item);
                            dataAvailableCheckBox.setAlignment(Pos.CENTER);
                            setGraphic(dataAvailableCheckBox);
                            setText(null);
                        }
                    }
                };
            }
        });

        operationOpenColumn.setCellFactory(new Callback<>() {
            @Override
            public TreeTableCell<OperationTableData, String> call(TreeTableColumn<OperationTableData, String> param) {
                return new TreeTableCell<>() {

                    private final Hyperlink open = new Hyperlink("Öffnen");

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            open.setOnAction(event -> {
                                TreeItem<OperationTableData> treeItem = getTreeTableView().getTreeItem(getIndex());
                                long operationId = treeItem.getValue().getId().get();
                                post(new OpenOperationOverviewEvent(operationId));
                            });
                            setGraphic(open);
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    private void post(Object o) {
        eventBus.post(o);
    }

    public void refreshData() {
        post(new RefreshOperationViewDataEvent());
    }
}
