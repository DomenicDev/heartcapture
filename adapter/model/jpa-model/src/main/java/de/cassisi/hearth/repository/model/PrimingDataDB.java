package de.cassisi.hearth.repository.model;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PrimingDataDB {

    private String text;
    private String unit;
    private int amount;

}
