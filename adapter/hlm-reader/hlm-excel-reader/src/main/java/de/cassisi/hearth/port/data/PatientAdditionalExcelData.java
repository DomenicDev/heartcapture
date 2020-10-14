package de.cassisi.hearth.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ExcelSheet("Table#9")
public class PatientAdditionalExcelData {

    @ExcelCellName("BLUTGRUPPE")
    private String bloodGroup;

    @ExcelCellName("BLUTRF")
    private String bloodRF;

    @ExcelCellName("GROESSE")
    private int height;

    @ExcelCellName("GEWICHT")
    private int weight;

    @ExcelCellName("BSA")
    private double bsa;

    @ExcelCellName("FAKTOR")
    private double factor;

    @ExcelCellName("SOLL_FLUSS")
    private double sollFluss;

}
