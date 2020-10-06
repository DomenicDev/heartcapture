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

    private String bloodGroup;
    private String bloodRef;
    private int height; // in cm
    private int weight;
    private double bsa;
    private double factor;
    private double sollFluss;


    public enum Sex {
        MALE,
        FEMALE
    }

}
