package de.cassisi.hearth.ui.utils;

import de.cassisi.hearth.ui.data.LatestOperationTableData;
import de.cassisi.hearth.ui.operation.OperationOverviewViewModel;
import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome.FontAwesome;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public static List<LatestOperationTableData> convert(List<SimpleOperationData> operationDataList) {
        List<LatestOperationTableData> tableDataList = new ArrayList<>();
        operationDataList.forEach(op -> {
            String dateFormatted = formatLocalDate(op.getDate());
            LatestOperationTableData tableData = new LatestOperationTableData(op.getId(), dateFormatted, op.getRoom());
            tableDataList.add(tableData);
        });
        return tableDataList;
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


    private static void updateFontIcon(ObjectProperty<Ikon> iconCode, ObjectProperty<Paint> iconColor, boolean available) {
        Paint color = available ? AVAILABLE_COLOR : UNAVAILABLE_COLOR;
        Ikon icon = available ? AVAILABLE_ICON : UNAVAILABLE_ICON;

        iconCode.set(icon);
        iconColor.set(color);
    }

}
