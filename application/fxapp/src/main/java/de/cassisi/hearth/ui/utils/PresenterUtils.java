package de.cassisi.hearth.ui.utils;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.cassisi.hearth.ui.view.dashboard.LatestOperation;
import de.cassisi.hearth.ui.view.operation.PerfusionUIData;
import de.cassisi.hearth.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.hearth.ui.view.operation.overview.OperationTableData;
import de.cassisi.hearth.usecase.AddInfusionData;
import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome.FontAwesome;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class PresenterUtils {

    // DATE TIME CONSTANTS
    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    // FONT ICON CONSTANTS
    private static final Ikon AVAILABLE_ICON = FontAwesome.CHECK;
    private static final Ikon UNAVAILABLE_ICON = FontAwesome.CLOSE;
    private static final Paint AVAILABLE_COLOR = Paint.valueOf("#0f6114");
    private static final Paint UNAVAILABLE_COLOR = Paint.valueOf("#601a0f");

    public static String formatLocalDate(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        return localDate.format(LOCAL_DATE_FORMATTER);
    }

    public static TreeItem<LatestOperation> convert(List<SimpleOperationData> operationDataList) {
        ObservableList<LatestOperation> items = FXCollections.observableArrayList();
        operationDataList.forEach(data ->
                items.add(new LatestOperation(data.getId(), formatLocalDate(data.getDate()), data.getRoom())));
        return new RecursiveTreeItem<>(items, RecursiveTreeObject::getChildren);
    }

    public static TreeItem<PerfusionUIData> convertInfusionData(List<AddInfusionData.OutputData.PerfusorData> infusionList) {
        ObservableList<PerfusionUIData> items = FXCollections.observableArrayList();
        infusionList.forEach(data ->
                items.add(new PerfusionUIData(data.name, data.rate)));
        return new RecursiveTreeItem<>(items, RecursiveTreeObject::getChildren);
    }

    public static void present(OperationOverviewViewModel viewModel, SimpleOperationData data) {
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

    public static TreeItem<OperationTableData> convertToOperationViewTableData(List<SimpleOperationData> data) {
        ObservableList<OperationTableData> items = FXCollections.observableArrayList();
        data.forEach(d -> {
            boolean liveDataAvailable = d.isBisDataAvailable() && d.isNirsDataAvailable() && d.isInfusionDataAvailable();
            items.add(new OperationTableData(
                    d.getId(),
                    formatLocalDate(d.getDate()),
                    d.getRoom(),
                    liveDataAvailable,
                    d.isHlmDataAvailable()));
        });
        return new RecursiveTreeItem<>(items, RecursiveTreeObject::getChildren);
    }

    private static void updateFontIcon(ObjectProperty<Ikon> iconCode, ObjectProperty<Paint> iconColor, boolean available) {
        Paint color = available ? AVAILABLE_COLOR : UNAVAILABLE_COLOR;
        Ikon icon = available ? AVAILABLE_ICON : UNAVAILABLE_ICON;

        iconCode.set(icon);
        iconColor.set(color);
    }

}
