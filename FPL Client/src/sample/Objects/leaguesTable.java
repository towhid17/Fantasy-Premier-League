package sample.Objects;


import javafx.scene.image.ImageView;

public class leaguesTable{
    String Lname;
    String Option;
    int Crank;
    int Lrank;
    String Code;
    ImageView im;

    public leaguesTable(String lname, String option, int crank, int lrank, String code, ImageView imageView) {
        Lname = lname;
        Option = option;
        Crank = crank;
        Lrank = lrank;
        Code = code;
        im = imageView;
    }

    public ImageView getIm() {
        return im;
    }

    public void setIm(ImageView im) {
        this.im = im;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public String getOption() {
        return Option;
    }

    public void setOption(String option) {
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
