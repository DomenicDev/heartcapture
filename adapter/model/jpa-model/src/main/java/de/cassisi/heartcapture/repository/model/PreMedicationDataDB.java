package de.cassisi.heartcapture.repository.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class PreMedicationDataDB {

    private Double suprareninPreOperation = 0D;
    private Double norepinephrinPreOperation = 0D;
    private Double vasopressinPreOperation = 0D;
    private Double milrinonPreOperation = 0D;
    private Double ntgPreOperation = 0D;
    private Double simdaxPreOperation = 0D;
    private Double heparinPreOperation = 0D;

    private Double suprareninPreHlm = 0D;
    private Double norepinephrinPreHlm = 0D;
    private Double vasopressinPreHlm = 0D;
    private Double milrinonPreHlm = 0D;
    private Double ntgPreHlm = 0D;
    private Double simdaxPreHlm = 0D;


}
