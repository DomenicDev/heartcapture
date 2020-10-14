package de.cassisi.hearth.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class HlmBloodSample {

    @NonNull private LocalDateTime timestamp;
    @NonNull private Type typ;
    private double hctOFL;
    private double hbOfl;
    private double phOfl;
    private double pco2Ofl;
    private double po2Ofl;
    private double hco3Ofl;
    private double tco2Ofl;
    private double bavtOfl;
    private double so2Ofl;
    private int glucose;
    private int na;
    private double k;
    private double ca;
    private double lactat;

    public enum Type {

        /**
         * arterial data
         */
        ART,

        /**
         * venous data
         */
        VEN
    }

}
