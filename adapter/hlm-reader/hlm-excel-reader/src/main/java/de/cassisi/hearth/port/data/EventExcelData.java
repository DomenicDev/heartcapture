package de.cassisi.hearth.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ExcelSheet("Table#0")
public class EventExcelData implements ChronologicalData {

    @ExcelCellName("ZEIT")
    private LocalDateTime timestamp;

    @ExcelCellName("TEXT")
    private String text;

    @ExcelCellName("MENGE")
    private int amount;

    @ExcelCellName("EINHEIT")
    private String unit;

    @ExcelCellName("FAKTOR")
    private int factor;

    @ExcelCellName("MENGE_ML")
    private int amountMililiter;

    @ExcelCellName("FREI")
    private String free;

    @ExcelCellName("TYP")
    private String type;

}
