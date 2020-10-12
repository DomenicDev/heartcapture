package de.cassisi.hearth.ui.controller;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import de.cassisi.hearth.ui.event.AddNirsDataEvent;
import de.cassisi.hearth.ui.event.CreateOperationEvent;
import de.cassisi.hearth.ui.interactor.UseCaseExecutor;
import de.cassisi.hearth.ui.presenter.AddNirsDataPresenter;
import de.cassisi.hearth.ui.presenter.CreateOperationPresenter;
import de.cassisi.hearth.ui.utils.EventBusProvider;
import de.cassisi.hearth.usecase.AddNirsData;
import de.cassisi.hearth.usecase.CreateOperation;
import org.springframework.stereotype.Component;

@Component
public class GuiEventController {

    private final UseCaseExecutor useCaseExecutor;
    private final AddNirsDataPresenter addNirsDataPresenter;
    private final CreateOperationPresenter createOperationPresenter;

    public GuiEventController(UseCaseExecutor useCaseExecutor, AddNirsDataPresenter addNirsDataPresenter, CreateOperationPresenter createOperationPresenter) {
        this.useCaseExecutor = useCaseExecutor;
        this.addNirsDataPresenter = addNirsDataPresenter;
        this.createOperationPresenter = createOperationPresenter;

        // register to event bus
        EventBus eventBus = EventBusProvider.getEventBus();
        eventBus.register(this);
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
    }


}
