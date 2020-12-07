package de.cassisi.heartcapture.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@ExcelSheet("Table#3")
public class BloodSampleExcelData implements ChronologicalData {

    @ExcelCellName("NR")
    private int nr;

    @ExcelCellName("ZEIT")
    private LocalDateTime timestamp;

    @ExcelCellName("TYP")
    private String typ;

    @ExcelCellName("HCT_OFL")
    private double hctOfl;

    @ExcelCellName("HB_OFL")
    private double hbOfl;

    @ExcelCellName("PH_OFL")
    private double phOfl;

    @ExcelCellName("PCO2_OFL")
    private double pco2Ofl;

    @ExcelCellName("PO2_OFL")
    private double po2Ofl;

    @ExcelCellName("HCO3_OFL")
    private double hco3Ofl;

    @ExcelCellName("TCO2_OFL")
    private double tco2Ofl;

    @ExcelCellName("BAVT_OFL")
    private double bavtOfl;

    @ExcelCellName("SO2_OFL")
    private double so2Ofl;

    @ExcelCellName("GLUCOSE")
    private int glucose;

    @ExcelCellName("NA")
    private int na;

    @ExcelCellName("K")
    private double k;

    @ExcelCellName("CA")
    private double ca;

    @ExcelCellName("LACTAT")
    private double lactat;

}
