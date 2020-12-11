package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Clients.ClientMain;
import sample.Clients.ServerData;
import sample.Objects.PlayerDetails;
import sample.Objects.playerDetailsStat;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;



public class status implements Initializable {
    @FXML Text highestPoints, averagePoints, mostCaptain, mostransferredIn, wildcardplayed, transfersMade, gwbox, gwstatus;
    public static Button btnAnim = new Button();
    @FXML Pane mainPane;
    @FXML Button teamClose;
    @FXML BorderPane teamBorderPane;
    @FXML ScrollPane scrlPane;
    private Stage stage;
    public static Button btnStatus = new Button();

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DreamTeamLoad();
        TopGWPlayer();
        topTransfersIn();
        topTransfersOut();
        bestLeagues();
        mostValuedTeam();
        getStatusInfo();

        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                teamCloseAction();
                StyleAnimation();
            }
        };
        btnStatus.setOnAction(event2);

        gwbox.setText("Gameweek "+(signin.Gameweek-1));
        gwstatus.setText("Gameweek "+(signin.Gameweek-1)+" Status");
    }

    ///////////////Dream Team table /////////////////////////

    @FXML private TableView<PlayerDetails> tableDT;
    @FXML private TableColumn<PlayerDetails, String> dtplayer;
    @FXML private TableColumn<PlayerDetails, String>dtpos;
    @FXML private TableColumn<PlayerDetails, Integer>dtpoints;
    @FXML private TableColumn<PlayerDetails, String>dtclub;


    private void DreamTeamLoad() {
        if(signin.Gameweek==1) return;
        List<String> list = ServerData.DreamTeamLoad();
        dtplayer.setCellValueFactory(new PropertyValueFactory<>("string"));
        dtclub.setCellValueFactory(new PropertyValueFactory<>("club"));
        dtpos.setCellValueFactory(new PropertyValueFactory<>("pos"));
        dtpoints.setCellValueFactory(new PropertyValueFactory<>("Points"));

        for(Object obj:list){
            String str1 = String.valueOf(obj);
            String[] strs = str1.split("#");
            String player = strs[0];
            String club = strs[1];
            int points = Integer.parseInt(strs[2]);
            int p = Integer.parseInt(strs[3]);
            String pp = "";
            if (p == 1)
                pp = "GK";
            else if (p == 2)
                pp = "DEF";
            else if (p == 3)
                pp = "MF";
            else if (p == 4)
                pp = "ST";

            PlayerDetails playerDetails = new PlayerDetails(0, player, club, pp, points, 0, 0);
            tableDT.getItems().addAll(playerDetails);
        }

    }


    //////////////Top player by GW ///////////////////////////////////////
    @FXML private TableView<PlayerDetails> tabletopgwp;
    @FXML private TableColumn<PlayerDetails, String> tpplayer;
    @FXML private TableColumn<PlayerDetails, Integer>tpgw;
    @FXML private TableColumn<PlayerDetails, Integer>tppoints;

    private void TopGWPlayer() {
        if(signin.Gameweek==1) return;
        List<String> list = ServerData.TopGWPlayer();
        tpplayer.setCellValueFactory(new PropertyValueFactory<>("string"));
        tpgw.setCellValueFactory(new PropertyValueFactory<>("pos"));
        tppoints.setCellValueFactory(new PropertyValueFactory<>("Points"));

        int i=1;
        for(Object obj:list){
            String str1 = String.valueOf(obj);
            String[] strs = str1.split("#");
            String player = strs[0];
            int points = Integer.parseInt(strs[1]);

            PlayerDetails playerDetails = new PlayerDetails(0, player, "", String.valueOf(i++), points, 0, 0);
            tabletopgwp.getItems().addAll(playerDetails);
        }

    }


    ////////////////////////////////////
    /////////////////////////////////////////////
    ////////////////////////////////////////////





    ///////////Top Transfers In/////////////////////
    @FXML private TableView<playerDetailsStat> transfersinTable;
    @FXML private TableColumn<playerDetailsStat, Button> info1;
    @FXML private TableColumn<playerDetailsStat, String>pos1;
    @FXML private TableColumn<playerDetailsStat, String>player1;
    @FXML private TableColumn<playerDetailsStat, String>club1;
    @FXML private TableColumn<playerDetailsStat, Integer>number1;
    @FXML private TableColumn<playerDetailsStat, HBox>img1;


    private void topTransfersIn() {
        //if(signin.Gameweek==1) return;
        List<String> list = ServerData.topTransfersIn();

        info1.setCellValueFactory(new PropertyValueFactory<>("info"));
        pos1.setCellValueFactory(new PropertyValueFactory<>("pos"));
        club1.setCellValueFactory(new PropertyValueFactory<>("club"));
        player1.setCellValueFactory(new PropertyValueFactory<>("player"));
        number1.setCellValueFactory(new PropertyValueFactory<>("points"));
        img1.setCellValueFactory(new PropertyValueFactory<>("playerbox"));


        for(Object obj:list){
            String str1 = String.valueOf(obj);
            String[] strs = str1.split("#");
            int pid = Integer.parseInt(strs[0]);
            int p = Integer.parseInt(strs[1]);
            String ln = strs[2];
            String tn = strs[3];
            int n = Integer.parseInt(strs[4]);
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
            info.setId(String.valueOf(pid));
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    ClientMain.addPane(10);
                    playerWindow.id = String.valueOf(pid);
                    playerWindow.btnAutoHistory.fire();
                }
            };
            info.setOnAction(event);
            info.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            info.getStyleClass().add("button16");

            HBox hBox = new HBox();
            ImageView im = new ImageView();
            Image image = new Image("/sample/icons/arrow.png");
            im.setImage(image);
            im.setFitHeight(15);
            im.setFitWidth(15);
            hBox.setPrefWidth(16);
            hBox.setPrefHeight(16);
            hBox.getChildren().setAll(im);
            hBox.setAlignment(Pos.CENTER);

            playerDetailsStat pds = new playerDetailsStat(info, hBox, ln, 0,0,n,tn,pp);
            transfersinTable.getItems().addAll(pds);
        }

    }


    ///////////Top Transfers Out/////////////////////
    @FXML private TableView<playerDetailsStat> transfersoutTable;
    @FXML private TableColumn<playerDetailsStat, Button> info11;
    @FXML private TableColumn<playerDetailsStat, String>pos11;
    @FXML private TableColumn<playerDetailsStat, String>player11;
    @FXML private TableColumn<playerDetailsStat, String>club11;
    @FXML private TableColumn<playerDetailsStat, Integer>number11;
    @FXML private TableColumn<playerDetailsStat, HBox>img11;


    private void topTransfersOut(){
        //if(signin.Gameweek==1) return;
        List<String> list = ServerData.topTransfersOut();

        info11.setCellValueFactory(new PropertyValueFactory<>("info"));
        pos11.setCellValueFactory(new PropertyValueFactory<>("pos"));
        club11.setCellValueFactory(new PropertyValueFactory<>("club"));
        player11.setCellValueFactory(new PropertyValueFactory<>("player"));
        number11.setCellValueFactory(new PropertyValueFactory<>("points"));
        img11.setCellValueFactory(new PropertyValueFactory<>("playerbox"));


        for(Object obj:list){
            String str1 = String.valueOf(obj);
            String[] strs = str1.split("#");
            int pid = Integer.parseInt(strs[0]);
            int p = Integer.parseInt(strs[1]);
            String ln = strs[2];
            String tn = strs[3];
            int n = Integer.parseInt(strs[4]);
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
            info.setId(String.valueOf(pid));
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    ClientMain.addPane(10);
                    playerWindow.id = String.valueOf(pid);
                    playerWindow.btnAutoHistory.fire();
                }
            };
            info.setOnAction(event);
            info.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            info.getStyleClass().add("button16");

            HBox hBox = new HBox();
            ImageView im = new ImageView();
            Image image = new Image("/sample/icons/arrow1.png");
            im.setImage(image);
            im.setFitHeight(15);
            im.setFitWidth(15);
            hBox.setPrefWidth(16);
            hBox.setPrefHeight(16);
            hBox.getChildren().setAll(im);
            hBox.setAlignment(Pos.CENTER);

            playerDetailsStat pds = new playerDetailsStat(info, hBox, ln, 0,0,n,tn,pp);
            transfersoutTable.getItems().addAll(pds);
        }

    }


    ///////////best Leagues/////////////////////
    @FXML private TableView<playerDetailsStat> bestLeaguesTable;
    @FXML private TableColumn<playerDetailsStat, Button> name2;
    @FXML private TableColumn<playerDetailsStat, String>pos2;
    @FXML private TableColumn<playerDetailsStat, Integer>num2;


    private void bestLeagues() {
        if(signin.Gameweek==1) return;
        List<String> list = ServerData.bestLeagues();
        name2.setCellValueFactory(new PropertyValueFactory<>("info"));
        pos2.setCellValueFactory(new PropertyValueFactory<>("pos"));
        num2.setCellValueFactory(new PropertyValueFactory<>("points"));


        int idd=1;
        for(Object obj:list){
            String str1 = String.valueOf(obj);
            String[] strs = str1.split("#");
            String c = strs[0];
            String name = strs[1];
            float pts = Float.parseFloat(strs[2]);
            Button btnLeague = new Button();
            btnLeague.setText(name);
            btnLeague.setId(c);

            EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
//                    try {
//                        leaguWindowPopup(c);
//                    } catch (ClassNotFoundException e1) {
//                        e1.printStackTrace();
//                    } catch (SQLException e1) {
//                        e1.printStackTrace();
//                    }
                }
            };

            btnLeague.setOnAction(event2);

            btnLeague.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnLeague.getStyleClass().add("button13");

            playerDetailsStat pds = new playerDetailsStat(btnLeague, new HBox(), "", 0,0,pts,"",String.valueOf(idd++));
            bestLeaguesTable.getItems().addAll(pds);
        }

    }

    ///////////best Leagues/////////////////////
    @FXML private TableView<playerDetailsStat> mostValuedTeamTable1;
    @FXML private TableColumn<playerDetailsStat, Button> name21;
    @FXML private TableColumn<playerDetailsStat, String>pos21;
    @FXML private TableColumn<playerDetailsStat, Integer>num21;


    private void mostValuedTeam() {
        if(signin.Gameweek==1) return;
        List<String> list = ServerData.mostValuedTeam();
        name21.setCellValueFactory(new PropertyValueFactory<>("info"));
        pos21.setCellValueFactory(new PropertyValueFactory<>("pos"));
        num21.setCellValueFactory(new PropertyValueFactory<>("points"));


        int idd=1;
        for(Object obj:list){
            String str1 = String.valueOf(obj);
            String[] strs = str1.split("#");
            int c = Integer.parseInt(strs[0]);
            String name = strs[1];
            String spt = strs[2].substring(0, 3);
            int pts = Integer.parseInt(spt)/10;
            Button btnTeam = new Button();
            btnTeam.setText(name);
            btnTeam.setId(String.valueOf(c));

            EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    teamBorderPane.toFront();
                    teamBorderPane.setOpacity(1);
                    scrlPane.setOpacity(0);
                    teamClose.setOpacity(1);
                    teamClose.toFront();
                    points.pointsTeamId = Integer.parseInt(btnTeam.getId());
                    points.playerPointsCalc(signin.Gameweek - 1, points.pointsTeamId);
                    points.btnAutoPoints.fire();
                    Pane pane = ClientMain.getWindow(3);
                    teamBorderPane.setCenter(pane);
                }
            };

            btnTeam.setOnAction(event2);

            btnTeam.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnTeam.getStyleClass().add("button13");

            playerDetailsStat pds = new playerDetailsStat(btnTeam, new HBox(), "", 0,0,pts,"",String.valueOf(idd++));
            mostValuedTeamTable1.getItems().addAll(pds);
        }

    }



    private void getStatusInfo() {
        String str = ServerData.getStatusInfo();
        String[] strs = str.split("#");
        highestPoints.setText(strs[0]);
        averagePoints.setText(strs[1]);
        mostCaptain.setText(strs[2]);
        mostransferredIn.setText(strs[3]);
        transfersMade.setText(strs[4]);
        int wc = Integer.parseInt(strs[5]) - 1;
        wildcardplayed.setText(String.valueOf(wc));
    }


    public void StyleAnimation(){
        new animatefx.animation.BounceIn(mainPane).play();
    }


    @FXML
    private void teamCloseAction(){
        scrlPane.toFront();
        teamBorderPane.setOpacity(0);
        scrlPane.setOpacity(1);
        teamClose.setOpacity(0);
    }



}

