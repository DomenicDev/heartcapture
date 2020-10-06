package de.cassisi.hearth.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

@Data
@ExcelSheet("Table#2")
public class MachineExcelData {

    @ExcelCellName("OXYGENATOR")
    private String oxygenator;

    @ExcelCellName("HAEMO_FIL")
    private String haemoFilter;

}
