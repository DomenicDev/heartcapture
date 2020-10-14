package de.cassisi.hearth.entity;

import lombok.*;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public final class HlmOperationData {

    @NonNull private final List<String> operation;

}
