package de.cassisi.hearth.entity;

import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter
@Builder
public final class HlmOperationData {

    @NonNull private final List<String> operation;

    public HlmOperationData(@NonNull List<String> operation) {
        this.operation = Collections.unmodifiableList(operation);
    }
}
