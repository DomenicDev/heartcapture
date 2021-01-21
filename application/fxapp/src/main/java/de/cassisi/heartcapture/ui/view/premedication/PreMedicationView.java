package de.cassisi.heartcapture.ui.view.premedication;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.structure.NodeElement;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import de.cassisi.heartcapture.ui.event.UpdatePreMedicationDataEvent;
import de.cassisi.heartcapture.ui.view.BaseView;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class PreMedicationView extends BaseView implements FxmlView<PreMedicationViewModel>, Initializable {

    @InjectViewModel
    private PreMedicationViewModel viewModel;

    @FXML
    private VBox container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // create pre med form
        Form form = Form.of(
                Group.of(
                        NodeElement.of(new Label(getString("ui.premeddata.group.title.pre_operation"))),
                        Field.ofDoubleType(viewModel.getSuprareninPreOperationProperty()).label(getString("ui.premeddata.field.suprarenin")),
                        Field.ofDoubleType(viewModel.getNorepinephrinPreOperationProperty()).label(getString("ui.premeddata.field.norepinephrin")),
                        Field.ofDoubleType(viewModel.getVasopressinPreOperationProperty()).label(getString("ui.premeddata.field.vasopressin")),
                        Field.ofDoubleType(viewModel.getMilrinonPreOperationProperty()).label(getString("ui.premeddata.field.milrinon")),
                        Field.ofDoubleType(viewModel.getNtgPreOperationProperty()).label(getString("ui.premeddata.field.ntg")),
                        Field.ofDoubleType(viewModel.getSimdaxPreOperationProperty()).label(getString("ui.premeddata.field.simdax")),
                        Field.ofDoubleType(viewModel.getHeparinPreOperationProperty()).label(getString("ui.premeddata.field.heparin"))
                ),
                Group.of(
                        NodeElement.of(new Label(getString("ui.premeddata.group.title.pre_hlm"))),
                        Field.ofDoubleType(viewModel.getSuprareninPreHlmProperty()).label(getString("ui.premeddata.field.suprarenin")),
                        Field.ofDoubleType(viewModel.getNorepinephrinPreHlmProperty()).label(getString("ui.premeddata.field.norepinephrin")),
                        Field.ofDoubleType(viewModel.getVasopressinPreHlmProperty()).label(getString("ui.premeddata.field.vasopressin")),
                        Field.ofDoubleType(viewModel.getMilrinonPreHlmProperty()).label(getString("ui.premeddata.field.milrinon")),
                        Field.ofDoubleType(viewModel.getNtgPreHlmProperty()).label(getString("ui.premeddata.field.ntg")),
                        Field.ofDoubleType(viewModel.getSimdaxPreHlmProperty()).label(getString("ui.premeddata.field.simdax"))
                )
        );


        // add form to view
        FormRenderer renderer = new FormRenderer(form);
        container.getChildren().add(renderer);

        // create submit button
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        Button submitButton = new Button(getString("ui.premeddata.submit"));
        submitButton.setDefaultButton(true);
        submitButton.setOnAction(event -> {
            form.persist();
            if (form.isValid()) {

                // post update request with new data
                 post(new UpdatePreMedicationDataEvent(
                         viewModel.getOperationIdProperty().get(),

                         viewModel.getSuprareninPreOperationProperty().get(),
                         viewModel.getNorepinephrinPreOperationProperty().get(),
                         viewModel.getVasopressinPreOperationProperty().get(),
                         viewModel.getMilrinonPreOperationProperty().get(),
                         viewModel.getNtgPreOperationProperty().get(),
                         viewModel.getSimdaxPreOperationProperty().get(),
                         viewModel.getHeparinPreOperationProperty().get(),

                         viewModel.getSuprareninPreHlmProperty().get(),
                         viewModel.getNorepinephrinPreHlmProperty().get(),
                         viewModel.getVasopressinPreHlmProperty().get(),
                         viewModel.getMilrinonPreHlmProperty().get(),
                         viewModel.getNtgPreHlmProperty().get(),
                         viewModel.getSimdaxPreHlmProperty().get()
                 ));

                ((Stage) getWindow()).close();

            }
        });
        buttonContainer.getChildren().add(submitButton);
        container.getChildren().add(buttonContainer);
    }

    @Override
    protected Window getWindow() {
        return container.getScene().getWindow();
    }
}
