package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public final class HlmOperationData {

    @NonNull private List<String> operation;

}
