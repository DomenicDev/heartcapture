package de.cassisi.hearth.repository.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
public class NirsDataDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    private double leftSaturation;
    private double rightSaturation;

    @ManyToOne
    @JoinColumn(name = "operationId", nullable = false)
    private OperationDB operation;



}
