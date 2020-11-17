package de.cassisi.hearth.ui.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.cassisi.hearth.tools.recorder.detect.AutoPortDetector;
import de.cassisi.hearth.tools.recorder.detect.DetectionResult;
import de.cassisi.hearth.ui.enums.MessageType;
import de.cassisi.hearth.ui.event.*;
import de.cassisi.hearth.ui.interactor.UseCaseExecutor;
import de.cassisi.hearth.ui.navigator.Navigator;
import de.cassisi.hearth.ui.presenter.*;
import de.cassisi.hearth.ui.recorder.RecordingController;
import de.cassisi.hearth.ui.utils.DialogCreator;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.cassisi.hearth.usecase.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GuiEventController {

    private final UseCaseExecutor useCaseExecutor;
    private final AddNirsDataPresenter addNirsDataPresenter;
    private final AddAnesthesiaDataPresenter addAnesthesiaDataPresenter;
    private final AddInfusionDataPresenter addInfusionDataPresenter;
    private final CreateOperationPresenter createOperationPresenter;
    private final RefreshLatestOperationPresenter refreshLatestOperationPresenter;
    private final ReadHLMDataFilePresenter hlmDataFilePresenter;
    private final GenerateReportPresenter generateReportPresenter;
    private final RecordingStatePresenter recordingStatePresenter;
    private final RefreshSerialPortPresenter refreshSerialPortPresenter;
    private final AutoDetectPortStartPresenter autoDetectPortStartPresenter;
    private final AutoDetectResultPresenter autoDetectResultPresenter;
    private final RefreshOperationViewDataPresenter refreshOperationViewDataPresenter;
    private final OperationViewPresenter operationViewPresenter;
    private final GenerateDashboardStatisticPresenter generateDashboardStatisticPresenter;

    private final Navigator navigator;
    private final RecordingController recordingController;

    private final EventBus eventBus;

    public GuiEventController(UseCaseExecutor useCaseExecutor, AddNirsDataPresenter addNirsDataPresenter, AddAnesthesiaDataPresenter addAnesthesiaDataPresenter, AddInfusionDataPresenter addInfusionDataPresenter, CreateOperationPresenter createOperationPresenter, RefreshLatestOperationPresenter refreshLatestOperationPresenter1, ReadHLMDataFilePresenter hlmDataFilePresenter, GenerateReportPresenter generateReportPresenter, RecordingStatePresenter recordingStatePresenter, RefreshSerialPortPresenter refreshSerialPortPresenter, AutoDetectPortStartPresenter autoDetectPortStartPresenter, AutoDetectResultPresenter autoDetectResultPresenter, RefreshOperationViewDataPresenter refreshOperationViewDataPresenter, OperationViewPresenter operationViewPresenter, GenerateDashboardStatisticPresenter generateDashboardStatisticPresenter, Navigator navigator, RecordingController recordingController) {
        this.useCaseExecutor = useCaseExecutor;
        this.addNirsDataPresenter = addNirsDataPresenter;
        this.addAnesthesiaDataPresenter = addAnesthesiaDataPresenter;
        this.addInfusionDataPresenter = addInfusionDataPresenter;
        this.createOperationPresenter = createOperationPresenter;
        this.refreshLatestOperationPresenter = refreshLatestOperationPresenter1;
        this.hlmDataFilePresenter = hlmDataFilePresenter;
        this.generateReportPresenter = generateReportPresenter;
        this.recordingStatePresenter = recordingStatePresenter;
        this.refreshSerialPortPresenter = refreshSerialPortPresenter;
        this.autoDetectPortStartPresenter = autoDetectPortStartPresenter;
        this.autoDetectResultPresenter = autoDetectResultPresenter;
        this.refreshOperationViewDataPresenter = refreshOperationViewDataPresenter;
        this.operationViewPresenter = operationViewPresenter;
        this.generateDashboardStatisticPresenter = generateDashboardStatisticPresenter;
        this.navigator = navigator;
        this.recordingController = recordingController;

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
        this.navigator.showOperation();
    }

    @Subscribe
    public void handle(OpenNewCreateOperationWindow event) {
        DialogCreator.showCreateOperationDialog(
                event.getOwner(),
                (operationDate, room) -> eventBus.post(new CreateOperationEvent(operationDate, room))
        );
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
        /*
        FindOperation.InputData inputData = new FindOperation.InputData();
        inputData.operationId = event.getOperationId();
        this.useCaseExecutor.findOperation(inputData, operationOverviewPresenter);
         */

        FindFullOperation.InputData inputData = new FindFullOperation.InputData();
        inputData.operationId = event.getOperationId();
        getUseCaseExecutor().findFullOperation(inputData, operationViewPresenter);

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

    @Subscribe
    public void handle(ShowMessageEvent event) {
        String messageKey = event.getMessageKey();
        MessageType messageType = event.getMessageType();
        navigator.showMessage(messageKey, messageType);
    }

    @Subscribe
    public void handle(StartRecordingEvent event) {
        boolean started = this.recordingController.startRecording(event);
        if (started) {
            recordingStatePresenter.handleStarted();
        }
    }

    @Subscribe
    public void handle(StopRecordingEvent event) {
        boolean stopped = this.recordingController.stopRecording();
        if (stopped) {
            recordingStatePresenter.handleStopped();
        }
        /*
        FindOperation.InputData inputData = new FindOperation.InputData();
        inputData.operationId = event.getOperationId();
        this.useCaseExecutor.findOperation(inputData, operationOverviewPresenter);
         */
        eventBus.post(new OpenOperationOverviewEvent(event.getOperationId()));
    }

    @Subscribe
    public void handle(AutoDetectEvent event) {
        new Thread(() -> {
            executePresenter(autoDetectPortStartPresenter);
            AutoPortDetector detector = new AutoPortDetector();
            DetectionResult result = detector.detectSerialPorts();
            executePresenter(autoDetectResultPresenter, result);
        }).start();
    }

    @Subscribe
    public void handle(RefreshOperationViewDataEvent event) {
        FindAllOperations.InputData inputData = new FindAllOperations.InputData();
        inputData.sortByLatest = true;
        inputData.limit = Integer.MAX_VALUE;
        getUseCaseExecutor().findAllOperations(inputData, refreshOperationViewDataPresenter);
    }

    @Subscribe
    public void handle(OpenRecordingDialogEvent event) {
        DialogCreator.showRecordingDialog(event.getOwner(), event.getOperationId());
    }

    @Subscribe
    public void handle(RefreshSerialPortEvent event) {
        executePresenter(refreshSerialPortPresenter, null);
    }

    @Subscribe
    public void handle(RefreshDashboardStatisticEvent event) {
        getUseCaseExecutor().generateStatistic(generateDashboardStatisticPresenter);
    }

    private UseCaseExecutor getUseCaseExecutor() {
        return this.useCaseExecutor;
    }

    private <T> void executePresenter(FXPresenter<T> presenter, T data) {
        presenter.present(data);
    }

    private void executePresenter(FXPresenter<Void> presenter) {
        presenter.present(null);
    }
}
