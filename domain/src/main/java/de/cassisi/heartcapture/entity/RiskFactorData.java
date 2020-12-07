package de.cassisi.heartcapture.entity;

import lombok.Getter;
import lombok.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * This class contains information about risk factors for a patient / operation.
 */
@Getter
public final class RiskFactorData {

    private final List<String> risks;

    public RiskFactorData(@NonNull List<String> risks) {
        this.risks = Collections.unmodifiableList(risks);
    }
}
