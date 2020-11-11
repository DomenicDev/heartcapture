package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.view.dashboard.DashboardViewModel;
import de.cassisi.hearth.ui.view.dashboard.LatestOperation;
import de.cassisi.hearth.ui.utils.PresenterUtils;
import de.cassisi.hearth.usecase.FindAllOperations;
import javafx.scene.control.TreeItem;
import org.springframework.stereotype.Component;

@Component
public class RefreshLatestOperationPresenter extends UseCasePresenter<FindAllOperations.OutputData> {

    private final DashboardViewModel viewModel;

    public RefreshLatestOperationPresenter(DashboardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(FindAllOperations.OutputData outputData) {
        TreeItem<LatestOperation> treeItem = PresenterUtils.convert(outputData.simpleOperationDataList);
        viewModel.getLatestOperation().setValue(treeItem);
    }

}
