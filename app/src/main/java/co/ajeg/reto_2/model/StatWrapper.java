package co.ajeg.reto_2.model;

public class StatWrapper {

    private int base_stat;
    private Stat stat;

    public StatWrapper(Stat stat, int base_stat) {
        this.base_stat=base_stat;
        this.stat = stat;
    }

    public StatWrapper() {
    }

    public int getBase_stat() {
        return base_stat;
    }

    public void setBase_stat(int base_stat) {
        this.base_stat = base_stat;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }
}
