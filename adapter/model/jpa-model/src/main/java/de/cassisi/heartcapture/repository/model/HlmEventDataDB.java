package de.cassisi.heartcapture.repository.model;

import de.cassisi.heartcapture.entity.HLMEventData;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HlmEventDataDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    @Enumerated(EnumType.ORDINAL)
    private HLMEventData.EventType eventType;
    private Integer amount;

    @Enumerated(EnumType.ORDINAL)
    private HLMEventData.Unit unit;

    private String free;

    @Enumerated(EnumType.ORDINAL)
    private HLMEventData.Typ typ;

    @ManyToOne(fetch = FetchType.LAZY)
    private HLMDataDB hlmData;

    private Integer factor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HlmEventDataDB that = (HlmEventDataDB) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


}
