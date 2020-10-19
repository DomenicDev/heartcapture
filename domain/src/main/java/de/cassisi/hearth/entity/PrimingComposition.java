package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * This class represents an immutable set of priming information.
 */
@Getter
@Builder
public final class PrimingComposition {

    private final double totalPriming;
    private final List<Priming> primingData;

    public PrimingComposition(double totalPriming, List<Priming> primingData) {
        this.totalPriming = totalPriming;
        this.primingData = Collections.unmodifiableList(primingData);
    }
}
