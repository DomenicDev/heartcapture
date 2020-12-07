package de.cassisi.heartcapture.entity;

import lombok.*;

import java.util.Collections;
import java.util.List;

/**
 * Immutable class for storing previous operation data.
 */
@Getter
@Builder
public final class HlmOperationData {

    @NonNull private final List<String> operation;

    /**
     * Creates new instance with specified operation data.
     * @param operation previous operation data - converted to an immutable list
     */
    public HlmOperationData(@NonNull List<String> operation) {
        this.operation = Collections.unmodifiableList(operation);
    }
}
