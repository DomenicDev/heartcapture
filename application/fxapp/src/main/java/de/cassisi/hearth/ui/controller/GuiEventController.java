package de.cassisi.hearth.ui.controller;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.cassisi.hearth.ui.event.*;
import de.cassisi.hearth.ui.interactor.UseCaseExecutor;
import de.cassisi.hearth.ui.navigator.Navigator;
import de.cassisi.hearth.ui.presenter.*;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.cassisi.hearth.usecase.*;
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
import java.util.stream.Collectors;

@Component
public class GuiEventController {

    private final UseCaseExecutor useCaseExecutor;
    private final AddNirsDataPresenter addNirsDataPresenter;
    private final AddAnesthesiaDataPresenter addAnesthesiaDataPresenter;
    private final AddInfusionDataPresenter addInfusionDataPresenter;
    private final CreateOperationPresenter createOperationPresenter;
    private final RefreshLatestOperationPresenter refreshLatestOperationPresenter;
    private final OperationOverviewPresenter operationOverviewPresenter;
    private final ReadHLMDataFilePresenter hlmDataFilePresenter;
    private final GenerateReportPresenter generateReportPresenter;

    private final Navigator navigator;

    private final EventBus eventBus;

    public GuiEventController(UseCaseExecutor useCaseExecutor, AddNirsDataPresenter addNirsDataPresenter, AddAnesthesiaDataPresenter addAnesthesiaDataPresenter, AddInfusionDataPresenter addInfusionDataPresenter, CreateOperationPresenter createOperationPresenter, RefreshLatestOperationPresenter refreshLatestOperationPresenter1, OperationOverviewPresenter operationOverviewPresenter, ReadHLMDataFilePresenter hlmDataFilePresenter, GenerateReportPresenter generateReportPresenter, Navigator navigator) {
        this.useCaseExecutor = useCaseExecutor;
        this.addNirsDataPresenter = addNirsDataPresenter;
        this.addAnesthesiaDataPresenter = addAnesthesiaDataPresenter;
        this.addInfusionDataPresenter = addInfusionDataPresenter;
        this.createOperationPresenter = createOperationPresenter;
        this.refreshLatestOperationPresenter = refreshLatestOperationPresenter1;
        this.operationOverviewPresenter = operationOverviewPresenter;
        this.hlmDataFilePresenter = hlmDataFilePresenter;
        this.generateReportPresenter = generateReportPresenter;
        this.navigator = navigator;

        // register to event bus
        this.eventBus = EventBusProvider.getEventBus();
        this.eventBus.register(this);
    }

    @Subscribe
    public void handle(AddNirsDataEvent event) {
        AddNirsData.InputData inputData = new AddNirsData.InputData();
        inputData.timestamp = event.getTimestamp();
        inputData.leftSaturation = event.getLeft();
        inputData.rightSaturation = event.getRight();
        inputData.operationId = event.getOperationId();
        this.useCaseExecutor.addNirsData(inputData, addNirsDataPresenter);
    }

    @Subscribe
    public void handle(AddAnesthesiaDataEvent event) {
        AddAnesthesiaData.InputData inputData = new AddAnesthesiaData.InputData();
        inputData.timestamp = event.getTimestamp();
        inputData.depthOfAnesthesia = event.getDepthOfAnesthesia();
        inputData.operationId = event.getOperationId();
        getUseCaseExecutor().addAnesthesiaData(inputData, addAnesthesiaDataPresenter);
    }

    @Subscribe
    public void handle(AddInfusionDataEvent event) {
        AddInfusionData.InputData inputData = new AddInfusionData.InputData();
        inputData.operationId = event.getOperationId();
        inputData.timestamp = event.getTimestamp();
        inputData.infusionData = event.getPerfusions().stream().map(data -> new AddInfusionData.InputData.PerfusorInput(data.name, data.rate)).collect(Collectors.toList());
        getUseCaseExecutor().addInfusionData(inputData, addInfusionDataPresenter);
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

    @Subscribe
    public void handle(RefreshLatestOperationDataEvent event) {
        FindAllOperations.InputData inputData = new FindAllOperations.InputData();
        inputData.limit = event.getLimit();
        inputData.sortByLatest = event.isSortByLatest();
        this.useCaseExecutor.findAllOperations(inputData, refreshLatestOperationPresenter);
    }

    @Subscribe
    public void handle(OpenOperationOverviewEvent event) {
        FindOperation.InputData inputData = new FindOperation.InputData();
        inputData.operationId = event.getOperationId();
        this.useCaseExecutor.findOperation(inputData, operationOverviewPresenter);
        this.navigator.showOperation();
    }

    @Subscribe
    public void handle(AddHlmFileToOperationEvent event) {
        ReadHLMDataFile.InputData inputData = new ReadHLMDataFile.InputData();
        inputData.operationId = event.getOperationId();
        inputData.hlmFile = event.getHlmFile();
        this.useCaseExecutor.readHlmDataFile(inputData, hlmDataFilePresenter);
    }

    @Subscribe
    public void handle(GenerateReportEvent event) {
        GenerateReport.InputData inputData = new GenerateReport.InputData();
        inputData.operationId = event.getOperationId();
        getUseCaseExecutor().generateReportEvent(inputData, generateReportPresenter);
    }

    private UseCaseExecutor getUseCaseExecutor() {
        return this.useCaseExecutor;
    }
}
