package de.cassisi.hearth.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ExcelSheet("Table#5")
public class OperationExcelData {

    @ExcelCellName("OPERATION")
    private String operation;

}
