package de.cassisi.heartcapture.repository.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnesthesiaDataDB {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime timestamp;
    @NonNull
    private Double depthOfAnesthesia;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "operationId", nullable = false)
    private OperationDB operation;

}
