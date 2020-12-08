package sample.Objects;

import javafx.scene.layout.HBox;

public class playerFixture {
    String Date;
    int gw;
    HBox opp;

    public playerFixture(String date, int gw, HBox opp) {
        Date = date;
        this.gw = gw;
        this.opp = opp;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getGw() {
        return gw;
    }

    public void setGw(int gw) {
        this.gw = gw;
    }

    public HBox getOpp() {
        return opp;
    }

    public void setOpp(HBox opp) {
        this.opp = opp;
    }
}


