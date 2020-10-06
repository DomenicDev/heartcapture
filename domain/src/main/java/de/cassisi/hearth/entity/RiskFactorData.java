package de.cassisi.hearth.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public final class RiskFactorData {

    private List<String> risks;

}
