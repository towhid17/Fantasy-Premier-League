package sample.Controllers;

import sample.Clients.ServerData;
import sample.Objects.fixtureData;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.List;

public class fixtures {
    @FXML
    Text textGW;
    @FXML
    Button btnprevious, btnnext;
    @FXML private TableView<fixtureData> fixtureTable;
    @FXML private TableColumn<fixtureData, HBox> hometeam;
    @FXML private TableColumn<fixtureData, HBox> awayteam;
    @FXML private TableColumn<fixtureData, Button> hg;
    @FXML private TableColumn<fixtureData, Button> ag;
    private static int gameweek;
    @FXML
    private void handleMouseEvent(MouseEvent event) {

        if(event.getSource().equals(btnprevious))
        {
            if(gameweek>1) {
                fixtureTable.getItems().clear();
                Fixture(--gameweek);
                textGW.setText("Gameweek "+ gameweek);


            }
        }
        if(event.getSource().equals(btnnext))
        {
            if(gameweek<38) {
                fixtureTable.getItems().clear();
                Fixture(++gameweek);
                textGW.setText("Gameweek "+ gameweek);

            }
        }

    }

    public void initialize() {
        Fixture(signin.Gameweek);
        gameweek = signin.Gameweek;
        textGW.setText("Gameweek "+ signin.Gameweek);
    }

    //fixture from database

    public void Fixture(int GW) {
        hometeam.setCellValueFactory(new PropertyValueFactory<>("hometeam"));
        awayteam.setCellValueFactory(new PropertyValueFactory<>("awayteam"));
        hg.setCellValueFactory(new PropertyValueFactory<>("hometeamScore"));
        ag.setCellValueFactory(new PropertyValueFactory<>("awayteamScore"));

        List list = ServerData.getFixture(GW);

        for(Object obj: list) {
            String ans = String.valueOf(obj);
            String[] strs = ans.split("#");

            String home = strs[0];
            int hg = Integer.parseInt(strs[1]);
            int ag = Integer.parseInt(strs[2]);
            String away = strs[3];
            String htav = strs[4];
            String atav = strs[5];

            Button button1 = new Button();
            Button button2 = new Button();

            if(GW>=signin.Gameweek){
                button1.setText("V");
                button2.setText("S");
                //button2.setOpacity(0);
            }
            else {
                button1.setText(String.valueOf(hg));
                button2.setText(String.valueOf(ag));
            }
            button1.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            button2.getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
            button1.getStyleClass().add("texture5");
            button2.getStyleClass().add("texture4");

            Text hm = new Text(home);
            Text aw = new Text(away);
            ImageView ivh = new ImageView();
            ImageView iva = new ImageView();
            hm.setStyle("-fx-font-weight: bold; -fx-fill: white; -fx-font-size: 16");
            aw.setStyle("-fx-font-weight: bold; -fx-fill: white; -fx-font-size: 16");

            Image imh = new Image("/sample/photos/TLogo/" +htav+".png");
            Image ima = new Image("/sample/photos/TLogo/" +atav+".png");
            ivh.setImage(imh);
            iva.setImage(ima);
            ivh.setFitHeight(25);
            ivh.setFitWidth(25);
            iva.setFitHeight(25);
            iva.setFitWidth(25);

            HBox hbbH = new HBox();
            hbbH.setSpacing(20);
            hbbH.setAlignment(Pos.CENTER);
            hbbH.getChildren().addAll(hm);
            hbbH.setPrefWidth(150);
            HBox hbbA = new HBox();
            hbbA.setSpacing(20);
            hbbA.setAlignment(Pos.CENTER);
            hbbA.getChildren().addAll(aw);
            hbbA.setPrefWidth(150);

            HBox hbH = new HBox();
            hbH.setSpacing(20);
            hbH.setAlignment(Pos.CENTER);
            hbH.getChildren().addAll(hbbH, ivh);
            HBox hbA = new HBox();
            hbA.setSpacing(20);
            hbA.setAlignment(Pos.CENTER);
            hbA.getChildren().addAll(iva, hbbA);

            fixtureData data = new fixtureData(hbH, button1, button2, hbA);
            fixtureTable.getItems().add(data);
        }
    }

}
