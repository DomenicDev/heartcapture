package de.cassisi.hearth.repository.model;

import com.sun.xml.bind.v2.model.core.ID;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HlmParamDataDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private HLMDataDB hlmDataDB;

    private LocalDateTime timestamp;

    private Double artFlow;
    private Double flow2;
    private Double flow3;

    private Double pressure1;
    private Double pressure2;

    private Double temp1;
    private Double temp2;
    private Double temp3;

    private Double plegieflowA;
    private Double plegieflowB;
    private Double plegiedruck;
    private Double gasmixflow;
    private Double gasfio2;

    private Double bgtempVen;
    private Double o2satVen;
    private Double hct;
    private Integer pathfreq;
    private Double pattemp1;
    private Integer patartdruck;
    private Integer patpuldruck;
    private Integer patzvdruck;

    private Double ci;
    private Double flowRel;
    private Double svr;
    private Double gasBlood;
    private Double narkosegas;

}
