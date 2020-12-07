package de.cassisi.heartcapture.entity;


import lombok.*;

import java.time.LocalDateTime;

/**
 * Representation of one user defined event during surgery.
 */
@Getter
@Builder
@AllArgsConstructor
public final class HLMEventData {

    @NonNull private final LocalDateTime timestamp;
    @NonNull private final EventType type;
    private final Integer amount;
    private final Unit unit;
    private final Integer factor;
    private final String free;
    @NonNull private final Typ typ;

    public enum EventType {
        UNKNOWN,
        ACT,
        BYPASS_BEGINN,
        AORTA_ZU,
        KARDIOPLEGIE,
        NABI_8_4_PC,
        JONOSTERIL,
        AORTA_AUF,
        REPERFUSION_BEGINN,
        DEFIBRILLATION,
        BYPASS_ENDE,
        REPERFUSION_ENDE,
        MASCHINENBLUT,
        RESTBLUT_PERF,
        HEPARIN,
        CS_EK,
        FREMDBLUT,
        HUMANALBUMIN_5,
        HUMANALBUMIN_20,
        HAEMOFILTRAT,
        CELL_SAVER_ABGESAUGT,
        HAEMOFILTRATION,
        CYTOKIN_ADSORPTION,
        LEVEL_ALARM_AUS,
        LEVEL_ALARM_EIN,
        RESERVOIRVOLUMEN,
        KOD
    }

    public enum Unit {
        ML,
        SEC
    }

    public enum Typ {
        COM,
        IN,
        OUT
    }

}
