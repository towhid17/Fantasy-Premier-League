package sample.Controllers;

import javafx.scene.layout.BorderPane;
import sample.Clients.ClientMain;
import sample.Clients.ServerData;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import sample.Objects.LeagueTable;
import sample.Objects.h2hStandingData;
import sample.Objects.leaguesTable;

import java.util.HashMap;
import java.util.List;

import static sample.Controllers.signin.*;

public class leagues {
    private VBox[] vBoxes = new VBox[10];
    ImageView imageView;
    public static Button btnRefresh = new Button();
    public static Button btnleaguesLoad = new Button();
    @FXML Text LSname, LStype, LScode;
    @FXML VBox Wmyleagues, Wcreate, Wjoincreate, Wcreateleagues, Wjoinleagues, Wjoinclassicfinal, Wcreateclassicfinal, Wjoinclassicfinal1,
            WOption, teamWindow, wmlVB,WmlVB1, leagueWindow, h2hfinal, H2HBox;
    @FXML HBox toptitle;
    @FXML Button btncreatejoin, btnjoinaleague, btncreatealeague, btnjoinprivateleague, btnjoinpublicleague, creath2hfinal,
            btncreateclassic, btncreatehth, btnjoinclassicfinal, btncreateclassicfinal, btnBack, btnDeleteLeague, btnEp1,
            btnjoinclassicfinal1, btnjoinprivateleague1, btnStanding, btnFixture;
    @FXML TextField TFjoinclassic, TFcreateclassic, TFcreateclassic1, TFjoinclassic1;
    @FXML Rectangle recttest;
    @FXML BorderPane teamBorderPane;
    @FXML Pane standingPane, fixturePane;
    @FXML private ComboBox<String> ScoreGWCombo, ScoreGWCombo1;
    //private classic league
    @FXML TableView<sample.Objects.LeagueTable> tablePrivateClassic;
    @FXML private TableColumn<LeagueTable, Button> leagues1;
    @FXML private TableColumn<LeagueTable, Button> option1;
    @FXML private TableColumn<LeagueTable, Integer>lrank1;
    @FXML private TableColumn<LeagueTable, Integer>crank1;
    @FXML private TableColumn<sample.Objects.LeagueTable, ImageView> rankIndicator;

    //h2h table
    @FXML TableView<sample.Objects.LeagueTable> tableh2h;
    @FXML private TableColumn<LeagueTable, Button> league4;
    @FXML private TableColumn<LeagueTable, Button> option4;
    @FXML private TableColumn<LeagueTable, Integer>lrank4;
    @FXML private TableColumn<LeagueTable, Integer>crank4;
    @FXML private TableColumn<sample.Objects.LeagueTable, ImageView> rankIndicator5;

    //public table
    @FXML TableView<sample.Objects.LeagueTable> tablePublic;
    @FXML private TableColumn<LeagueTable, Button> league3;
    @FXML private TableColumn<LeagueTable, Button> option3;
    @FXML private TableColumn<LeagueTable, Integer>lrank3;
    @FXML private TableColumn<LeagueTable, Integer>crank3;
    @FXML private TableColumn<sample.Objects.LeagueTable, ImageView> rankIndicator4;

    //global classic league
    @FXML TableView<sample.Objects.LeagueTable> globalLeaguetable;
    @FXML private TableColumn<LeagueTable, Button> leagues2;
    @FXML private TableColumn<LeagueTable, Button> option2;
    @FXML private TableColumn<LeagueTable, Integer>lrank2;
    @FXML private TableColumn<LeagueTable, Integer>crank2;
    @FXML private TableColumn<sample.Objects.LeagueTable, ImageView> rankIndicator3;

    private leaguesTable table = new leaguesTable("", "", 0, 0, "", imageView);
    @FXML TableView<leaguesTable> LeagueTable;
    @FXML private TableColumn<leaguesTable, Integer> ltrank;
    @FXML private TableColumn<leaguesTable, String> ltname;
    @FXML private TableColumn<leaguesTable, Integer>ltpoints;
    @FXML private TableColumn<leaguesTable, Integer>id;
    @FXML private TableColumn<leaguesTable, Integer> tpoints;
    @FXML private TableColumn<leaguesTable, ImageView>rankindicator2;

    ///H2H standing Table
    @FXML TableView<h2hStandingData> h2hstandingTable;
    @FXML private TableColumn<h2hStandingData, Integer> rank;
    @FXML private TableColumn<h2hStandingData, String> tname;
    @FXML private TableColumn<h2hStandingData, Integer>point;
    @FXML private TableColumn<h2hStandingData, Integer>wins;
    @FXML private TableColumn<h2hStandingData, Integer> lost;
    @FXML private TableColumn<h2hStandingData, Integer>drew;
    @FXML private TableColumn<h2hStandingData, Integer>idh2h;

    //H2h fixture Table
    @FXML TableView<leaguesTable> h2hfixture;
    @FXML private TableColumn<leaguesTable, String> teama;
    @FXML private TableColumn<leaguesTable, String> teamb;
    @FXML private TableColumn<leaguesTable, Integer>teamap;
    @FXML private TableColumn<leaguesTable, Integer>teambp;

    private static String ScoreGw = "";
    private static int idofteam = 0;
    private static int gwofteam = signin.Gameweek-1;
    private static String h2hCode = "";

    @FXML ComboBox fixtureGWh2h;

    /////////////////////////////METHODS////////////////////////////////////////////

    public void initialize() {
        btnEp1.setId("0");
        Wmyleagues.toFront();
        Wmyleagues.setOpacity(1);
        Wcreate.setOpacity(0);

        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                leaguesTableLoad();
                globalTableLoad();
                publicTableLoad();
                H2HLeagueLoad();
                ScoreGw = String.valueOf(signin.Gameweek);
                int Gws = 38- signin.Gameweek+1;
                String[] ScoreGws = new String[Gws];
                for(int i=0; i<Gws; i++){
                    ScoreGws[i] = String.valueOf(signin.Gameweek+i);
                }
                ScoreGWCombo.getItems().clear();
                ScoreGWCombo1.getItems().clear();
                ScoreGWCombo.setItems(FXCollections.observableArrayList(ScoreGws));
                ScoreGWCombo1.setItems(FXCollections.observableArrayList(ScoreGws));
            }
        };
        btnleaguesLoad.setOnAction(event2);

        vBoxes[0]=Wmyleagues; vBoxes[1]=Wcreate; vBoxes[2]=Wjoincreate; vBoxes[3]= Wcreateleagues;
        vBoxes[4]=Wjoinleagues; vBoxes[5]= Wjoinclassicfinal; vBoxes[6]= Wcreateclassicfinal; vBoxes[7] = h2hfinal;
        vBoxes[8]=Wjoinclassicfinal1; vBoxes[9] = H2HBox;

        EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                Wmyleagues.setOpacity(1);
                Wmyleagues.toFront();
                for(int i=1; i<=9; i++){
                    vBoxes[i].setOpacity(0);
                }
                teamWindow.setOpacity(0);
                leagueWindow.setOpacity(0);
                WOption.setOpacity(0);
            }
        };
        btnRefresh.setOnAction(event3);

        ScoreGWCombo.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        try {
                            ScoreGw = newValue.toString();
                        }catch (NullPointerException e){
                            System.out.println("got null exception");
                        }
                    }
                });

        ScoreGWCombo1.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        try {
                            ScoreGw = newValue.toString();
                        }catch (NullPointerException e){
                            System.out.println("got null exception");
                        }
                    }
                });

        final String[] str = {""};
        fixtureGWh2h.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        str[0] = newValue.toString();
                        System.out.println(str[0]);
                        h2hFixtureUpdate(h2hCode, Integer.parseInt(str[0]));
                    }
                });


    }

    private void DeleteLeague() {
        String code = btnDeleteLeague.getId();
        int admin = ServerData.getLeagueAdmin(code);
        if(admin==signin.teamid){
            ServerData.DeleteLeague(code);
            Wmyleagues.setOpacity(1);
            Wmyleagues.toFront();
            WOption.setOpacity(0);
            leaguesTableLoad();
        }
    }

    private void createClassicLeagues(String LeagueName) {
        ServerData.createClassicLeagues(LeagueName, teamid, ScoreGw);
    }

    private void createH2HLeagues(String name){
        if(ScoreGw.equals(String.valueOf(Gameweek))){
            ScoreGw = String.valueOf(Gameweek+1);
        }
        ServerData.createH2HLeagues(name, teamid, ScoreGw);
    }

    private boolean joinClassicLeagues(String ClassicCode) {
        boolean b = ServerData.joinClassicLeagues(ClassicCode, teamid, Gameweek);
        if(b) return true;
        return false;
    }

    private boolean joinH2HLeagues(String ClassicCode) {
        boolean b = ServerData.joinH2HLeagues(ClassicCode, teamid, Gameweek);
        if(b) return true;
        return false;
    }


    ///private classic league load
    @FXML
    private void leaguesTableLoad() {
        Image im1  = new Image("/sample/photos/indicator/nutral.png");
        Image im2  = new Image("/sample/photos/indicator/up.png");
        Image im3  = new Image("/sample/photos/indicator/down.png");

        tablePrivateClassic.getItems().clear();
        leagues1.setCellValueFactory(new PropertyValueFactory<>("League"));
        option1.setCellValueFactory(new PropertyValueFactory<>("Option"));
        crank1.setCellValueFactory(new PropertyValueFactory<>("Crank"));
        lrank1.setCellValueFactory(new PropertyValueFactory<>("Lrank"));
        rankIndicator.setCellValueFactory(new PropertyValueFactory<>("im"));

        List<String> list = ServerData.leagueTableLoad(teamid);

        for(Object obj: list){
            String ans = String.valueOf(obj);
            String[] strs = ans.split("#");
            String name = strs[0];
            String c = strs[1];
            Button btnLeague = new Button();
            btnLeague.setText(name);
            btnLeague.setId(c);

            EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    leaguWindowPopup(c);
                }
            };

            btnLeague.setOnAction(event2);
            Button btnOption = new Button();
            btnOption.setText("Option");
            btnOption.setId(c);
            EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    OptionWindowControll(name, "Classic", c);
                }
            };

            btnOption.setOnAction(event3);

            btnLeague.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnLeague.getStyleClass().add("button13");

            btnOption.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnOption.getStyleClass().add("button13");


            int crank = ServerData.getGWRank(signin.Gameweek, c);
            int lrank = ServerData.getGWRank(signin.Gameweek-1, c);

            ImageView imageView = new ImageView();
            if(crank>lrank) {
                imageView.setImage(im3);
            }
            else if(lrank>crank){
                imageView.setImage(im2);
            }
            else {
                imageView.setImage(im1);
            }

            LeagueTable leagues = new LeagueTable(btnLeague, btnOption, crank, lrank, imageView);
            tablePrivateClassic.getItems().addAll(leagues);
        }
    }

    //h2h table load
    private void H2HLeagueLoad() {
        Image im1  = new Image("/sample/photos/indicator/nutral.png");
        Image im2  = new Image("/sample/photos/indicator/up.png");
        Image im3  = new Image("/sample/photos/indicator/down.png");

        tableh2h.getItems().clear();
        league4.setCellValueFactory(new PropertyValueFactory<>("League"));
        option4.setCellValueFactory(new PropertyValueFactory<>("Option"));
        crank4.setCellValueFactory(new PropertyValueFactory<>("Crank"));
        lrank4.setCellValueFactory(new PropertyValueFactory<>("Lrank"));
        rankIndicator5.setCellValueFactory(new PropertyValueFactory<>("im"));

        List<String> list = ServerData.H2HLeagueLoad(teamid);

        for(Object obj: list){
            String ans = String.valueOf(obj);
            String[] strs = ans.split("#");
            String name = strs[0];
            String c = strs[1];
            Button btnLeague = new Button();
            btnLeague.setText(name);
            btnLeague.setId(c);

            EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    H2HleaguWindowPopup(c);
                }
            };

            btnLeague.setOnAction(event2);
            Button btnOption = new Button();
            btnOption.setText("Option");
            btnOption.setId(c);
            EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    OptionWindowControll(name, "Classic", c);
                }
            };

            btnOption.setOnAction(event3);

            btnLeague.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnLeague.getStyleClass().add("button13");

            btnOption.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnOption.getStyleClass().add("button13");


            //int crank = ServerData.getGWRank(signin.Gameweek, c);
            //int lrank = ServerData.getGWRank(signin.Gameweek-1, c);

            int crank = 0;
            int lrank = 0;

            ImageView imageView = new ImageView();
            if(crank>lrank) {
                imageView.setImage(im3);
            }
            else if(lrank>crank){
                imageView.setImage(im2);
            }
            else {
                imageView.setImage(im1);
            }

            LeagueTable leagues = new LeagueTable(btnLeague, btnOption, crank, lrank, imageView);
            tableh2h.getItems().addAll(leagues);
        }
    }

    //if particular h2h league clicked:
    private void H2HleaguWindowPopup(String code){
        vBoxes[0].setOpacity(0);
        vBoxes[9].toFront();
        vBoxes[9].setOpacity(1);
        standingPane.toFront();
        standingPane.setOpacity(1);
        fixturePane.setOpacity(0);

        //for standing:
        h2hstandingTable.getItems().clear();
        rank.setCellValueFactory(new PropertyValueFactory<>("rank"));
        tname.setCellValueFactory(new PropertyValueFactory<>("name"));
        point.setCellValueFactory(new PropertyValueFactory<>("point"));
        wins.setCellValueFactory(new PropertyValueFactory<>("win"));
        lost.setCellValueFactory(new PropertyValueFactory<>("lose"));
        drew.setCellValueFactory(new PropertyValueFactory<>("draw"));
        idh2h.setCellValueFactory(new PropertyValueFactory<>("id"));

        List<String> listStanding = ServerData.H2HLeagueTeamLoad(code);

        for (Object obj:listStanding){
            String ans = String.valueOf(obj);
            String[] strs = ans.split("#");
            int r = Integer.parseInt(strs[0]);
            int id = Integer.parseInt(strs[1]);
            String n = strs[2];
            int w = Integer.parseInt(strs[3]);
            int l = Integer.parseInt(strs[4]);
            int d = Integer.parseInt(strs[5]);
            int p = Integer.parseInt(strs[6]);

            h2hStandingData hd = new h2hStandingData(r, n, w, l, d, id, p);
            h2hstandingTable.getItems().addAll(hd);
        }

        //for Fixture:
        int stgw = ServerData.getH2HStartGw(code);
        h2hCode = code;
        String[] viewboxElement = new String[38-stgw+1];
        for(int i=0; i+stgw<=38; i++){
            viewboxElement[i] = String.valueOf(stgw+i);
        }
        fixtureGWh2h.setItems(FXCollections.observableArrayList(viewboxElement));
        h2hFixtureUpdate(code, stgw);

    }

    private void h2hFixtureUpdate(String code, int gw){
        h2hfixture.getItems().clear();
        teama.setCellValueFactory(new PropertyValueFactory<>("Lname"));
        teamb.setCellValueFactory(new PropertyValueFactory<>("Option"));
        teamap.setCellValueFactory(new PropertyValueFactory<>("crank"));
        teambp.setCellValueFactory(new PropertyValueFactory<>("lrank"));

        List<String> list = ServerData.H2HFixtureLoad(code, gw);

        for (Object obj:list) {
            String ans = String.valueOf(obj);
            String[] strs = ans.split("#");
            String nA = strs[0];
            String nB = strs[1];
            int pA = Integer.parseInt(strs[2]);
            int pB = Integer.parseInt(strs[3]);
            ImageView imageView = new ImageView();

            leaguesTable l = new leaguesTable(nA, nB, pA, pB, "", imageView);
            h2hfixture.getItems().addAll(l);
        }
    }

    //public table load::
    @FXML
    private void publicTableLoad() {
        Image im1  = new Image("/sample/photos/indicator/nutral.png");
        Image im2  = new Image("/sample/photos/indicator/up.png");
        Image im3  = new Image("/sample/photos/indicator/down.png");

        tablePublic.getItems().clear();
        league3.setCellValueFactory(new PropertyValueFactory<>("League"));
        option3.setCellValueFactory(new PropertyValueFactory<>("Option"));
        crank3.setCellValueFactory(new PropertyValueFactory<>("Crank"));
        lrank3.setCellValueFactory(new PropertyValueFactory<>("Lrank"));
        rankIndicator4.setCellValueFactory(new PropertyValueFactory<>("im"));

        List<String> list = ServerData.publicTableLoad(teamid);

        for(Object obj: list){
            String ans = String.valueOf(obj);
            String[] strs = ans.split("#");
            String name = strs[0];
            String c = strs[1];
            Button btnLeague = new Button();
            btnLeague.setText(name);
            btnLeague.setId(c);

            EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    leaguWindowPopup(c);
                }
            };

            btnLeague.setOnAction(event2);
            Button btnOption = new Button();
            btnOption.setText("Option");
            btnOption.setId(c);
            EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e)
                {
                    OptionWindowControll(name, "Classic", c);
                }
            };

            btnOption.setOnAction(event3);

            btnLeague.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnLeague.getStyleClass().add("button13");

            btnOption.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            btnOption.getStyleClass().add("button13");


            int crank = ServerData.getGWRank(signin.Gameweek, c);
            int lrank = ServerData.getGWRank(signin.Gameweek-1, c);

            ImageView imageView = new ImageView();
            if(crank>lrank) {
                imageView.setImage(im3);
            }
            else if(lrank>crank){
                imageView.setImage(im2);
            }
            else {
                imageView.setImage(im1);
            }

            LeagueTable leagues = new LeagueTable(btnLeague, btnOption, crank, lrank, imageView);
            tablePublic.getItems().addAll(leagues);
        }
    }




    ///global league load
    private void globalTableLoad() {
        //if(Gameweek==1) return;
        Image im1  = new Image("/sample/photos/indicator/nutral.png");
        Image im2  = new Image("/sample/photos/indicator/up.png");
        Image im3  = new Image("/sample/photos/indicator/down.png");

        globalLeaguetable.getItems().clear();
        leagues2.setCellValueFactory(new PropertyValueFactory<>("League"));
        option2.setCellValueFactory(new PropertyValueFactory<>("Option"));
        crank2.setCellValueFactory(new PropertyValueFactory<>("Crank"));
        lrank2.setCellValueFactory(new PropertyValueFactory<>("Lrank"));
        rankIndicator3.setCellValueFactory(new PropertyValueFactory<>("im"));

        String ans = ServerData.globaltableload(teamid);

        String[] strs = ans.split("#");
        String name = strs[0];
        String cs = "";
        cs = strs[0];
        String c = cs;
        Button btnLeague = new Button();
        btnLeague.setText(name);
        btnLeague.setId(c);

        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                globalLeaguWindowPopup();
            }
        };

        btnLeague.setOnAction(event2);
        Button btnOption = new Button();
        btnOption.setText("Option");
        btnOption.setId(c);
        EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                OptionWindowControll(name, "Classic", c);
            }
        };

        btnOption.setOnAction(event3);

        btnLeague.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
        btnLeague.getStyleClass().add("button13");

        btnOption.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
        btnOption.getStyleClass().add("button13");


        int crank, lrank;
        crank = Integer.parseInt(strs[1]);
        lrank = Integer.parseInt(strs[2]);

        ImageView imageView = new ImageView();
        if(crank>lrank) {
            imageView.setImage(im3);
        }
        else if(lrank>crank){
            imageView.setImage(im2);
        }
        else {
            imageView.setImage(im1);
        }

        LeagueTable leagues = new LeagueTable(btnLeague, btnOption, crank, lrank, imageView);
        globalLeaguetable.getItems().addAll(leagues);

    }

    private void globalLeaguWindowPopup(){
        leagueWindow.toFront();
        leagueWindow.setOpacity(1);
        vBoxes[0].setOpacity(0);
        Image im1  = new Image("/sample/photos/indicator/nutral.png");
        Image im2  = new Image("/sample/photos/indicator/up.png");
        Image im3  = new Image("/sample/photos/indicator/down.png");

        LeagueTable.getItems().clear();

        List<String> list1 = ServerData.globalLeagueTeams();

        ltname.setCellValueFactory(new PropertyValueFactory<>("Lname"));
        ltrank.setCellValueFactory(new PropertyValueFactory<>("Option"));
        ltpoints.setCellValueFactory(new PropertyValueFactory<>("Crank"));
        id.setCellValueFactory(new PropertyValueFactory<>("Lrank"));
        tpoints.setCellValueFactory(new PropertyValueFactory<>("code"));
        rankindicator2.setCellValueFactory(new PropertyValueFactory<>("im"));
        ltpoints.setText("GW"+(signin.Gameweek-1));

        for(Object obj: list1){
            String str = (String) obj;
            String[] strs = str.split("#");
            int id = Integer.parseInt(strs[1]);
            int tpoints = Integer.parseInt(strs[3]);
            String name = strs[2];
            int gwpoints = Integer.parseInt(strs[4]);
            int rank = Integer.parseInt(strs[0]);

            ImageView imageView = new ImageView();

                imageView.setImage(im1);


            leaguesTable leagues = new leaguesTable(name, String.valueOf(rank), gwpoints, id, String.valueOf(tpoints), imageView);
            LeagueTable.getItems().addAll(leagues);
        }


    }

    //if particular league clicked:
    @FXML
    private void leaguWindowPopup(String code){
        leagueWindow.toFront();
        leagueWindow.setOpacity(1);
        vBoxes[0].setOpacity(0);
        Image im1  = new Image("/sample/photos/indicator/nutral.png");
        Image im2  = new Image("/sample/photos/indicator/up.png");
        Image im3  = new Image("/sample/photos/indicator/down.png");

        LeagueTable.getItems().clear();

        int GameWk = ServerData.getLeagueStartGW(code);

        ///////////////////////for previous gw rank of each team ////////////////////////////////

        ltname.setCellValueFactory(new PropertyValueFactory<>("Lname"));
        ltrank.setCellValueFactory(new PropertyValueFactory<>("Option"));
        ltpoints.setCellValueFactory(new PropertyValueFactory<>("Crank"));
        id.setCellValueFactory(new PropertyValueFactory<>("Lrank"));
        tpoints.setCellValueFactory(new PropertyValueFactory<>("code"));
        rankindicator2.setCellValueFactory(new PropertyValueFactory<>("im"));
        ltpoints.setText("GW" + (signin.Gameweek - 1));

        if(GameWk< Gameweek) {
            HashMap<Integer, Integer> map = new HashMap<>();

            List list = ServerData.teamPreGWRank(code, Gameweek);

            for (Object obj : list) {
                String str = (String) obj;
                String[] strs = str.split("#");
                map.put(Integer.parseInt(strs[0]), Integer.parseInt(strs[1]));
            }

            /////////////////////////////////////////////////////////////////////////////////////////

            List list1 = ServerData.presentLeagueTeam(code, Gameweek);

            for (Object obj : list1) {
                String str = (String) obj;
                String[] strs = str.split("#");
                int id = Integer.parseInt(strs[0]);
                int tpoints = Integer.parseInt(strs[1]);
                String name = strs[2];
                int gwpoints = Integer.parseInt(strs[3]);
                int rank = Integer.parseInt(strs[4]);

                ImageView imageView = new ImageView();
                if (map.containsKey(id)) {
                    if (map.get(id) > rank) {
                        imageView.setImage(im2);
                    } else if (map.get(id) < rank) {
                        imageView.setImage(im3);
                    } else {
                        imageView.setImage(im1);
                    }
                } else {
                    imageView.setImage(im1);
                }

                leaguesTable leagues = new leaguesTable(name, String.valueOf(rank), gwpoints, id, String.valueOf(tpoints), imageView);
                LeagueTable.getItems().addAll(leagues);
            }
        }

        List<String> list = ServerData.futureLeagueTeam(code);
        leaguesTable leagues1 = new leaguesTable("team will join", "", 0, 0, "", imageView);
        LeagueTable.getItems().addAll(leagues1);
        for (Object obj: list){
            String str = (String) obj;
            String[] strs = str.split("#");
            int id = Integer.parseInt(strs[0]);
            String name = strs[1];
            ImageView imageView = new ImageView();
            imageView.setImage(im1);
            leaguesTable leagues = new leaguesTable(name, "", 0, id, "", imageView);
            LeagueTable.getItems().addAll(leagues);
        }



    }

    @FXML
    private void teamWindowPopup(MouseEvent event) {
        if(LeagueTable.getSelectionModel().getSelectedItem()!=null) {
            table = LeagueTable.getSelectionModel().getSelectedItem();
            teamWindow.toFront();
            teamWindow.setOpacity(1);
            vBoxes[0].setOpacity(0);
            leagueWindow.setOpacity(0);
            idofteam = table.getLrank();
            points.pointsTeamId = idofteam;
            points.playerPointsCalc(signin.Gameweek - 1, idofteam);
            points.btnAutoPoints.fire();
            Pane pane = ClientMain.getWindow(3);
            teamBorderPane.setCenter(pane);
        }
    }

    private void OpacityControll(int idx){
        for(int i=2; i<=7; i++){
            vBoxes[i].setOpacity(0);
        }
        vBoxes[idx].setOpacity(1);
        vBoxes[idx].toFront();
    }

    private void OptionWindowControll(String name, String type, String code) {
        int admin = ServerData.getLeagueAdmin(code);
        Wmyleagues.setOpacity(0);
        //leagueSettingsImg.setOpacity(1);
        WOption.setOpacity(1);
        WOption.toFront();
        LScode.setOpacity(1);
        btnDeleteLeague.setOpacity(1);
        btnDeleteLeague.setId(code);
        LSname.setText("League Name:  "+name);
        LScode.setText("League Code:  "+code);
        LStype.setText("League Type:  "+type);
        if(signin.teamid!=admin){
            LScode.setOpacity(0);
            btnDeleteLeague.setOpacity(0);
        }
    }

    @FXML
    private void handleLeaguesButton(MouseEvent event) {
        float op = (float) 1;
        if(event.getSource().equals(btncreatejoin)){
            vBoxes[1].toFront();
            vBoxes[1].setOpacity(1);
            vBoxes[0].setOpacity(0);
//            iTwo1.setOpacity(op);
//            iTwo2.setOpacity(op);
//            iOne.setOpacity(0);

            //itable.setOpacity(0);
            OpacityControll(2);
        }
        if(event.getSource().equals(btnjoinaleague)){
            OpacityControll(4);
        }
        if(event.getSource().equals(btncreatealeague)){
            OpacityControll(3);
        }
        if(event.getSource().equals(btnjoinprivateleague)){
            OpacityControll(5);
        }
        if(event.getSource().equals(btncreateclassic)){
            ScoreGw = "";
            OpacityControll(6);
        }
        if(event.getSource().equals(btncreateclassicfinal)){
            String LeagueName = "";
            LeagueName = TFcreateclassic.getText();
            if(LeagueName.length()>0){
                createClassicLeagues(LeagueName);
                System.out.println("League Created");
                btnleaguesLoad.fire();
                Wmyleagues.toFront();
                Wmyleagues.setOpacity(1);
                Wcreate.setOpacity(0);
            }
        }
        if(event.getSource().equals(btnjoinclassicfinal)){
            String ClassicCode = "";
            ClassicCode = TFjoinclassic.getText();
            if(ClassicCode.length()>0 && ClassicCode.length()<10){
                if(joinClassicLeagues(ClassicCode)){
                    Wmyleagues.toFront();
                    Wmyleagues.setOpacity(1);
                    btnleaguesLoad.fire();
                    Wcreate.setOpacity(0);
                }
                else {
                    TFjoinclassic.setText("Wrong Code");
                }
            }
        }
        if(event.getSource().equals(btnjoinpublicleague)){
            ServerData.CreatePublicClassicLeague(teamid);
            System.out.println("League Created");
            btnleaguesLoad.fire();
            Wmyleagues.toFront();
            Wmyleagues.setOpacity(1);
            Wcreate.setOpacity(0);
        }
        if(event.getSource().equals(btncreatehth)){
            ScoreGw = "";
            OpacityControll(7);
        }
        if(event.getSource().equals(creath2hfinal)){
            String LeagueName = "";
            LeagueName = TFcreateclassic1.getText();
            if(LeagueName.length()>0){
                createH2HLeagues(LeagueName);
                System.out.println("League Created");
                btnleaguesLoad.fire();
                Wmyleagues.toFront();
                Wmyleagues.setOpacity(1);
                Wcreate.setOpacity(0);
            }
        }
        //click for h2h join window
        if(event.getSource().equals(btnjoinprivateleague1)){
            OpacityControll(8);
        }
        //h2h joinfinal
        if(event.getSource().equals(btnjoinclassicfinal1)){
            String ClassicCode = "";
            ClassicCode = TFjoinclassic1.getText();
            if(ClassicCode.length()>0 && ClassicCode.length()<10){
                if(joinH2HLeagues(ClassicCode)){
                    Wmyleagues.toFront();
                    Wmyleagues.setOpacity(1);
                    btnleaguesLoad.fire();
                    Wcreate.setOpacity(0);
                }
                else {
                    TFjoinclassic.setText("Wrong Code");
                }
            }
        }

        //////////////////////////////////
        if(event.getSource().equals(btnBack)){
            btnBackControll();
        }
        if(event.getSource().equals(btnDeleteLeague)){
            DeleteLeague();
        }

        //////////////
        if(event.getSource().equals(btnStanding)){
            standingPane.toFront();
            standingPane.setOpacity(1);
            fixturePane.setOpacity(0);
        }
        if(event.getSource().equals(btnFixture)){
            fixturePane.toFront();
            standingPane.setOpacity(0);
            fixturePane.setOpacity(1);
        }
    }

    private void btnBackControll(){
        if(leagueWindow.getOpacity()==1){
            Wmyleagues.toFront();
            Wmyleagues.setOpacity(1);
            leagueWindow.setOpacity(0);

        }
        if(teamWindow.getOpacity()==1){
            leagueWindow.toFront();
            leagueWindow.setOpacity(1);
            teamWindow.setOpacity(0);
            gwofteam = signin.Gameweek-1;
        }
        if(WOption.getOpacity()==1){
            Wmyleagues.toFront();
            Wmyleagues.setOpacity(1);
            WOption.setOpacity(0);
        }
    }

    @FXML
    private void expandTable(){
        if(btnEp1.getId()=="0") {
            wmlVB.setPrefHeight(1600.0);
            WmlVB1.setPrefHeight( 700.0);
            tablePrivateClassic.setPrefHeight( 650);
            btnEp1.setText("^");
            btnEp1.setId("1");
        }
        else {
            wmlVB.setPrefHeight(1100);
            WmlVB1.setPrefHeight(200);
            tablePrivateClassic.setPrefHeight( 170);
            btnEp1.setText("V");
            btnEp1.setId("0");
        }
        //animtest();
    }

}


