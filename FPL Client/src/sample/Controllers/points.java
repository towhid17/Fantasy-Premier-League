package sample.Controllers;

import sample.Clients.ServerData;
import sample.Clients.ClientMain;
import sample.Objects.PlayerDetails;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static sample.Controllers.signin.*;

public class points {
    public static Button btnAutoPoints = new Button();
    public static Button btnAnim = new Button();
    public static int totalGWPoints, pointsGW;
    Button[] btnp = new Button[15];
    Button[] btnv = new Button[15];
    ImageView[] imageArray = new ImageView[15];
    private static PlayerDetails[] playerPointsDetails = new PlayerDetails[15];
    @FXML Button btnprevious, btnnext;
    public static Button btnClearPlayerButton = new Button();
    public static int[] pointsBenchPlayerPriority = new int[4];
    @FXML HBox CBOX;
    @FXML Text TGWpoints, textGW, HighestPoints, totalPoints, chipsPlayed;
    @FXML Pane playerBtnPane;
    public static Button btnsetInfo = new Button();
    public static boolean hasPoints = false;
    public static int pointsTeamId;

    /////////////////////////////////METHOD////////////////////////////////////

    public void initialize(){
        pointsTeamId = teamid;

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                setPlayerPosition();
                if(hasPoints)
                    setCaptainPointsOnButton(signin.Gameweek-1, pointsTeamId);
                pointsGW = signin.Gameweek-1;
                textGW.setText("Gameweek "+String.valueOf(pointsGW));
            }
        };

        btnAutoPoints.setOnAction(event);

        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                clearPlayerButton();
            }
        };
        btnClearPlayerButton.setOnAction(event2);

        EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if(hasPoints)
                    setInfo(signin.Gameweek-1);
            }
        };
        btnsetInfo.setOnAction(event3);

        createPlayerBox();
    }

    private void createPlayerBox() {
        for(int i=0; i<=14; i++){
            imageArray[i] = new ImageView();
            imageArray[i].setFitHeight(58);
            imageArray[i].setFitWidth(44);
            btnp[i] = new Button();
            btnp[i].getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnp[i].getStyleClass().add("button20");
            btnp[i].setPrefHeight(20);
            btnp[i].setPrefWidth(90);
            btnv[i] = new Button();
            btnv[i].getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnv[i].getStyleClass().add("button21");
            btnv[i].setPrefHeight(20);
            btnv[i].setPrefWidth(90);
            playerBtnPane.getChildren().addAll(imageArray[i], btnp[i], btnv[i]);
        }
        for(int i=0; i<=14; i++) {
            imageArray[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    playerBtnAction(event);
                }
            });
        }
    }

    private void setOpacityOfPlayerBtn(int x){
        for(int i=0; i<=14; i++){
            btnp[i].setOpacity(x);
            btnv[i].setOpacity(x);
            imageArray[i].setOpacity(x);
        }
    }

    public void setPlayerPosition(){
        int bx=690, by=510, f1x=345, f1y=55, fallx=690, f2y=160, f3y=275, f4y=405, dx=23, dy=58;

        if(playerPointsDetails[0].getString().length()==0){
            setOpacityOfPlayerBtn(0);
        }
        else {
            setOpacityOfPlayerBtn(1);
            int mf = 0;
            int df = 0;
            int srt = 0;
            String frm = "";
            for (int i = 0; i <= 14; i++) {
                btnp[i].setText(playerPointsDetails[i].getString());
                btnv[i].setText(String.valueOf(playerPointsDetails[i].getPoints()));
                if (i == 0 || i==1)
                    frm = "GK.png";
                else frm = ".png";
                Image image = new Image("/sample/photos/Jersey/" + playerPointsDetails[i].getClub() + frm);
                imageArray[i].setImage(image);
                if (playerPointsDetails[i].getIsOnField() == 1) {
                    if (playerPointsDetails[i].getPos() == "MF") {
                        mf++;
                    }
                    if (playerPointsDetails[i].getPos() == "DEF") {
                        df++;
                    }
                    if (playerPointsDetails[i].getPos() == "ST") {
                        srt++;
                    }
                }
            }

            int itf = 0;
            int itb = 1;
            int ly = 0;
            int f = 0;
            for (int i = 0; i <= 14; i++) {
                if (i == 2) {
                    ly = f2y;
                    f = df;
                } else if (i == 7) {
                    ly = f3y;
                    itf = 0;
                    f = mf;
                } else if (i == 12) {
                    ly = f4y;
                    itf = 0;
                    f = srt;
                }
                if (i == 0) {
                    btnp[i].setLayoutX(f1x);
                    btnp[i].setLayoutY(f1y);
                    btnv[i].setLayoutX(f1x);
                    btnv[i].setLayoutY(f1y+25);
                    btnp[i].setId(String.valueOf(playerPointsDetails[i].getId()));
                    imageArray[i].setLayoutX(f1x + dx);
                    imageArray[i].setLayoutY(f1y - dy);
                } else {
                    if (playerPointsDetails[i].getIsOnField() == 1) {
                        itf++;
                        btnp[i].setLayoutX((fallx / (f + 1.0)) * itf);
                        btnp[i].setLayoutY(ly);
                        btnv[i].setLayoutX((fallx / (f + 1.0)) * itf);
                        btnv[i].setLayoutY(ly+25);
                        imageArray[i].setLayoutX(((fallx / (f + 1.0)) * itf) + dx);
                        imageArray[i].setLayoutY(ly - dy);
                    } else {
                        for(int j=1; j<=3; j++){
                            if(pointsBenchPlayerPriority[j]==playerPointsDetails[i].getId()){
                                itb = j+1;
                            }
                        }
                        btnp[i].setLayoutX((bx / 5.0) * itb);
                        btnp[i].setLayoutY(by);
                        btnv[i].setLayoutX((bx / 5.0) * itb);
                        btnv[i].setLayoutY(by+25);
                        imageArray[i].setLayoutX(((bx / 5.0) * itb) + dx);
                        imageArray[i].setLayoutY(by - dy);
                    }
                    btnp[i].setId(String.valueOf(playerPointsDetails[i].getId()));

                }
            }
        }

    }

    @FXML
    private void playerBtnAction(MouseEvent event){
        for(int i=0; i<=14; i++){
            if(event.getSource().equals(imageArray[i])) {
                playerWindow.id = btnp[i].getId();
            }
        }
        playerWindow.playerWindowGW = pointsGW;
        playerWindow.btnAutoHistory.fire();
        ClientMain.addPane(10);
    }

    public static void playerPointsCalc(int GW, int teamid) {
        playerPointsDetails[0].setString("");
        playerPointsDetails = ServerData.getPlayerData(teamid, GW);
        if(playerPointsDetails[0].getString().equals("")){
            hasPoints = false;
            return;
        }
        pointsBenchPlayerPriority = ServerData.getBechPriority(teamid, GW);
        hasPoints = true;
    }


    public static void pointsDestructor(){
        for(int i=0; i<=14; i++){
            playerPointsDetails[i] = null;
        }
    }

    public static void pointsContructor(){
        for(int i=0; i<=14; i++){
            playerPointsDetails[i] = new PlayerDetails();
        }
    }

    @FXML
    private void handleMouseEvent(MouseEvent event){
        if(event.getSource() == btnprevious){
            if(pointsGW >1) {
                playerPointsCalc(--pointsGW, pointsTeamId);
                setPlayerPosition();
                if(hasPoints) {
                    setCaptainPointsOnButton(pointsGW, pointsTeamId);
                    setInfo(pointsGW);
                }
            }
        }
        if(event.getSource() == btnnext){
            if(pointsGW < signin.Gameweek-1) {
                playerPointsCalc(++pointsGW, pointsTeamId);
                setPlayerPosition();
                if(hasPoints) {
                    setCaptainPointsOnButton(pointsGW, pointsTeamId);
                    setInfo(pointsGW);
                }
            }
        }


    }

    private void clearPlayerButton(){
        Image img = new Image("/sample/photos/Jersey/NON.png");
        for(int i=0; i<=14; i++){
            imageArray[i].setImage(img);
            btnp[i].setId("");
            btnp[i].setText("");
            btnv[i].setText("");
        }
    }

    private void setCaptainPointsOnButton(int gw, int tid) {
        int capid = ServerData.getCaptain(gw, tid);
        for(int i=0; i<=14; i++){
            if(playerPointsDetails[i].getId()==capid){
                int cp = playerPointsDetails[i].getPoints();
                cp = cp*2;
                System.out.println(cp);
                btnv[i].setText(String.valueOf(cp));
                CBOX.toFront();
                CBOX.setLayoutX(imageArray[i].getLayoutX()-15);
                CBOX.setLayoutY(imageArray[i].getLayoutY());
            }
        }
    }

    private void setInfo(int Gameweek) {
        textGW.setText("Gameweek "+String.valueOf(Gameweek));
        int gwp = ServerData.getGWpoints(Gameweek, teamid);
        TGWpoints.setText(String.valueOf(gwp));
        int tp = ServerData.getTotalpoints(Gameweek, teamid);
        totalPoints.setText(String.valueOf(tp));
        int hp = ServerData.getHighestPoints(Gameweek);
        HighestPoints.setText(String.valueOf(hp));
        int chips = ServerData.getChips(Gameweek, teamid);
        chipsPlayed.setStyle("-fx-fill: #00b400");
        if(chips==1) chipsPlayed.setText("Wildcard");
        else if(chips==2) chipsPlayed.setText("Bench Boost");
        else if(chips==3) chipsPlayed.setText("Tripe Captain");
        else if(chips==4) chipsPlayed.setText("Free Hit");
        else {
            chipsPlayed.setText("None");
            chipsPlayed.setStyle("-fx-fill: gray");
        }
    }


    //Image image = new Image("/sample.photos/sample.Jersey/NON.png");
    //            for(int i=0; i<=14; i++){
    //                imageArray[i].setImage(image);
    //                btnp[i].setText("");
    //                btnv[i].setText("");
    //                btnp[i].setId("0");
    //                playerPointsDetails[i].setIsOnField(1);
    //            }
    //            playerPointsDetails[1].setIsOnField(0);
    //            playerPointsDetails[5].setIsOnField(0);
    //            playerPointsDetails[6].setIsOnField(0);
    //            playerPointsDetails[11].setIsOnField(0);
    //
    //            int mf=4;
    //            int df=3;
    //            int srt=3;
    //            int itf = 0;
    //            int itb = 0;
    //            int ly = 0;
    //            int f = 0;
    //            for (int i = 0; i <= 14; i++) {
    //                if (i == 2) {
    //                    ly = f2y;
    //                    f = df;
    //                } else if (i == 7) {
    //                    ly = f3y;
    //                    itf = 0;
    //                    f = mf;
    //                } else if (i == 12) {
    //                    ly = f4y;
    //                    itf = 0;
    //                    f = srt;
    //                }
    //                if (i == 0) {
    //                    btnp[i].setLayoutX(f1x);
    //                    btnp[i].setLayoutY(f1y);
    //                    btnv[i].setLayoutX(f1x);
    //                    btnv[i].setLayoutY(f1y+25);
    //                    btnp[i].setId(String.valueOf(playerPointsDetails[i].getId()));
    //                    imageArray[i].setLayoutX(f1x + dx);
    //                    imageArray[i].setLayoutY(f1y - dy);
    //                } else {
    //                    if (playerPointsDetails[i].getIsOnField() == 1) {
    //                        itf++;
    //                        btnp[i].setLayoutX((fallx / (f + 1.0)) * itf);
    //                        btnp[i].setLayoutY(ly);
    //                        btnv[i].setLayoutX((fallx / (f + 1.0)) * itf);
    //                        btnv[i].setLayoutY(ly+25);
    //                        imageArray[i].setLayoutX(((fallx / (f + 1.0)) * itf) + dx);
    //                        imageArray[i].setLayoutY(ly - dy);
    //                    } else {
    //                        itb++;
    //                        btnp[i].setLayoutX((bx / 5.0) * itb);
    //                        btnp[i].setLayoutY(by);
    //                        btnv[i].setLayoutX((bx / 5.0) * itb);
    //                        btnv[i].setLayoutY(by+25);
    //                        imageArray[i].setLayoutX(((bx / 5.0) * itb) + dx);
    //                        imageArray[i].setLayoutY(by - dy);
    //                    }
    //                }
    //            }
}
