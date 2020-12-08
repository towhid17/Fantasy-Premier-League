package sample.Controllers;

import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import sample.Clients.ClientMain;
import javafx.animation.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileOutputStream;
import java.util.ArrayList;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Random;

public class Menu {
    private HBox[] buttons = new HBox[8];
    private Button[] btnSize = new Button[5];
    private Text[] btnText = new Text[8];
    private String[] btnString = {"Status", "My Team", "Points", "Transfers", "Leagues", "Fixtures", "Statistics", "Rules"};
    String color = "";
    int[] sideMap = {121, 202, 546};
    int[] animMap = {90,140,190,240,290,340, 390, 440};

    public static Button btntheme = new Button();
    public static Button btnSquadSelectionOff = new Button();
    public static Button btnSaveAnim = new Button();
    public static Button btnSquadSelectionCheck = new Button();
    private double xOffset = 0;
    private double yOffset = 0;
    public static Button btnUsernameSet = new Button();
    boolean isbox = false;
    static boolean isSquadSelected = false;
    public static double ScaleRatio = 1.0;
    public static double W = 1200.0;
    public static double H = 800.0;

    @FXML AnchorPane topane, sidePane, MenuAnchorPane;
    @FXML BorderPane borderMenu;
    @FXML ImageView topimage, backimg, fantasyIcon, profilePic;
    @FXML Pane SettingsPane, topTransparentPane, sideTransparentPane, sideBorderPane, topBorderPane, blackPane1, topBlackTab;
    @FXML Button btnClose, btnS5, btnS8, btnE1, btnG5, btnG8, btnMaximize, btnMinimize;
    @FXML Circle animCircle;
    @FXML HBox status, transfers, leagues, fixtures, rules, statistics, points, myTeam, topMenu;
    @FXML Button btnSignOut, btnThemes, logout;
    @FXML Pane sideUp, sideDown, topane1, savePane, tickPane, sideBarBorder, hexPlane, hexPlane1,
            hexPlane2, blackPane, hexPlane3, hoverPane, blackPane11;
    @FXML Rectangle rectanimB;
    @FXML Button btnMenuEp, btnDraft, btnResize;
    @FXML VBox sidePaneVbox, userPhotoBox;
    @FXML VBox userBox;
    @FXML Text username, t1, t2,t3,t4,t5,t6,t7,t8, fantasy, HierText;
    private boolean isOp1 = true;
    public static Button btnOpPg = new Button();

    private static final Effect frostEffect1 = new BoxBlur(15, 15, 3);
    private static final Effect frostEffect2 = new BoxBlur(50, 50, 3);

    private static final ImageView background = new ImageView();
    private static final ImageView background1 = new ImageView();
    private static int hexCol = 8;
    public static Button btnActiveWindow = new Button();
    public static Button btnInActiveWindow = new Button();
    public static Button btnInitialBlurSet = new Button();
    private boolean[] isBtnSelected = new boolean[8];
    private static double prevScaleRatio = 1.0;
    public static int presentWindowIndex = 0;

    ArrayList<ArrayList<Polygon>> polygonList = new ArrayList<>();
    ArrayList<ArrayList<Polygon>> polygonList1 = new ArrayList<>();
    ArrayList<ArrayList<Polygon>> polygonList2 = new ArrayList<>();
    ArrayList<ArrayList<Polygon>> polygonList3 = new ArrayList<>();


    //////////////////////////METHOD///////////////////////////////////

    public void initialize() {
        setCursor();
        initializeButtons();
        makeStageDragable(topane1);
        setInitialOpacityNPosition();

        background.setEffect(frostEffect1);
        background1.setEffect(frostEffect2);

        Pane pane = ClientMain.getWindow(1);
        borderMenu.setCenter(pane);

        setButtonAction();
        func(animMap[0], pane);
        setPolygon();
        setSquadSelectionBtnAction();
        setActiveInactiveWindowBlur();
        setInitialBlur();
        setInitialPolygonColor();
        dragResize();
        setRoundImage(profilePic);
        setTopPolygon();
        setPolygonlist1Opacity(0);
    }

    private void setTopPolygon(){
        double[] points = {
                0,0,
                70,0,
                80,14,
                70,28,
                0,28,
                10,14
        };
        Polygon hex0 = new Polygon();

        hex0.getPoints().addAll(
                points[0], points[1], points[2]-10, points[3], points[4]-10, points[5],
                points[6]-10, points[7], points[8], points[9], points[10], points[11]
        );

        hex0.setLayoutY(9);
        hex0.setLayoutX(40);
        hex0.setStyle("-fx-fill: rgba(255,255,255,.1)");
        //hex0.setStyle("-fx-fill: #6229D2");

        Polygon hex1 = new Polygon();
        hex1.getPoints().addAll(
                points[0], points[1], points[2]+10, points[3], points[4]+10, points[5],
                points[6]+10, points[7], points[8], points[9], points[10], points[11]
        );
        hex1.setLayoutX(106);
        hex1.setLayoutY(9);
        hex1.setStyle("-fx-fill: rgba(255,255,255,.1)");
        //hex1.setStyle("-fx-fill: #6229D2");

        topane1.getChildren().addAll(hex0, hex1);
        hex0.toBack();
        hex1.toBack();
    }

    private void setRoundImage(ImageView imageView){
        Rectangle clip = new Rectangle(
                imageView.getFitWidth(), imageView.getFitHeight()
        );
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        imageView.setClip(clip);

        // snapshot the rounded image.
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);
        WritableImage image = imageView.snapshot(parameters, null);

//        // remove the rounding clip so that our effect can show through.
//        imageView.setClip(null);
//
//        // apply a shadow effect.
//        imageView.setEffect(new DropShadow(20, Color.BLACK));
//
//        // store the rounded image in the imageView.
        imageView.setImage(image);
    }

    private void setInitialOpacityNPosition() {
        savePane.toBack();
        savePane.setOpacity(0);
        SettingsPane.toBack();
        SettingsPane.setOpacity(0);
        topimage.setOpacity(0);
        rectanimB.setOpacity(.5);
        hexPlane.setOpacity(.8);
        rectanimB.setOpacity(0);
    }

    private void setButtonAction() {
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                themeChange();
            }
        };
        btntheme.setOnAction(event);
        EventHandler<ActionEvent> event4 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                username.setText(signin.USER);
            }
        };
        btnUsernameSet.setOnAction(event4);
        EventHandler<ActionEvent> event3 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                saveAnim();
            }
        };
        btnSaveAnim.setOnAction(event3);
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if(isOp1) setPolygonlist1Opacity(0);
                else setPolygonlist1Opacity(.2);
            }
        };
        btnOpPg.setOnAction(event2);
    }

    private void setPolygonlist1Opacity(double x){
        for (int i=0; i<30; i++){
            for (int j=hexCol; j<40; j++){
                polygonList1.get(i).get(j).setOpacity(x);
            }
        }
    }

    private void setPolygon() {
        setPolygonPlane(hexPlane, 0, polygonList);
        startPolygonWave();
        setPolygonPlane(hexPlane1, 0.2, polygonList1);
        setPolygonPlane(hexPlane2, 0, polygonList2);
        setPolygonPlane(hexPlane3, 0, polygonList3);
    }

    private void setCursor() {
        Image image = new Image("/sample/icons/cursor.png");
        borderMenu.setCursor(new ImageCursor(image, image.getWidth() / 2,
                image.getHeight() /4));
    }

    private void initializeButtons(){
        btnMenuEp.setId("0");
        buttons[0]=status; buttons[1]=myTeam; buttons[3]=transfers; buttons[4]=leagues; buttons[5]=fixtures;
        btnText[0]=t1; btnText[1]=t2; btnText[2]=t3; btnText[3]=t4; btnText[4]=t5; btnText[5]=t6; btnText[6]=t7; btnText[7]=t8;
        buttons[7]=rules; buttons[6]=statistics;buttons[2]= points;
        btnSize[0] = btnS5; btnSize[1] = btnS8; btnSize[2] = btnE1; btnSize[3] = btnG5; btnSize[4] = btnG8;
        //set btnsizeFunc
        double Wsize = 0.6;
        for(int i=0; i<=4; i++){
            final double ws = Wsize;
            EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    setWindowSize(ws);
                }
            };
            btnSize[i].setOnAction(eventHandler);
            Wsize += 0.2;
        }
        //set hover
        for(int i=0; i<=7; i++){
            isBtnSelected[i] = false;
            Pane pane = new Pane();
            pane.setPrefWidth(300);
            pane.setPrefHeight(50);
            pane.setOpacity(0);
            pane.setStyle("-fx-background-color: linear-gradient(to right, rgba(0,0,0,0), rgba(255,255,255,.2), rgba(0,0,0,0));" +
                    "-fx-border-color: linear-gradient(to right, rgba(0,0,0,0),rgba(0,0,0,0), rgba(255,255,255,.25),rgba(0,0,0,0), rgba(0,0,0,0)); -fx-border-width: 2");
            hoverPane.getChildren().addAll(pane);
            final int id = i;
            buttons[i].setOnMouseEntered(event -> {
                hoverW10(id, pane);
            });
            buttons[i].setOnMouseExited(event -> {
                hoverW10E(pane);
            });
        }

    }

    private void setSquadSelectionBtnAction(){
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                SquadSelectionModeOn();
            }
        };
        btnSquadSelectionCheck.setOnAction(event1);
        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                SquadSelectionModeOff();
            }
        };
        btnSquadSelectionOff.setOnAction(event2);
    }

    private void setActiveInactiveWindowBlur(){
        EventHandler<ActionEvent> event5 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                sideHide(0);
                sidePaneVbox.setOpacity(0);
                //topane1.setOpacity(0);
                sideUp.setOpacity(0);
                sideDown.setOpacity(0);
                blackPane.setOpacity(0);
                //topBorderPane.setOpacity(1);
                sideBorderPane.setOpacity(1);
                //settingsIcon.setOpacity(0);
//                Rectangle rectangle = new javafx.scene.shape.Rectangle(
//                        topTransparentPane.getWidth(),
//                        topTransparentPane.getHeight(),
//                        Color.WHITESMOKE.deriveColor(
//                                0, 1, .2, 0.08
//                        )
//                );
//                topTransparentPane.getChildren().setAll(rectangle);
                Rectangle rectangle1 = new javafx.scene.shape.Rectangle(
                        sideTransparentPane.getWidth(),
                        sideTransparentPane.getHeight(),
                        Color.WHITESMOKE.deriveColor(
                                0, 1, .2, 0.08
                        )
                );
                sideTransparentPane.getChildren().setAll(rectangle1);

            }
        };
        btnInActiveWindow.setOnAction(event5);
        EventHandler<ActionEvent> event6 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Timeline t = new Timeline();
                t.getKeyFrames().addAll(
                        new KeyFrame(Duration.millis(200), event -> {
//                            background.setImage(copyBackground(topTransparentPane, 0, 0));
//                            background.setFitWidth(topTransparentPane.getWidth());
//                            background.setFitHeight(topTransparentPane.getHeight());
//                            topTransparentPane.getChildren().setAll(background);
                            background1.setImage(copyBackground(sideTransparentPane, 0, 0));
                            background1.setFitWidth(sideTransparentPane.getWidth());
                            background1.setFitHeight(sideTransparentPane.getHeight());
                            sideTransparentPane.getChildren().setAll(background1);
                        }),
                        new KeyFrame(Duration.millis(220), event -> {
                            sidePaneVbox.setOpacity(1);
                            //topane1.setOpacity(1);
                            sideUp.setOpacity(.2);
                            sideDown.setOpacity(.2);
                            blackPane.setOpacity(1);
                            //settingsIcon.setOpacity(1);
                            //topBorderPane.setOpacity(0);
                            sideBorderPane.setOpacity(0);
                            sideHide(1);
                        })
                );
                t.play();
            }
        };
        btnActiveWindow.setOnAction(event6);
    }

    private void setInitialPolygonColor(){
        for(int i=0; i<30; i++){
            for(int j=0; j<hexCol; j++){
                polygonList1.get(i).get(j).setOpacity(0);
            }
        }
        for(int i=0; i<30; i++){
            for(int j=hexCol; j<40; j++){
                polygonList1.get(i).get(j).setOpacity(0.2);
            }
        }
    }

    private void setInitialBlur(){
        EventHandler<ActionEvent> event7 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Timeline t = new Timeline();
                t.getKeyFrames().addAll(
                        new KeyFrame(Duration.millis(50), event5 ->{
                            sidePaneVbox.setOpacity(0);
                            //topane1.setOpacity(0);
                            //settingsIcon.setOpacity(0);
                            sideUp.setOpacity(0);
                            sideDown.setOpacity(0);
                            blackPane.setOpacity(0);
                            sideHide(0);
                        } ),
                        new KeyFrame(Duration.millis(2000), event->{
                            //background.setImage(copyBackground(topTransparentPane, 0, 0));
                            //topTransparentPane.getChildren().setAll(background);
                            background1.setImage(copyBackground(sideTransparentPane, 0, 0));
                            sideTransparentPane.getChildren().setAll(background1);
                        }),
                        new KeyFrame(Duration.millis(2050), event->{
                            sidePaneVbox.setOpacity(1);
                            //topane1.setOpacity(1);
                            //settingsIcon.setOpacity(1);
                            sideUp.setOpacity(.2);
                            sideDown.setOpacity(.2);
                            blackPane.setOpacity(1);
                            sideHide(1);
                        })
                );
                t.play();
            }
        };
        btnInitialBlurSet.setOnAction(event7);
    }

    @FXML
    private void setMinimize(){
        ClientMain.primaryStage.setIconified(true);
    }

    @FXML
    private void setMaximize(){
        if(ClientMain.primaryStage.isMaximized()){
            ClientMain.primaryStage.setWidth(1200*prevScaleRatio);
            ClientMain.primaryStage.setHeight(800*prevScaleRatio);

            double x2 = ClientMain.primaryStage.getWidth()-1200;
            double y2 = ClientMain.primaryStage.getHeight()-800;

            MenuAnchorPane.setLayoutX(x2/2);
            MenuAnchorPane.setLayoutY(y2/2);
            MenuAnchorPane.setScaleX(prevScaleRatio);
            MenuAnchorPane.setScaleY(prevScaleRatio);
            MenuAnchorPane.setScaleZ(prevScaleRatio);
            ScaleRatio = prevScaleRatio;
            ClientMain.primaryStage.setMaximized(false);
            playerWindow.btnResize.fire();

        }
        else{
            ClientMain.primaryStage.setMaximized(true);
            ClientMain.primaryStage.setWidth(1920);
            ClientMain.primaryStage.setHeight(1080);

            double x2 = ClientMain.primaryStage.getWidth()-1200;
            double y2 = ClientMain.primaryStage.getHeight()-800;

            MenuAnchorPane.setLayoutX(x2/2);
            MenuAnchorPane.setLayoutY(y2/2);

            MenuAnchorPane.setScaleX(1.6);
            MenuAnchorPane.setScaleY(1.35);

            prevScaleRatio = ScaleRatio;
            ScaleRatio = 1.6;
            playerWindow.btnResize.fire();

        }
    }

    @FXML
    private void enterResize(){
        btnResize.setCursor(Cursor.SE_RESIZE);
    }

    private void dragResize(){
        btnResize.setOnMousePressed(event -> {
            xOffset = event.getScreenX();
            yOffset = event.getScreenY();
        });
        btnResize.setOnMouseDragged(event -> {
            double x = event.getScreenX();
            double y = event.getScreenY();
            double d = 0.0;
            double r = 0.0;
            if(x>xOffset){
                if(x>y){
                    d = (x-xOffset)/10;
                    W+=d;
                    r = W/1200;
                    ScaleRatio = r;
                }
                else {
                    d = (y-yOffset)/10;
                    H+=d;
                    r = H/800;
                    ScaleRatio = r;
                }
                double x1 = 1200*r;
                double y1 = 800*r;
                ClientMain.primaryStage.setWidth(x1);
                ClientMain.primaryStage.setHeight(y1);

                double x2 = ClientMain.primaryStage.getWidth()-1200;
                double y2 = ClientMain.primaryStage.getHeight()-800;

                MenuAnchorPane.setLayoutX(x2/2);
                MenuAnchorPane.setLayoutY(y2/2);

                MenuAnchorPane.setScaleX(r);
                MenuAnchorPane.setScaleY(r);
                MenuAnchorPane.setScaleZ(r);
            }
            else {
                if(x<y){
                    d = (xOffset-x)/10;
                    W-=d;
                    r = W/1200;
                    ScaleRatio = r;
                }
                else {
                    d = (yOffset-y)/10;
                    H-=d;
                    r = H/800;
                    ScaleRatio = r;
                }
                double x1 = 1200*r;
                double y1 = 800*r;
                ClientMain.primaryStage.setWidth(x1);
                ClientMain.primaryStage.setHeight(y1);

                double x2 = ClientMain.primaryStage.getWidth()-1200;
                double y2 = ClientMain.primaryStage.getHeight()-800;

                MenuAnchorPane.setLayoutX(x2/2);
                MenuAnchorPane.setLayoutY(y2/2);

                MenuAnchorPane.setScaleX(r);
                MenuAnchorPane.setScaleY(r);
                MenuAnchorPane.setScaleZ(r);
            }
        });

        btnResize.setOnMouseReleased(event -> {
            W = ClientMain.primaryStage.getWidth();
            H = ClientMain.primaryStage.getHeight();
            playerWindow.btnResize.fire();
        });

    }

    private void setWindowSize(double xx){
        if(xx>1.3){
            xx=1.3;
        }
        final double x = xx;
        ScaleRatio = x;
        DoubleProperty sx = MenuAnchorPane.scaleXProperty();
        DoubleProperty sy = MenuAnchorPane.scaleYProperty();
        DoubleProperty sz = MenuAnchorPane.scaleZProperty();
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.millis(0), event -> {
                    double x1 = 1200*x;
                    double y1 = 800*x;
                    W = x1;
                    H = y1;
                    ClientMain.primaryStage.setWidth(x1);
                    ClientMain.primaryStage.setHeight(y1);

                    double x2 = ClientMain.primaryStage.getWidth()-1200;
                    double y2 = ClientMain.primaryStage.getHeight()-800;

                    MenuAnchorPane.setLayoutX(x2/2);
                    MenuAnchorPane.setLayoutY(y2/2);
                }),
                new KeyFrame(new Duration(250), new KeyValue(sx, x), new KeyValue(sy, x), new KeyValue(sz, x)),
                new KeyFrame(Duration.millis(270), event -> {
                    playerWindow.btnResize.fire();
                })
        );
        timeline.play();
    }

    @FXML
    private void hoverW10(int i, Pane hoverW10Pane){
        if(!isBtnSelected[i]) {
            hoverW10Pane.setOpacity(1);
            hoverW10Pane.setLayoutY(buttons[i].getLayoutY() + 50 + 140);
            AnimationTimer an = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    Point p = MouseInfo.getPointerInfo().getLocation();
                    double x = p.getX() - ClientMain.primaryStage.getX() - hoverW10Pane.getPrefWidth() / 2;
                    hoverW10Pane.setLayoutX(x);
                }
            };
            an.start();
        }
        else {
            hoverW10Pane.setOpacity(0);
        }
    }

    @FXML
    private void hoverW10E(Pane hoverW10Pane){
        hoverW10Pane.setOpacity(0);
    }

    private void startPolygonWave() {
        AnimationTimer an = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (presentWindowIndex != 6 && presentWindowIndex!=0 && presentWindowIndex!=4) {
                    for (int i = 0; i < 30; i++) {
                        for (int j = hexCol; j < 40; j++) {
                            double dist;
                            Point p = MouseInfo.getPointerInfo().getLocation();
                            double mouseX = p.getX() - ClientMain.primaryStage.getX() + (31 - 22) * ScaleRatio;
                            double mouseY = p.getY() - ClientMain.primaryStage.getY() - (-31 - 15) * ScaleRatio;
                            double xdist = mouseX - polygonList.get(i).get(j).getLayoutX() * ScaleRatio;
                            double ydist = mouseY - polygonList.get(i).get(j).getLayoutY() * ScaleRatio;
                            dist = xdist * xdist + ydist * ydist;
                            if (dist > 10000) {
                                polygonList.get(i).get(j).setOpacity(.0);
                            } else if (dist > 9000) {
                                polygonList.get(i).get(j).setOpacity(.1);
                            } else if (dist > 8000) {
                                polygonList.get(i).get(j).setOpacity(.2);
                            } else if (dist > 7000) {
                                polygonList.get(i).get(j).setOpacity(.3);
                            } else if (dist > 6000) {
                                polygonList.get(i).get(j).setOpacity(.4);
                            } else if (dist > 5000) {
                                polygonList.get(i).get(j).setOpacity(.5);
                            } else if (dist > 4000) {
                                polygonList.get(i).get(j).setOpacity(.6);
                            } else if (dist > 3000) {
                                polygonList.get(i).get(j).setOpacity(.7);
                            } else if (dist > 2000) {
                                polygonList.get(i).get(j).setOpacity(.8);
                            } else if (dist > 1000) {
                                polygonList.get(i).get(j).setOpacity(.9);
                            } else polygonList.get(i).get(j).setOpacity(1);

                        }
                    }
                }
            }
        };
        an.start();
    }

    private void setPolygonPlane(Pane hexPlane, double id, ArrayList<ArrayList<Polygon>> polygonList){
        for(int i=0; i<30; i++){
            int xx = 31;
            int dd = 0;
            ArrayList<Polygon> row = new ArrayList<>();
            for(int j=0; j<40; j++){
                Polygon p = new Polygon();
                if(j%2==0) {
                    p = getPolygon(j * xx, i * 31, id);
                }
                else {
                    p = getPolygon(j*xx, i*31-15, id);
                }
                row.add(p);
                hexPlane.getChildren().addAll(p);
                dd++;
            }
            polygonList.add(row);
        }
    }

    private Polygon getPolygon(double lx, double ly, double id){
        double x1 = 15, x2 = 30, x3 = 45, x4 = 0;
        double y1 = 0, y2 = 15, y3 = 30;
        Polygon hex0 = new Polygon();

        hex0.getPoints().addAll(
                x1, y1,
                x2, y1,
                x3, y2,
                x2, y3,
                x1, y3,
                x4, y2
        );

        hex0.setLayoutY(ly);
        hex0.setLayoutX(lx);

        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);

        if(id==0) {
            hex0.setStyle("-fx-fill: transparent; -fx-stroke: rgb(" + r + "," + g + "," + b + "); -fx-stroke-width: 1");
        }
        else {
            hex0.setStyle("-fx-fill: black;");
        }
        hex0.setOpacity(id);

        return hex0;
    }

    public void SquadSelectionModeOn(){
        isSquadSelected = true;
        Pane pane = ClientMain.getWindow(12);
        borderMenu.setCenter(pane);
        for (int i = 5; i <= 7; i++) {
            buttons[i].setOpacity(0.001);
        }
        sideBarAnim(1);
        btnText[1].setText("Squad Selection");
        btnText[2].setText("Fixtures");
        btnText[3].setText("Statistics");
        btnText[4].setText("Rules");
        //buttons[1].fire();
    }

    public void SquadSelectionModeOff(){
        isSquadSelected = false;
        Pane pane = ClientMain.getWindow(1);
        borderMenu.setCenter(pane);
        for (int i = 5; i <= 7; i++) {
            buttons[i].setOpacity(1);
        }
        sideBarAnim(0);
        btnText[1].setText("My Team");
        btnText[2].setText("Points");
        btnText[3].setText("Transfers");
        btnText[4].setText("Leagues");
        //status.fire();
    }

    public void SingoutAction() {
        sample.Controllers.points.pointsDestructor();
        for(int i=0; i<=14; i++){
            signin.dataplayerDetails[i] = null;
            signin.playerDetails[i] = null;
        }

        sample.Controllers.points.btnClearPlayerButton.fire();
        sample.Controllers.transfers.btnClearPlayerButton.fire();
        squadSelection.btnClearPlayerButton.fire();

        try{
            FileOutputStream fout = new FileOutputStream("src/sample.Files/user.txt");
            String fileContent = "";
            fout.write(fileContent.getBytes());
            fout.close();
        }catch(Exception e){System.out.println(e);}
        SquadSelectionModeOff();

    }

    private void makeStageDragable(Pane topane){
        topane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        final BooleanProperty inDrag = new SimpleBooleanProperty(false);

        topane.setOnMouseDragged(event -> {

            ClientMain.primaryStage.setX(event.getScreenX()-xOffset);
            ClientMain.primaryStage.setY(event.getScreenY()-yOffset);
            ClientMain.primaryStage.setOpacity(1.0f);
            inDrag.set(true);

//            Rectangle rectangle = new javafx.scene.shape.Rectangle(
//                    topTransparentPane.getWidth(),
//                    topTransparentPane.getHeight(),
//                    Color.WHITESMOKE.deriveColor(
//                            0, 1, .2, 0.08
//                    )
//            );

            sideBorderPane.setOpacity(1);
            //topBorderPane.setStyle("-fx-border-color: rgba(255,255,255,.4); -fx-background-color: rgba(0,0,0,.2)");
            // settingsIcon.setOpacity(0);
            //topane.setOpacity(0);
            sideHide(0);
            sideUp.setOpacity(0);
            sideDown.setOpacity(0);
            blackPane.setOpacity(0);
            //topTransparentPane.getChildren().setAll(rectangle);
            sidePaneVbox.setOpacity(0);
            Rectangle rectangle1 = new javafx.scene.shape.Rectangle(
                    sideTransparentPane.getWidth(),
                    sideTransparentPane.getHeight(),
                    Color.WHITESMOKE.deriveColor(
                            0, 1, .2, 0.08
                    )
            );
            sideTransparentPane.getChildren().setAll(rectangle1);
        });
        topane.setOnDragDone(event -> {
            ClientMain.primaryStage.setOpacity(1.0f);
        });
        topane.setOnMouseReleased(event -> {
            if (inDrag.get()) {
                ClientMain.primaryStage.setOpacity(1.0f);
                Timeline timeline = new Timeline();
                timeline.getKeyFrames().addAll(
                        new KeyFrame(Duration.millis(50), event1 -> {
//                            background.setImage(copyBackground(topTransparentPane,0,0));
//                            background.setFitWidth(topTransparentPane.getWidth());
//                            background.setFitHeight(topTransparentPane.getHeight());
//                            topTransparentPane.getChildren().setAll(background);
                            background1.setImage(copyBackground(sideTransparentPane,0,0));
                            background1.setFitWidth(sideTransparentPane.getWidth());
                            background1.setFitHeight(sideTransparentPane.getHeight());
                            sideTransparentPane.getChildren().setAll(background1);
                        }),
                        new KeyFrame(Duration.millis(100), event1 -> {
                            sidePaneVbox.setOpacity(1);
                            //topane.setOpacity(1);
                            sideUp.setOpacity(.2);
                            sideDown.setOpacity(.2);
                            blackPane.setOpacity(1);
                            sideBorderPane.setOpacity(0);
                            sideHide(1);
                            //topBorderPane.setStyle("-fx-border-color: transparent; -fx-background-color: rgba(0,0,0,.2)");
                            //settingsIcon.setOpacity(1);
                        })
                );
                timeline.play();
            }
            inDrag.set(false);
        });

    }

    private void sideBarAnim(int idx){
        double h1 = 200;
        double y2 = 200;
        double h2 = 550;
        double x = 50;

        double ph1 = sideUp.getPrefHeight();
        double py2 = sideDown.getLayoutY();
        double ph2 = sideDown.getPrefHeight();

        Timeline timeline = new Timeline();
        DoubleProperty sh1 = sideUp.prefHeightProperty();
        DoubleProperty sy2 = sideDown.layoutYProperty();
        DoubleProperty sh2 = sideDown.prefHeightProperty();

        timeline.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(sh1, ph1), new KeyValue(sy2, py2), new KeyValue(sh2, ph2)),
                new KeyFrame(new Duration(100), new KeyValue(sh1, h1+x*idx), new KeyValue(sy2,
                        y2+x*idx), new KeyValue(sh2, h2-x*idx))
        );

        timeline.play();

    }

    public void func(int y, Pane pane){
        Timeline timeline = new Timeline();
        DoubleProperty ps1 = rectanimB.heightProperty();
        DoubleProperty ps2 = rectanimB.widthProperty();
        DoubleProperty ps3 = rectanimB.layoutYProperty();
        DoubleProperty ps4 = rectanimB.opacityProperty();
        DoubleProperty ps5 = rectanimB.layoutXProperty();
        DoubleProperty ps6 = pane.opacityProperty();
        int x=115;
        int lx = 0;
        int dx = 220;
        y = y+110;
        final double yy = y;
        if(btnMenuEp.getId()=="0") {
            lx=100;
            dx = 220;
        }
        else{
            lx = 40;
            dx = 100;
        }


        timeline.getKeyFrames().addAll(
//                new KeyFrame(new Duration(x+0),new KeyValue(ps2, 0), new KeyValue(ps1, 50), new KeyValue(ps3, y), new KeyValue(ps5, 0)),
//                new KeyFrame(new Duration(x+40), new KeyValue(ps2, 220), new KeyValue(ps1, 50), new KeyValue(ps3, y),
//                        new KeyValue(ps5, 0), new KeyValue(ps2, 220), new KeyValue(ps1, 50), new KeyValue(ps3, y), new KeyValue(ps5, 0)),
//                new KeyFrame(new Duration(x+80), new KeyValue(ps2, 0), new KeyValue(ps1, 50), new KeyValue(ps3, y),
//                        new KeyValue(ps5, 100),new KeyValue(ps2, 0),new KeyValue(ps1, 50), new KeyValue(ps3, y), new KeyValue(ps4, .3), new KeyValue(ps1, 80)),
//
//                new KeyFrame(new Duration(x+200), new KeyValue(ps6, 1),new KeyValue(ps2, 980),new KeyValue(ps1, 750), new KeyValue(ps3, 50),
//                        new KeyValue(ps4, 0), new KeyValue(ps1, 750)),
//
                new KeyFrame(Duration.millis(0), event -> {
                    rectanimB.setOpacity(1);
                    rectanimB.setLayoutY(yy);
                    rectanimB.setWidth(10);
                }),
                new KeyFrame(new Duration(0), new KeyValue(ps6, 0)),
                new KeyFrame(new Duration(x+100), new KeyValue(ps6, 0)),
                new KeyFrame(Duration.millis(x), event -> {
                    for(int i=0; i<30; i++){
                        for(int j=hexCol; j<40; j++){
                            polygonList2.get(i).get(j).setOpacity(.2);
                        }
                    }
                }),
                new KeyFrame(Duration.millis(x+160), event -> {
                    for(int i=0; i<30; i++){
                        for(int j=hexCol; j<40; j++){
                            polygonList2.get(i).get(j).setOpacity(0);
                        }
                    }
                }),

                new KeyFrame(new Duration(x), new KeyValue(ps5, lx), new KeyValue(ps2, 10), new KeyValue(ps4, 1)),
                new KeyFrame(new Duration(x+160), new KeyValue(ps5, 0), new KeyValue(ps2, dx),
                        new KeyValue(ps6, 1), new KeyValue(ps4, 0))
        );
        timeline.play();

    }

    private void themeChange(){
//        color = "";
//        try(FileInputStream fin = new FileInputStream("src/sample.Files/theme.txt")) {
//            int i=0;
//            while((i=fin.read())!=-1){
//                color+=(char)i;
//            }
//            fin.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("file not found");
//        } catch (IOException e) {
//            System.out.println(e);
//        }
//
//        if(color.length()==10) {
//            color = color.substring(2);
//        }
//        if(color.length()==0){
//            return;
//        }
//
//
//        if(color.equals("sample.FXMLS")){
//            ColorAnim = "000021";
//            sideDown.setStyle("-fx-background-color: linear-gradient(to bottom left, #000057, #000033);" +
//                    " -fx-background-radius: 0 40 0 0; " +
//                    "-fx-border-color: linear-gradient(to top right, rgba(0,0,0,0), rgba(255,255,255,.5)); " +
//                    "-fx-border-width: 2; -fx-border-radius: 0 40 0 0");
//            sideUp.setStyle("-fx-background-color: linear-gradient(to bottom right, #000033, #000057); " +
//                    "-fx-background-radius: 0 0 40 0; " +
//                    "-fx-border-color: linear-gradient(to bottom right, rgba(0,0,0,0), rgba(255,255,255,.5)); " +
//                    "-fx-border-width: 2; -fx-border-radius: 0 0 40 0");
//            topane1.setStyle("-fx-background-color: linear-gradient(to top left, #000057, #000033, #000057);" +
//                    " -fx-background-radius: 0 0 30 30;" +
//                    " -fx-border-color: linear-gradient(to right, rgba(255,255,255,.5),rgba(0,0,0,0), rgba(255,255,255,.5));" +
//                    " -fx-border-width: 2; -fx-border-radius: 0 0 30 30");
//
//        }
//        else if(color.equals("ThemeT3")){
//            ColorAnim = "2B2B2B";
//            sideDown.setStyle("-fx-background-color: gray; -fx-background-radius: 0 40 0 0");
//            sideUp.setStyle("-fx-background-color: gray; -fx-background-radius: 0 0 40 0");
//            topane1.setStyle("-fx-background-color: gray; -fx-background-radius: 0 0 30 30");
//
//        }
//        else if(color.equals("ThemeT4")){
//            ColorAnim = "202020";
//            sideDown.setStyle("-fx-background-color: linear-gradient(to bottom left, #1f1f1f, #000000); -fx-background-radius: 0 40 0 0");
//            sideUp.setStyle("-fx-background-color: linear-gradient(to bottom right, #000000, #1f1f1f); -fx-background-radius: 0 0 40 0");
//            topane1.setStyle("-fx-background-color: linear-gradient(to top left, #000000, #202020, #000000); -fx-background-radius: 0 0 30 30");
//        }
//        else if(color.equals("ThemeT5")){
//            ColorAnim = "6a6a6a";
//            sideDown.setStyle("-fx-background-color: linear-gradient(to bottom left, #f6f6f6, #d8d8d8); -fx-background-radius: 0 40 0 0");
//            sideUp.setStyle("-fx-background-color: linear-gradient(to bottom right, #dadada, #f6f6f6); -fx-background-radius: 0 0 40 0");
//            topane1.setStyle("-fx-background-color: linear-gradient(to top left, #f9f9f9, #dadada,#fefefe); -fx-background-radius: 0 0 30 30");
//        }
//        else if(color.equals("ThemeT6")){
//            ColorAnim = "FF1E50";
//            sideDown.setStyle("-fx-background-color: linear-gradient(to bottom left, #FF1E50, #2F2C44); -fx-background-radius: 0 40 0 0");
//            sideUp.setStyle("-fx-background-color: linear-gradient(to bottom right, #98122f, #FF1E50); -fx-background-radius: 0 0 40 0");
//            topane1.setStyle("-fx-background-color: linear-gradient(to top left, #98122f,#FF1E50,#98122f); -fx-background-radius: 0 0 30 30");
//
//        }
//        else {
//            ColorAnim = color;
//
//            sideDown.setStyle("-fx-background-color: #"+color+";" +
//                    " -fx-background-radius: 0 40 0 0; " +
//                    "-fx-border-color: linear-gradient(to right, rgba(0,0,0,0), rgba(255,255,255,.5)); " +
//                    "-fx-border-width: 2; -fx-border-radius: 0 40 0 0");
//            sideUp.setStyle("-fx-background-color: #"+color+";" +
//                    "-fx-background-radius: 0 0 40 0; " +
//                    "-fx-border-color: linear-gradient(to right, rgba(0,0,0,0), rgba(255,255,255,.5)); " +
//                    "-fx-border-width: 2; -fx-border-radius: 0 0 40 0");
//            topane1.setStyle("-fx-background-color: #"+color+";" +
//                    " -fx-background-radius: 0 0 30 30;" +
//                    " -fx-border-color: linear-gradient(to right, rgba(255,255,255,.5),rgba(0,0,0,0), rgba(255,255,255,.5));" +
//                    " -fx-border-width: 2; -fx-border-radius: 0 0 30 30");
//        }
    }

    private void setHexColor(int x){
        if(x==0){
            for(int i=0; i<30; i++){
                for(int j=4; j<40; j++){
                    polygonList1.get(i).get(j).setStyle("-fx-fill: black");
                }
            }
        }
        else {
            for(int i=0; i<x; i++){
                for(int j=4; j<40; j++){
                    polygonList1.get(i).get(j).setStyle("-fx-fill: rgba(255,255,255,.4)");
                }
            }
            for(int i=x; i<30; i++){
                for(int j=4; j<40; j++){
                    polygonList1.get(i).get(j).setStyle("-fx-fill: black");
                }
            }
        }
    }

    private void topTab(int b){
        float o = (float)0.3;
        float c = (float)0.0;
        if(b==0){
            topimage.setOpacity(0);
            //setHexColor(0);
        }
        if(b==1){
            topimage.setFitHeight(250);
            topimage.setOpacity(o);
            //setHexColor(9);
        }
        if(b==2){
            topimage.setFitHeight(200);
            topimage.setOpacity(o);
            //setHexColor(9);
        }
    }

    private void saveAnim(){
        savePane.toFront();
        savePane.setOpacity(1);
        Timeline timeline = new Timeline();
        DoubleProperty r = animCircle.rotateProperty();
        DoubleProperty r1 = animCircle.opacityProperty();
        DoubleProperty r2 = savePane.opacityProperty();
        DoubleProperty r3 = tickPane.opacityProperty();

        timeline.setCycleCount(1);
        timeline.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(r, 0), new KeyValue(r1, 1), new KeyValue(r3, 0)),
                new KeyFrame(new Duration(100), new KeyValue(r, -150)),
                new KeyFrame(new Duration(150), new KeyValue(r, -360)),
                new KeyFrame(new Duration(220), new KeyValue(r, -150)),
                new KeyFrame(new Duration(250), new KeyValue(r, -360), new KeyValue(r1, 1), new KeyValue(r3, 0), new KeyValue(r2, 1)),
                new KeyFrame(new Duration(270), new KeyValue(r1, 0), new KeyValue(r3, 0)),
                new KeyFrame(new Duration(280), new KeyValue(r3, 1), new KeyValue(r2, 1)),
                new KeyFrame(new Duration(500), new KeyValue(r2, 1)),
                new KeyFrame(new Duration(580), new KeyValue(r2, 0)),
                new KeyFrame(Duration.millis(585), event -> {
                    savePane.toBack();
                })
        );
        timeline.play();

    }

    @FXML
    private void handleMouseEvent(MouseEvent event) {
        if(isSquadSelected==false) {
            for (int i=0; i<=7; i++){
                if(event.getSource().equals(buttons[i])){
                    presentWindowIndex = i;
                    isBtnSelected[i] = true;
                    for(int j=0; j<=7; j++){
                        if(j!=i) isBtnSelected[j] = false;
                    }
                    HierText.setText("Home         "+btnString[i]);
                    if(i==0){
                        sample.Controllers.status.btnStatus.fire();
                    }
                    Pane pane = ClientMain.getWindow(i+1);
                    borderMenu.setCenter(pane);
                    sideBarAnim(i);
                    buttonControll(i);
                    func(animMap[i], pane);
                    if(i==0 || i==5 || i==6){
                        topTab(0);
                    }
                    else if(i==1 || i==4){
                        topTab(2);
                    }
                    else topTab(1);
                    if(i==0 || i==4 || i==6) setPolygonlist1Opacity(0);
                    else setPolygonlist1Opacity(.2);

                    if(i==2) {
                        sample.Controllers.points.pointsTeamId = signin.teamid;
                        sample.Controllers.points.playerPointsCalc(signin.Gameweek-1, signin.teamid);
                        sample.Controllers.points.btnAutoPoints.fire();
                    }

                }
            }
        }
        else{
            int[] ii = {1, 12, 6, 7, 8};
            for(int i=0; i<=4; i++){
                if(event.getSource().equals(buttons[i])) {
                    Pane pane = ClientMain.getWindow(ii[i]);
                    borderMenu.setCenter(pane);
                    sideBarAnim(i);
                    buttonControll(i);
                    func(animMap[i], pane);
                }
            }

        }

        if (event.getSource() == btnClose) {
            System.exit(0);
        }

        if(event.getSource().equals(userBox)) {
            if (SettingsPane.getOpacity() == 1) {
                SettingsPane.setOpacity(0);
                SettingsPane.toBack();

            } else {
                SettingsPane.toFront();
                SettingsPane.setOpacity(1);
            }
        }
        if(event.getSource().equals(btnThemes)){
            ClientMain.addPane(9);
        }
        if(event.getSource().equals(btnSignOut)){
            SingoutAction();
            ClientMain.removePane(11);
            ClientMain.addPane(0);
        }
        if(event.getSource().equals(logout)){
            SingoutAction();
            ClientMain.removePane(11);
            ClientMain.addPane(0);
        }
    }

    private void buttonControll(int idx){
        for(int i=0; i<8; i++){
            if(i!=idx) {
                buttons[i].getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
                buttons[i].getStyleClass().remove("button15");
                buttons[i].getStyleClass().add("button8");
            }
            if(i==4){
                sample.Controllers.leagues.btnRefresh.fire();
            }
        }
        buttons[idx].getStylesheets().add(getClass().getResource("/sample/Styles/design.css").toExternalForm());
        buttons[idx].getStyleClass().remove("button8");
        buttons[idx].getStyleClass().add("button15");


        if(btnMenuEp.getId()=="1"){
            buttons[0].setStyle("-fx-font-size: .1");
            buttons[4].setStyle("-fx-font-size: .1");
        }
    }

    @FXML
    private void MenuExpand(){
        if(btnMenuEp.getId()=="0"){
            sideBorderPane.setPrefWidth(100);
            sideBarBorder.setPrefWidth(160);
            sidePane.setPrefWidth(100);
            sidePaneVbox.setPrefWidth(100);
            sideDown.setPrefWidth(100);
            sideUp.setPrefWidth(100);
            blackPane.setPrefWidth(100);
            blackPane1.setPrefWidth(1100);
            blackPane1.setLayoutX(100);
            fantasy.setOpacity(0);
            username.setLayoutX(30);
            userBox.setLayoutX(22);
            logout.setText("");
            logout.setPrefWidth(25);

            topMenu.setLayoutX(490);
            topBlackTab.setPrefWidth(1100);
            topBlackTab.setLayoutX(100);

            for(int i=0; i<=7; i++){
                btnText[i].setOpacity(0);
                btnText[i].setText("");
            }
            btnMenuEp.setId("1");
            topimage.setFitWidth(1100);
            topimage.setLayoutX(100);
            hexCol = 4;
            backimg.setLayoutX(100);
            backimg.setFitWidth(1100);
            sideTransparentPane.setPrefWidth(300);
            topane1.setLayoutX(100);
            topane1.setPrefWidth(1100);
            blackPane11.setPrefWidth(1100);
            blackPane11.setLayoutX(100);

            if(presentWindowIndex!=0 && presentWindowIndex!=4 && presentWindowIndex!=6) {
                setPolygonlist1Opacity(0);
                setPolygonlist1Opacity(0.2);
            }


        }
        else {
            blackPane11.setLayoutX(220);
            blackPane11.setPrefWidth(980);
            topane1.setPrefWidth(980);
            topane1.setLayoutX(220);
            sideBorderPane.setPrefWidth(220);
            userBox.setLayoutX(78);
            username.setLayoutX(83);
            logout.setText("    Log Out");
            logout.setPrefWidth(100);

            topBlackTab.setLayoutX(220);
            topBlackTab.setPrefWidth(980);
            topMenu.setLayoutX(550);

            blackPane.setPrefWidth(220);
            blackPane1.setPrefWidth(980);
            blackPane1.setLayoutX(220);
            sideBarBorder.setPrefWidth(220);
            sidePane.setPrefWidth(220);
            sidePaneVbox.setPrefWidth(220);
            sideDown.setPrefWidth(220);
            sideUp.setPrefWidth(220);
            for(int i=0; i<=7; i++){
                btnText[i].setOpacity(1);
                btnText[i].setText(btnString[i]);
            }
            btnMenuEp.setId("0");
            topimage.setLayoutX(220);
            topimage.setFitWidth(980);
            hexCol=8;
            backimg.setLayoutX(220);
            backimg.setFitWidth(980);
            sideTransparentPane.setPrefWidth(300);

            sidePaneVbox.setOpacity(0);
            sideUp.setOpacity(0);
            sideDown.setOpacity(0);
            Rectangle rectangle1 = new javafx.scene.shape.Rectangle(
                    sideTransparentPane.getWidth(),
                    sideTransparentPane.getHeight(),
                    Color.WHITESMOKE.deriveColor(
                            0, 1, .2, 0.08
                    )
            );
            sideTransparentPane.getChildren().setAll(rectangle1);


            sideHide(0);
            Timeline t = new Timeline();
            t.getKeyFrames().addAll(
                    new KeyFrame(Duration.millis(0), event -> {
                        blackPane.setOpacity(0);
                    }),
                    new KeyFrame(Duration.millis(50), event -> {
                        background1.setImage(copyBackground(sideTransparentPane, 0, 0));
                        sideTransparentPane.getChildren().setAll(background1);
                    }),
                    new KeyFrame(Duration.millis(70), event -> {
                        sidePaneVbox.setOpacity(1);
                        sideUp.setOpacity(.2);
                        sideDown.setOpacity(.2);
                        blackPane.setOpacity(1);
                        sideHide(1);
                    })
            );
            t.play();


            for(int i=0; i<30; i++){
                for(int j=0; j<hexCol; j++){
                    polygonList1.get(i).get(j).setOpacity(0);
                    polygonList.get(i).get(j).setOpacity(0);
                }
            }

            if(presentWindowIndex!=0 && presentWindowIndex!=4 && presentWindowIndex!=6) {
                setPolygonlist1Opacity(0.2);
            }
        }
    }

    public static Image copyBackground(Pane stage, int x, int y) {
        final int X = (int) (stage.getLayoutX()*ScaleRatio)+(int) ClientMain.primaryStage.getX()+(int)(x*ScaleRatio);
        final int Y = (int) (stage.getLayoutY()*ScaleRatio)+(int) ClientMain.primaryStage.getY()+(int)(y*ScaleRatio);
        double xx = stage.getWidth()-stage.getWidth()*ScaleRatio;
        double yy = stage.getHeight()-stage.getHeight()*ScaleRatio;
        final int W = (int) stage.getWidth()-(int)xx+1;
        final int H = (int) stage.getHeight()-(int)yy+1;

        try {
            java.awt.Robot robot = new java.awt.Robot();
            java.awt.image.BufferedImage image = robot.createScreenCapture(new java.awt.Rectangle(X, Y, W, H));
            return SwingFXUtils.toFXImage(image, null);
        } catch (java.awt.AWTException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void sideHide(int i){
        userBox.setOpacity(i);
        username.setOpacity(i);
        logout.setOpacity(i);
        fantasyIcon.setOpacity(i);
        if(btnMenuEp.getId().equals("0"))
            fantasy.setOpacity(i);
    }

}
