package de.cassisi.hearth.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
public class InfusionDataDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private List<SinglePerfusorData> infusionData;

    @ManyToOne
    @JoinColumn(name = "operationId", nullable = false)
    private OperationDB operation;

}
