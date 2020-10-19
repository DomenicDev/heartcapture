package de.cassisi.hearth.entity;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * DiagnosisData is an immutable class that covers a collection of detected diagnostics.
 */
@Getter
public final class DiagnosisData {

    private final List<String> diagnosisData;

    /**
     * Creates new object containing the specified data.
     * Note that the data will be stored as an immutable list.
     * @param diagnosisData diagnostic data
     */
    public DiagnosisData(List<String> diagnosisData) {
        this.diagnosisData = Collections.unmodifiableList(diagnosisData);
    }
}
