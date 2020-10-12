package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.dashboard.MyViewModel;
import de.cassisi.hearth.usecase.AddNirsData;
import de.cassisi.hearth.usecase.output.OutputHandler;
import org.springframework.stereotype.Component;

@Component
public class AddNirsDataPresenter implements OutputHandler<AddNirsData.OutputData> {

    private MyViewModel myViewModel;

    public AddNirsDataPresenter(MyViewModel myViewModel) {
        this.myViewModel = myViewModel;
    }

    @Override
    public void handle(AddNirsData.OutputData outputData) {
        boolean saved = outputData.saved;
        myViewModel.testMessage.setValue("new state: " + saved);
    }
}
