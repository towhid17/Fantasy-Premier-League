package sample.Objects;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class LeagueTable {
    Button League;
    Button Option;
    int Crank;
    int Lrank;
    ImageView im;

    public LeagueTable(Button league, Button option, int crank, int lrank, ImageView imageView) {
        League = league;
        Option = option;
        Crank = crank;
        Lrank = lrank;
        im = imageView;
    }

    public ImageView getIm() {
        return im;
    }

    public void setIm(ImageView im) {
        this.im = im;
    }

    public Button getLeague() {
        return League;
    }

    public void setLeague(Button league) {
        League = league;
    }

    public Button getOption() {
        return Option;
    }

    public void setOption(Button option) {
        Option = option;
    }

    public int getCrank() {
        return Crank;
    }

    public void setCrank(int crank) {
        Crank = crank;
    }

    public int getLrank() {
        return Lrank;
    }

    public void setLrank(int lrank) {
        Lrank = lrank;
    }
}
