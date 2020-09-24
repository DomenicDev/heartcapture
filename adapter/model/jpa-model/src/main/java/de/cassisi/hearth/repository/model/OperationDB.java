package de.cassisi.hearth.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@AllArgsConstructor
@Builder
@Data
@Entity
public class OperationDB {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private String roomNr;
    private boolean nirsDataAvailable;
    private boolean infusionDataAvailable;
    private boolean anesthesiaDataAvailable;
    private boolean hlmDataAvailable;
    private boolean locked;

}
