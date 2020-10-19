package de.cassisi.hearth.entity;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

@Getter
public final class RiskFactorData {

    private final List<String> risks;

    public RiskFactorData(List<String> risks) {
        this.risks = Collections.unmodifiableList(risks);
    }
}
