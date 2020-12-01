package de.cassisi.hearth.ui.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.cassisi.hearth.tools.recorder.detect.AutoPortDetector;
import de.cassisi.hearth.tools.recorder.detect.DetectionResult;
import de.cassisi.hearth.ui.enums.MessageType;
import de.cassisi.hearth.ui.event.*;
import de.cassisi.hearth.ui.exception.ExceptionHandler;
import de.cassisi.hearth.ui.interactor.UseCaseExecutor;
import de.cassisi.hearth.ui.navigator.Navigator;
import de.cassisi.hearth.ui.presenter.*;
import de.cassisi.hearth.ui.recorder.RecordingController;
import de.cassisi.hearth.ui.utils.DialogCreator;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.cassisi.hearth.usecase.*;
import de.cassisi.hearth.usecase.exception.*;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.stream.Collectors;

@Component
public class GuiEventController {

    private static final Logger LOGGER = Logger.getLogger(GuiEventController.class);

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
    private final LoggingPresenter loggingPresenter;
    private final LockOperationPresenter lockOperationPresenter;
    private final EditOperationInformationPresenter editOperationInformationPresenter;

    private final Navigator navigator;
    private final RecordingController recordingController;

    private final EventBus eventBus;

    private final ExceptionHandler exceptionHandler;
    private final Executor executor = new Executor();


    public GuiEventController(UseCaseExecutor useCaseExecutor, AddNirsDataPresenter addNirsDataPresenter, AddAnesthesiaDataPresenter addAnesthesiaDataPresenter, AddInfusionDataPresenter addInfusionDataPresenter, CreateOperationPresenter createOperationPresenter, RefreshLatestOperationPresenter refreshLatestOperationPresenter1, ReadHLMDataFilePresenter hlmDataFilePresenter, GenerateReportPresenter generateReportPresenter, RecordingStatePresenter recordingStatePresenter, RefreshSerialPortPresenter refreshSerialPortPresenter, AutoDetectPortStartPresenter autoDetectPortStartPresenter, AutoDetectResultPresenter autoDetectResultPresenter, RefreshOperationViewDataPresenter refreshOperationViewDataPresenter, OperationViewPresenter operationViewPresenter, GenerateDashboardStatisticPresenter generateDashboardStatisticPresenter, LoggingPresenter loggingPresenter, LockOperationPresenter lockOperationPresenter, EditOperationInformationPresenter editOperationInformationPresenter, Navigator navigator, RecordingController recordingController, ExceptionHandler exceptionHandler) {
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
        this.loggingPresenter = loggingPresenter;
        this.lockOperationPresenter = lockOperationPresenter;
        this.editOperationInformationPresenter = editOperationInformationPresenter;
        this.navigator = navigator;
        this.recordingController = recordingController;
        this.exceptionHandler = exceptionHandler;

        // register to event bus
        this.eventBus = EventBusProvider.getEventBus();
        this.eventBus.register(this);

        // start executor thread
        executor.start();
    }

    @Subscribe
    public void handle(AddNirsDataEvent event) {
        addJob(() -> {
            AddNirsData.InputData inputData = new AddNirsData.InputData();
            inputData.timestamp = event.getTimestamp();
            inputData.leftSaturation = event.getLeft();
            inputData.rightSaturation = event.getRight();
            inputData.operationId = event.getOperationId();
            getUseCaseExecutor().addNirsData(inputData, addNirsDataPresenter);
        });

    }

    @Subscribe
    public void handle(AddAnesthesiaDataEvent event) {
        addJob(() -> {
            AddAnesthesiaData.InputData inputData = new AddAnesthesiaData.InputData();
            inputData.timestamp = event.getTimestamp();
            inputData.depthOfAnesthesia = event.getDepthOfAnesthesia();
            inputData.operationId = event.getOperationId();
            getUseCaseExecutor().addAnesthesiaData(inputData, addAnesthesiaDataPresenter);
        });
    }

    @Subscribe
    public void handle(AddInfusionDataEvent event) {
        addJob(() -> {
            AddInfusionData.InputData inputData = new AddInfusionData.InputData();
            inputData.operationId = event.getOperationId();
            inputData.timestamp = event.getTimestamp();
            inputData.infusionData = event.getPerfusions().stream().map(data -> new AddInfusionData.InputData.PerfusorInput(data.name, data.rate)).collect(Collectors.toList());
            getUseCaseExecutor().addInfusionData(inputData, addInfusionDataPresenter);
        });
    }

    @Subscribe
    public void handle(CreateOperationEvent event) {
        addJob(() -> {
            CreateOperation.InputData inputData = new CreateOperation.InputData();
            inputData.localDate = event.getOperationDate();
            inputData.room = event.getRoomNr();
            getUseCaseExecutor().createOperation(inputData, createOperationPresenter);
            Platform.runLater(navigator::showOperation);
        });
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
        addJob(() -> {
            FindAllOperations.InputData inputData = new FindAllOperations.InputData();
            inputData.limit = event.getLimit();
            inputData.sortByLatest = event.isSortByLatest();
            getUseCaseExecutor().findAllOperations(inputData, refreshLatestOperationPresenter);
        });
    }

    @Subscribe
    public void handle(OpenOperationOverviewEvent event) {
        addJob(() -> {
                },
                () -> {
                    FindFullOperation.InputData inputData = new FindFullOperation.InputData();
                    inputData.operationId = event.getOperationId();
                    getUseCaseExecutor().findFullOperation(inputData, operationViewPresenter);

                },
                navigator::showOperation);
    }

    @Subscribe
    public void handle(AddHlmFileToOperationEvent event) {
        addHeavyTask(
                event.getOwner(),
                () -> {
                    ReadHLMDataFile.InputData inputData = new ReadHLMDataFile.InputData();
                    inputData.operationId = event.getOperationId();
                    inputData.hlmFile = event.getHlmFile();
                    getUseCaseExecutor().readHlmDataFile(inputData, hlmDataFilePresenter);
                });
    }

    @Subscribe
    public void handle(GenerateReportEvent event) {
        addHeavyTask(
                event.getWindow(),
                () -> {
                    GenerateReport.InputData inputData = new GenerateReport.InputData();
                    inputData.operationId = event.getOperationId();
                    getUseCaseExecutor().generateReport(inputData, generateReportPresenter);
                });
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

    @Subscribe
    public void handle(RefreshLoggingDataEvent event) {
        executePresenter(loggingPresenter, event);
    }

    @Subscribe
    public void handle(LockOperationEvent event) {
        addJob(() -> {
            LockOperation.InputData inputData = new LockOperation.InputData();
            inputData.operationId = event.getOperationId();
            inputData.locked = event.isLocked();
            getUseCaseExecutor().setLockState(inputData, lockOperationPresenter);
        });
    }

    @Subscribe
    public void handle(EditOperationInformationEvent event) {
        addJob(() -> {
            EditOperationInformation.InputData inputData = new EditOperationInformation.InputData();
            inputData.operationId = event.getOperationId();
            inputData.operationDate = event.getOperationDate();
            inputData.operationRoom = event.getOperationRoom();

            getUseCaseExecutor().editOperationInformation(inputData, editOperationInformationPresenter);
        });
    }

    //-----------------------------------------
    /*
            HELPER METHODS
     */

    private UseCaseExecutor getUseCaseExecutor() {
        return this.useCaseExecutor;
    }

    private <T> void executePresenter(FXPresenter<T> presenter, T data) {
        presenter.present(data);
    }

    private void executePresenter(FXPresenter<Void> presenter) {
        presenter.present(null);
    }

    private void addJob(Runnable preUI, Runnable task, Runnable postUI) {
        executor.add(() -> {
            // run pre gui code first
            Platform.runLater(preUI);

            // run task
            try {
                task.run();
            } catch (OperationNotFoundException e) {
                LOGGER.error("operation with id #" + e.getId() + " not found", e);
                exceptionHandler.handle(e);
            } catch (InputValidationException e) {
                LOGGER.error("specified input for use case was not valid", e);
                exceptionHandler.handle(e);
            } catch (OperationLockException e) {
                LOGGER.error("operation is locked. operation id #" + e.getId(), e);
                exceptionHandler.handle(e);
            } catch (ReadHLMFileException e) {
                LOGGER.error("error during read of hlm file: ", e);
                exceptionHandler.handle(e);
            } catch (MissingHlmFileException e) {
                LOGGER.error("missing hlm data for operation", e);
                exceptionHandler.handle(e);
            } catch (ReportGenerationException e) {
                LOGGER.error("report could not be generated: ", e);
                exceptionHandler.handle(e);
            } catch (Exception e) {
                LOGGER.error("There was an unknown error during job execution : " + e);
                exceptionHandler.handleGenericException(e);
            }

            // run post code
            Platform.runLater(postUI);
        });
    }

    private void addHeavyTask(Window owner, Runnable task) {
        Stage progressWindow = DialogCreator.showModalProgressIndicatorWindow(owner);
        addJob(
                progressWindow::show,
                task,
                progressWindow::close
        );
    }

    private void addJob(Runnable task) {
        executor.add(task::run);
    }

    private class Executor implements Runnable {

        private final Queue<Job> jobs = new ConcurrentLinkedDeque<>();
        private final Thread executorThread = new Thread(this);
        private boolean active = false;

        private Executor() {
            this.executorThread.setName("UseCase-Executor");
            this.executorThread.setDaemon(true);
        }

        void add(Job job) {
            if (job != null) {
                this.jobs.add(job);
            }
        }

        void start() {
            this.active = true;
            this.executorThread.start();
        }

        @Override
        public void run() {
            while (active) {
                Job job = jobs.poll();
                try {
                    if (job != null) {
                        job.execute();
                    }
                } catch (Exception e) {
                    LOGGER.error("unknown error during job execution: ", e);
                    exceptionHandler.handleGenericException(e);
                }

                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    LOGGER.error(e);
                }
            }
        }
    }

    private interface Job {

        void execute();

    }
}
