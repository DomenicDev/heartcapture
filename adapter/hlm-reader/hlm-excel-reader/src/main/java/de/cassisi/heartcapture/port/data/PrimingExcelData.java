package de.cassisi.heartcapture.port.data;

import com.poiji.annotation.ExcelCellName;
import com.poiji.annotation.ExcelSheet;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ExcelSheet("Table#13")
public class PrimingExcelData {

    @ExcelCellName("PRI_GESAMT")
    private double totalPriming;

    
    @ExcelCellName("PRI_1_ML")
    private Integer priming1amount;

    @ExcelCellName("PRI_1_EINH")
    private String priming1Unit;

    @ExcelCellName("PRI_1_TXT")
    private String priming1Text;

    
    @ExcelCellName("PRI_2_ML")
    private Integer priming2amount;

    @ExcelCellName("PRI_2_EINH")
    private String priming2Unit;

    @ExcelCellName("PRI_2_TXT")
    private String priming2Text;


    @ExcelCellName("PRI_3_ML")
    private Integer priming3amount;

    @ExcelCellName("PRI_3_EINH")
    private String priming3Unit;

    @ExcelCellName("PRI_3_TXT")
    private String priming3Text;


    @ExcelCellName("PRI_4_ML")
    private Integer priming4amount;

    @ExcelCellName("PRI_4_EINH")
    private String priming4Unit;

    @ExcelCellName("PRI_4_TXT")
    private String priming4Text;


    @ExcelCellName("PRI_5_ML")
    private Integer priming5amount;

    @ExcelCellName("PRI_5_EINH")
    private String priming5Unit;

    @ExcelCellName("PRI_5_TXT")
    private String priming5Text;


    @ExcelCellName("PRI_6_ML")
    private Integer priming6amount;

    @ExcelCellName("PRI_6_EINH")
    private String priming6Unit;

    @ExcelCellName("PRI_6_TXT")
    private String priming6Text;


    @ExcelCellName("PRI_7_ML")
    private Integer priming7amount;

    @ExcelCellName("PRI_7_EINH")
    private String priming7Unit;

    @ExcelCellName("PRI_7_TXT")
    private String priming7Text;


    @ExcelCellName("PRI_8_ML")
    private Integer priming8amount;

    @ExcelCellName("PRI_8_EINH")
    private String priming8Unit;

    @ExcelCellName("PRI_8_TXT")
    private String priming8Text;


    @ExcelCellName("PRI_9_ML")
    private Integer priming9amount;

    @ExcelCellName("PRI_9_EINH")
    private String priming9Unit;

    @ExcelCellName("PRI_9_TXT")
    private String priming9Text;


    @ExcelCellName("PRI_10_ML")
    private Integer priming10amount;

    @ExcelCellName("PRI_10_EINH")
    private String priming10Unit;

    @ExcelCellName("PRI_10_TXT")
    private String priming10Text;

}
