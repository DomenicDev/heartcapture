package de.cassisi.hearth.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@ExcelSheet("Table#14")
public class MetaExcelData {

    @ExcelCellName("OPDATUM")
    private LocalDate operationDate;

    @ExcelCellName("DRINGLICHKEIT")
    private String urgency;
}
