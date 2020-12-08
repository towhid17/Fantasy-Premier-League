package sample.Controllers;

import javafx.scene.layout.*;
import sample.Clients.ServerData;
import sample.Clients.ClientMain;
import sample.Objects.playerDetailsStat;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sample.Objects.playerDetailsStat2;

import java.util.HashMap;
import java.util.List;

import java.awt.Point;
import java.awt.MouseInfo;

import static sample.Controllers.signin.*;


public class transfers {
    @FXML public TableView<playerDetailsStat2> playerTable;
    private playerDetailsStat2 pDetails;
    @FXML private TableColumn<playerDetailsStat2, String> Player;
    @FXML private TableColumn<playerDetailsStat2, String>pos;
    @FXML private TableColumn<playerDetailsStat2, Integer>cost;
    @FXML private TableColumn<playerDetailsStat2, Integer>sel;
    @FXML private TableColumn<playerDetailsStat2, Integer>Points;
    @FXML private TableColumn<playerDetailsStat2, String>club;
    @FXML private TableColumn<playerDetailsStat2, Button>no;
    @FXML private TableColumn<playerDetailsStat2, HBox>hbox;

    Button[] btnp = new Button[15];
    Button[] btnv = new Button[15];
    ImageView[] imageArray = new ImageView[15];
    VBox[] hoverButton = new VBox[15];
    Button[] btnInfo = new Button[15];
    Button[] btnSub = new Button[15];
    Pane[] box = new Pane[15];
    Pane[] backBox = new Pane[15];
    HashMap<String, Integer> map = new HashMap<>();
    private final ObservableList<playerDetailsStat2> dataList = FXCollections.observableArrayList();

    @FXML Rectangle recty;
    @FXML AnchorPane transfersAnch;
    @FXML StackPane playerWindow;
    @FXML Button btnClose1, btnClose11;
    @FXML HBox RmoneyBox, TransBox, pOpHbox;
    @FXML VBox conBox, messageBox, message1Pane;
    @FXML Button btnConfirmTransfers, btnmakeTransfer, btnInformation, btnReplace, btnRemove, btnReset, btnWildcard;
    @FXML TextField searchBox;
    @FXML Text trText, TRmoney, TRtrans, textGW, message, freeTR, TRvalue, TRcost, message1;
    @FXML Pane playerOption1, trWhitePane, playerBtnPane, playerBtnPane1, confirmPane, confirmEPane, playerOption,
            clickPane, hoverPane, playerPane;

    public static Button btnAutoTransfers = new Button();
    public static Button btnAnim = new Button();
    public static Button btnClearPlayerButton = new Button();
    public static float RemainingMoney = 0;
    public static int freeTransfers = 0;
    private static int getFreeTransfersfromDatabase = 0;
    private static final double BLUR_AMOUNT = 30;
    private static final Effect frostEffect = new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 5);
    private static final ImageView background = new ImageView();
    private static final ImageView background1 = new ImageView();
    private double[] imgX = new double[15];
    private static int selectedPlayerId = 0;
    private static boolean isHovering = false;
    private static double rowY = 0.0;
    private static double rowX = 0.0;
    public static Button btnChipsWildcard = new Button();
    public static Button btngetInfo = new Button();
    public static boolean WCFlag = false;
    public static Button btnSlideAnim = new Button();

    /////////////////METHOD///////////////////////////////////////////

    public void initialize() {
        createBtnSubInfo();
        createPlayerBox();

        confirmEPane.toBack();
        confirmEPane.setOpacity(0);
        confirmPane.toBack();
        confirmPane.setOpacity(0);
        playerOption.toBack();
        playerOption.setOpacity(0);
        playerOption1.toBack();
        playerOption1.setOpacity(0);
        playerWindow.toBack();
        playerWindow.setOpacity(0);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                setinitLabel();
                textGW.setText("Gameweek "+String.valueOf(signin.Gameweek)+ " Deadline: ");
            }
        };
        btnAutoTransfers.setOnAction(event);

        EventHandler<ActionEvent> event5 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                Timeline t = new Timeline();
                DoubleProperty d = playerPane.layoutXProperty();
                t.getKeyFrames().addAll(
                        new KeyFrame(new Duration(0), new KeyValue(d, 300)),
                        new KeyFrame(new Duration(200), new KeyValue(d, 100))
                );
                t.play();
            }
        };
        btnSlideAnim.setOnAction(event5);

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
        btnChipsWildcard.setOnAction(event3);

        EventHandler<ActionEvent> event4 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                getInfo();
                setInfo();
                getFreeTransfersfromDatabase = freeTransfers;
            }
        };
        btngetInfo.setOnAction(event4);

        messageBox.setOpacity(0);
        //myteam.rectFillAnim(recty);
        //////////////////////////////////////////////////////////////////////////////////////////////////////
        background.setEffect(frostEffect);
        background1.setEffect(frostEffect);
        confirmPane.getChildren().setAll(background);
        playerOption1.getChildren().setAll(background1);
        trWhitePane.setOpacity(0);
        trWhitePane.toBack();
        playerBtnPane.toFront();
        btnmakeTransfer.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
        btnReset.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");

        tableHover();
    }

    private void tableHover(){
        AnimationTimer an = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(isHovering) {
                    hoverPane.setOpacity(1);
                    Point p = MouseInfo.getPointerInfo().getLocation();
                    double x = p.getX()-ClientMain.primaryStage.getX()-(hoverPane.getWidth()/2+220+306)*Menu.ScaleRatio;
                    hoverPane.setLayoutY(rowY+186);
                    if(x>-30 && x<600){
                        hoverPane.setLayoutX(x);
                    }
                }
                else hoverPane.setOpacity(0);
            }
        };
        an.start();
    }

    private void createBtnSubInfo(){
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
    }

    private void createPlayerBox(){
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

            backBox[i] = new Pane();
            backBox[i].setPrefWidth(0);
            backBox[i].setPrefHeight(120);
            backBox[i].setStyle("-fx-background-color: linear-gradient(to right top, #6229D2, #AD25E4); -fx-background-radius: 10");

            box[i] = new Pane();
            box[i].setPrefWidth(90);
            box[i].setPrefHeight(58);
            box[i].setOpacity(0);
            box[i].setStyle("-fx-border-color: white; -fx-border-width: 2; -fx-background-color: linear-gradient(to top, rgba(255,255,255, .3), rgba(0,0,0,0))");
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
            playerBtnPane1.getChildren().addAll(imageArray[i], btnp[i], box[i], btnv[i], backBox[i]);
        }
    }

    private void slideIn(int i){
        Timeline t = new Timeline();
        DoubleProperty d = backBox[i].prefWidthProperty();
        DoubleProperty d2 = imageArray[i].layoutXProperty();

        t.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(d, 0), new KeyValue(d2, imgX[i])),
                new KeyFrame(new Duration(200), new KeyValue(d, 90), new KeyValue(d2, imgX[i]-23))
        );
        t.play();
    }

    private void slideOut(int i){
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

    @FXML
    private void handleMouseEvent(MouseEvent event){
        if(event.getSource().equals(btnClose1)){
            playerWindow.setOpacity(0);
            playerWindow.toBack();
            trWhitePane.setOpacity(0);
            trWhitePane.toBack();
            selectedPlayerId = 0;
        }
        if(event.getSource().equals(btnClose11)){
            closeMenuPane();
        }
        if(event.getSource().equals(btnReplace)){
            closeMenuPane();
            background.setImage(Menu.copyBackground(trWhitePane,220,50));
            background.setFitWidth(trWhitePane.getWidth());
            background.setFitHeight(trWhitePane.getHeight());
            trWhitePane.getChildren().setAll(background);
            replaceAnim();
        }
        if (event.getSource().equals(btnInformation)){
            closeMenuPane();
            ClientMain.addPane(10);
            sample.Controllers.playerWindow.btnAutoHistory.fire();
            selectedPlayerId = 0;

        }
        if (event.getSource().equals(btnRemove)){
            closeMenuPane();
            selectedPlayerId = 0;
            btnReset.setStyle("-fx-border-color: red; -fx-text-fill: white");
        }
        if(event.getSource().equals(btnmakeTransfer)){
            background.setImage(Menu.copyBackground(confirmPane, 220, 50));
            background.setFitWidth(confirmPane.getWidth());
            background.setFitHeight(confirmPane.getHeight());
            confirmPane.getChildren().setAll(background);
            confirmPane.toFront();
            confirmPane.setOpacity(1);
            confirmEPane.toFront();
            confirmEPane.setOpacity(1);
            finalTransfersPlayer();
        }
    }

    private void replaceAnim(){
        Timeline timeline = new Timeline();
        DoubleProperty r = trWhitePane.layoutXProperty();
        trWhitePane.toFront();
        trWhitePane.setOpacity(1);

        timeline.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(r, 980)),
                new KeyFrame(new Duration(250), new KeyValue(r, 330)),
                new KeyFrame(Duration.millis(260), event -> {
                    playerWindow.toFront();
                    playerWindow.setOpacity(1);
                    playerOption.setOpacity(0);
                    playerOption.toBack();
                })
        );
        timeline.play();
    }

    private void finalTransfersPlayer(){
        conBox.getChildren().clear();
        int t = checkTransfers();
        if(t<15 || !detectPlayerReplace()){
            closeMenuPane();
            return;
        }
        if(RemainingMoney < 0 || teamPlayerCheck()){
            btnConfirmTransfers.setOpacity(0);
            message1Pane.setOpacity(1);
            message1Pane.toFront();
            if(RemainingMoney < 0){
                message1.setText("Remaining money should not be less than 0.0");
            }
            else {
                message1.setText("More than 3 players should not be from same team");
            }
        }
        else {
            btnConfirmTransfers.toFront();
            message1Pane.setOpacity(0);
            btnConfirmTransfers.setOpacity(1);
        }

        for(int i=0; i<=14; i++){
            if(signin.playerDetails[i].getId()!= signin.dataplayerDetails[i].getId()){
                String frm = "";
                if(i==0 || i==1)
                    frm="GK.png";
                else frm = ".png";

                HBox hBox = new HBox();
                VBox vBox1 = new VBox();
                HBox vBox2 = new HBox();
                VBox vBox3 = new VBox();
                ImageView imageView1 = new ImageView();
                Image image1 = new Image("/sample/photos/Jersey/" + signin.playerDetails[i].getClub()+frm);
                imageView1.setImage(image1);
                ImageView imageView2 = new ImageView();
                Image image2 = new Image("/sample/photos/Jersey/" + signin.dataplayerDetails[i].getClub()+frm);
                imageView2.setImage(image2);
                imageView1.setFitHeight(58);
                imageView1.setFitWidth(44);
                imageView2.setFitHeight(58);
                imageView2.setFitWidth(44);
                ImageView imageView3 = new ImageView();
                ImageView imageView4 = new ImageView();
                Image image3 = new Image("/sample/icons/in.png");
                Image image4 = new Image("/sample/icons/out.png");
                imageView3.setImage(image3);
                imageView4.setImage(image4);
                vBox1.setAlignment(Pos.CENTER);
                vBox2.setAlignment(Pos.CENTER);
                vBox3.setAlignment(Pos.CENTER);
                hBox.setAlignment(Pos.CENTER);
                hBox.setSpacing(25);

                vBox2.setSpacing(100);
                vBox1.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
                vBox3.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
                hBox.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
                hBox.getStyleClass().add("dropShadowPane");
                vBox1.getStyleClass().add("button11");
                vBox3.getStyleClass().add("button11");
                vBox1.setPrefWidth(100);
                vBox3.setPrefWidth(100);
                vBox1.setPrefHeight(90);
                vBox3.setPrefHeight(90);
                VBox vBox11 = new VBox();
                VBox vBox33 = new VBox();
                vBox11.setPrefWidth(110);
                vBox11.setPrefHeight(100);
                vBox11.getChildren().add(vBox1);
                vBox33.setPrefWidth(110);
                vBox33.setPrefHeight(100);
                vBox33.getChildren().add(vBox3);
                vBox11.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
                vBox33.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
                vBox11.getStyleClass().add("bottomPanGreen");
                vBox33.getStyleClass().add("bottomPanRed");
                vBox11.setAlignment(Pos.CENTER);
                vBox33.setAlignment(Pos.CENTER);
                Text text1 = new Text(signin.playerDetails[i].getString());
                Text text2 = new Text(signin.dataplayerDetails[i].getString());
                text1.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 15");
                text2.setStyle("-fx-fill: white; -fx-font-weight: bold; -fx-font-size: 15");
                vBox1.getChildren().addAll(imageView1, text1);
                vBox2.getChildren().addAll(imageView3, imageView4);
                vBox3.getChildren().addAll(imageView2, text2);
                hBox.getChildren().addAll(vBox11,vBox2,vBox33);
                hBox.setPadding(new Insets(0,0,10,0));
                conBox.getChildren().addAll(hBox);
            }
        }
    }

    private void setPlayerLabel(int i, String getClub, String getString, double getCost, int id){
        float fcost = (float)getCost/10;

        btnp[i].setText(getString);
        btnv[i].setText("$"+String.valueOf(fcost));
        btnp[i].setId(String.valueOf(id));

        String frm = "";
        if(i==0 || i==1)
            frm="GK.png";
        else frm = ".png";
        Image image = new Image("/sample/photos/Jersey/" +getClub+frm);
        imageArray[i].setImage(image);

    }

    ////////////to get data from table row///////////
    @FXML
    private void tableMouseHandle(MouseEvent event) {
        if(playerTable.getSelectionModel().getSelectedItem()!=null){
            pDetails = playerTable.getSelectionModel().getSelectedItem();

            boolean t = true;
            for (int i = 0; i <= 14; i++) {
                if (Integer.parseInt(pDetails.getInfo().getId()) == signin.playerDetails[i].getId()) {
                    t = false;
                }
            }
            if (t) {
                for (int i = 0; i <= 14; i++) {
                    if (selectedPlayerId == signin.playerDetails[i].getId()) {
                        RemainingMoney += (signin.playerDetails[i].getCost()/10-pDetails.getCost());
                        signin.playerDetails[i].setString(pDetails.getPlayer());
                        signin.playerDetails[i].setClub(pDetails.getClub());
                        signin.playerDetails[i].setCost(pDetails.getCost()*10);
                        signin.playerDetails[i].setId(Integer.parseInt(pDetails.getInfo().getId()));
                        signin.playerDetails[i].setPoints(pDetails.getPoints());
                        signin.playerDetails[i].setPos(pDetails.getPos());
                        signin.playerDetails[i].setSel(pDetails.getSel());
                        setPlayerLabel(i, pDetails.getClub(), pDetails.getPlayer(), pDetails.getCost()*10, signin.playerDetails[i].getId());
                        if (signin.playerDetails[i].getId() != signin.dataplayerDetails[i].getId()) {
                            box[i].setOpacity(1);
                            box[i].toBack();
                        } else box[i].setOpacity(0);
                        playerWindow.toBack();
                        playerWindow.setOpacity(0);
                        trWhitePane.toBack();
                        trWhitePane.setOpacity(0);
                        selectedPlayerId = 0;

                    }
                }
                int itr = 0;
                for(int i=0; i<=14; i++){
                    if(signin.dataplayerDetails[i].getId()!= signin.playerDetails[i].getId()){
                        itr++;
                    }
                }
                freeTransfers = getFreeTransfersfromDatabase-itr;
                setInfo();
                if (detectPlayerReplace()) {
                    btnmakeTransfer.setStyle("-fx-border-color: red");
                    btnReset.setStyle("-fx-border-color: red");
                } else {
                    btnmakeTransfer.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
                    btnReset.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
                }
                if (teamPlayerCheck()) {
                    messageBox.setOpacity(1);
                } else {
                    messageBox.setOpacity(0);
                    message.setText("");
                }
            }
        }
    }

    private boolean detectPlayerReplace(){
        for(int i=0; i<=14; i++){
            if(signin.dataplayerDetails[i].getId()!= signin.playerDetails[i].getId()){
                return true;
            }
        }
        return false;
    }


    public void PlayerStats(int Pos) {
        FilteredList<playerDetailsStat2> filteredData = new FilteredList<>(dataList, b -> true);
        SortedList<playerDetailsStat2> sortedData = new SortedList<>(filteredData);
        dataList.clear();

        playerTable.setRowFactory(tableView -> {
            final TableRow row = new TableRow();

            row.hoverProperty().addListener((observable) -> {
                rowX = row.getLayoutX();
                rowY = row.getLayoutY();
                isHovering = true;
            });
            row.setOnMouseExited(event -> {
                isHovering = false;
            });

            return row;
        });

        List list = ServerData.getStatistics(Pos, "", 1);

        Player.setCellValueFactory(new PropertyValueFactory<>("player"));
        club.setCellValueFactory(new PropertyValueFactory<>("club"));
        pos.setCellValueFactory(new PropertyValueFactory<>("pos"));
        Points.setCellValueFactory(new PropertyValueFactory<>("Points"));
        sel.setCellValueFactory(new PropertyValueFactory<>("sel"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        no.setCellValueFactory(new PropertyValueFactory<>("info"));
        hbox.setCellValueFactory(new PropertyValueFactory<>("playerbox"));


        for(Object obj: list) {
            String ans = String.valueOf(obj);
            String[] strs = ans.split("#");
            int i = Integer.parseInt(strs[0]);
            String player = strs[1];
            int p = Integer.parseInt(strs[2]);
            double c = Double.parseDouble(strs[3]);
            double s = Double.parseDouble(strs[4]);
            String t = strs[5];
            int po = Integer.parseInt(strs[6]);
            String pp = "";
            if(p==1)
                pp = "GK";
            else if(p==2)
                pp = "DEF";
            else if(p==3)
                pp = "MF";
            else if(p==4)
                pp = "ST";

            Button info = new Button("i");
            info.setId(String.valueOf(i));
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    ClientMain.addPane(10);
                    sample.Controllers.playerWindow.id = String.valueOf(i);
                    sample.Controllers.playerWindow.btnAutoHistory.fire();
                }
            };
            info.setOnAction(event);
            info.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            info.getStyleClass().add("button16");

            HBox hBox = new HBox();
            VBox vBox = new VBox();
            ImageView im = new ImageView();
            String frm = ".png";
            if (p == 1)
                frm = "GK.png";
            else frm = ".png";
            Image image = new Image("/sample/photos/Jersey/" + t + frm);
            im.setImage(image);
            im.setFitHeight(35);
            im.setFitWidth(25);
            Text name = new Text(player);
            Text clubandpos = new Text(t+"    "+pp);
            name.setStyle("-fx-fill: white");
            clubandpos.setStyle("-fx-fill: white");
            for(int k=0; k<=14; k++){
                if(signin.playerDetails[k].getId()==i){
                    name.setStyle("-fx-fill: #6f6f6f");
                    clubandpos.setStyle("-fx-fill: #6f6f6f");
                }
            }
            vBox.getChildren().setAll(name, clubandpos);
            hBox.getChildren().setAll(im, vBox);
            hBox.setSpacing(15);
            vBox.setSpacing(7);
            playerDetailsStat2 pds = new playerDetailsStat2(info, hBox, player, c,s,po, t, pp);

            dataList.add(pds);
        }

        searchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(player -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (player.getPlayer().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                }
                else
                    return false; // Does not match.
            });
        });

        sortedData.comparatorProperty().bind(playerTable.comparatorProperty());
        playerTable.setItems(sortedData);
    }

    @FXML private void playerBtnAction(MouseEvent event) {
        int Pos = 0;
        String postr= "";
        for(int i=0; i<=14; i++) {
            if(i<2) {
                Pos = 1;
                postr="Goalkeepers";
            }
            else if(i>=2 && i<7) {
                Pos = 2;
                postr="Defenders";
            }
            else if(i>=7 && i<12) {
                Pos = 3;
                postr="Midfielders";
            }
            else{
                Pos = 4;
                postr="Forward";
            }

            if(event.getSource().equals(btnSub[i])){
                background1.setImage(Menu.copyBackground(playerOption1, 220, 50));
                playerOption1.getChildren().setAll(background1);
                sample.Controllers.playerWindow.id = btnp[i].getId();
                PlayerStats(Pos);
                trText.setText(postr);
                clickPane.toFront();
                playerOptionSet(i);
                selectedPlayerId = signin.playerDetails[i].getId();
            }
            if(event.getSource().equals(btnInfo[i])){
                sample.Controllers.playerWindow.id = btnp[i].getId();
                //id = Integer.parseInt(btnp[i].getId());
                //d = i;
                sample.Controllers.playerWindow.playerWindowGW = signin.Gameweek;
                sample.Controllers.playerWindow.btnAutoHistory.fire();
                ClientMain.addPane(10);
                playerOption.setOpacity(0);
                playerOption.toBack();
                selectedPlayerId = 0;
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


        double x = playerBtnPane.getLayoutX()+hoverButton[i].getLayoutX();
        double y = playerBtnPane.getLayoutY()+hoverButton[i].getLayoutY();
        playerOption1.setLayoutX(btnSub[i].getLayoutX()+x-dx);
        playerOption1.setLayoutY(btnSub[i].getLayoutY()+y-dy);
        playerOption.setLayoutX(btnSub[i].getLayoutX()+x-dx);
        playerOption.setLayoutY(btnSub[i].getLayoutY()+y-dy);
        Timeline t = new Timeline();

        double px = 389;
        double py = 208;
        DoubleProperty d1 = playerOption1.prefHeightProperty();
        DoubleProperty d2 = playerOption1.prefWidthProperty();
        DoubleProperty d3 = playerOption.opacityProperty();
        playerOption1.setOpacity(1);
        playerOption.setOpacity(1);

        t.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(d1, 1), new KeyValue(d2, 1),
                        new KeyValue(d3, 0)),
                new KeyFrame(new Duration(100), new KeyValue(d1, py), new KeyValue(d2, px),
                        new KeyValue(d3, 0)),
                new KeyFrame(new Duration(150), new KeyValue(d3, 1))
        );
        t.play();
    }

    private int checkTransfers(){
        int tr=0;
        for(int i=0; i<=14; i++){
            if(signin.playerDetails[i].getId()>0){
                tr++;
            }
        }
        return tr;
    }

    @FXML
    private void makeTransfers() {
        if(RemainingMoney<0){
            return;
        }
        int t = checkTransfers();
        if(t<15){
            return;
        }

        if(teamPlayerCheck()){
            return;
        }

        ServerData.makeTransfers(RemainingMoney);

        setTransfersINandOUT();

        System.out.println("donnnnnnnnnnnnnnnnnnnnnnnnne!");

        int itr=0;
        for(int i=0; i<=14; i++){
            if(signin.dataplayerDetails[i].getId()!= signin.playerDetails[i].getId()) itr++;
            box[i].setOpacity(0);
            signin.dataplayerDetails[i].setString(signin.playerDetails[i].getString());
            signin.dataplayerDetails[i].setClub(signin.playerDetails[i].getClub());
            signin.dataplayerDetails[i].setId(signin.playerDetails[i].getId());
            signin.dataplayerDetails[i].setCost(signin.playerDetails[i].getCost());
            playerDetails[i].setPos(dataplayerDetails[i].getPos());
        }

        System.out.println("update done");

        freeTransfers = getFreeTransfersfromDatabase-itr;
        getFreeTransfersfromDatabase = freeTransfers;

        myteam.btnAutoMyteam.fire();
        System.out.println("myteam update done");
        Menu.btnSaveAnim.fire();
        closeMenuPane();
        btnmakeTransfer.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
        btnReset.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");

        System.out.println("transfers done");
    }


    @FXML
    public void setinitLabel() {
        int f1y=75, fallx=690, f2y=200, f3y=325, f4y=465, dx=23, dy=58;
        int j=0;
        for(int i=0; i<=1; i++){
            btnp[i].setLayoutX((fallx / (2 + 1.0)) * (i+1));
            btnp[i].setLayoutY(f1y);
            btnv[i].setLayoutX((fallx / (2 + 1.0)) * (i+1));
            btnv[i].setLayoutY(f1y+25);
            imageArray[i].setLayoutX(((fallx / (2 + 1.0)) * (i+1)) + dx);
            imageArray[i].setLayoutY(f1y - dy);
            hoverButton[i].setLayoutX((fallx / (2 + 1.0)) * (i+1));
            hoverButton[i].setLayoutY(f1y-dy);
            backBox[i].setLayoutX((fallx / (2 + 1.0)) * (i+1));
            backBox[i].setLayoutY(f1y-dy-10);
            backBox[i].toBack();
            box[i].setLayoutX((fallx / (2 + 1.0)) * (i+1));
            box[i].setLayoutY(f1y-dy);
        }
        for(int i=2; i<=6; i++){
            btnp[i].setLayoutX((fallx / (5 + 1.0)) * (++j));
            btnp[i].setLayoutY(f2y);
            btnv[i].setLayoutX((fallx / (5 + 1.0)) * (j));
            btnv[i].setLayoutY(f2y+25);
            imageArray[i].setLayoutX(((fallx / (5 + 1.0)) * (j)) + dx);
            imageArray[i].setLayoutY(f2y - dy);
            hoverButton[i].setLayoutX((fallx / (5 + 1.0)) * (j));
            hoverButton[i].setLayoutY(f2y-dy);
            backBox[i].setLayoutX((fallx / (5 + 1.0)) * (j));
            backBox[i].setLayoutY(f2y-dy-10);
            backBox[i].toBack();
            box[i].setLayoutX((fallx / (5 + 1.0)) * (j));
            box[i].setLayoutY(f2y-dy);
        }
        j=0;
        for(int i=7; i<=11; i++){
            btnp[i].setLayoutX((fallx / (5 + 1.0)) * (++j));
            btnp[i].setLayoutY(f3y);
            btnv[i].setLayoutX((fallx / (5 + 1.0)) * (j));
            btnv[i].setLayoutY(f3y+25);
            imageArray[i].setLayoutX(((fallx / (5 + 1.0)) * (j)) + dx);
            imageArray[i].setLayoutY(f3y - dy);
            hoverButton[i].setLayoutX((fallx / (5 + 1.0)) * (j));
            hoverButton[i].setLayoutY(f3y-dy);
            backBox[i].setLayoutX((fallx / (5 + 1.0)) * (j));
            backBox[i].setLayoutY(f3y-dy-10);
            backBox[i].toBack();

            box[i].setLayoutX((fallx / (5 + 1.0)) * (j));
            box[i].setLayoutY(f3y-dy);
        }
        j=0;
        for(int i=12; i<=14; i++){
            btnp[i].setLayoutX((fallx / (3 + 1.0)) * (++j));
            btnp[i].setLayoutY(f4y);
            btnv[i].setLayoutX((fallx / (3 + 1.0)) * (j));
            btnv[i].setLayoutY(f4y+25);
            imageArray[i].setLayoutX(((fallx / (3 + 1.0)) * (j)) + dx);
            imageArray[i].setLayoutY(f4y - dy);
            hoverButton[i].setLayoutX((fallx / (3 + 1.0)) * (j));
            hoverButton[i].setLayoutY(f4y-dy);
            backBox[i].setLayoutX((fallx / (3 + 1.0)) * (j));
            backBox[i].setLayoutY(f4y-dy-10);
            backBox[i].toBack();

            box[i].setLayoutX((fallx / (3 + 1.0)) * (j));
            box[i].setLayoutY(f4y-dy);
        }

        if(signin.dataplayerDetails[0].getString().length() == 0){
            Image image = new Image("/sample/photos/Jersey/NON.png");
            for(int i=0; i<=14; i++){
                imageArray[i].setImage(image);
                btnp[i].setText("");
                btnv[i].setText("");
                btnp[i].setId("0");
            }
        }
        else {
            for (int i = 0; i <= 14; i++) {
                imgX[i] = imageArray[i].getLayoutX();
                String getClub = signin.dataplayerDetails[i].getClub();
                String getString = signin.dataplayerDetails[i].getString();
                double getCost = signin.dataplayerDetails[i].getCost();
                int id = signin.dataplayerDetails[i].getId();
                setPlayerLabel(i, getClub, getString, getCost, id);
            }
        }
        playerBtnPane.toFront();
        playerBtnPane.setOpacity(1);
    }

    @FXML
    private void setReset() {
        for(int i=0; i<=14; i++){
            box[i].setOpacity(0);
            if(signin.playerDetails[i].getId()!= signin.dataplayerDetails[i].getId()){
                RemainingMoney+=(signin.playerDetails[i].getCost()/10- signin.dataplayerDetails[i].getCost()/10);
                freeTransfers = getFreeTransfersfromDatabase;
            }
            signin.playerDetails[i].setString(signin.dataplayerDetails[i].getString());
            signin.playerDetails[i].setCost(signin.dataplayerDetails[i].getCost());
            signin.playerDetails[i].setClub(signin.dataplayerDetails[i].getClub());
            signin.playerDetails[i].setPoints(signin.dataplayerDetails[i].getPoints());
            signin.playerDetails[i].setId(signin.dataplayerDetails[i].getId());
            setPlayerLabel(i, signin.dataplayerDetails[i].getClub(), signin.dataplayerDetails[i].getString(),
                    signin.dataplayerDetails[i].getCost(), signin.dataplayerDetails[i].getId());
        }
        messageBox.setOpacity(0);
        btnmakeTransfer.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
        btnReset.setStyle("-fx-border-color: #6e6e6e; -fx-text-fill: #6e6e6e");
        setInfo();
    }

    public static boolean isPlayerChanged(){
        for(int i=0; i<=14; i++){
            if(signin.dataplayerDetails[i].getId()!= signin.playerDetails[i].getId()){
                return true;
            }
        }
        return false;
    }

    private boolean teamPlayerCheck(){
        for(int i=0; i<=14; i++){
            if(signin.playerDetails[i].getId()!=-1) {
                String c = signin.playerDetails[i].getClub();
                if (map.containsKey(c)) {
                    map.replace(c, 0);
                }
                else {
                    map.put(c, 0);
                }

            }
        }
        String teams = "";
        boolean b = false;
        for(int i=0; i<=14; i++) {
            if(signin.playerDetails[i].getId()!=-1) {
                String c = signin.playerDetails[i].getClub();
                if (map.containsKey(c)) {
                    int n = map.get(c)+1;
                    map.replace(c, n);
                    if(n>3){
                        teams+=c+",";
                        b = true;
                    }
                } else {
                    map.put(c, 1);
                }
            }
        }

        message.setText("More than 3 players Selected From "+teams);
        return b;
    }

    @FXML private void playerRemove(){
        String idx = sample.Controllers.playerWindow.id;
        for(int i=0; i<=14; i++){
            if(signin.playerDetails[i].getId()==Integer.parseInt(idx)){
                RemainingMoney += signin.playerDetails[i].getCost()/10;
                freeTransfers--;
                signin.playerDetails[i].setString(" ");
                signin.playerDetails[i].setClub(" ");
                signin.playerDetails[i].setCost(0);
                signin.playerDetails[i].setId(-1*i);
                Image image = new Image("/sample/photos/Jersey/NON.png");
                imageArray[i].setImage(image);
                btnp[i].setText("");
                btnv[i].setText("");
            }
        }
        setInfo();
    }

    private void clearPlayerButton(){
        Image img = new Image("/sample/photos/Jersey/NON.png");
        for(int i=0; i<=14; i++){
            imageArray[i].setImage(img);
            btnp[i].setId("");
            btnv[i].setText("");
            btnp[i].setText("");
        }
    }

    private void closeMenuPane(){
        confirmPane.toBack();
        confirmEPane.toBack();
        confirmEPane.setOpacity(0);
        confirmPane.setOpacity(0);
        playerOption1.toBack();
        playerOption1.setOpacity(0);
        playerOption.toBack();
        playerOption.setOpacity(0);
        clickPane.toBack();
    }

    @FXML private void OnclickPane(MouseEvent event){
        if(event.getSource().equals(clickPane)){
            closeMenuPane();
        }
    }

    @FXML
    private void setWildcard() {
        int chips = ServerData.getChips(signin.Gameweek, teamid);
        if(chips==0){
            if(!ServerData.isPlayedChips(1, teamid, Gameweek)){
                ServerData.setWildCard(Gameweek, teamid);
                WCFlag = true;
                btnWildcard.setText("Playing");
                setInfo();
                myteam.btnChips.fire();
            }
        }
    }

    private void setChipsCondition() {
        int chips = ServerData.getChips(signin.Gameweek, teamid);
        btnWildcard.setText("Play");
        if(chips==2||chips==3||chips==4) btnWildcard.setText("Unavailable");
        if(chips==0){
            if(ServerData.isPlayedChips(1, teamid, Gameweek)){
                btnWildcard.setText("Unavailable");
            }
        }
        if(chips==1){
            btnWildcard.setText("playing");
        }

    }

    private void setInfo() {
        if(!WCFlag) {
            if (freeTransfers < 0) freeTR.setText("0");
            else freeTR.setText(String.valueOf(freeTransfers));
            if(freeTransfers>=0) {
                TRcost.setText("0");
                TRcost.setStyle("-fx-fill: white");
            }
            else {
                TRcost.setText(String.valueOf(4*freeTransfers*(-1)));
                TRcost.setStyle("-fx-fill: #ff3308");
            }
        }
        else {
            freeTR.setText("Unlimited");
            freeTR.setStyle("-fx-fill: #00b200");
            TRcost.setText("0");
        }

        TRmoney.setText(String.format("%.1f", RemainingMoney));


        if(RemainingMoney>-0.005 && RemainingMoney<=0) RemainingMoney = (float) 0.001;

        if(RemainingMoney >= 0){
            TRmoney.setStyle("-fx-fill: white");
        }
        else TRmoney.setStyle("-fx-fill: #ff3308");

    }

    private void getInfo() {
        String str = ServerData.getTransfersInfo(Gameweek, teamid);
        String[] strs = str.split("#");

        RemainingMoney = Float.parseFloat(strs[0]);
        freeTransfers = Integer.parseInt(strs[1]);

        if(ServerData.getChips(signin.Gameweek, teamid)==1){
            WCFlag = true;
        }
    }

    private void setTransfersINandOUT() {
        for(int i=0; i<=14; i++){
            if(signin.dataplayerDetails[i].getId()!= signin.playerDetails[i].getId()){
                ServerData.setTransfersINandOUT(playerDetails[i].getId(), dataplayerDetails[i].getId(), Gameweek);
            }
        }

    }

    @FXML
    private void setAutoPick() {
        playerDetails = ServerData.AutoPick(playerDetails, transfers.RemainingMoney);
        for(int i=0; i<=14; i++){
            setPlayerLabel(i, playerDetails[i].getClub(), playerDetails[i].getString(), playerDetails[i].getCost(), playerDetails[i].getId());
        }
        teamPlayerCheck();

        int trx = 0;

        for(int i=0; i<=14; i++){
            playerDetails[i].setIsOnField(dataplayerDetails[i].getIsOnField());
            if(signin.playerDetails[i].getId()!= signin.dataplayerDetails[i].getId()) {
                trx++;
            }
        }
        freeTransfers = getFreeTransfersfromDatabase;
        freeTransfers -= trx;

        setInfo();
    }


}
