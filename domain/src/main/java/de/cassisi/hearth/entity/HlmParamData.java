package de.cassisi.hearth.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public final class HlmParamData {

    @NonNull private final LocalDateTime timestamp;
    
    private final Double artFlow;
    private final Double flow2;
    private final Double flow3;
    
    private final Double pressure1;
    private final Double pressure2;
    
    private final Double temp1;
    private final Double temp2;
    private final Double temp3;
    
    private final Double plegieflowA;
    private final Double plegieflowB;
    private final Double plegiedruck;
    private final Double gasmixflow;
    private final Double gasfio2;

    private final Double bgtempVen;
    private final Double o2satVen;
    private final Double hct;
    private final Integer pathfreq;
    private final Double pattemp1;
    private final Integer patartdruck;
    private final Integer patpuldruck;
    private final Integer patzvdruck;

    private final Double ci;
    private final Double flowRel;
    private final Double svr;
    private final Double gasBlood;
    private final Double narkosegas;

}
