package de.cassisi.hearth.ui.utils;

import de.cassisi.hearth.ui.data.LatestOperationTableData;
import de.cassisi.hearth.usecase.dto.SimpleOperationData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class PresenterUtils {

    private static final DateTimeFormatter LOCAL_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

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

}
