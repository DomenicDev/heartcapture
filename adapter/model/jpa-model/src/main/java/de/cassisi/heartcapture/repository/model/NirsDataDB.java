package de.cassisi.heartcapture.repository.model;

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
    private int leftSaturation;
    private int rightSaturation;

    @ManyToOne(fetch = FetchType.LAZY)
    private OperationDB operation;

}
