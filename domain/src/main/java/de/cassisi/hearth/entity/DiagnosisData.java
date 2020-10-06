package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public final class DiagnosisData {

    private final List<String> diagnose;

}
