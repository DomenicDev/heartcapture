package de.cassisi.hearth.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
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

    @OneToMany(
            mappedBy = "operation",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<NirsDataDB> nirsData;

    @OneToMany(
            mappedBy = "operation",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<AnesthesiaDataDB> anesthesiaData;

    @OneToMany(
            mappedBy = "operation",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<InfusionDataDB> infusionData = new HashSet<>();

    @OneToOne
    @JoinColumn(name = "hlm_data_id", nullable = true, unique = true)
    private HLMDataDB hlmData;


    public void addNirsData(NirsDataDB nirsDataDB) {
        nirsData.add(nirsDataDB);
        nirsDataDB.setOperation(this);
    }

    public void addInfusionData(InfusionDataDB data) {
        if (infusionData == null) {
            infusionData = new HashSet<>();
        }
        this.infusionData.add(data);
        data.setOperation(this);
    }

    public void addAnesthesiaData(AnesthesiaDataDB data) {
        this.anesthesiaData.add(data);
        data.setOperation(this);
    }

}
