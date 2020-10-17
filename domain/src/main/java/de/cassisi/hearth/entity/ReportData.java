package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.List;

@Getter
@AllArgsConstructor
public class ReportData {

    @NonNull private final Operation operation;
    @NonNull private final HLMData hlmData;
    @NonNull private final List<NIRSData> nirsData;
    @NonNull private final List<InfusionData> infusionData;
    @NonNull private final List<AnesthesiaData> anesthesiaData;

}
