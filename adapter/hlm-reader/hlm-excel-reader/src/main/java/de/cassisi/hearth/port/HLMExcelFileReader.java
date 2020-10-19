package de.cassisi.hearth.port;

import com.poiji.bind.Poiji;
import com.poiji.option.PoijiOptions;
import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.port.data.*;
import de.cassisi.hearth.port.data.MachineExcelData;
import de.cassisi.hearth.port.util.ExcelToEntityConverter;
import de.cassisi.hearth.usecase.port.HLMFileReader;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HLMExcelFileReader implements HLMFileReader {

    private static final HashSet<String> FIRST_CELL_TABLE_INDICATOR = new HashSet<>(Arrays.asList("NR", "PROTNR", "PATID", "EKZNr"));
    private static final String TEMPORARY_FILE_NAME = "SEPARATED_HLM_DATA_FILE.xlsx";
    private static final int THRESHOLD_HOURS_SAME_DAY = 16; // hours


    @Override
    public HLMData readHLMFile(File file) {
        try {
            // create workbook instance from excel file
            Workbook originalWorkbook = WorkbookFactory.create(file);

            // all data should be contained in the very first sheet
            Sheet dataSheet = originalWorkbook.getSheetAt(0);

            // the file consists out of several semantic tables within one data sheet
            // later we want each table to be in one sheet, so processing the data is a lot easier
            List<InnerTable> innerTables = getInnerTables(dataSheet);

            // create own workbook and store each inner table in one sheet
            File tableData = writeInnerTablesToFile(innerTables);

            // we use custom settings when it comes to processing the excel data and converting them to POJOs
            PoijiOptions options = getDefaultPoijiOptions();

            // next we need to fixDateTime and "fix" the data before further use:
            // In some tables we need to correct the timestamps since only the time but not the date is stored
            // There is a meta data table which we can extract the operation date from.
            MetaExcelData metaData = Poiji.fromExcel(tableData, MetaExcelData.class, options).get(0);
            LocalDate operationDate = metaData.getOperationDate();

            // load hlm data
            List<HLMParamsExcelData> hlmParamsExcelData = Poiji.fromExcel(tableData, HLMParamsExcelData.class, options);
            List<EventExcelData> eventExcelData = Poiji.fromExcel(tableData, EventExcelData.class, options);
            List<BloodSampleExcelData> bloodSampleExcelData = Poiji.fromExcel(tableData, BloodSampleExcelData.class, options);
            MachineExcelData machineExcelData = Poiji.fromExcel(tableData, MachineExcelData.class, options).get(0);
            List<DiagnosisExcelData> diagnosisExcelData = Poiji.fromExcel(tableData, DiagnosisExcelData.class, options);
            List<OperationExcelData> operationExcelData = Poiji.fromExcel(tableData, OperationExcelData.class, options);
            PatientExcelData patientExcelData = Poiji.fromExcel(tableData, PatientExcelData.class, options).get(0);
            PatientAdditionalExcelData patientAdditionalExcelData = Poiji.fromExcel(tableData, PatientAdditionalExcelData.class, options).get(0);
            List<RiskFactorsExcelData> riskFactorsExcelData = Poiji.fromExcel(tableData, RiskFactorsExcelData.class, options);
            List<PrimingExcelData> primingExcelDataList = Poiji.fromExcel(tableData, PrimingExcelData.class, options);
            PrimingExcelData primingExcelData = (primingExcelDataList.isEmpty()) ? null : primingExcelDataList.get(0);

            // we start by setting our initial timestamp by hand
            // regarding to staff the first row from params data matches the date from the meta data
            HLMParamsExcelData initialEntry = hlmParamsExcelData.get(0);
            LocalDateTime initDateTime = LocalDateTime.of(operationDate, initialEntry.getTimestamp().toLocalTime());
            initialEntry.setTimestamp(initDateTime);

            // from now on every chronological data can be corrected
            fixDateTime(hlmParamsExcelData, initDateTime);
            fixDateTime(eventExcelData, initDateTime);
            fixDateTime(bloodSampleExcelData, initDateTime);


            // create HLM Data object and return data
            List<HLMEventData> eventData = ExcelToEntityConverter.convertToEventData(eventExcelData);
            List<HlmParamData> paramData = ExcelToEntityConverter.convertToParamData(hlmParamsExcelData);
            List<HlmBloodSample> bloodSampleData = ExcelToEntityConverter.convertToBloodSampleData(bloodSampleExcelData);

            DiagnosisData diagnosisData = ExcelToEntityConverter.convertToDiagnosisData(diagnosisExcelData);
            HlmOperationData hlmOperationData = ExcelToEntityConverter.convertToOperationData(operationExcelData);
            RiskFactorData riskFactorData = ExcelToEntityConverter.convertToRiskFactorData(riskFactorsExcelData);
            PatientData patientData = ExcelToEntityConverter.convertToPatientData(patientExcelData, patientAdditionalExcelData);
            MachineData machineData = ExcelToEntityConverter.convertToMachineData(machineExcelData);
            PrimingComposition primingComposition = ExcelToEntityConverter.convertToPrimingComposition(primingExcelData);

            // close input stream and delete helper file
            originalWorkbook.close();
            tableData.delete();


            return HLMData.builder()
                    .eventList(eventData)
                    .paramData(paramData)
                    .diagnosisData(diagnosisData)
                    .bloodSamples(bloodSampleData)
                    .operationData(hlmOperationData)
                    .riskFactorData(riskFactorData)
                    .patientData(patientData)
                    .machineData(machineData)
                    .primingComposition(primingComposition)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private <T extends ChronologicalData> void fixDateTime(List<T> sampleData, LocalDateTime initialDateTime) {
        sampleData.forEach(data -> {
            int index = sampleData.indexOf(data);
            LocalTime currentTime = data.getTimestamp().toLocalTime();
            if (index == 0) {
                data.setTimestamp(computeDateTime(initialDateTime, currentTime));
            } else {
                data.setTimestamp(computeDateTime(sampleData.get(index-1).getTimestamp(), currentTime));
            }
        });
    }

    private LocalDateTime computeDateTime(LocalDateTime initDateTime, LocalTime time) {
        LocalDate initDate = initDateTime.toLocalDate();
        LocalTime initTime = initDateTime.toLocalTime();
        long hoursDiff = initTime.until(time, ChronoUnit.HOURS);
        if (Math.abs(hoursDiff) < THRESHOLD_HOURS_SAME_DAY) {
            return LocalDateTime.of(initDate, time);
        } else {
            if (hoursDiff < 0) {
                // next day
                return LocalDateTime.of(initDate.plusDays(1), time);
            } else {
                // previous day
                return LocalDateTime.of(initDate.minusDays(1), time);
            }
        }
    }

    private List<InnerTable> getInnerTables(Sheet sheet) {
        List<InnerTable> innerTables = new ArrayList<>();
        InnerTable foundTable = null;
        for (Row row : sheet) {
            Cell firstCell = row.getCell(0);

            // the very first cell either indicates the beginning of a new inner table
            // or just represents a value of the first column of an inner table.
            // In some cases this cell might be empty.
            // In these cases we still want to add the row to an already found inner table.
            if (firstCell == null && foundTable != null) {
                foundTable.rows.add(row);
                continue;
            }

            // if we did not found an inner table yet and the cell is empty, there is no need to keep it
            // note: this should never be the case
            if (firstCell == null) {
                continue;
            }

            // let's check if this cell represents the beginning of a new table
            String firstCellValue = firstCell.toString();
            if (isStartOfNewTable(firstCellValue)) {
                // this is the beginning of a new inner table
                foundTable = new InnerTable();
                innerTables.add(foundTable);
                foundTable.rows.add(row);
            } else {
                // we add the row to an already found inner table
                if (foundTable != null) {
                    foundTable.rows.add(row);
                }
            }
        }
        return innerTables;
    }

    private File writeInnerTablesToFile(List<InnerTable> innerTables) {
        XSSFWorkbook workbook = new XSSFWorkbook();

        //date formatter
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle timeStyle = workbook.createCellStyle();
        timeStyle.setDataFormat(createHelper.createDataFormat().getFormat("YYYY-MM-DD HH:mm:ss"));

        int tableCounter = 0;
        for (InnerTable table : innerTables) {
            XSSFSheet eventDataSheet = workbook.createSheet("Table#" + tableCounter++);

            int rowCounter = 0;
            for (Row row : table.rows) {
                Row saveRow = eventDataSheet.createRow(rowCounter++);
                for (Cell c : row) {

                    Cell cell = saveRow.createCell(c.getColumnIndex());
                    cell.getCellStyle().setDataFormat(c.getCellStyle().getDataFormat());

                    CellType cellType = c.getCellType();
                    if (cellType.equals(CellType.NUMERIC)) {
                        if (DateUtil.isCellDateFormatted(c)) {
                            cell.setCellStyle(timeStyle);
                        }
                        cell.setCellValue(c.getNumericCellValue());
                    } else if (cellType.equals(CellType.STRING)) {
                        cell.setCellValue(c.getStringCellValue());
                    } else if (cellType.equals(CellType.BLANK)) {
                        cell.setCellValue(c.getStringCellValue());
                    }
                }
            }
        }

        // write workbook to file
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(HLMExcelFileReader.TEMPORARY_FILE_NAME);
            workbook.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return new File(HLMExcelFileReader.TEMPORARY_FILE_NAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private PoijiOptions getDefaultPoijiOptions() {
        return PoijiOptions.PoijiOptionsBuilder.settings()
                .preferNullOverDefault(true)
                .dateTimeFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .dateFormatter(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .build();
    }

    private boolean isStartOfNewTable(String firstCellValue) {
        return FIRST_CELL_TABLE_INDICATOR.contains(firstCellValue);
    }

    /**
     * Helper class for representing (inner) tables within the original excel sheet.
     */
    private static class InnerTable {
        private final List<Row> rows = new ArrayList<>();
    }
}
