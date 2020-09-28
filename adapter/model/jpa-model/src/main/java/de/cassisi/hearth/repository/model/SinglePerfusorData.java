package de.cassisi.hearth.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SinglePerfusorData {

    private String infusionName;
    private double rate;

}
