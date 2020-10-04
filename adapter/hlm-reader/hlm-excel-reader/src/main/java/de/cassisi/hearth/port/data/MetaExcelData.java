package de.cassisi.hearth.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

import java.time.LocalDate;

@Data
@ExcelSheet("Table#14")
public class MetaExcelData {

    @ExcelCellName("OPDATUM")
    private LocalDate operationDate;

    @ExcelCellName("DRINGLICHKEIT")
    private String urgency;
}
