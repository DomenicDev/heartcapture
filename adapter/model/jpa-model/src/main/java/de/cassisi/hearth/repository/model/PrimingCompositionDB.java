package de.cassisi.hearth.repository.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrimingCompositionDB {

    private double totalPriming;

    @ElementCollection
    private final List<PrimingDataDB> primingData = new ArrayList<>();

}
