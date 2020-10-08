package de.cassisi.heart.port;

import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.usecase.port.ReportFileGenerator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

public class ExcelReportGenerator implements ReportFileGenerator {

    private Sheet sheet;


    @Override
    public byte[] generateReport(Operation operation, HLMData hlmData, List<InfusionData> infusionData, List<AnesthesiaData> anesthesiaData, List<NIRSData> nirsData) {
        try {
            Workbook template = WorkbookFactory.create(new File(ExcelReportGenerator.class.getClassLoader().getResource("report_template.xlsx").toURI()));

            this.sheet = template.getSheetAt(0);
            // date and room
            setCellValue(0, 1, operation.getDate());
            setCellValue(1, 1, operation.getRoomNr());

            // PATIENT DATA
            setCellValue(3, 1, hlmData.getPatientData().getSex().toString());
            setCellValue(4, 1, hlmData.getPatientData().getBirthday());
            setCellValue(5, 1, hlmData.getPatientData().getAge());
            setCellValue(6, 1, hlmData.getPatientData().getHeight());
            setCellValue(7, 1, hlmData.getPatientData().getWeight());
            setCellValue(12, 1, hlmData.getPatientData().getBloodGroup());
            setCellValue(12, 1, hlmData.getPatientData().getBloodRef());

            // OPERATION
            // HLM SETUP
            setCellValue(136, 1, hlmData.getMachineData().getOxygenator());
            setCellValue(141, 1, hlmData.getMachineData().getHaemoFil());


            // evaluate all formula cells
            XSSFFormulaEvaluator.evaluateAllFormulaCells(template);


            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            template.write(byteArrayOutputStream);

            this.sheet = null; // reset
            return byteArrayOutputStream.toByteArray();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValue(int row, int col, String value) {
        getCellAt(sheet, row, col).setCellValue(value);
    }

    private void setCellValue(int row, int col, LocalDate localDate) {
        getCellAt(sheet, row, col).setCellValue(localDate);
    }

    private void setCellValue(int row, int col, int value) {
        getCellAt(sheet, row,col).setCellValue(value);
    }

    private Cell getCellAt(Sheet sheet, int row, int col) {
        Cell cell = sheet.getRow(row).getCell(col);
        if (cell == null) {
            cell = sheet.getRow(row).createCell(col);
        }
        return cell;
    }

}
