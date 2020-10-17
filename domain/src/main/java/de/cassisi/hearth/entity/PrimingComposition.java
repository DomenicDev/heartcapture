package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public final class PrimingComposition {

    private final double totalPriming;
    private final List<Priming> primingData;

}
