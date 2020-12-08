package sample.Controllers;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.Clients.ClientMain;
import sample.Clients.ServerData;
import sample.Objects.playerDetailsStat;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.Point;
import java.awt.MouseInfo;


import java.util.ArrayList;
import java.util.List;

public class statistics {
    private String[] viewboxElement = {"All Player","GoalKeeper", "Defender", "Midfielder", "Forward", "Arsenal", "Aston Villa",
            "Bournemouth", "Burnley", "Brighton", "Chelsea","Crystal Palace", "Everton", "Leicester",
            "Liverpool", "Man City", "Man Utd", "Newcastle", "Norwich", "Sheffield Utd", "Southampton",
            "Spurs", "Watford", "West Ham", "Wolves"};
    private static double rowX = 0;
    private static double rowY = 0;


    @FXML private ComboBox<String> viewbox;
    @FXML private ComboBox<String> sortbox;
    @FXML private TableView<playerDetailsStat> tableView;
    @FXML private TableColumn<playerDetailsStat, String> Player;
    @FXML private TableColumn<playerDetailsStat, Double>cost;
    @FXML private TableColumn<playerDetailsStat, Double>sel;
    @FXML private TableColumn<playerDetailsStat, Integer>Points;
    @FXML private TableColumn<playerDetailsStat, Button>no;
    @FXML private TableColumn<playerDetailsStat, HBox>vbox;
    @FXML private TextField searchBox;
    private static final double BLUR_AMOUNT = 30;
    private static final Effect frostEffect = new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 5);
    private static final ImageView background = new ImageView();
    private static boolean isHovering = false;

    @FXML
    Pane hoverPane;

    private final ObservableList<playerDetailsStat> dataList = FXCollections.observableArrayList();

    @FXML Button btnstat;

    public static Button btnAnim = new Button();

    public void initialize() {
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                Statistics(0,"",0);
            }
        };

        btnstat.setOnAction(event);
        btnstat.fire();

        viewbox.setItems(FXCollections.observableArrayList(viewboxElement));
        final String[] str = {""};
        viewbox.getSelectionModel().selectedItemProperty()
                .addListener(new ChangeListener<String>() {
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
                        str[0] = newValue.toString();
                        System.out.println(str[0]);
                        if(str[0]=="All Player"){
                            Statistics(0, "0", 0);
                        }
                        else if(str[0]=="GoalKeeper"|| str[0]== "Defender" || str[0]=="Midfielder"||str[0]== "Forward"){
                            if(str[0]=="Defender") {
                                Statistics(2,"", 1);
                            }
                            if(str[0]=="Midfielder") {
                                Statistics(3,"", 1);
                            }
                            if(str[0]=="Forward") {
                                Statistics(4,"", 1);
                            }
                            if(str[0]=="GoalKeeper") {
                                Statistics(1,"", 1);
                            }
                        }
                        else {
                            Statistics(0, str[0], 2);
                        }
                    }
                });

        background.setEffect(frostEffect);
        tableHover();
    }

    private void tableHover(){
        AnimationTimer an = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(isHovering) {
                    hoverPane.setOpacity(1);
                    Point p = MouseInfo.getPointerInfo().getLocation();
                    double x = p.getX()-ClientMain.primaryStage.getX()-(hoverPane.getWidth()/2+236)*Menu.ScaleRatio;
                    hoverPane.setLayoutY(rowY+197);
                    if(x>-30 && x<600){
                        hoverPane.setLayoutX(x);
                    }
                }
                else hoverPane.setOpacity(0);
            }
        };
        an.start();
    }

    @FXML
    private void handleMouseEvent(MouseEvent event){

    }

    public void Statistics(int position, String team, int idx) {
        FilteredList<playerDetailsStat> filteredData = new FilteredList<>(dataList, b -> true);
        SortedList<playerDetailsStat> sortedData = new SortedList<>(filteredData);
        dataList.clear();

        tableView.setRowFactory(tableView -> {
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

        List<String> list = new ArrayList<>();

        if(idx==0 || idx==1 || idx==2){
            list = ServerData.getStatistics(position, team, idx);
        }
        else {
            return;
        }

        Player.setCellValueFactory(new PropertyValueFactory<>("player"));
        Points.setCellValueFactory(new PropertyValueFactory<>("Points"));
        sel.setCellValueFactory(new PropertyValueFactory<>("sel"));
        cost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        no.setCellValueFactory(new PropertyValueFactory<>("info"));
        vbox.setCellValueFactory(new PropertyValueFactory<>("playerbox"));


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
                    playerWindow.id = String.valueOf(i);
                    playerWindow.btnAutoHistory.fire();
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
            vBox.getChildren().setAll(name, clubandpos);
            hBox.getChildren().setAll(im, vBox);
            hBox.setSpacing(15);
            vBox.setSpacing(7);
            playerDetailsStat pds = new playerDetailsStat(info, hBox, player, c,s,po);
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

        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }


}
