package sample.Objects;

public class playerStat {
    String opponent;
    int mp, gs, a, cs, og, ps, pm, yc, rc, points, b, s, gc, gw;

    public playerStat(int gw, String opponent, int mp, int gs, int a, int cs, int og, int ps, int pm, int yc, int rc, int points, int b, int s, int gc) {
        this.gw = gw;
        this.opponent = opponent;
        this.mp = mp;
        this.gs = gs;
        this.a = a;
        this.cs = cs;
        this.og = og;
        this.ps = ps;
        this.pm = pm;
        this.yc = yc;
        this.rc = rc;
        this.points = points;
        this.b = b;
        this.s = s;
        this.gc = gc;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public int getMp() {
        return mp;
    }

    public void setMp(int mp) {
        this.mp = mp;
    }

    public int getGs() {
        return gs;
    }

    public void setGs(int gs) {
        this.gs = gs;
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public int getCs() {
        return cs;
    }

    public void setCs(int cs) {
        this.cs = cs;
    }

    public int getOg() {
        return og;
    }

    public void setOg(int og) {
        this.og = og;
    }

    public int getPs() {
        return ps;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    public int getYc() {
        return yc;
    }

    public void setYc(int yc) {
        this.yc = yc;
    }

    public int getRc() {
        return rc;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getGc() {
        return gc;
    }

    public void setGc(int gc) {
        this.gc = gc;
    }

    public int getGw() {
        return gw;
    }

    public void setGw(int gw) {
        this.gw = gw;
    }
}
