package de.cassisi.heartcapture.ui.presenter;

import de.cassisi.heartcapture.ui.view.operation.overview.OperationTableData;
import de.cassisi.heartcapture.ui.view.operation.overview.OperationViewViewModel;
import de.cassisi.heartcapture.ui.utils.PresenterUtils;
import de.cassisi.heartcapture.usecase.FindAllOperations;
import javafx.scene.control.TreeItem;
import org.springframework.stereotype.Component;

@Component
public class RefreshOperationViewDataPresenter extends UseCasePresenter<FindAllOperations.OutputData> {

    private final OperationViewViewModel viewModel;

    public RefreshOperationViewDataPresenter(OperationViewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void runOnUI(FindAllOperations.OutputData data) {
        TreeItem<OperationTableData> items = PresenterUtils.convertToOperationViewTableData(data.simpleOperationDataList);
        viewModel.getOperationData().set(items);
    }
}
