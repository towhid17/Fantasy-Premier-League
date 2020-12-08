package sample.Controllers;

import sample.Clients.ServerData;
import sample.Clients.ClientMain;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;


import static sample.Controllers.signin.*;
import static sample.Controllers.signin.Gameweek;

public class myteam{
    public static Button btnAutoMyteam = new Button();
    public static int id = 0;
    static int SubIndicator = 0;
    static int idx = 0;
    Pane[] box = new Pane[15];
    Button[] btnp = new Button[15];
    public static Button btnAnim = new Button();
    int[] myteamPlayerPosition = new int[15];
    ImageView[] imageArray = new ImageView[15];
    private int bx=690, by=510, f1x=345, f1y=55, fallx=690, f2y=160, f3y=275, f4y=405, dx=23, dy=58;
    public static Button btnClearPlayerButton = new Button();
    public static int captainId;
    VBox[] hoverButton = new VBox[15];
    HBox[] detailsBox = new HBox[15];
    Button[] btnInfo = new Button[15];
    Button[] btnSub = new Button[15];
    Pane[] backBox = new Pane[15];
    private double[] imgX = new double[15];

    private static final ImageView background = new ImageView();
    private static final double BLUR_AMOUNT = 30;
    private static final Effect frostEffect = new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 5);
    private boolean isPitchView = false;

    @FXML Button btnSave, btnReset, btnBenchBoost, btnTripleCaptain, btnFreeHit;
    @FXML Pane playerOption;
    @FXML Button btncaptain, btnvcaptain, btnInformation, btnsubstitute, btnListView;
    @FXML Text textGW, txtBB, txtTC, txtFH;
    @FXML HBox CBOX, pOpHbox;
    @FXML Pane playerBtnPane, playerBtnPane1, playerOption1;
    @FXML Pane listImg, clickPane;
    private static boolean chipsFlag = false;
    public static Button btnChips = new Button();


    /////////////////////////////// Methods ///////////////////////////////////////////////////////////////

    public void initialize() {
        for(int i=0; i<=14; i++){
            btnSub[i] = new Button();
            btnInfo[i] = new Button();
            btnInfo[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    playerBtnAction(event);
                }
            });
            btnSub[i].setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    playerBtnAction(event);
                }
            });
        }

        createPlayerBox();

        background.setEffect(frostEffect);
        playerOption.toBack();
        playerOption.setOpacity(0);
        playerOption1.toBack();
        playerOption1.setOpacity(0);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println("getCapatain Start");
                captainId = ServerData.getCaptain(Gameweek, teamid);
                System.out.println("getCaptain");
                setPlayerPitchView();
                for(int i=0; i<=14; i++){
                    myteamPlayerPosition[i] = signin.dataplayerDetails[i].getIsOnField();
                }
                textGW.setText("Gameweek "+String.valueOf(signin.Gameweek)+" Deadline: ");
            }
        };
        btnAutoMyteam.setOnAction(event);
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
                setChipsCondition();
            }
        };
        btnChips.setOnAction(event3);

        btnSave.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
        btnReset.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
    }

    private void createPlayerBox() {
        for(int i=0; i<=14; i++){
            hoverButton[i] = new VBox();
            hoverButton[i].getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            hoverButton[i].getStyleClass().add("button17");

            final int ib = i;
            hoverButton[i].setOnMouseEntered(e->{
                slideIn(ib);
            });
            hoverButton[i].setOnMouseExited(e->{
                slideOut(ib);
            });

            backBox[i] = new Pane();
            backBox[i].setPrefWidth(0);
            backBox[i].setPrefHeight(95);
            backBox[i].setStyle("-fx-background-color: linear-gradient(to right top, #6229D2, #AD25E4); -fx-background-radius: 10");


            btnInfo[i].setText("Info");
            btnInfo[i].getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnInfo[i].getStyleClass().add("button18");
            btnSub[i].setText("+");
            btnSub[i].getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnSub[i].getStyleClass().add("button18");
            hoverButton[i].setSpacing(2);
            hoverButton[i].getChildren().setAll(btnInfo[i], btnSub[i]);
            hoverButton[i].setAlignment(Pos.TOP_RIGHT);

            playerBtnPane.getChildren().add(hoverButton[i]);
            String idd = btnInfo[i].getId();

            //for List View
            detailsBox[i] = new HBox();
            detailsBox[i].setPrefWidth(250);
            detailsBox[i].setPrefHeight(40);
            detailsBox[i].setSpacing(25);
            detailsBox[i].setAlignment(Pos.CENTER_LEFT);

            box[i] = new Pane();
            box[i].setPrefWidth(90);
            box[i].setPrefHeight(58);
            imageArray[i] = new ImageView();
            imageArray[i].setFitHeight(58);
            imageArray[i].setFitWidth(44);
            btnp[i] = new Button();
            btnp[i].getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnp[i].getStyleClass().add("button20");
            btnp[i].setPrefHeight(20);
            btnp[i].setPrefWidth(90);
            playerBtnPane1.getChildren().addAll(imageArray[i], btnp[i], box[i], detailsBox[i], backBox[i]);
        }
    }

    private void slideIn(int i){
        if(!isPitchView) {
            Timeline t = new Timeline();
            DoubleProperty d = backBox[i].prefWidthProperty();
            DoubleProperty d2 = imageArray[i].layoutXProperty();

            t.getKeyFrames().addAll(
                    new KeyFrame(new Duration(0), new KeyValue(d, 0), new KeyValue(d2, imgX[i])),
                    new KeyFrame(new Duration(200), new KeyValue(d, 90), new KeyValue(d2, imgX[i] - 23))
            );
            t.play();
        }
    }

    private void slideOut(int i){
        if(!isPitchView) {
            Timeline t = new Timeline();
            DoubleProperty d = backBox[i].prefWidthProperty();
            double d1 = backBox[i].getPrefWidth();
            DoubleProperty d2 = imageArray[i].layoutXProperty();
            double d3 = imageArray[i].getLayoutX();

            t.getKeyFrames().addAll(
                    new KeyFrame(new Duration(0), new KeyValue(d, d1), new KeyValue(d2, d3)),
                    new KeyFrame(new Duration(200), new KeyValue(d, 0), new KeyValue(d2, imgX[i]))
            );
            t.play();
        }
    }

    @FXML
    private void handleMouseEvent(MouseEvent event){
        if(event.getSource().equals(btncaptain))
        {
            captainId = Integer.parseInt(playerWindow.id);
            captainBoxPosition();
            closePlOption();

            if(detectChange()){
                btnSave.setStyle("-fx-border-color: red; -fx-text-fill: white");
                btnReset.setStyle("-fx-border-color: red; -fx-text-fill: white");
            }
            else {
                btnSave.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
                btnReset.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
            }

        }
        if(event.getSource().equals(btnvcaptain))
        {
            closePlOption();
        }
        if(event.getSource().equals(btnInformation))
        {
            playerWindow.playerWindowGW = signin.Gameweek;
            playerWindow.btnAutoHistory.fire();
            ClientMain.addPane(10);
            closePlOption();
        }
        if(event.getSource().equals(btnsubstitute)){
            Substitute();
            closePlOption();
        }


    }

    private void closePlOption(){
        playerOption.setOpacity(0);
        playerOption.toBack();
        playerOption1.setOpacity(0);
        playerOption1.toBack();
        clickPane.toBack();
    }

    private void infoAction(String idd){
        playerWindow.id = idd;
        playerWindow.playerWindowGW = signin.Gameweek;
        playerWindow.btnAutoHistory.fire();
        ClientMain.addPane(10);
    }

    private void defaultPlayerPosition(){
        Image image = new Image("/sample/photos/Jersey/NON.png");
        for(int i=0; i<=14; i++){
            imageArray[i].setImage(image);
            btnp[i].setText("");
            btnp[i].setId("0");
        }
        CBOX.setOpacity(0);
        signin.dataplayerDetails[1].setIsOnField(0); signin.dataplayerDetails[5].setIsOnField(0);
        signin.dataplayerDetails[6].setIsOnField(0);
        signin.dataplayerDetails[11].setIsOnField(0);
        int mf=4;
        int df=3;
        int srt=3;

        int itf = 0;
        int itb = 0;
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
                btnp[i].setId(String.valueOf(signin.dataplayerDetails[i].getId()));
                imageArray[i].setLayoutX(f1x + dx);
                imageArray[i].setLayoutY(f1y - dy);
            } else {
                if (signin.dataplayerDetails[i].getIsOnField() == 1) {
                    itf++;
                    btnp[i].setLayoutX((fallx / (f + 1.0)) * itf);
                    btnp[i].setLayoutY(ly);
                    imageArray[i].setLayoutX(((fallx / (f + 1.0)) * itf) + dx);
                    imageArray[i].setLayoutY(ly - dy);
                } else {
                    itb++;
                    btnp[i].setLayoutX((bx / 5.0) * itb);
                    btnp[i].setLayoutY(by);
                    imageArray[i].setLayoutX(((bx / 5.0) * itb) + dx);
                    imageArray[i].setLayoutY(by - dy);
                }
            }
        }
    }

    private void userTeamPlayerPosition(){
        int mf = 0;
        int df = 0;
        int srt = 0;
        String frm = "";
        for (int i = 0; i <= 14; i++) {
            btnp[i].setText(signin.dataplayerDetails[i].getString());
            if (i == 0 || i == 1)
                frm = "GK.png";
            else frm = ".png";
            Image image = new Image("/sample/photos/Jersey/" + signin.dataplayerDetails[i].getClub() + frm);
            imageArray[i].setImage(image);
            if (signin.dataplayerDetails[i].getIsOnField() == 1) {
                if (signin.dataplayerDetails[i].getPos() == "MF") {
                    mf++;
                }
                if (signin.dataplayerDetails[i].getPos() == "DEF") {
                    df++;
                }
                if (signin.dataplayerDetails[i].getPos() == "ST") {
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

            if( signin.dataplayerDetails[0].getIsOnField() == 0) {
                hoverButton[1].setLayoutX(f1x);
                hoverButton[1].setLayoutY(f1y-dy);
                hoverButton[0].setLayoutX(bx/5.0);
                hoverButton[0].setLayoutY(by-dy);

                btnp[1].setLayoutX(f1x);
                btnp[1].setLayoutY(f1y);
                btnp[1].setId(String.valueOf(signin.dataplayerDetails[1].getId()));
                btnInfo[1].setId(String.valueOf(signin.dataplayerDetails[1].getId()));
                btnSub[1].setId(String.valueOf(signin.dataplayerDetails[1].getId()));

                imageArray[1].setLayoutX(f1x + dx);
                imageArray[1].setLayoutY(f1y - dy);
                btnp[0].setLayoutX(bx / 5.0);
                btnp[0].setLayoutY(by);
                btnp[0].setId(String.valueOf(signin.dataplayerDetails[0].getId()));
                btnInfo[0].setId(String.valueOf(signin.dataplayerDetails[0].getId()));
                btnSub[0].setId(String.valueOf(signin.dataplayerDetails[0].getId()));
                imageArray[0].setLayoutX((bx / 5.0)+dx);
                imageArray[0].setLayoutY(by - dy);
                backBox[0].setLayoutX((bx / 5.0));
                backBox[0].setLayoutY(by - dy-10);
                backBox[0].toBack();

            }
            else {
                hoverButton[0].setLayoutX(f1x);
                hoverButton[0].setLayoutY(f1y-dy);
                hoverButton[1].setLayoutX(bx/5.0);
                hoverButton[1].setLayoutY(by-dy);

                btnp[0].setLayoutX(f1x);
                btnp[0].setLayoutY(f1y);
                btnp[0].setId(String.valueOf(signin.dataplayerDetails[0].getId()));
                btnInfo[0].setId(String.valueOf(signin.dataplayerDetails[0].getId()));
                btnSub[0].setId(String.valueOf(signin.dataplayerDetails[0].getId()));
                imageArray[0].setLayoutX(f1x + dx);
                imageArray[0].setLayoutY(f1y - dy);
                backBox[0].setLayoutX(f1x);
                backBox[0].setLayoutY(f1y - dy-10);
                backBox[0].toBack();

                btnp[1].setLayoutX(bx / 5.0);
                btnp[1].setLayoutY(by);
                btnp[1].setId(String.valueOf(signin.dataplayerDetails[1].getId()));
                btnInfo[1].setId(String.valueOf(signin.dataplayerDetails[1].getId()));
                btnSub[1].setId(String.valueOf(signin.dataplayerDetails[1].getId()));
                imageArray[1].setLayoutX((bx / 5.0)+dx);
                imageArray[1].setLayoutY(by - dy);
                backBox[1].setLayoutX((bx / 5.0));
                backBox[1].setLayoutY(by - dy-10);
                backBox[1].toBack();

            }

            if(i>1) {
                if (signin.dataplayerDetails[i].getIsOnField() == 1) {
                    itf++;
                    hoverButton[i].setLayoutX(((fallx / (f + 1.0)) * itf));
                    hoverButton[i].setLayoutY(ly - dy);
                    btnp[i].setLayoutX((fallx / (f + 1.0)) * itf);
                    btnp[i].setLayoutY(ly);
                    imageArray[i].setLayoutX(((fallx / (f + 1.0)) * itf) + dx);
                    imageArray[i].setLayoutY(ly - dy);
                    backBox[i].setLayoutX(((fallx / (f + 1.0)) * itf));
                    backBox[i].setLayoutY(ly - dy-10);
                    backBox[i].toBack();
                } else {
                    for(int j=1; j<=3; j++){
                        if(signin.BenchPlayerPriority[j]== signin.dataplayerDetails[i].getId()){
                            itb = j+1;
                        }
                    }
                    hoverButton[i].setLayoutX(((bx / 5.0) * itb));
                    hoverButton[i].setLayoutY(by - dy);
                    btnp[i].setLayoutX((bx / 5.0) * itb);
                    btnp[i].setLayoutY(by);
                    imageArray[i].setLayoutX(((bx / 5.0) * itb) + dx);
                    imageArray[i].setLayoutY(by - dy);
                    backBox[i].setLayoutX(((bx / 5.0) * itb));
                    backBox[i].setLayoutY(by - dy-10);
                    backBox[i].toBack();
                }
                String idd = String.valueOf(signin.dataplayerDetails[i].getId());
                btnp[i].setId(idd);
                btnInfo[i].setId(idd);
                btnSub[i].setId(idd);
            }
        }

    }

    private void captainBoxPosition(){
        CBOX.setOpacity(1);
        for(int i=0; i<=14; i++){
            if(signin.dataplayerDetails[i].getId()==captainId){
                CBOX.setLayoutX(imageArray[i].getLayoutX()-15);
                CBOX.setLayoutY(imageArray[i].getLayoutY());
            }
        }
    }

    @FXML
    private void setPlayerPitchView() {
        if(signin.dataplayerDetails[0].getString().length()==0){
            defaultPlayerPosition();
            CBOX.setOpacity(0);
        }
        else {
            userTeamPlayerPosition();
            CBOX.setOpacity(1);
            CBOX.toFront();
        }
        for(int i=0; i<=14; i++){
            imgX[i] = imageArray[i].getLayoutX();
        }
        captainBoxPosition();
    }

    @FXML
    private void setPlayerPosition(){
        if(isPitchView){
            styleForPitch();
            setPlayerPitchView();
            isPitchView=false;
            btnListView.setText("List View");
        }
        else {
            styleForList();
            setPlayerListView();
            isPitchView = true;
            btnListView.setText("Pitch View");
        }
    }

    private void setPlayerListView(){
        int y1 = 48;
        int x1 = 20;
        int y2 = 5;
        int x2 = 420;
        int x5 = 570;
        int x3 = 25;
        int x4 = 150;
        int yy = 50;
        int ib = 0;
        int iF = 0;
        for(int i=0; i<=14; i++){
            if(signin.dataplayerDetails[i].getIsOnField()==1){
                imageArray[i].setLayoutY(y1*iF+yy);
                imageArray[i].setLayoutX(x3);
                hoverButton[i].setLayoutY(y1*iF+yy);
                hoverButton[i].setLayoutX(x3);
                btnp[i].setLayoutX(x1+x3);
                btnp[i].setLayoutY(y2+y1*iF+yy);
                detailsBox[i].setLayoutX(x4);
                detailsBox[i].setLayoutY(y1*iF+yy);
                iF++;
            }
            else {
                imageArray[i].setLayoutY(y1*ib+yy);
                imageArray[i].setLayoutX(x2);
                hoverButton[i].setLayoutY(y1*ib+yy);
                hoverButton[i].setLayoutX(x2);
                btnp[i].setLayoutX(x1+x2);
                btnp[i].setLayoutY(y2+y1*ib+yy);
                detailsBox[i].setLayoutX(x5);
                detailsBox[i].setLayoutY(y1*ib+yy);
                ib++;
            }
        }
        captainBoxPosition();
    }

    private void styleForList(){
        listImg.setOpacity(1);
        for(int i=0; i<=14; i++){
            imageArray[i].setFitHeight(40);
            imageArray[i].setFitWidth(30);
            hoverButton[i].getStyleClass().removeAll();
            hoverButton[i].getStyleClass().add("button17b");
            hoverButton[i].getChildren().remove(0);
            Text point = new Text(String.valueOf(signin.dataplayerDetails[i].getPoints()));
            Text pos = new Text(signin.dataplayerDetails[i].getPos());
            Text fix = new Text("Fixture");
            point.setStyle("-fx-fill: white; -fx-font-weight: bold");
            pos.setStyle("-fx-fill: white;-fx-font-weight: bold");
            fix.setStyle("-fx-fill: white;-fx-font-weight: bold");
            detailsBox[i].getChildren().addAll(pos, point, fix);
        }
    }

    private void styleForPitch(){
        listImg.setOpacity(0);
        for(int i=0; i<=14; i++){
            imageArray[i].setFitHeight(58);
            imageArray[i].setFitWidth(44);
            hoverButton[i].getStyleClass().remove("button17b");
            hoverButton[i].getStyleClass().add("button17");
            hoverButton[i].getChildren().removeAll(btnSub[i], btnInfo[i]);
            hoverButton[i].getChildren().addAll(btnInfo[i], btnSub[i]);
            detailsBox[i].getChildren().remove(0, 3);
        }
    }

    @FXML
    private void OnclickPane(MouseEvent event){
        if(event.getSource().equals(clickPane)){
            closePlOption();
        }
    }

    @FXML
    private void playerBtnAction(MouseEvent event) {
        int d=0;
        for(int i=0; i<=14; i++){
            if(event.getSource().equals(btnSub[i])) {
                playerWindow.id = btnSub[i].getId();
                id = Integer.parseInt(btnp[i].getId());
                d = i;

                if (SubIndicator == 1) {
                    SubstituteButtonUpdate(d);
                } else {
                    if (signin.dataplayerDetails[d].getIsOnField() == 0) {
                        btncaptain.setOpacity(0);
                        btnvcaptain.setOpacity(0);
                    } else {
                        btncaptain.setOpacity(1);
                        btnvcaptain.setOpacity(1);
                    }
                    clickPane.toFront();
                    playerOptionSet(i);
                }
            }
            if(event.getSource().equals(btnInfo[i])){
                playerWindow.id = btnInfo[i].getId();
                id = Integer.parseInt(btnp[i].getId());
                d = i;
                playerWindow.playerWindowGW = signin.Gameweek;
                System.out.println(btnInfo[i].getId());
                playerWindow.btnAutoHistory.fire();
                ClientMain.addPane(10);
                playerOption.setOpacity(0);
                playerOption.toBack();
            }
        }
    }

    private void playerOptionSet(int i)
    {
        double dy = 0;
        double dx = 0;
        if(hoverButton[i].getLayoutY()>330) dy=playerOption.getPrefHeight();
        if(hoverButton[i].getLayoutX()>500) dx=playerOption.getPrefWidth();

        background.setImage(Menu.copyBackground(playerOption, 220,50));
        background.setFitWidth(playerOption.getWidth());
        background.setFitHeight(playerOption.getHeight());
        playerOption1.getChildren().setAll(background);
        playerOption1.toFront();
        playerOption.toFront();

        double x = playerBtnPane.getLayoutX()+hoverButton[i].getLayoutX();
        double y = playerBtnPane.getLayoutY()+hoverButton[i].getLayoutY();
        playerOption1.setLayoutX(btnSub[i].getLayoutX()+x-dx);
        playerOption1.setLayoutY(btnSub[i].getLayoutY()+y-dy);
        playerOption.setLayoutX(btnSub[i].getLayoutX()+x-dx);
        playerOption.setLayoutY(btnSub[i].getLayoutY()+y-dy);
        Timeline t = new Timeline();

        /////top portion///////
        String frm = "";
        if (i == 0 || i == 1)
            frm = "GK.png";
        else frm = ".png";

        ImageView IM = new ImageView();
        Image image = new Image("/sample/photos/Jersey/" + signin.dataplayerDetails[i].getClub() + frm);
        IM.setImage(image);
        IM.setFitWidth(35);
        IM.setFitHeight(45);

        VBox vBox1 = new VBox();
        vBox1.setSpacing(5);
        vBox1.setAlignment(Pos.CENTER);
        Text name = new Text(dataplayerDetails[i].getString());
        name.setStyle("-fx-font-weight: bold; -fx-font-size: 15; -fx-fill: white");
        HBox hBox1 = new HBox();
        hBox1.setSpacing(5);
        hBox1.setAlignment(Pos.CENTER);
        Text cl = new Text(dataplayerDetails[i].getClub());
        Text pos = new Text(dataplayerDetails[i].getPos());
        cl.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-fill: white");
        pos.setStyle("-fx-font-weight: bold; -fx-font-size: 12; -fx-fill: white");
        hBox1.getChildren().setAll(cl, pos);
        vBox1.getChildren().setAll(name, hBox1);

        HBox hBox0 = new HBox();
        hBox0.setSpacing(5);
        hBox0.setAlignment(Pos.CENTER);
        hBox0.getChildren().setAll(IM, vBox1);

        VBox vBox2 = new VBox();
        vBox2.setSpacing(5);
        vBox2.setAlignment(Pos.CENTER);
        Text text = new Text("Points");
        Text text1 = new Text(String.valueOf(dataplayerDetails[i].getPoints()));
        text.setStyle("-fx-font-weight: bold; -fx-font-size: 15; -fx-fill: white");
        text1.setStyle("-fx-font-weight: bold; -fx-font-size: 15; -fx-fill: white");
        vBox2.getChildren().setAll(text, text1);

        VBox vBox3 = new VBox();
        vBox3.setSpacing(5);
        vBox3.setAlignment(Pos.CENTER);
        Text text3 = new Text("Cost");
        Text text4 = new Text(String.valueOf(dataplayerDetails[i].getCost()/10));
        text3.setStyle("-fx-font-weight: bold; -fx-font-size: 15; -fx-fill: white");
        text4.setStyle("-fx-font-weight: bold; -fx-font-size: 15; -fx-fill: white");
        vBox3.getChildren().setAll(text3, text4);

        pOpHbox.getChildren().setAll(hBox0, vBox2, vBox3);
        ///////////////////////////////////////////////////////////////

        double px = 389;
        double py = 208;
        DoubleProperty d1 = playerOption1.prefHeightProperty();
        DoubleProperty d2 = playerOption1.prefWidthProperty();
        DoubleProperty d3 = playerOption.opacityProperty();
        playerOption1.setOpacity(1);

        t.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(d1, 1), new KeyValue(d2, 1),
                        new KeyValue(d3, 0)),
                new KeyFrame(new Duration(100), new KeyValue(d1, py), new KeyValue(d2, px),
                        new KeyValue(d3, 0)),
                new KeyFrame(new Duration(150), new KeyValue(d3, 1))
        );
        t.play();
    }

    private void Substitute(){
        int[] idx1 = new int[4];
        int k=0;
        String pos = "";
        for(int i=0; i<=14; i++){
            if(signin.dataplayerDetails[i].getId()==id){
                pos = signin.dataplayerDetails[i].getPos();
                idx = i;
            }
            if(signin.dataplayerDetails[i].getIsOnField()==0){
                idx1[k++]=i;
            }
        }
        SubIndicator = 1;
        int d=0;
//        if(playerPosition[0]==0){
//            d=0;
//        }
//        else d=1;
        if(signin.dataplayerDetails[idx].getIsOnField()==0){
            if (pos == "GK") {
                if(idx==0){
                    d=1;
                }
                else d=0;
                box[idx].setOpacity(1);
                box[idx].toFront();
                box[idx].setLayoutX(btnp[idx].getLayoutX());
                box[idx].setLayoutY(imageArray[idx].getLayoutY());
                btnp[idx].toFront();
                imageArray[idx].toFront();
                box[idx].setStyle("-fx-border-color: Green; -fx-border-width: 2; -fx-background-color: linear-gradient(to top, rgba(0,255,0, .3), rgba(0,0,0,0))");
                box[d].setStyle("-fx-border-color: Red; -fx-border-width: 2; -fx-background-color: linear-gradient(to top, rgba(255,0,0, .3), rgba(0,0,0,0))");
                box[d].setLayoutX(btnp[d].getLayoutX());
                box[d].setLayoutY(imageArray[d].getLayoutY());
                box[d].setOpacity(1);
                box[d].toFront();
                btnp[d].toFront();
                imageArray[d].toFront();
            }
            else {
                for(int i=2; i<=14; i++){
                    box[i-2].setOpacity(1);
                    box[i-2].toFront();
                    box[i-2].setLayoutX(btnp[i].getLayoutX());
                    box[i-2].setLayoutY(imageArray[i].getLayoutY());
                    btnp[i].toFront();
                    imageArray[i].toFront();
                    if(signin.dataplayerDetails[i].getIsOnField()==1){
                        box[i-2].setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-background-color: linear-gradient(to top, rgba(255,0,0, .3), rgba(0,0,0,0))");
                    }
                    else box[i-2].setStyle("-fx-border-color: Green; -fx-border-width: 2; -fx-background-color: linear-gradient(to top, rgba(0,255,0, .3), rgba(0,0,0,0))");
                }
            }
        }
        else{
            if (pos == "GK") {
                if(idx==0){
                    d=1;
                }
                else d=0;
                box[idx].setOpacity(1);
                box[idx].toFront();
                box[idx].setLayoutX(btnp[idx].getLayoutX());
                box[idx].setLayoutY(imageArray[idx].getLayoutY());
                box[d].setStyle("-fx-border-color: Green; -fx-border-width: 2; -fx-background-color: linear-gradient(to top, rgba(0,255,0, .3), rgba(0,0,0,0))");
                box[idx].setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-background-color: linear-gradient(to top, rgba(255,0,0, .3), rgba(0,0,0,0))");
                btnp[idx].toFront();
                imageArray[idx].toFront();
                box[d].setLayoutX(btnp[d].getLayoutX());
                box[d].setLayoutY(imageArray[d].getLayoutY());
                box[d].setOpacity(1);
                box[d].toFront();
                btnp[d].toFront();
                imageArray[d].toFront();
            } else {
                box[0].setOpacity(1);
                box[0].setLayoutX(btnp[idx].getLayoutX());
                box[0].setLayoutY(imageArray[idx].getLayoutY());
                box[0].setStyle("-fx-border-color: red; -fx-border-width: 2; -fx-background-color: linear-gradient(to top, rgba(255,0,0, .3), rgba(0,0,0,0))");
                box[0].toFront();
                for(int i=1; i<=3; i++ ){
                    box[i].setOpacity(1);
                    box[i].setLayoutX(btnp[idx1[i]].getLayoutX());
                    box[i].setLayoutY(imageArray[idx1[i]].getLayoutY());
                    box[i].toFront();
                    btnp[idx1[i]].toFront();
                    imageArray[idx1[i]].toFront();
                    box[i].setStyle("-fx-border-color: Green; -fx-border-width: 2; -fx-background-color: linear-gradient(to top, rgba(0,255,0, .3), rgba(0,0,0,0))");
                }
            }
        }
    }

    private void setBtnAlert()  {
        if(detectChange()){
            btnSave.setStyle("-fx-border-color: red; -fx-text-fill: white");
            btnReset.setStyle("-fx-border-color: red; -fx-text-fill: white");
            System.out.println("detectChange is true");
        }
        else {
            btnSave.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
            btnReset.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
            System.out.println("detectChange is false");

        }
    }

    private void SubstituteButtonUpdate(int d) {
        if(signin.dataplayerDetails[idx].getPos()=="GK"){
            if(signin.dataplayerDetails[d].getPos()=="GK"){
                int dd = signin.dataplayerDetails[d].getIsOnField();
                signin.dataplayerDetails[d].setIsOnField(signin.dataplayerDetails[idx].getIsOnField());
                signin.dataplayerDetails[idx].setIsOnField(dd);
                if(signin.dataplayerDetails[idx].getIsOnField()==0){
                    signin.BenchPlayerPriority[0]= signin.dataplayerDetails[idx].getId();
                }
                else{
                    signin.BenchPlayerPriority[0] = signin.dataplayerDetails[d].getId();
                }
                if(captainId== signin.dataplayerDetails[idx].getId()){
                    captainId = signin.dataplayerDetails[d].getId();
                }
                if(!isPitchView) setPlayerPitchView();
                else setPlayerListView();
                SubIndicator=0;
                for(int i=0; i<=13; i++){
                    box[i].setOpacity(0);
                    box[i].toBack();
                }
                setBtnAlert();
            }
        }
        else {
            if(signin.dataplayerDetails[idx].getIsOnField()==1){
                if(signin.dataplayerDetails[d].getIsOnField()==0 && signin.dataplayerDetails[d].getPos()!="GK"){
                    int dd = signin.dataplayerDetails[d].getIsOnField();
                    signin.dataplayerDetails[d].setIsOnField(signin.dataplayerDetails[idx].getIsOnField());
                    signin.dataplayerDetails[idx].setIsOnField(dd);
                    for(int j=1; j<=3; j++){
                        if(signin.BenchPlayerPriority[j]== signin.dataplayerDetails[d].getId()){
                            signin.BenchPlayerPriority[j]= signin.dataplayerDetails[idx].getId();
                        }
                    }
                    if(captainId== signin.dataplayerDetails[idx].getId()){
                        captainId = signin.dataplayerDetails[d].getId();
                    }
                    if(!isPitchView) setPlayerPitchView();
                    else setPlayerListView();
                    SubIndicator=0;
                    for(int i=0; i<=13; i++){
                        box[i].setOpacity(0);
                        box[i].toBack();
                    }
                    setBtnAlert();
                }
            }
            else{
                if(signin.dataplayerDetails[d].getPos()!="GK"){
                    if(signin.dataplayerDetails[d].getIsOnField()==0){
                        int dx = 0;
                        int idxx =0;
                        for(int j=1; j<=3; j++){
                            if(signin.BenchPlayerPriority[j]== signin.dataplayerDetails[d].getId()){
                                dx = j;
                            }
                            if(signin.BenchPlayerPriority[j]== signin.dataplayerDetails[idx].getId()){
                                idxx = j;
                            }
                        }
                        signin.BenchPlayerPriority[dx] = signin.dataplayerDetails[idx].getId();
                        signin.BenchPlayerPriority[idxx] = signin.dataplayerDetails[d].getId();
                        if(captainId== signin.dataplayerDetails[idx].getId()){
                            captainId = signin.dataplayerDetails[d].getId();
                        }
                        if(!isPitchView) setPlayerPitchView();
                        else setPlayerListView();
                    }
                    else {
                        for(int j=1; j<=3; j++){
                            if(signin.BenchPlayerPriority[j]== signin.dataplayerDetails[idx].getId()){
                                signin.BenchPlayerPriority[j]= signin.dataplayerDetails[d].getId();
                            }
                        }
                        int dd = signin.dataplayerDetails[d].getIsOnField();
                        signin.dataplayerDetails[d].setIsOnField(signin.dataplayerDetails[idx].getIsOnField());
                        signin.dataplayerDetails[idx].setIsOnField(dd);
                        if(captainId== signin.dataplayerDetails[idx].getId()){
                            captainId = signin.dataplayerDetails[d].getId();
                        }
                        if(!isPitchView) setPlayerPitchView();
                        else setPlayerListView();
                    }
                    SubIndicator=0;
                    for(int i=0; i<=13; i++){
                        box[i].setOpacity(0);
                        box[i].toBack();
                    }
                    setBtnAlert();

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
        }
    }

    private boolean detectChange()  {
        boolean flag = false;
        for(int i=0; i<=3; i++){
            if(signin.BenchPlayerPriority[i]!= signin.BenchPlayerPrioritycheck[i]){
                flag=true;
                System.out.println(signin.BenchPlayerPriority[i]+"  !=  "+ signin.BenchPlayerPrioritycheck[i]);
            }
        }
        int capid = ServerData.getCaptain(Gameweek, teamid);
        if(capid!=captainId){
            flag = true;
            System.out.println("captrue");
        }
        return flag;
    }

    @FXML
    private void saveTeam()  {
        boolean flag = false;
        flag = detectChange();

        if(flag){
            ServerData.saveTeamChange();
            ServerData.setCaptain(captainId);
            for(int i=0; i<=14; i++){
                myteamPlayerPosition[i] = signin.dataplayerDetails[i].getIsOnField();
            }
            for(int i=0; i<=3; i++){
                signin.BenchPlayerPrioritycheck[i]= signin.BenchPlayerPriority[i];
            }
            Menu.btnSaveAnim.fire();
        }
        btnSave.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
        btnReset.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
    }

    @FXML
    private void setReset() {
        for(int i=0; i<=14; i++){
            signin.dataplayerDetails[i].setString(signin.playerDetails[i].getString());
            signin.dataplayerDetails[i].setClub(signin.playerDetails[i].getClub());
            signin.dataplayerDetails[i].setCost(signin.playerDetails[i].getCost());
            signin.dataplayerDetails[i].setPoints(signin.playerDetails[i].getPoints());
            signin.dataplayerDetails[i].setPos(signin.playerDetails[i].getPos());
            signin.dataplayerDetails[i].setIsOnField(signin.playerDetails[i].getIsOnField());
        }
        for(int i=0; i<=3; i++){
            signin.BenchPlayerPriority[i] = signin.BenchPlayerPrioritycheck[i];
        }
        captainId = ServerData.getCaptain(Gameweek, teamid);
        if(!isPitchView) setPlayerPitchView();
        else setPlayerListView();
        btnSave.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
        btnReset.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
    }

    @FXML
    private void benchBoost() {
        ServerData.benchBoost(Gameweek, teamid);
        setChipsCondition();
        transfers.btnChipsWildcard.fire();
    }

    @FXML
    private void tripleCaptain() {
        ServerData.tripleCaptain(Gameweek, teamid);
        setChipsCondition();
        transfers.btnChipsWildcard.fire();
    }

    @FXML
    private void freeHit() {
        ServerData.freeHit(Gameweek, teamid);
        setChipsCondition();
        transfers.btnChipsWildcard.fire();
    }

    private void setChipsCondition() {
        int chips = ServerData.getChips(signin.Gameweek, teamid);
        if(chips==0){
            btnFreeHit.setText("play");
            btnTripleCaptain.setText("play");
            btnBenchBoost.setText("play");
            txtBB.setText("Bench Boost");
            txtFH.setText("Free Hit");
            txtTC.setText("Triple Captain");

            if(ServerData.isPlayedChips(2, teamid, Gameweek)){
                btnBenchBoost.setText("Unavailable");
            }
            if(ServerData.isPlayedChips(3, teamid, Gameweek)){
                btnTripleCaptain.setText("Unavailable");
            }
            if(ServerData.isPlayedChips(4, teamid, Gameweek)){
                btnFreeHit.setText("Unavailable");
            }
        }
        else if(chips==2){
            txtBB.setText("Bench Boost Playing");
            btnBenchBoost.setText("Cancel");
            btnTripleCaptain.setText("Unavailable");
            btnFreeHit.setText("Unavailable");
            chipsFlag = true;
        }
        else if(chips==3){
            txtTC.setText("Triple Captain Playing");
            btnTripleCaptain.setText("Cancel");
            btnBenchBoost.setText("Unavailable");
            btnFreeHit.setText("Unavailable");
            chipsFlag = true;
        }
        else if(chips==4){
            txtFH.setText("Free Hit Playing");
            btnFreeHit.setText("Done");
            btnTripleCaptain.setText("Unavailable");
            btnBenchBoost.setText("Unavailable");
            chipsFlag = true;
        }
        if(chips==1){
            btnFreeHit.setText("Unavailable");
            btnTripleCaptain.setText("Unavailable");
            btnBenchBoost.setText("Unavailable");
            chipsFlag = true;
        }
    }

}

