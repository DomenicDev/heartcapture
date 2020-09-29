package de.cassisi.hearth.repository.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NirsDataDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    private double leftSaturation;
    private double rightSaturation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operationId", nullable = false)
    private OperationDB operation;



}
