package de.cassisi.heart.port;

import de.cassisi.hearth.entity.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.modelmapper.ModelMapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ExcelReportGenerator {

    private final Map<LocalDateTime, TimeData> timestampTimeDataMap = new HashMap<>();
    private final ReportData reportData;

    private Sheet sheet;
    private CellStyle timeStyle;

    private static final String ARTERENOL = "Norepinephrin";
    private static final String VASOPRESSIN = "Argipressin";
    private static final String SUFENTANIL = "Sufentanil";

    public ExcelReportGenerator(ReportData reportData) {
        this.reportData = reportData;
    }

    /**
     * Generates an excel report document based on the specified report data.
     * @return an byte[] containing the whole workbook (could be used to write to file)
     */
    public byte[] generate() {
        try {
            Operation operation = reportData.getOperation();
            HLMData hlmData = reportData.getHlmData();
            List<InfusionData> infusionData = reportData.getInfusionData();
            List<AnesthesiaData> anesthesiaData = reportData.getAnesthesiaData();
            List<NIRSData> nirsData = reportData.getNirsData();

            Workbook template = WorkbookFactory.create(Objects.requireNonNull(ExcelReportFileGeneratorImpl.class.getClassLoader().getResourceAsStream("report_template.xlsx")));

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
            setCellValue(13, 1, hlmData.getPatientData().getBloodRef());

            // -- OPERATION -- //
            // HLM SETUP
            setCellValue(136, 1, hlmData.getMachineData().getOxygenator());
            setCellValue(141, 1, hlmData.getMachineData().getHaemoFil());
            setCellValue(144, 1, hlmData.getMachineData().getKanuelArt());
            setCellValue(145, 1, hlmData.getMachineData().getKanuelVen());
            setCellValue(146, 1, hlmData.getMachineData().getKanuelVen2());

            // PRIMING COMPOSITION
            addPrimingComposition(hlmData.getPrimingComposition());

            // convert to TimeData objects and add them to the list
            convertNirsData(nirsData);
            convertAnesthesiaData(anesthesiaData);
            convertInfusionData(infusionData);
            convertTemporalHLMData(hlmData);

            // Sort TimeData by timestamp
            TimeData[] timeDataArray = timestampTimeDataMap.values().toArray(new TimeData[0]);
            Arrays.sort(timeDataArray);
            List<TimeData> sortedList = Arrays.asList(timeDataArray);

            // edit data after sorting
            postEditTimeData(sortedList);

            // write sorted list to excel file
            writeToCells(sortedList);

            // evaluate all formula cells
            XSSFFormulaEvaluator.evaluateAllFormulaCells(template);

            // write to output stream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            template.write(byteArrayOutputStream);

            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private TimeData createOrGetTimeData(LocalDateTime timestamp) {
        // we truncate the specified timestamp to seconds which is good (precise) enough
        // this way we avoid duplicate timestamps in the excel sheet if two timestamps differ by nanos only
        LocalDateTime truncatedTimestamp = timestamp.truncatedTo(ChronoUnit.SECONDS);

        // create new time data if there is none for the specific timestamp yet
        if (!timestampTimeDataMap.containsKey(truncatedTimestamp)) {
            // create TimeData instance for this timestamp
            TimeData timeData = new TimeData(truncatedTimestamp);

            // put into map for later reuse
            timestampTimeDataMap.put(truncatedTimestamp, timeData);
        }

        // return specific TimeData object for this timestamp
        return timestampTimeDataMap.get(truncatedTimestamp);
    }


    private void postEditTimeData(List<TimeData> timeDataList) {
        completeBypassRange(timeDataList);
        completeAorta(timeDataList);
        completeReperfusion(timeDataList);
        computeDO2(timeDataList);
        completeHaemofiltration(timeDataList);
    }

    private void completeHaemofiltration(List<TimeData> sortedTimeData) {
        boolean filtrationRunning = false;
        for (TimeData timeData : sortedTimeData) {
            Integer val = timeData.getHaemofiltration();

            // set value to 1 to indicate that filtration is running
            if (val == null && filtrationRunning) {
                timeData.setHaemofiltration(1);
            }
            
            // set value to 0 to indicate that filtration is not running
            if (val == null && !filtrationRunning) {
                timeData.setHaemofiltration(0);
            }

            // if value "1" is already set, that indicates the start of the filtration process
            if (val != null && val == 1) {
                filtrationRunning = true;
            }

            // the filtration process stops when we find an filtration value
            if (timeData.getHaemofiltrat() != null) {
                filtrationRunning = false;
                timeData.setHaemofiltration(1);
            }
        }
    }

    private void computeDO2(List<TimeData> sortedTimeData) {
        for (TimeData timeData : sortedTimeData) {
            // check if this TimeData object contains a blood sample
            BloodSampleData bloodSampleArt = timeData.getBloodDataArt();
            if (bloodSampleArt.getHbOfl() != null) {

                double bsa = reportData.getHlmData().getPatientData().getBsa();
                double hbOfl = bloodSampleArt.getHbOfl();
                double po2Ofl = bloodSampleArt.getPo2Ofl();
                double so2Ofl = bloodSampleArt.getSo2Ofl();

                // we must look for the latest art flow value
                int index = sortedTimeData.indexOf(timeData);
                Double artFlow = searchBackwards(TimeData::getArtFlow, sortedTimeData, index);

                // compute value and update time data object
                if (artFlow != null) {
                    double do2 = 10 * (artFlow/bsa) * ( (hbOfl * 1.34 * so2Ofl) + ( 0.003 * po2Ofl )) / 100.0;
                    timeData.setDo2(do2);
                }
            }
        }
    }

    /**
     * This method allows to search backwards for a specific value within the specified list.
     * Note that this list has to be sorted!
     *
     * @param callback the method call to obtain the value you are searching for
     * @param sortedList the list to be searched
     * @param startIndex the index to start the search from
     * @param <T> the type of the returned value
     * @return the first found value that is not null. If no value is found, null is returned.
     */
    private <T> T searchBackwards(SearchCallback<T> callback, List<TimeData> sortedList, int startIndex) {
        for (int i = startIndex; i > 0; i--) {
            TimeData data = sortedList.get(i);
            T result = callback.get(data);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    interface SearchCallback<T> {

        T get(TimeData timeData);

    }

    private void completeBypassRange(List<TimeData> timeDataList) {
        boolean started = false;
        for (TimeData timeData : timeDataList) {
            Integer val = timeData.getBypass();
            if (val != null) {
                if (val.equals(1)) {
                    started = true;
                } else if (val.equals(0)) {
                    started = false;
                }
            } else {
                timeData.setBypass(started ? 1: 0);
            }
        }
    }

    private void completeAorta(List<TimeData> timeDataList) {
        boolean closed = false;
        for (TimeData timeData : timeDataList) {
            Integer val = timeData.getAorta();
            if (val != null) {
                if (val == 1) {
                    closed = true;
                } else if (val == 0) {
                    closed = false;
                }
            } else {
                timeData.setAorta(closed ? 1 : 0);
            }
        }
    }

    private void completeReperfusion(List<TimeData> timeDataList) {
        boolean running = false;
        for (TimeData timeData : timeDataList) {
            Integer val = timeData.getReperfusion();
            if (val != null) {
                if (val.equals(1)) {
                    running = true;
                } else if (val.equals(0)) {
                    running = false;
                }
            } else {
                timeData.setReperfusion(running ? 1 : 0);
            }
        }
    }

    private void convertTemporalHLMData(HLMData hlmData) {
        convertEventData(hlmData.getEventList());
        convertParamData(hlmData.getParamData());
        convertBloodSample(hlmData.getBloodSamples());
    }

    private void convertBloodSample(List<HlmBloodSample> bloodSamples) {
        bloodSamples.forEach(data -> {
            TimeData timeData = createOrGetTimeData(data.getTimestamp());
            ModelMapper mapper = new ModelMapper();
            if (data.getTyp().equals(HlmBloodSample.Type.ART)) {
                BloodSampleData artData = mapper.map(data, BloodSampleData.class);
                timeData.setBloodDataArt(artData);
            } else if (HlmBloodSample.Type.VEN.equals(data.getTyp())) {
                timeData.setBloodDataVen(mapper.map(data, BloodSampleData.class));
            }
        });
    }

    private void convertParamData(List<HlmParamData> paramData) {
        paramData.forEach(data -> {
            TimeData timeData = createOrGetTimeData(data.getTimestamp());
            ModelMapper mapper = new ModelMapper();
            mapper.map(data, timeData);
        });
    }

    /**
     * Reads the specified event data and creates TimeData objects for each relevant event.
     * This method will either set the 'amount' value (e.g. in ml) for an event or
     * an indication value (e.g. '1') to mark the begin or end of a process that covers multiple time data objects.
     * @param eventList the event data list
     */
    private void convertEventData(List<HLMEventData> eventList) {
        eventList.forEach(event -> {

            if (event.getType() == null || HLMEventData.EventType.UNKNOWN == event.getType()) {
                return;
            }

            if (HLMEventData.EventType.BYPASS_ENDE.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setBypass(0);
            }
            if (HLMEventData.EventType.BYPASS_BEGINN.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setBypass(1);
            }
            if (HLMEventData.EventType.AORTA_AUF.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setAorta(0);
            }
            if (HLMEventData.EventType.AORTA_ZU.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setAorta(1);
            }
            if (HLMEventData.EventType.REPERFUSION_BEGINN.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setReperfusion(1);
            }
            if (HLMEventData.EventType.REPERFUSION_ENDE.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setReperfusion(0);
            }
            if (HLMEventData.EventType.KARDIOPLEGIE.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setKardioplegie(event.getAmount());
            }
            if (HLMEventData.EventType.JONOSTERIL.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setJonosteril(event.getAmount());
            }
            if (HLMEventData.EventType.HEPARIN.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setHeparin(event.getAmount());
            }
            if (HLMEventData.EventType.NABI_8_4_PC.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setNabi_8_4(event.getAmount());
            }
            if (HLMEventData.EventType.RESERVOIRVOLUMEN.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setLevelstand(event.getAmount());
            }
            if (HLMEventData.EventType.CS_EK.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setCs_ek(event.getAmount());
            }
            if (HLMEventData.EventType.FREMDBLUT.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setFremdblut(event.getAmount());
            }
            if (HLMEventData.EventType.HUMANALBUMIN_5.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setHumanalbumin_5pc(event.getAmount());
            }
            if (HLMEventData.EventType.HUMANALBUMIN_20.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setHumanalbumin_20pc(event.getAmount());
            }
            if (HLMEventData.EventType.HAEMOFILTRAT.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setHaemofiltrat(event.getAmount());
            }
            if (HLMEventData.EventType.RESTBLUT_PERF.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setRestblut_perf(event.getAmount());
            }
            if (HLMEventData.EventType.MASCHINENBLUT.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setMaschinenblut(event.getAmount());
            }
            if (HLMEventData.EventType.CELL_SAVER_ABGESAUGT.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setCell_saver_abgesaugt(event.getAmount());
            }
            if (HLMEventData.EventType.DEFIBRILLATION.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setDefibrillation(1);
            }
            if (HLMEventData.EventType.ACT.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setAct(event.getAmount());
            }
            if (HLMEventData.EventType.HAEMOFILTRATION.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setHaemofiltration(1);
            }
            if (HLMEventData.EventType.CYTOKIN_ADSORPTION.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setCytokin_adsorption("TODO!");
            }
            if (HLMEventData.EventType.KOD.equals(event.getType())) {
                createOrGetTimeData(event.getTimestamp()).setKod(event.getAmount());
            }
        });
    }


    private void convertInfusionData(List<InfusionData> infusionData) {
        infusionData.forEach(data -> {

            boolean hasRelevantPerfusorData = false;

            // look for relevant perfusor data
            for (PerfusorData perfusor : data.getPerfusorDataList()) {
                String name = perfusor.getName();
                if (name == null) continue;
                if (name.contains(ARTERENOL)
                        || name.contains(VASOPRESSIN)
                        || name.contains(SUFENTANIL)) {
                    hasRelevantPerfusorData = true;
                    break;
                }
            }

            // only add time data object if it has relevant data for documentation
            if (hasRelevantPerfusorData) {
                TimeData timeData = createOrGetTimeData(data.getTimestamp());
                for (PerfusorData perfusor : data.getPerfusorDataList()) {
                    String name = perfusor.getName();
                    Double rate = perfusor.getRate();
                    if (name.contains(ARTERENOL)) {
                        timeData.setPerfusorArterenol(rate);
                    }
                    if (name.contains(VASOPRESSIN)) {
                        timeData.setPerfusorVasopressin(rate);
                    }
                    if (name.contains(SUFENTANIL)) {
                        timeData.setPerfusorSufentanil(rate);
                    }
                }
            }
        });
    }

    private void convertAnesthesiaData(List<AnesthesiaData> anesthesiaData) {
        anesthesiaData.forEach(data -> {
            TimeData timeData = createOrGetTimeData(data.getTimestamp());
            timeData.setDepthOfAnesthesia(data.getDepthOfAnesthesia());
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

    private void convertNirsData(List<NIRSData> infusionData) {
        infusionData.forEach(data -> {
            TimeData timeData = createOrGetTimeData(data.getTimestamp());
            timeData.setLeftSaturation(data.getLeftSaturation());
            timeData.setRightSaturation(data.getRightSaturation());
        });
    }

    private void addPrimingComposition(PrimingComposition primingComposition) {
        setCellValue(151, 1, primingComposition.getTotalPriming());
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
