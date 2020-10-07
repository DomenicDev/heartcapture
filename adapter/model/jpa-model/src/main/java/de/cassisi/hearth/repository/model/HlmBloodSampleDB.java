package de.cassisi.hearth.repository.model;

import de.cassisi.hearth.entity.HlmBloodSample;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HlmBloodSampleDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // data
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private HlmBloodSample.Type typ;
    private double hctOFL;
    private double hbOfl;
    private double phOfl;
    private double pco2Ofl;
    private double po2Ofl;
    private double hco3Ofl;
    private double bavtOfl;
    private double so2Ofl;
    private int glucose;
    private int na;
    private double k;
    private double ca;
    private double lactat;

    @ManyToOne
    private HLMDataDB hlmDataDB;

}
