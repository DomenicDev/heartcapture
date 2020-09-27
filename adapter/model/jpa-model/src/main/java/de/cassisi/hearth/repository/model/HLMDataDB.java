package de.cassisi.hearth.repository.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Builder
public class HLMDataDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, mappedBy = "hlmData")
    private OperationDB operation;

}
