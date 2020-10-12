package de.cassisi.heart.port;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class BloodSampleData {

    private Double hctOFL;
    private Double hbOfl;
    private Double phOfl;
    private Double pco2Ofl;
    private Double po2Ofl;
    private Double hco3Ofl;
    private Double tco2Ofl;
    private Double bavtOfl;
    private Double so2Ofl;
    private Integer glucose;
    private Integer na;
    private Double k;
    private Double ca;
    private Double lactat;

}
