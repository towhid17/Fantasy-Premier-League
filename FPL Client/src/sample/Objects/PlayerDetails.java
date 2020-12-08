package sample.Objects;

public class PlayerDetails {
    private String string;
    private String club;
    private String pos;
    private int Points;
    private double sel;
    private double cost;
    int id;
    int isOnField;

    public PlayerDetails(){
        this.string = "";
        this.club = "";
        this.pos = "";
        this.Points = -1;
        this.sel = -1;
        this.cost = -1;
        this.id = -1;
        this.isOnField = -1;
    }
    public PlayerDetails(int id, String string, String club, String pos, int points, double sel, double cost) {
        this.string = string;
        this.club = club;
        this.pos = pos;
        this.Points = points;
        this.sel = sel;
        this.cost = cost;
        this.id = id;
    }

    public int getIsOnField() {
        return isOnField;
    }

    public void setIsOnField(int isOnField) {
        this.isOnField = isOnField;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
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

    public int getPoints() {
        return Points;
    }

    public void setPoints(int points) {
        Points = points;
    }

    public double getSel() {
        return sel;
    }

    public void setSel(double sel) {
        this.sel = sel;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

}

