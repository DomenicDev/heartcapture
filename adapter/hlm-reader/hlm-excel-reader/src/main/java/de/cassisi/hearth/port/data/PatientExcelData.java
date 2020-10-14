package de.cassisi.hearth.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
