package de.cassisi.heartcapture.repository.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HLMDataDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private OperationDB operation;

    @OneToMany(
            mappedBy = "hlmData",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<HlmEventDataDB> hlmEventDataDBList = new ArrayList<>();


    @OneToMany(
            mappedBy = "hlmDataDB",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<HlmBloodSampleDB> hlmBloodSampleDBList = new ArrayList<>();

    @OneToMany(
            mappedBy = "hlmDataDB",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private final List<HlmParamDataDB> hlmParamDataDBS = new ArrayList<>();

    @Embedded
    private  DiagnosisDataDB diagnosisDataDB;

    @Embedded
    private HlmOperationDataDB hlmOperationDataDB;

    @Embedded
    private RiskFactorDataDB riskFactorDataDB;;

    @Embedded
    private PatientDataDB patientDataDB;

    @Embedded
    private MachineDataDB machineDataDB;

    @Embedded
    private PrimingCompositionDB primingCompositionDB;


    // ---------------- HELPER METHODS ----------------- //

    public void add(HlmEventDataDB hlmEventDataDB) {
        this.hlmEventDataDBList.add(hlmEventDataDB);
        hlmEventDataDB.setHlmData(this);
    }

    public void add(HlmBloodSampleDB hlmBloodSampleDB) {
        this.hlmBloodSampleDBList.add(hlmBloodSampleDB);
        hlmBloodSampleDB.setHlmDataDB(this);
    }

    public void add(HlmParamDataDB paramDataDB) {
        this.hlmParamDataDBS.add(paramDataDB);
        paramDataDB.setHlmDataDB(this);
    }


    // ----------------------------------------------- //
}
