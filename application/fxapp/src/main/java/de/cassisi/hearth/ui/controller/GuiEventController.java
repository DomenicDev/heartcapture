package de.cassisi.hearth.ui.controller;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.cassisi.hearth.ui.event.AddNirsDataEvent;
import de.cassisi.hearth.ui.event.CreateOperationEvent;
import de.cassisi.hearth.ui.event.OpenNewCreateOperationWindow;
import de.cassisi.hearth.ui.event.ShowOperationView;
import de.cassisi.hearth.ui.interactor.UseCaseExecutor;
import de.cassisi.hearth.ui.presenter.AddNirsDataPresenter;
import de.cassisi.hearth.ui.presenter.CreateOperationPresenter;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.cassisi.hearth.usecase.AddNirsData;
import de.cassisi.hearth.usecase.CreateOperation;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class GuiEventController {

    private final UseCaseExecutor useCaseExecutor;
    private final AddNirsDataPresenter addNirsDataPresenter;
    private final CreateOperationPresenter createOperationPresenter;

    private final EventBus eventBus;

    public GuiEventController(UseCaseExecutor useCaseExecutor, AddNirsDataPresenter addNirsDataPresenter, CreateOperationPresenter createOperationPresenter) {
        this.useCaseExecutor = useCaseExecutor;
        this.addNirsDataPresenter = addNirsDataPresenter;
        this.createOperationPresenter = createOperationPresenter;

        // register to event bus
        this.eventBus = EventBusProvider.getEventBus();
        this.eventBus.register(this);
    }

    @Subscribe
    public void handle(AddNirsDataEvent event) {
        AddNirsData.InputData inputData = new AddNirsData.InputData();
        inputData.timestamp = event.getTimestamp();
        inputData.leftSaturation = event.getLeft();
        inputData.operationId = event.getOperationId();
        inputData.rightSaturation = event.getRight();
        this.useCaseExecutor.addNirsData(inputData, addNirsDataPresenter);
    }

    @Subscribe
    public void handle(CreateOperationEvent event) {
        CreateOperation.InputData inputData = new CreateOperation.InputData();
        inputData.localDate = event.getOperationDate();
        inputData.room = event.getRoomNr();
        this.useCaseExecutor.createOperation(inputData, createOperationPresenter);
        this.eventBus.post(new ShowOperationView());
    }

    @Subscribe
    public void handle(OpenNewCreateOperationWindow event) {
        ObjectProperty<LocalDate> localDate = new SimpleObjectProperty<>();
        StringProperty roomProperty = new SimpleStringProperty("");
        Form form = Form.of(
                Group.of(
                        Field.ofDate(localDate).label("Datum").required(true),
                        Field.ofStringType(roomProperty).label("Raum").required(true)
                )
        ).title("Der Titel");
        Stage stage = new Stage();
        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            form.persist();
            eventBus.post(new CreateOperationEvent(localDate.get(), roomProperty.get()));
            stage.close();
        });

        VBox formPane = new VBox();
        formPane.getChildren().add(new FormRenderer(form));
        formPane.getChildren().add(submitButton);
        stage.setScene(new Scene(formPane));
        stage.setWidth(400);
        stage.setHeight(300);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(event.getOwner());
        stage.show();

    }

}
