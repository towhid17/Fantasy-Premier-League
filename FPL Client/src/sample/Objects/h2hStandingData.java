package sample.Objects;

public class h2hStandingData {
    int rank;
    String name;
    int win;
    int lose;
    int draw;
    int id;
    int point;

    public h2hStandingData(int rank, String name, int win, int lose, int draw, int id, int point) {
        this.rank = rank;
        this.name = name;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
        this.id = id;
        this.point = point;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
