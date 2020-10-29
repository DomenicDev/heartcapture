package de.cassisi.hearth.ui.presenter;

import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome.FontAwesome;
import org.springframework.stereotype.Component;

import static de.cassisi.hearth.usecase.FindOperation.OutputData;

@Component
public class OperationOverviewPresenter extends UseCasePresenter<OutputData> {

    private static final Paint AVAILABLE_COLOR = Paint.valueOf("#0f6114");
    private static final Paint NOT_AVAILABLE_COLOR = Paint.valueOf("#601a0f");

    private final OperationOverviewViewModel viewModel;


    public OperationOverviewPresenter(OperationOverviewViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void runOnUI(OutputData outputData) {
        SimpleOperationData data = outputData.operationData;

        // General
        viewModel.idProperty().setValue(data.getId());
        viewModel.roomProperty().setValue(data.getRoom());
        viewModel.dateProperty().setValue(data.getDate());
        viewModel.titleLabel().setValue("Operation #" + data.getId());

        // Status
        updateFontIcon(viewModel.getBisAvailableIconCode(), viewModel.getBisAvailableIconColor(), data.isBisDataAvailable());
        updateFontIcon(viewModel.getNirsAvailableIconCode(), viewModel.getNirsAvailableIconColor(), data.isNirsDataAvailable());
        updateFontIcon(viewModel.getInfusionAvailableIconCode(), viewModel.getInfusionAvailableIconColor(), data.isInfusionDataAvailable());
        updateFontIcon(viewModel.getHlmAvailableIconCode(), viewModel.getHlmAvailableIconColor(), data.isHlmDataAvailable());
    }

    private void updateFontIcon(ObjectProperty<Ikon> iconCode, ObjectProperty<Paint> iconColor, boolean available) {
        Paint color = available ? AVAILABLE_COLOR : NOT_AVAILABLE_COLOR;
        Ikon icon = available ? FontAwesome.CHECK : FontAwesome.MINUS_CIRCLE;

        iconCode.set(icon);
        iconColor.set(color);
    }

}
