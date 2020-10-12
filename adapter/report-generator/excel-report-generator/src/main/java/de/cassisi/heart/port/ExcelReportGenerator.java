package de.cassisi.heart.port;

import de.cassisi.hearth.entity.*;
import de.cassisi.hearth.entity.HLMEventData.EventType;
import de.cassisi.hearth.usecase.port.ReportFileGenerator;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.modelmapper.ModelMapper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ExcelReportGenerator implements ReportFileGenerator {

    private Sheet sheet;
    private CellStyle timeStyle;

    @Override
    public byte[] generateReport(Operation operation, HLMData hlmData, List<InfusionData> infusionData, List<AnesthesiaData> anesthesiaData, List<NIRSData> nirsData) {
        try {
            Workbook template = WorkbookFactory.create(new File(ExcelReportGenerator.class.getClassLoader().getResource("report_template.xlsx").toURI()));

            CreationHelper createHelper = template.getCreationHelper();
            timeStyle = template.createCellStyle();
            timeStyle.setDataFormat(createHelper.createDataFormat().getFormat("DD.MM.YYYY HH:mm:ss"));


            this.sheet = template.getSheetAt(0);
            // date and room
            setCellValue(0, 1, operation.getDate());
            setCellValue(1, 1, operation.getRoomNr());

            // PATIENT DATA - META DATA
            setCellValue(3, 1, hlmData.getPatientData().getSex().toString());
            setCellValue(4, 1, hlmData.getPatientData().getBirthday());
            setCellValue(5, 1, hlmData.getPatientData().getAge());
            setCellValue(6, 1, hlmData.getPatientData().getHeight());
            setCellValue(7, 1, hlmData.getPatientData().getWeight());
            setCellValue(12, 1, hlmData.getPatientData().getBloodGroup());
            setCellValue(12, 1, hlmData.getPatientData().getBloodRef());

            // -- OPERATION -- //
            // HLM SETUP
            setCellValue(136, 1, hlmData.getMachineData().getOxygenator());
            setCellValue(141, 1, hlmData.getMachineData().getHaemoFil());
            setCellValue(144, 1, hlmData.getMachineData().getKanuelArt());
            setCellValue(145, 1, hlmData.getMachineData().getKanuelVen());
            setCellValue(146, 1, hlmData.getMachineData().getKanuelVen2());

            // PRIMING COMPOSITION
            addPrimingComposition(hlmData.getPrimingComposition());

            // TIME DATA
            List<TimeData> timeDataList = new LinkedList<>();

            // convert to TimeData objects and add them to the list
            convertNirsData(nirsData, timeDataList);
            convertAnesthesiaData(anesthesiaData, timeDataList);
            convertInfusionData(infusionData, timeDataList);
            convertTemporalHLMData(hlmData, timeDataList);

            // Sort TimeData by timestamp
            TimeData[] timeDataArray = timeDataList.toArray(new TimeData[0]);
            Arrays.sort(timeDataArray);
            List<TimeData> sortedList = Arrays.asList(timeDataArray);

            // write sorted list to excel file
            writeToCells(sortedList);

            // evaluate all formula cells
            XSSFFormulaEvaluator.evaluateAllFormulaCells(template);

            // write to output stream
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

    private void convertTemporalHLMData(HLMData hlmData, List<TimeData> timeDataList) {
        convertEventData(hlmData.getEventList(), timeDataList);
        convertParamData(hlmData.getParamData(), timeDataList);
        convertBloodSample(hlmData.getBloodSamples(), timeDataList);
    }

    private void convertBloodSample(List<HlmBloodSample> bloodSamples, List<TimeData> timeDataList) {
        bloodSamples.forEach(data -> {
            TimeData timeData = new TimeData(data.getTimestamp());
            ModelMapper mapper = new ModelMapper();
            if (data.getTyp().equals(HlmBloodSample.Type.ART)) {
                BloodSampleData artData = mapper.map(data, BloodSampleData.class);
                timeData.setBloodDataArt(artData);
            } else if (HlmBloodSample.Type.VEN.equals(data.getTyp())) {
                timeData.setBloodDataVen(mapper.map(data, BloodSampleData.class));
            }

            timeDataList.add(timeData);

        });
    }

    private void convertParamData(List<HlmParamData> paramData, List<TimeData> timeDataList) {
        paramData.forEach(data -> {
            TimeData timeData = new TimeData(data.getTimestamp());
            ModelMapper mapper = new ModelMapper();
            mapper.map(data, timeData);
            timeDataList.add(timeData);
        });
    }

    /**
     * TODO: WIP
     * @param eventList
     * @param timeDataList
     */
    private void convertEventData(List<HLMEventData> eventList, List<TimeData> timeDataList) {
        eventList.forEach(event -> {

            if (event.getType() == null || EventType.UNKNOWN == event.getType()) {
                return;
            }

            TimeData timeData = new TimeData(event.getTimestamp());
            if (EventType.BYPASS_ENDE.equals(event.getType())) {
                timeData.setBypass("Ende");
            }
            if (EventType.BYPASS_BEGINN.equals(event.getType())) {
                timeData.setBypass("Beginn");
            }
            if (EventType.AORTA_AUF.equals(event.getType())) {
                timeData.setAorta("AUF");
            }
            if (EventType.AORTA_ZU.equals(event.getType())) {
                timeData.setAorta("ZU");
            }
            if (EventType.REPERFUSION_BEGINN.equals(event.getType())) {
                timeData.setReperfusion("BEGINN");
            }
            if (EventType.REPERFUSION_ENDE.equals(event.getType())) {
                timeData.setReperfusion("ENDE");
            }
            if (EventType.LEVELSTAND.equals(event.getType())) {
                timeData.setLevelstand(String.valueOf(event.getAmount()));
            }
            if (EventType.KARDIOPLEGIE.equals(event.getType())) {
                timeData.setKardioplegie(event.getAmount());
            }
            if (EventType.JONOSTERIL.equals(event.getType())) {
                timeData.setJonosteril(event.getAmount());
            }
            if (EventType.HEPARIN.equals(event.getType())) {
                timeData.setHeparin(event.getAmount());
            }
            if (EventType.NABI_8_4_PC.equals(event.getType())) {
                timeData.setNabi_8_4(event.getAmount());
            }
            if (EventType.CS_EK.equals(event.getType())) {
                timeData.setCs_ek("TODO -> CS-EK");
            }
            if (EventType.FREMDBLUT.equals(event.getType())) {
                timeData.setFremdblut("FREMDBLUT");
            }
            if (EventType.HUMANALBUMIN_5.equals(event.getType())) {
                timeData.setHumanalbumin_5pc(String.valueOf(event.getAmount()));
            }
            if (EventType.HUMANALBUMIN_20.equals(event.getType())) {
                timeData.setHumanalbumin_20pc(String.valueOf(event.getAmount()));
            }
            if (EventType.HAEMOFILTRAT.equals(event.getType())) {
                timeData.setHaemofiltrat("haemofiltrat data ????");
            }
            if (EventType.RESTBLUT_PERF.equals(event.getType())) {
                timeData.setRestblut_perf(event.getAmount());
            }
            if (EventType.MASCHINENBLUT.equals(event.getType())) {
                timeData.setMaschinenblut(event.getAmount());
            }
            if (EventType.CELL_SAVER_ABGESAUGT.equals(event.getType())) {
                timeData.setCell_saver_abgesaugt("TODO!!!");
            }
            if (EventType.DEFIBRILLATION.equals(event.getType())) {
                timeData.setDefibrillation("TODO!");
            }
            if (EventType.ACT.equals(event.getType())) {
                timeData.setAct(event.getAmount());
            }
            if (EventType.HAEMOFILTRATION.equals(event.getType())) {
                timeData.setHaemofiltration("TODO!");
            }
            if (EventType.CYTOKIN_ADSORPTION.equals(event.getType())) {
                timeData.setCytokin_adsorption("TODO!");
            }

            // add to list
            timeDataList.add(timeData);
        });
    }


    private void convertInfusionData(List<InfusionData> infusionData, List<TimeData> timeDataList) {
        infusionData.forEach(data -> {

            TimeData timeData = new TimeData(data.getLocalDateTime());
            boolean hasRelevantPerfusorData = false;

            // look for relevant perfusor data
            for (PerfusorData perfusor : data.getPerfusorDataList()) {
                if ("Arterenol".equals(perfusor.getName())) {
                    timeData.setPerfusorArterenol(perfusor.getRate());
                    hasRelevantPerfusorData = true;
                } else if ("Vasopressin".equals(perfusor.getName())) {
                    timeData.setPerfusorVasopressin(perfusor.getRate());
                    hasRelevantPerfusorData = true;
                } else if ("Sufentanil".equals(perfusor.getName())) {
                    timeData.setPerfusorSufentanil(perfusor.getRate());
                    hasRelevantPerfusorData = true;
                }
            }

            // only add time data object if it has relevant data for documentation
            if (hasRelevantPerfusorData) {
                timeDataList.add(timeData);
            }
        });
    }

    private void convertAnesthesiaData(List<AnesthesiaData> anesthesiaData, List<TimeData> timeDataList) {
        anesthesiaData.forEach(data -> {
            TimeData timeData = new TimeData(data.getTimestamp());
            timeData.setDepthOfAnesthesia(data.getDepthOfAnesthesia());
            timeDataList.add(timeData);
        });
    }

    private void writeToCells(List<TimeData> timeDataList) {
        int row = 164; // start row
        for (TimeData data : timeDataList) {
            writeTimeData(data, row);
            row++;
        }
    }

    private void writeTimeData(TimeData data, int row) {
        setCellValue(row, 0, data.getTimestamp());
        setCellValue(row, 1, data.getPathfreq());
        setCellValue(row, 2, data.getPattemp1());
        setCellValue(row, 3, data.getPatartdruck());
        setCellValue(row, 4, data.getPatpuldruck());
        setCellValue(row, 5, data.getPatzvdruck());
        setCellValue(row, 6, data.getLeftSaturation());
        setCellValue(row, 7, data.getRightSaturation());
        setCellValue(row, 8, data.getPerfusorArterenol());
        setCellValue(row, 9, data.getPerfusorVasopressin());
        setCellValue(row, 10, data.getPerfusorSufentanil());
        setCellValue(row, 11, data.getDepthOfAnesthesia());
        setCellValue(row, 12, data.getKod());
        setCellValue(row, 13, data.getBypass());
        setCellValue(row, 14, data.getAorta());
        setCellValue(row, 15, data.getReperfusion());
        setCellValue(row, 16, data.getArtFlow());
        setCellValue(row, 17, data.getFlow2());
        setCellValue(row, 18, data.getFlow3());
        setCellValue(row, 19, data.getPressure1());
        setCellValue(row, 20, data.getPressure2());
        setCellValue(row, 21, data.getLevelstand());
        setCellValue(row, 22, data.getTemp1());
        setCellValue(row, 23, data.getTemp2());
        setCellValue(row, 24, data.getTemp3());
        setCellValue(row, 25, data.getPlegieflowA());
        setCellValue(row, 26, data.getPlegieflowB());
        setCellValue(row, 27, data.getPlegiedruck());
        setCellValue(row, 28, data.getGasmixflow());
        setCellValue(row, 29, data.getGasfio2());
        setCellValue(row, 30, data.getBgtempVen());
        setCellValue(row, 31, data.getO2satVen());
        setCellValue(row, 32, data.getHct());
        setCellValue(row, 33, data.getCi());
        setCellValue(row, 34, data.getFlowRel());
        setCellValue(row, 35, data.getSvr());
        setCellValue(row, 36, data.getGasBlood());
        setCellValue(row, 37, data.getNarkosegas());
        setCellValue(row, 38, data.getKardioplegie());
        setCellValue(row, 39, data.getJonosteril());
        setCellValue(row, 40, data.getHeparin());
        setCellValue(row, 41, data.getNabi_8_4());
        setCellValue(row, 42, data.getCs_ek());
        setCellValue(row, 43, data.getFremdblut());
        setCellValue(row, 44, data.getHumanalbumin_5pc());
        setCellValue(row, 45, data.getHumanalbumin_20pc());
        setCellValue(row, 46, data.getHaemofiltrat());
        setCellValue(row, 47, data.getRestblut_perf());
        setCellValue(row, 48, data.getMaschinenblut());
        setCellValue(row, 49, data.getCell_saver_abgesaugt());
        setCellValue(row, 50, data.getDefibrillation());
        setCellValue(row, 51, data.getAct());
        setCellValue(row, 52, data.getHaemofiltration());
        setCellValue(row, 53, data.getCytokin_adsorption());
        //arterial data
        setCellValue(row, 54, data.getBloodDataArt().getHctOFL());
        setCellValue(row, 55, data.getBloodDataArt().getHbOfl());
        setCellValue(row, 56, data.getBloodDataArt().getPhOfl());
        setCellValue(row, 57, data.getBloodDataArt().getPco2Ofl());
        setCellValue(row, 58, data.getBloodDataArt().getPo2Ofl());
        setCellValue(row, 59, data.getBloodDataArt().getHco3Ofl());
        setCellValue(row, 60, data.getBloodDataArt().getTco2Ofl());
        setCellValue(row, 61, data.getBloodDataArt().getBavtOfl());
        setCellValue(row, 62, data.getBloodDataArt().getSo2Ofl());
        setCellValue(row, 63, data.getBloodDataArt().getGlucose());
        setCellValue(row, 64, data.getBloodDataArt().getNa());
        setCellValue(row, 65, data.getBloodDataArt().getK());
        setCellValue(row, 66, data.getBloodDataArt().getCa());
        setCellValue(row, 67, data.getBloodDataArt().getLactat());
        //venous data
        setCellValue(row, 68, data.getBloodDataVen().getHctOFL());
        setCellValue(row, 69, data.getBloodDataVen().getHbOfl());
        setCellValue(row, 70, data.getBloodDataVen().getPhOfl());
        setCellValue(row, 71, data.getBloodDataVen().getPco2Ofl());
        setCellValue(row, 72, data.getBloodDataVen().getPo2Ofl());
        setCellValue(row, 73, data.getBloodDataVen().getHco3Ofl());
        setCellValue(row, 74, data.getBloodDataVen().getTco2Ofl());
        setCellValue(row, 75, data.getBloodDataVen().getBavtOfl());
        setCellValue(row, 76, data.getBloodDataVen().getSo2Ofl());
        setCellValue(row, 77, data.getBloodDataVen().getGlucose());
        setCellValue(row, 78, data.getBloodDataVen().getNa());
        setCellValue(row, 79, data.getBloodDataVen().getK());
        setCellValue(row, 80, data.getBloodDataVen().getCa());
        setCellValue(row, 81, data.getBloodDataVen().getLactat());

        // do2
        setCellValue(row, 82, data.getDo2());
    }

    private void convertNirsData(List<NIRSData> infusionData, List<TimeData> timeDataList) {
        infusionData.forEach(data -> {
            TimeData timeData = new TimeData(data.getTimestamp());
            timeData.setLeftSaturation(data.getLeftSaturation());
            timeData.setRightSaturation(data.getRightSaturation());
            timeDataList.add(timeData);
        });
    }

    private void addPrimingComposition(PrimingComposition primingComposition) {
        int currentRow = 152;
        for (Priming p : primingComposition.getPrimingData()) {
            setCellValue(currentRow, 0, p.getText());
            setCellValue(currentRow, 1, p.getAmount());
            setCellValue(currentRow, 2, p.getUnit());
            currentRow++;
        }
    }

    private void setCellValue(int row, int col, String value) {
        if (value == null) return;
        getCellAt(sheet, row, col).setCellValue(value);
    }

    private void setCellValue(int row, int col, Integer value) {
        if (value == null) return;
        getCellAt(sheet, row, col).setCellValue(value);
    }

    private void setCellValue(int row, int col, Double value) {
        if (value == null) return;
        getCellAt(sheet, row, col).setCellValue(value);
    }

    private void setCellValue(int row, int col, LocalDate localDate) {
        getCellAt(sheet, row, col).setCellValue(localDate);
    }

    private void setCellValue(int row, int col, LocalDateTime localDateTime) {
        getCellAt(sheet, row, col).setCellValue(localDateTime);
        getCellAt(sheet, row, col).setCellStyle(timeStyle);
    }

    private void setCellValue(int row, int col, int value) {
        getCellAt(sheet, row,col).setCellValue(value);
    }

    private Cell getCellAt(Sheet sheet, int row, int col) {
        Cell cell = getRowAt(row).getCell(col);
        if (cell == null) {
            cell = sheet.getRow(row).createCell(col);
        }
        return cell;
    }

    private Row getRowAt(int row) {
        Row r = sheet.getRow(row);
        if (r == null) {
            return sheet.createRow(row);
        } else {
            return r;
        }
    }

}
