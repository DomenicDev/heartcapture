package de.cassisi.hearth.repository.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MachineDataDB {

    private String oxygenator;
    private String haemoFil;

}
