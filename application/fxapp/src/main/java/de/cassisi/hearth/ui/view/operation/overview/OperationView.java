package de.cassisi.hearth.ui.view.operation.overview;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTreeTableView;
import de.cassisi.hearth.ui.event.OpenNewCreateOperationWindow;
import de.cassisi.hearth.ui.event.OpenOperationOverviewEvent;
import de.cassisi.hearth.ui.event.RefreshOperationViewDataEvent;
import de.cassisi.hearth.ui.view.BaseView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.stage.Window;
import javafx.util.Callback;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class OperationView extends BaseView implements FxmlView<OperationViewViewModel>, Initializable {

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

    @FXML
    private Button createOperationButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCreateOperationButton();
        initOperationTable();
    }

    private void initCreateOperationButton() {
        this.createOperationButton.setOnAction(event -> post(new OpenNewCreateOperationWindow(getWindow())));
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

                    private final CheckBox liveDataCheckBox = new JFXCheckBox(getString("ui.label.available"));

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

        operationHlmColumn.setCellFactory(new Callback<>() {
            @Override
            public TreeTableCell<OperationTableData, Boolean> call(TreeTableColumn<OperationTableData, Boolean> param) {
                return new TreeTableCell<>() {

                    private final CheckBox dataAvailableCheckBox = new JFXCheckBox(getString("ui.label.available"));

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

                    private final Hyperlink open = new Hyperlink(getString("ui.label.open"));

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

    @Override
    public Window getWindow() {
        return createOperationButton.getScene().getWindow();
    }

    public void refreshData() {
        post(new RefreshOperationViewDataEvent());
    }
}
