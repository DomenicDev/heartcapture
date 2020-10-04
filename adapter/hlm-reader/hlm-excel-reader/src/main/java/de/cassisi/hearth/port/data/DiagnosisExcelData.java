package de.cassisi.hearth.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

@Data
@ExcelSheet("Table#4")
public class DiagnosisExcelData {

    @ExcelCellName("DIAGNOSE")
    private String diagnosis;

}
