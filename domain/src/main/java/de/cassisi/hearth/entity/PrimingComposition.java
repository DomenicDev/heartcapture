package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public final class PrimingComposition {

    private final double totalPriming;
    private final List<Priming> primingData;

}
