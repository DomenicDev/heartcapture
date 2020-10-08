package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public final class PatientData {

    private final LocalDate birthday;
    private final Sex sex;
    private final int age;

    private final String bloodGroup;
    private final String bloodRef;
    private final int height; // in cm
    private final int weight;
    private final double bsa;
    private final double factor;
    private final double sollFluss;


    public enum Sex {
        MALE,
        FEMALE
    }

}
