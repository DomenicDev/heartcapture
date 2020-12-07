package de.cassisi.heartcapture.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ExcelSheet("Table#4")
public class DiagnosisExcelData {

    @ExcelCellName("DIAGNOSE")
    private String diagnosis;

}
