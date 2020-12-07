package de.cassisi.heartcapture.repository.model;

import lombok.Getter;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
public class DiagnosisDataDB {

    @ElementCollection
    private final List<String> diagnosisData = new ArrayList<>();

}
