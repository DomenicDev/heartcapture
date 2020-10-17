package de.cassisi.hearth.repository.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class RiskFactorDataDB {

    @ElementCollection
    private List<String> riskFactors = new ArrayList<>();

}
