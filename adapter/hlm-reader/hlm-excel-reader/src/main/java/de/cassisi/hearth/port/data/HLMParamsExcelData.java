package de.cassisi.hearth.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ExcelSheet("Table#10")
public class HLMParamsExcelData implements ChronologicalData {

    @ExcelCellName("ZEIT")
    private LocalDateTime timestamp;

    @ExcelCellName("ARTFLOW")
    private Double artFlow;

    @ExcelCellName("FLOW2")
    private Double flow2;

    @ExcelCellName("FLOW3")
    private Double flow3;

    @ExcelCellName("DRUCK_1")
    private Double pressure1;

    @ExcelCellName("DRUCK_2")
    private Double pressure2;

    @ExcelCellName("TEMP_1")
    private Double temp1;

    @ExcelCellName("TEMP_2")
    private Double temp2;

    @ExcelCellName("TEMP_3")
    private Double temp3;

    @ExcelCellName("PLEGIEFLOW_A")
    private Double plegieflowA;

    @ExcelCellName("PLEGIEFLOW_B")
    private Double plegieflowB;

    @ExcelCellName("PLEGIEDRUCK")
    private Double plegiedruck;

    @ExcelCellName("GASMIXFLOW")
    private Double gasmixflow;

    @ExcelCellName("GASFIO2")
    private Double gasfio2;

    @ExcelCellName("BGTEMP_VEN")
    private Double bgtempVen;

    @ExcelCellName("O2SAT_VEN")
    private Double o2satVen;

    @ExcelCellName("HCT")
    private Double hct;

    @ExcelCellName("PATHFREQ")
    private Integer pathfreq;

    @ExcelCellName("PATTEMP1")
    private Double pattemp1;

    @ExcelCellName("PATARTDRUCK")
    private int patartdruck;

    @ExcelCellName("PATPULDRUCK")
    private int patpuldruck;

    @ExcelCellName("PATZVDRUCK")
    private int patzvdruck;

    @ExcelCellName("CI")
    private Double ci;

    @ExcelCellName("FLOW_REL")
    private Double flowRel;

    @ExcelCellName("SVR")
    private Double svr;

    @ExcelCellName("GAS_BLOOD")
    private Double gasBlood;

    @ExcelCellName("NARKOSEGAS")
    private Double narkosegas;
}
