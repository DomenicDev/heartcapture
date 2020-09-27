package de.cassisi.hearth.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

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

    @OneToMany(mappedBy = "operation")
    private Set<NirsDataDB> nirsData;

    @OneToMany(mappedBy = "operation")
    private Set<AnesthesiaDataDB> anesthesiaData;

    @OneToMany(mappedBy = "operation")
    private Set<InfusionDataDB> infusionData;

    @OneToOne(optional = true)
    @JoinColumn(name = "hlm_data_id", nullable = true, unique = true)
    private HLMDataDB hlmData;

}
