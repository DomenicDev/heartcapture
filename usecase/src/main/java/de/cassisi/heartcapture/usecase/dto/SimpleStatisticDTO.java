package de.cassisi.heartcapture.usecase.dto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class SimpleStatisticDTO {

    public long numberOfOperations;
    public long numberOfIncompleteOperations;
    public long numberOfOpenOperations;

    public Map<LocalDate, Integer> numberOfOperationsLast2Weeks = new HashMap<>();
    public Map<String, Long> ageDistribution = new HashMap<>();

    @Override
    public String toString() {
        return "SimpleStatisticDTO{" +
                "numberOfOperations=" + numberOfOperations +
                ", numberOfIncompleteOperations=" + numberOfIncompleteOperations +
                ", numberOfOpenOperations=" + numberOfOpenOperations +
                ", numberOfOperationsLast2Weeks=" + numberOfOperationsLast2Weeks +
                ", ageDistribution=" + ageDistribution +
                '}';
    }
}
