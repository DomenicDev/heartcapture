package de.cassisi.hearth.repository.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InfusionDataDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "infusion_id")
    private final List<PerfusorDataDB> infusionData = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operationId", nullable = false)
    private OperationDB operation;

    public void addPerfusorData(PerfusorDataDB data) {
        this.infusionData.add(data);
    }

    public void addPerfusorData(PerfusorDataDB... data) {
        if (data == null) return;
        infusionData.addAll(Arrays.asList(data));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfusionDataDB that = (InfusionDataDB) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(infusionData, that.infusionData) &&
                Objects.equals(operation, that.operation);
    }

}
