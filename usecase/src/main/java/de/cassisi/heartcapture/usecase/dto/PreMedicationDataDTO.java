package de.cassisi.heartcapture.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PreMedicationDataDTO {

    private final long operationId;

    // pre-operation
    private final double suprareninPreOperation;
    private final double norepinephrinPreOperation;
    private final double vasopressinPreOperation;
    private final double milrinonPreOperation;
    private final double ntgPreOperation;
    private final double simdaxPreOperation;
    private final double heparinPreOperation;

    // pre-HLM
    private final double suprareninPreHlm;
    private final double norepinephrinPreHlm;
    private final double vasopressinPreHlm;
    private final double milrinonPreHlm;
    private final double ntgPreHlm;
    private final double simdaxPreHlm;
    
}
