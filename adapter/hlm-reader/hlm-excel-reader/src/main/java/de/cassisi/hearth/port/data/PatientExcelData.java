package de.cassisi.hearth.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

import java.time.LocalDate;

@Data
@ExcelSheet("Table#8")
public class PatientExcelData {

    @ExcelCellName("GEBDATUM")
    private LocalDate birthday;

    @ExcelCellName("GESCHLECHT")
    private String sex;

    @ExcelCellName("AGE")
    private int age;

    @ExcelCellName("MONTH")
    private int month;

    @ExcelCellName("DAY")
    private int day;

}
