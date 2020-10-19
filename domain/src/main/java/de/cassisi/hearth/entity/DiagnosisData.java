package de.cassisi.hearth.entity;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public final class DiagnosisData {

    private final List<String> diagnosisData;

    public DiagnosisData(List<String> diagnosisData) {
        this.diagnosisData = Collections.unmodifiableList(diagnosisData);
    }
}
