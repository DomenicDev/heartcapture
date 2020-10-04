package de.cassisi.hearth.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ExcelSheet("Table#10")
public class HLMParamsExcelData implements ChronologicalData {

    @ExcelCellName("ZEIT")
    private LocalDateTime timestamp;

    @ExcelCellName("ARTFLOW")
    private double artFlow;

}
