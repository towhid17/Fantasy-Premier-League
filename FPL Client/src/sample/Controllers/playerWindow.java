package sample.Controllers;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import sample.Clients.ClientMain;
import sample.Clients.ServerData;
import sample.Objects.playerFixture;
import sample.Objects.playerStat;

import java.util.List;

import static sample.Controllers.signin.dataplayerDetails;

public class playerWindow {
    ///////////////table History/////////////////////////////////
    @FXML private TableView<playerStat> tableHistory;
    @FXML private TableColumn<playerStat, Integer> gw;
    @FXML private TableColumn<playerStat, String>opponent;
    @FXML private TableColumn<playerStat, Integer>pts;
    @FXML private TableColumn<playerStat, Integer>mp;
    @FXML private TableColumn<playerStat, Integer>gs;
    @FXML private TableColumn<playerStat, String>a;
    @FXML private TableColumn<playerStat, Integer>gc;
    @FXML private TableColumn<playerStat, Integer>pm;
    @FXML private TableColumn<playerStat, Integer>ps;
    @FXML private TableColumn<playerStat, Integer>s;
    @FXML private TableColumn<playerStat, Integer>b;
    @FXML private TableColumn<playerStat, Integer>yc;
    @FXML private TableColumn<playerStat, Integer>rc;
    @FXML private TableColumn<playerStat, Integer>og;
    @FXML private TableColumn<playerStat, Integer>cs;

    @FXML private TableView<playerFixture> fixtureTable;
    @FXML private TableColumn<playerFixture, String>date;
    @FXML private TableColumn<playerFixture, Integer>GW;
    @FXML private TableColumn<playerFixture, HBox>opp;


    @FXML StackPane playerWindow;
    @FXML
    AnchorPane mainPane;
    @FXML Pane fixturePane, historyPane;
    @FXML Button btnhistory, btnfixture;
    @FXML Circle btnClose1;
    @FXML Text tpoints, cgwp, cgw, bp, ccost, psel, agw, agwo, aagw, aagwo, pname, cname ;
    @FXML Button btnpos;
    @FXML Pane btnPane;
    @FXML ImageView imgTeam;
    Timeline rotate = new Timeline();
    public static String id = "";
    public static int playerWindowGW = 0;
    public static Button btnAutoHistory = new Button();
    @FXML Pane whitePane;
    private static final double BLUR_AMOUNT = 30;

    private static final Effect frostEffect =
            new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 5);

    private static final ImageView background = new ImageView();
    public static Button btnResize = new Button();


    //////////////////////////METHOD///////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////

    public void initialize(){
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                playerHistory(Integer.parseInt(id), playerWindowGW);
            }
        };
        btnAutoHistory.setOnAction(event);
        background.setEffect(frostEffect);
        Rectangle r = new Rectangle(20,20);
        whitePane.getChildren().setAll(r);

        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                double x2 = ClientMain.primaryStage.getWidth()-1200;
                double y2 = ClientMain.primaryStage.getHeight()-800;

                mainPane.setLayoutX(x2/2);
                mainPane.setLayoutY(y2/2);
                mainPane.setScaleX(Menu.ScaleRatio);
                mainPane.setScaleY(Menu.ScaleRatio);
                mainPane.setScaleZ(Menu.ScaleRatio);
            }
        };
        btnResize.setOnAction(event2);

    }

    private void playerHistory(int id, int Gameweek) {

        background.setImage(Menu.copyBackground(whitePane, 2,2));
        background.setFitHeight(whitePane.getHeight());
        background.setFitWidth(whitePane.getWidth());
        whitePane.getChildren().setAll(background);

        int cgwpp = -100000;
        int tpts = -100000;
        double ccosts = -100000;
        String pos = "";
        String plname = "";

        String str= ServerData.playerHistoryTopInfo(Gameweek, id);
        String[] strs = str.split("#");
        plname = strs[0];
        tpts = Integer.parseInt(strs[1]);
        ccosts = Double.parseDouble(strs[2]);
        ccosts = ccosts/10;
        cgwpp = Integer.parseInt(strs[3]);
        int teamid = Integer.parseInt(strs[4]);
        String teamname = strs[5];
        String tav = strs[6];
        int tbp = Integer.parseInt(strs[7]);

        for(int i=0; i<=14; i++){
            if(id==dataplayerDetails[i].getId()){
                pos =  dataplayerDetails[i].getPos();
            }
        }
        if(pos=="DEF") pos = "DEFENDER";
        if(pos=="GK") pos = "GOALKEEPER";
        if(pos=="MF") pos = "MIDFIELDER";
        if(pos=="ST") pos = "STRIKER";

        Image image = new Image("/sample/photos/TLogo/" +tav+".png");
        imgTeam.setImage(image);

        ///////////////label////////////////////
        String scgwpp = null;
        if(cgwpp==-100000){
            scgwpp = "";
        }
        else {
            scgwpp = String.valueOf(cgwpp);
        }

        bp.setText(String.valueOf(tbp));
        tpoints.setText(String.valueOf(tpts));
        cgw.setText("GW"+(Gameweek));
        cgwp.setText(scgwpp);
        ccost.setText(String.valueOf(ccosts));
        pname.setText(plname);
        btnpos.setText(pos);
        agw.setText("GW"+(Gameweek+1));
        aagw.setText("GW"+(Gameweek+2));
        cname.setText(teamname);

        /////////////////////////////////////////

        gw.setCellValueFactory(new PropertyValueFactory<>("gw"));
        opponent.setCellValueFactory(new PropertyValueFactory<>("opponent"));
        mp.setCellValueFactory(new PropertyValueFactory<>("mp"));
        gs.setCellValueFactory(new PropertyValueFactory<>("gs"));
        a.setCellValueFactory(new PropertyValueFactory<>("a"));
        cs.setCellValueFactory(new PropertyValueFactory<>("cs"));
        og.setCellValueFactory(new PropertyValueFactory<>("og"));
        ps.setCellValueFactory(new PropertyValueFactory<>("ps"));
        pm.setCellValueFactory(new PropertyValueFactory<>("pm"));
        yc.setCellValueFactory(new PropertyValueFactory<>("yc"));
        rc.setCellValueFactory(new PropertyValueFactory<>("rc"));
        pts.setCellValueFactory(new PropertyValueFactory<>("points"));
        b.setCellValueFactory(new PropertyValueFactory<>("b"));
        s.setCellValueFactory(new PropertyValueFactory<>("s"));
        gc.setCellValueFactory(new PropertyValueFactory<>("gc"));


        List list = ServerData.playerHistory(id, teamid);

        for(Object obj: list) {
            String ans = String.valueOf(obj);
            String[] anss = ans.split("#");
            playerStat stats = new playerStat(Integer.parseInt(anss[0]), anss[1], Integer.parseInt(anss[2]), Integer.parseInt(anss[3]),
                    Integer.parseInt(anss[4]), Integer.parseInt(anss[5]), Integer.parseInt(anss[6]), Integer.parseInt(anss[7]),
                    Integer.parseInt(anss[8]), Integer.parseInt(anss[9]), Integer.parseInt(anss[10]), Integer.parseInt(anss[11]),
                    Integer.parseInt(anss[12]), Integer.parseInt(anss[13]), Integer.parseInt(anss[14]));
            tableHistory.getItems().add(stats);
        }
        playerFixture(id, Gameweek);
    }

    private void playerFixture(int id, int Gameweek) {
        fixtureTable.getItems().clear();

        date.setCellValueFactory(new PropertyValueFactory<>("Date"));
        GW.setCellValueFactory(new PropertyValueFactory<>("gw"));
        opp.setCellValueFactory(new PropertyValueFactory<>("opp"));

        List list = ServerData.playerFixture(id, Gameweek);

        for(Object obj: list){
            String str = String.valueOf(obj);
            String[] strs = str.split("#");
            int Gw = Integer.parseInt(strs[0]);
            String avvbr = strs[1];
            Text text = new Text(avvbr);
            text.setStyle("-fx-fill: white; -fx-font-weight: bold");
            HBox hb = new HBox();
            hb.setAlignment(Pos.CENTER);
            hb.setSpacing(10);
            ImageView iv = new ImageView();
            Image image = new Image("/sample/photos/TLogo/" +avvbr+".png");
            iv.setImage(image);
            iv.setFitWidth(20);
            iv.setFitHeight(30);
            hb.getChildren().addAll(iv, text);
            playerFixture pl = new playerFixture("TBD", Gw, hb);
            fixtureTable.getItems().add(pl);
        }
    }


    @FXML
    private void handleMouseEvent(MouseEvent event) {
        if (event.getSource() == btnClose1) {
            ClientMain.removePane(10);
            tableHistory.getItems().clear();
        }
        if(event.getSource().equals(btnfixture)){
            fixturePane.setOpacity(1);
            historyPane.setOpacity(0);
            fixturePane.toFront();

        }
        if (event.getSource().equals(btnhistory)){
            fixturePane.setOpacity(0);
            historyPane.setOpacity(1);
            historyPane.toFront();

        }

    }

}
