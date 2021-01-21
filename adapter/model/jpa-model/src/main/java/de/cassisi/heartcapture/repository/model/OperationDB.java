package de.cassisi.heartcapture.repository.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class OperationDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private final Set<NirsDataDB> nirsData = new HashSet<>();

    @OneToMany(
            mappedBy = "operation",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final Set<AnesthesiaDataDB> anesthesiaData = new HashSet<>();

    @OneToMany(
            mappedBy = "operation",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private final Set<InfusionDataDB> infusionData = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "operation")
    //@JoinColumn(name = "hlm_data_id", nullable = true, unique = true)
    private HLMDataDB hlmData;

    @Embedded
    private final PreMedicationDataDB preMedicationData = new PreMedicationDataDB();


    public PreMedicationDataDB getPreMedicationData() {
        if (preMedicationData == null) {
            return new PreMedicationDataDB();
        }
        return preMedicationData;
    }

    public void addNirsData(NirsDataDB nirsDataDB) {
        nirsData.add(nirsDataDB);
        nirsDataDB.setOperation(this);
    }

    public void addInfusionData(InfusionDataDB data) {
        this.infusionData.add(data);
        data.setOperation(this);
    }

    public void addAnesthesiaData(AnesthesiaDataDB data) {
        this.anesthesiaData.add(data);
        data.setOperation(this);
    }

    public void setHlmData(HLMDataDB hlmDataDB) {
        this.hlmData = hlmDataDB;
        if (hlmDataDB != null) {
            hlmDataDB.setOperation(this);
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperationDB )) return false;
        return id != null && id.equals(((OperationDB) o).getId());
    }


}
