package de.cassisi.hearth.entity;

public class HLMFullData {

    private final String blutgruppe;
    private final String blutref;
    private final int groesse; // in cm
    private final int weight;
    private final String bsa;
    private final String factor;
    private final String sollFluss;

    public HLMFullData(String blutgruppe, String blutref, int groesse, int weight, String bsa, String factor, String sollFluss) {
        this.blutgruppe = blutgruppe;
        this.blutref = blutref;
        this.groesse = groesse;
        this.weight = weight;
        this.bsa = bsa;
        this.factor = factor;
        this.sollFluss = sollFluss;
    }
}
