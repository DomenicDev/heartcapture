package de.cassisi.hearth.ui.utils;

import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import de.cassisi.hearth.ui.lang.LanguageResourceProvider;
import de.cassisi.hearth.ui.view.dashboard.LatestOperation;
import de.cassisi.hearth.ui.view.operation.OperationOverviewViewModel;
import de.cassisi.hearth.ui.view.operation.PerfusionUIData;
import de.cassisi.hearth.ui.view.operation.overview.OperationTableData;
import de.cassisi.hearth.usecase.AddInfusionData;
import de.cassisi.hearth.usecase.dto.BISDataDTO;
import de.cassisi.hearth.usecase.dto.CompleteOperationDataDTO;
import de.cassisi.hearth.usecase.dto.NirsDataDTO;
import de.cassisi.hearth.usecase.dto.SimpleOperationData;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome.FontAwesome;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public final class PresenterUtils {

    private static String getString(String key) {
        return LanguageResourceProvider.getLanguageBundle().getString(key);
    }

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

    public static void present(CompleteOperationDataDTO data, OperationOverviewViewModel viewModel) {
        viewModel.idProperty().setValue(data.getId());
        viewModel.roomProperty().setValue(data.getRoom());
        viewModel.dateProperty().setValue(data.getDate());
        viewModel.titleLabel().setValue("Operation #" + data.getId());

        // Status
        updateFontIcon(viewModel.getBisAvailableIconCode(), viewModel.getBisAvailableIconColor(), data.isBisDataAvailable());
        updateFontIcon(viewModel.getNirsAvailableIconCode(), viewModel.getNirsAvailableIconColor(), data.isNirsDataAvailable());
        updateFontIcon(viewModel.getInfusionAvailableIconCode(), viewModel.getInfusionAvailableIconColor(), data.isInfusionDataAvailable());
        updateFontIcon(viewModel.getHlmAvailableIconCode(), viewModel.getHlmAvailableIconColor(), data.isHlmDataAvailable());

        // Nirs Chart
        List<NirsDataDTO> nirsData = data.getNirsData();
        nirsData.sort(Comparator.comparing(NirsDataDTO::getTimestamp));

        XYChart.Series<String, Integer> nirsLeftSeries = new XYChart.Series<>();
        nirsLeftSeries.setName("NIRS (L)");

        XYChart.Series<String, Integer> nirsRightSeries = new XYChart.Series<>();
        nirsRightSeries.setName("NIRS (R)");

        int size = nirsData.size();
        int comp = size / 150;
        int counter = 0;

        for (NirsDataDTO e : nirsData) {
            int v = comp <= 0 ? 0 : counter % comp;
            counter++;
            if (v == 0) {
                String timestamp = e.getTimestamp().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                nirsLeftSeries.getData().add(new XYChart.Data<>(timestamp, e.getLeft()));
                nirsRightSeries.getData().add(new XYChart.Data<>(timestamp, e.getRight()));
            }

        }
        ObservableList<XYChart.Series<String, Integer>> resultData = FXCollections.observableArrayList();
        resultData.add(nirsLeftSeries);
        resultData.add(nirsRightSeries);

        viewModel.getNirsChartData().setValue(resultData);

        // BIS CHART
        List<BISDataDTO> bisDataList = data.getBisData();
        bisDataList.sort(Comparator.comparing(BISDataDTO::getTimestamp));

        XYChart.Series<String, Integer> bisSeries = new XYChart.Series<>();
        bisSeries.setName("BIS");

        for (BISDataDTO bis : bisDataList) {
            String timestamp = bis.getTimestamp().toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            bisSeries.getData().add(new XYChart.Data<>(timestamp, (int)(bis.getBsi())));
        }

        ObservableList<XYChart.Series<String, Integer>> bisSeriesData = FXCollections.observableArrayList();
        bisSeriesData.add(bisSeries);

        viewModel.getBisChartData().setValue(bisSeriesData);


    }


}
