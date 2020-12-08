package sample.Objects;


import javafx.scene.control.Button;
import javafx.scene.layout.HBox;


public class fixtureData{
    private HBox hometeam;
    private Button hometeamScore;
    private Button awayteamScore;
    private HBox awayteam;

    public fixtureData(HBox hometeam, Button hometeamScore, Button awayteamScore, HBox awayteam) {
        this.hometeam = hometeam;
        this.hometeamScore = hometeamScore;
        this.awayteamScore = awayteamScore;
        this.awayteam = awayteam;
    }

    public HBox getHometeam() {
        return hometeam;
    }

    public void setHometeam(HBox hometeam) {
        this.hometeam = hometeam;
    }

    public Button getHometeamScore() {
        return hometeamScore;
    }

    public void setHometeamScore(Button hometeamScore) {
        this.hometeamScore = hometeamScore;
    }

    public Button getAwayteamScore() {
        return awayteamScore;
    }

    public void setAwayteamScore(Button awayteamScore) {
        this.awayteamScore = awayteamScore;
    }

    public HBox getAwayteam() {
        return awayteam;
    }

    public void setAwayteam(HBox awayteam) {
        this.awayteam = awayteam;
    }
}
