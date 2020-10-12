package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.dashboard.MyViewModel;
import de.cassisi.hearth.usecase.CreateOperation;
import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateOperationPresenter implements OutputHandler<CreateOperation.OutputData> {

    private final MyViewModel myViewModel;

    public CreateOperationPresenter(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    @Override
    public void handle(CreateOperation.OutputData outputData) {
        myViewModel.testMessage.setValue(outputData.result);
    }
}
