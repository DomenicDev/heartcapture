package de.cassisi.heartcapture.repository.model;

import de.cassisi.heartcapture.entity.PatientData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDataDB {

    private LocalDate birthday;
    @Enumerated(EnumType.ORDINAL)
    private PatientData.Sex sex;
    private int age;
    private String bloodGroup;
    private String bloodRef;
    private int height; // in cm
    private int weight;
    private double bsa;
    private double factor;
    private double sollFluss;

}
