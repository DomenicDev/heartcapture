package de.cassisi.hearth.entity;

import java.time.LocalDateTime;
import java.util.List;

public final class InfusionData {

    private final LocalDateTime localDateTime;
    private final List<PerfusorData> perfusorDataList;

    public InfusionData(LocalDateTime localDateTime, List<PerfusorData> perfusorDataList) {
        this.localDateTime = localDateTime;
        this.perfusorDataList = perfusorDataList;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public List<PerfusorData> getPerfusorDataList() {
        return perfusorDataList;
    }

    public static final class PerfusorData {
        private  String name;
        private double rate;

        public PerfusorData(String name, double rate) {
            this.name = name;
            this.rate = rate;
        }

        public String getName() {
            return name;
        }

        public double getRate() {
            return rate;
        }
    }
}
