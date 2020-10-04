package de.cassisi.hearth.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

@Data
@ExcelSheet("Table#6")
public class RiskFactorsExcelData {

    @ExcelCellName("RISIKOFAKTOR")
    private String riskFactor;

}
