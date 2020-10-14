package de.cassisi.hearth.entity;


import lombok.*;

import java.time.LocalDateTime;

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
        LEVELSTAND,
        HEPARIN,
        CS_EK,
        FREMDBLUT,
        HUMANALBUMIN_5,
        HUMANALBUMIN_20,
        HAEMOFILTRAT,
        CELL_SAVER_ABGESAUGT,
        HAEMOFILTRATION,
        CYTOKIN_ADSORPTION,
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
