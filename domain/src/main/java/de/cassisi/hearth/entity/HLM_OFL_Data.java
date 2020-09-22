package de.cassisi.hearth.entity;

public class HLM_OFL_Data {

    private final int nr;
    private final String type;
    private final String hct_ofl;
    private final String hb_ofl;

    public HLM_OFL_Data(int nr, String type, String hct_ofl, String hb_ofl) {
        this.nr = nr;
        this.type = type;
        this.hct_ofl = hct_ofl;
        this.hb_ofl = hb_ofl;
    }

    public int getNr() {
        return nr;
    }

    public String getType() {
        return type;
    }

    public String getHct_ofl() {
        return hct_ofl;
    }

    public String getHb_ofl() {
        return hb_ofl;
    }
}
