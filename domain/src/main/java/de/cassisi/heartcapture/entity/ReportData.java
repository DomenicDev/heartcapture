package de.cassisi.heartcapture.entity;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * This class covers all data needed to generate a report
 * for a specific operation.
 */
@Getter
public class ReportData {

    @NonNull private final Operation operation;
    @NonNull private final HLMData hlmData;
    @NonNull private final List<NIRSData> nirsData;
    @NonNull private final List<InfusionData> infusionData;
    @NonNull private final List<AnesthesiaData> anesthesiaData;
    @NonNull private final PreMedicationData preMedicationData;

    /**
     * Creates a ReportData object with the specified data.
     * @param operation the operation this report covers
     * @param hlmData the hearth lung machine data this report covers
     * @param nirsData the NIRS data this report covers
     * @param infusionData the infusion data this report covers
     * @param anesthesiaData the anesthesia data this report covers
     * @param preMedicationData the pre medication data for that operation
     */
    public ReportData(@NonNull Operation operation, @NonNull HLMData hlmData, @NonNull List<NIRSData> nirsData, @NonNull List<InfusionData> infusionData, @NonNull List<AnesthesiaData> anesthesiaData, @NonNull PreMedicationData preMedicationData) {
        this.operation = operation;
        this.hlmData = hlmData;
        this.nirsData = unmodifiableList(nirsData);
        this.infusionData = unmodifiableList(infusionData);
        this.anesthesiaData = unmodifiableList(anesthesiaData);
        this.preMedicationData = preMedicationData;
    }
}
