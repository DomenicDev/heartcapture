package de.cassisi.heart.port;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
class TimeData implements Comparable<TimeData> {

    // TIMESTAMP
    private final LocalDateTime timestamp;

    // ----------------------------
    // HLM PARAM DATA
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
    // ----------------------------

    // --------------------------------
    // BLOOD SAMPLES
    private BloodSampleData bloodDataArt = new BloodSampleData();
    private BloodSampleData bloodDataVen = new BloodSampleData();

    //-----------------------------------

    // EVENTS
    private Integer bypass;
    private Integer aorta;
    private Integer reperfusion;
    private Integer levelstand;
    private Integer kardioplegie;
    private Integer jonosteril;
    private Integer heparin;
    private Integer nabi_8_4;
    private Integer cs_ek;
    private String fremdblut;
    private String humanalbumin_5pc;
    private String humanalbumin_20pc;
    private Integer haemofiltrat;
    private Integer restblut_perf;
    private Integer maschinenblut;
    private Integer cell_saver_abgesaugt;
    private String defibrillation;
    private Integer act;
    private String haemofiltration;
    private String cytokin_adsorption;

    // ------------------------------

    // NIRS DATA
    private Integer leftSaturation;
    private Integer rightSaturation;


    // BIS
    private Double depthOfAnesthesia;


    // PERFUSORS
    private Double perfusorArterenol;
    private Double perfusorVasopressin;
    private Double perfusorSufentanil;

    //------------------------------------------

    // UNKNOWN
    private Double kod;
    private Double do2;

    

    TimeData(@NonNull LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(TimeData o) {
        return timestamp.compareTo(o.timestamp);
    }
}
