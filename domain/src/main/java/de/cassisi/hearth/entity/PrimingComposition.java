package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public final class PrimingComposition {

    private final double totalPriming;
    private final List<Priming> primingData;

}
