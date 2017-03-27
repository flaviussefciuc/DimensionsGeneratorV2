package sample;

/**
 * Created by flavius.sefciuc on 3/17/2017.
 */
public class Precode<P,L> {
    private P precode;
    private L label;

    public Precode(P precod, L label){
            this.precode = precod;
            this.label = label;
    }

    public P getPrecod() {
        return precode;
    }

    public void setPrecod(P precode) {
        this.precode = precode;
    }

    public L getLabel() {
        return label;
    }

    public void setLabel(L label) {
        this.label = label;
    }

}
