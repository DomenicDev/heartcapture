package de.cassisi.hearth.entity;

import java.time.LocalTime;

public final class HLMEventData {

    private final long nr;
    private final long protNr;
    private final LocalTime time;
    private final String text;
    private final int amount;
    private final String unit;
    private final int factor;
    private final int amountML;
    private final char free;
    private final String type;

    public HLMEventData(long nr, long protNr, LocalTime time, String text, int amount, String unit, int factor, int amountML, char free, String type) {
        this.nr = nr;
        this.protNr = protNr;
        this.time = time;
        this.text = text;
        this.amount = amount;
        this.unit = unit;
        this.factor = factor;
        this.amountML = amountML;
        this.free = free;
        this.type = type;
    }

    public long getNr() {
        return nr;
    }

    public long getProtNr() {
        return protNr;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getText() {
        return text;
    }

    public int getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public int getFactor() {
        return factor;
    }

    public int getAmountML() {
        return amountML;
    }

    public char getFree() {
        return free;
    }

    public String getType() {
        return type;
    }
}
