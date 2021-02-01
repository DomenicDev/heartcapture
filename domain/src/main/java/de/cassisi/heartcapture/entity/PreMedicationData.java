package de.cassisi.heartcapture.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PreMedicationData {

    // pre-operation
    private final double suprareninPreOperation;
    private final double norepinephrinPreOperation;
    private final double vasopressinPreOperation;
    private final double milrinonPreOperation;
    private final double ntgPreOperation;
    private final double simdaxPreOperation;
    private final double heparinPreOperation;

    // pre-hlm
    private final double suprareninPreHlm;
    private final double norepinephrinPreHlm;
    private final double vasopressinPreHlm;
    private final double milrinonPreHlm;
    private final double ntgPreHlm;
    private final double simdaxPreHlm;

}
