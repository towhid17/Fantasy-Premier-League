package sample.Controllers;

import javafx.scene.control.*;
import sample.Clients.ClientMain;
import sample.Clients.ServerData;
import sample.Objects.PlayerDetails;
import sample.Objects.playerDetailsStat;
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
import javafx.geometry.Pos;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import sample.Objects.playerDetailsStat2;

import java.util.HashMap;
import java.util.*;

import static sample.Controllers.signin.*;


public class squadSelection {
    public static Button btnAutoTransfers = new Button();
    @FXML public TableView<playerDetailsStat2> playerTable;
    Button[] btnp = new Button[15];
    //////////////////////////////////////////////////////////////////////////
    ImageView[] imageArray = new ImageView[15];
    ////////////////////////////////playerChooser////////////////////////////////////////
    private playerDetailsStat2 pDetails;
    @FXML private TableColumn<playerDetailsStat2, String> Player;
    @FXML private TableColumn<playerDetailsStat2, String>pos;
    @FXML private TableColumn<playerDetailsStat2, Integer>cost;
    @FXML private TableColumn<playerDetailsStat2, Integer>sel;
    @FXML private TableColumn<playerDetailsStat2, Integer>Points;
    @FXML private TableColumn<playerDetailsStat2, String>club;
    @FXML private TableColumn<playerDetailsStat2, Button>no;
    @FXML private TableColumn<playerDetailsStat2, HBox>hbox;
    @FXML
    Pane playerOption, playerOption1;
    public static Button btnAnim = new Button();
    @FXML
    Rectangle recty;
    @FXML Pane transferPane;
    @FXML Button btnInformation, btnReplace, btnRemove;
    @FXML StackPane playerWindow;
    @FXML Circle btnClose1;
    @FXML Text message, message1;
    @FXML VBox messageBox;
    HashMap<String, Integer> map = new HashMap<>();
    @FXML Button btnSetTeamname, btnReset, btnAutoPick, btnSetTeamnameFinal;
    @FXML Pane setNamePane;
    @FXML TextField Tteamname;
    @FXML Text TRmoney;
    @FXML HBox RmoneyBox;
    public static Button btnClearPlayerButton = new Button();
    @FXML Text plnforOption;
    @FXML Pane playerBtnPane, playerBtnPane1;

    VBox[] hoverButton = new VBox[15];
    Button[] btnInfo = new Button[15];
    Button[] btnSub = new Button[15];
    Pane[] box = new Pane[15];
    Button[] btnv = new Button[15];
    private static final double BLUR_AMOUNT = 30;
    private static final Effect frostEffect = new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 5);
    private static final ImageView background = new ImageView();
    private static final ImageView background1 = new ImageView();
    private final ObservableList<playerDetailsStat2> dataList = FXCollections.observableArrayList();

    @FXML Pane trWhitePane;
    @FXML TextField searchBox;
    @FXML Text trText;
    public static Button btnsetinitLabel = new Button();
    private static int selectedPlayerId = 0;
    private static String user_team_name = "";


    //////////////////////////////////////////////////METHODS////////////////////////////////////////////////////////////////

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


        for(int i=0; i<=14; i++){
            hoverButton[i] = new VBox();
            hoverButton[i].getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            hoverButton[i].getStyleClass().add("button17");
            btnInfo[i].setText("Info");
            btnInfo[i].getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnInfo[i].getStyleClass().add("button18");
            btnSub[i].setText("+");
            btnSub[i].getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnSub[i].getStyleClass().add("button18");
            hoverButton[i].setSpacing(2);
            hoverButton[i].getChildren().setAll(btnInfo[i], btnSub[i]);
            hoverButton[i].setAlignment(Pos.CENTER);
            playerBtnPane.getChildren().add(hoverButton[i]);
            String idd = btnInfo[i].getId();

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
            playerBtnPane1.getChildren().addAll(imageArray[i], btnp[i], box[i], btnv[i]);
        }
        playerOption.toBack();
        playerOption.setOpacity(0);
        playerOption1.toBack();
        playerOption1.setOpacity(0);
        playerWindow.toBack();
        playerWindow.setOpacity(0);

        TRmoney.setText("100");
        transfers.RemainingMoney = 100;
        playerArrayInitialize();

        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                clearPlayerButton();
            }
        };
        btnClearPlayerButton.setOnAction(event2);
        btnClearPlayerButton.setOpacity(0);
        btnClearPlayerButton.toBack();

        messageBox.setOpacity(0);

        background1.setEffect(frostEffect);
        background.setEffect(frostEffect);
        playerOption1.getChildren().setAll(background1);

        trWhitePane.setOpacity(0);
        trWhitePane.toBack();
        playerBtnPane.toFront();
        EventHandler<ActionEvent> event5 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                setinitLabel();
            }
        };
        btnsetinitLabel.setOnAction(event5);
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
        if(event.getSource().equals(btnReplace)){
            playerOption.setOpacity(0);
            playerOption.toBack();
            playerOption1.setOpacity(0);
            playerOption1.toBack();
            background.setImage(Menu.copyBackground(trWhitePane,220,50));
            trWhitePane.getChildren().setAll(background);
            replaceAnim();
        }
        if (event.getSource().equals(btnInformation)){
            playerOption.setOpacity(0);
            playerOption.toBack();
            playerOption1.setOpacity(0);
            playerOption1.toBack();
            ClientMain.addPane(10);
            sample.Controllers.playerWindow.btnAutoHistory.fire();
            selectedPlayerId = 0;


        }
        if (event.getSource().equals(btnRemove)){
            playerOption.setOpacity(0);
            playerOption.toBack();
            playerOption1.setOpacity(0);
            playerOption1.toBack();
            selectedPlayerId = 0;
        }
        if(event.getSource().equals(btnSetTeamname)){
            setNamePane.toFront();
            setNamePane.setOpacity(1);
            selectedPlayerId = 0;
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

    @FXML
    public void setinitLabel() {
        int f1y=75, fallx=690, f2y=180, f3y=295, f4y=425, dx=23, dy=58;
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
            box[i].setLayoutX((fallx / (3 + 1.0)) * (j));
            box[i].setLayoutY(f4y-dy);
        }
        int iii = 0;
        if(iii == 0){
            Image image = new Image("/sample/photos/Jersey/NON.png");
            for(int i=0; i<=14; i++){
                imageArray[i].setImage(image);
                btnp[i].setText("");
                btnv[i].setText("");
                btnp[i].setId(String.valueOf(-1*i));
            }
        }
        playerBtnPane.toFront();
        playerBtnPane.setOpacity(1);
    }

    public static void playerArrayInitialize(){
        for(int i=0; i<=14; i++){
            signin.playerDetails[i] = new PlayerDetails();
            signin.dataplayerDetails[i] = new PlayerDetails();
            signin.playerDetails[i].setId((-1*i));
            signin.dataplayerDetails[i].setId((-1*i));
            playerDetails[i].setClub(" ");
            playerDetails[i].setCost(0);
            playerDetails[i].setString(" ");
        }
    }

    private void setPlayerLabel(int i, String getClub, String getString, double getCost, int id){
        float fcost = (float)getCost/10;

        btnp[i].setText(getString);
        btnv[i].setText(String.valueOf(fcost));
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
        if(playerTable.getSelectionModel().getSelectedItem()!=null) {
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
                        signin.playerDetails[i].setString(pDetails.getPlayer());
                        signin.playerDetails[i].setClub(pDetails.getClub());
                        signin.playerDetails[i].setCost(pDetails.getCost() * 10);
                        signin.playerDetails[i].setId(Integer.parseInt(pDetails.getInfo().getId()));
                        signin.playerDetails[i].setPoints(pDetails.getPoints());
                        signin.playerDetails[i].setPos(pDetails.getPos());
                        signin.playerDetails[i].setSel(pDetails.getSel());
                        setPlayerLabel(i, pDetails.getClub(), pDetails.getPlayer(), pDetails.getCost() * 10, signin.playerDetails[i].getId());
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
                isRemainingMoney();
                if (teamPlayerCheck()) {
                    messageBox.setOpacity(1);
                } else {
                    messageBox.setOpacity(0);
                    message.setText("");
                }
            }
        }
    }

    ///////////popupList///////////
    public void PlayerStats(int Pos) {
        FilteredList<playerDetailsStat2> filteredData = new FilteredList<>(dataList, b -> true);
        SortedList<playerDetailsStat2> sortedData = new SortedList<>(filteredData);
        dataList.clear();

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

    /////////player change option/////////
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
                plnforOption.setText(btnp[i].getText());
                playerOptionSet(i);
                selectedPlayerId = signin.playerDetails[i].getId();
                System.out.println("Sel: "+ selectedPlayerId);
                System.out.println("i: "+i);
            }
            if(event.getSource().equals(btnInfo[i])){
                sample.Controllers.playerWindow.id = btnp[i].getId();
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
        playerOption1.getChildren().setAll(background);
        playerOption1.toFront();
        playerOption.toFront();

        double x = playerBtnPane.getLayoutX()+hoverButton[i].getLayoutX();
        double y = playerBtnPane.getLayoutY()+hoverButton[i].getLayoutY();
        playerOption1.setLayoutX(btnSub[i].getLayoutX()+x-dx);
        playerOption1.setLayoutY(btnSub[i].getLayoutY()+y-dy);
        playerOption.setLayoutX(btnSub[i].getLayoutX()+x-dx);
        playerOption.setLayoutY(btnSub[i].getLayoutY()+y-dy);
        plnforOption.setText(btnp[i].getText());
        Timeline t = new Timeline();

        double px = 389;
        double py = 208;
        DoubleProperty d1 = playerOption1.prefHeightProperty();
        DoubleProperty d2 = playerOption1.prefWidthProperty();
        DoubleProperty d3 = playerOption.opacityProperty();
        playerOption1.setOpacity(1);
        playerOption.setOpacity(1);
        playerOption1.setStyle("-fx-border-color: white");

        t.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(d1, 1), new KeyValue(d2, 1),
                        new KeyValue(d3, 0)),
                new KeyFrame(new Duration(100), new KeyValue(d1, py), new KeyValue(d2, px),
                        new KeyValue(d3, 0)),
                new KeyFrame(new Duration(150), new KeyValue(d3, 1))
        );
        t.play();
    }


    private boolean teamPlayerCheck(){
        for(int i=0; i<=14; i++){
            if(signin.playerDetails[i].getId()>0) {
                String c = signin.playerDetails[i].getClub();
                if(!c.equals("")) {
                    if (map.containsKey(c)) {
                        map.replace(c, 0);
                    } else {
                        map.put(c, 0);
                    }
                }

            }
        }
        String teams = "";
        boolean b = false;
        for(int i=0; i<=14; i++) {
            if(signin.playerDetails[i].getId()>0) {
                String c = signin.playerDetails[i].getClub();
                if(!c.equals("")) {
                    if (map.containsKey(c)) {
                        int n = map.get(c) + 1;
                        map.replace(c, n);
                        if (n > 3) {
                            teams += c + ",";
                            b = true;
                        }
                    } else {
                        map.put(c, 1);
                    }
                }
            }
        }

        message.setText("More than 3 players Selected From "+teams);
        return b;
    }

    private int checkTransfers(){
        int tr=0;
        int noTeam = 0;
        for(int i=0; i<=14; i++){
            if(signin.playerDetails[i].getId()!=-1){
                tr++;
            }
        }
        return tr;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////
    @FXML
    private void EnterSquad() {
        int tr = checkTransfers();
        boolean b = teamPlayerCheck();

        if(tr<15) {
            message1.setText("" + (15 - tr) + " more player required to be selected");
            messageBox.setOpacity(1);
        }

        if(!isRemainingMoney()){
            message1.setText("Money Short");
            messageBox.setOpacity(1);
            messageBox.toFront();
            return;
        }

        boolean bn = checkTeamname();
        if(!bn){
            setNamePane.setOpacity(1);
            setNamePane.toFront();
        }

        else if(tr==15 && !b && bn) {
            String str = "";
            for(int i=0; i<=14; i++) str += playerDetails[i].getId()+"#";
            signin.teamid = ServerData.EnterSquad(USER, Gameweek, str, transfers.RemainingMoney, user_team_name);
            Menu.btnSaveAnim.fire();
            playerArrayDestructor();
            Menu.btnSquadSelectionOff.fire();
            signin.btnAutoSignin.fire();
            System.out.println("signinAuto");
            btnRefresh();

        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////

    private void btnRefresh(){
        for(int i=0; i<=14; i++){
            btnp[i].setText("");
            btnp[i].setText("");
            btnv[i].setText("");
            btnp[i].setId("-1");
        }
    }

    private void playerArrayDestructor(){
        for(int i=0; i<=14; i++){
            signin.dataplayerDetails[i] = null;
            signin.playerDetails[i] = null;
        }
    }

    @FXML
    private void setTeamName()  {
        user_team_name = Tteamname.getText();
        setNamePane.setOpacity(0);
        setNamePane.toBack();
    }

    private boolean checkTeamname(){
        if(user_team_name=="") return false;
        else return true;
    }

    @FXML
    private void setReset(){
        for(int i=0; i<=14; i++){
            signin.playerDetails[i].setString(" ");
            signin.playerDetails[i].setCost(0);
            signin.playerDetails[i].setClub(" ");
            signin.playerDetails[i].setPoints(-1000);
            signin.playerDetails[i].setId(-1*i);
            btnp[i].setText(" ");
            btnv[i].setText(" ");
            Image image = new Image("/sample/photos/Jersey/NON.png");
            imageArray[i].setImage(image);
        }
        isRemainingMoney();
    }

    @FXML
    private void setAutoPick() {
        playerDetails = ServerData.AutoPick(playerDetails, transfers.RemainingMoney);
        for(int i=0; i<=14; i++){
            setPlayerLabel(i, playerDetails[i].getClub(), playerDetails[i].getString(), playerDetails[i].getCost(), playerDetails[i].getId());
        }
        isRemainingMoney();
        teamPlayerCheck();
    }

    private boolean playerCostCheck(){
        float totalCost = 0;

        for(int i=0; i<=14; i++){
            float c = (float) signin.playerDetails[i].getCost();
            totalCost += c/10;
        }
        if(totalCost<=100){
            System.out.println("totalCost: " + totalCost);
            return false;
        }
        else return true;
    }

    private boolean isRemainingMoney(){
        float totalCost = 0;
        for(int i=0; i<=14; i++){
            float m = (float) signin.playerDetails[i].getCost();
            totalCost += m/10;
        }
        transfers.RemainingMoney = 100-totalCost;

        TRmoney.setText(String.format("%.1f", transfers.RemainingMoney));

        if(totalCost<=100){
            RmoneyBox.setStyle("-fx-border-color: white; -fx-border-radius: 20");
            TRmoney.setStyle("-fx-text-fill: white");
            return true;
        }
        else{
            RmoneyBox.setStyle("-fx-border-color: Red; -fx-border-radius: 20");
            return false;
        }
    }

    @FXML private void playerRemove(){
        String idx = sample.Controllers.playerWindow.id;
        for(int i=0; i<=14; i++){
            if(signin.playerDetails[i].getId()==Integer.parseInt(idx)){
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
        isRemainingMoney();
    }

    private void clearPlayerButton(){
        Image img = new Image("/sample/photos/Jersey/NON.png");
        for(int i=0; i<=14; i++){
            imageArray[i].setImage(img);
            btnp[i].setId("");
            btnp[i].setText("");
            btnv[i].setText("");
        }
        TRmoney.setText("100");
    }
}
