package sample.Objects;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class playerDetailsStat2 {
    Button info;
    HBox playerbox;
    String player;
    double cost;
    double sel;
    int points;
    String club;
    String pos;

    public playerDetailsStat2(Button info, HBox playerbox, String player, double cost, double sel, int points, String club, String pos) {
        this.info = info;
        this.playerbox = playerbox;
        this.player = player;
        this.cost = cost;
        this.sel = sel;
        this.points = points;
        this.club = club;
        this.pos = pos;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public playerDetailsStat2(Button info, HBox playerbox, String player, double cost, double sel, int points) {
        this.info = info;
        this.playerbox = playerbox;
        this.player = player;
        this.cost = cost;
        this.sel = sel;
        this.points = points;
    }

    public Button getInfo() {
        return info;
    }

    public void setInfo(Button info) {
        this.info = info;
    }

    public HBox getPlayerbox() {
        return playerbox;
    }

    public void setPlayerbox(HBox playerbox) {
        this.playerbox = playerbox;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getSel() {
        return sel;
    }

    public void setSel(double sel) {
        this.sel = sel;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
