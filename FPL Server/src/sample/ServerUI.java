package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ServerUI {
    @FXML
    Button btnGWUpdate;
    public static int GAMEWEEK = 3;
    public static double progress = 0;
    public static double unit = 0;
    public static double unitTank, progressTank;
    static int idxx=0;
    public static boolean isExpand = true;
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML Text GWbntText, activeUsers, activePctg;
    @FXML Arc progressBar, activeBar, arc1, arc2, arc3, arc4;
    @FXML Circle cir1, cir2, cir11, cir21, cir111, cir211;
    @FXML Pane tank, gwPane, serverPane, leftPane, hexPlane;
    @FXML CubicCurve wave1, wave2;
    @FXML Button btnExpand, close, mini;
    @FXML AnchorPane mainPane;
    @FXML HBox topBar;
    public static int totalUsers = 0;

    public static Button btnActiveUsers = new Button();
    public static Button btnActiveBar = new Button();

    public void initialize() throws SQLException, ClassNotFoundException {
        GAMEWEEK = Integer.parseInt(FileToString("GW"));
        GWbntText.setText("GW"+GAMEWEEK);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                activeUsers.setText(String.valueOf(ServerMain.activeUsersCount));
            }
        };
        btnActiveUsers.setOnAction(event);
        totalUsers = DatabaseData.getTotalUsers();
        activeBar.setLength(0);
        activePctg.setText("0%");

        GameweekUpdate.Average_points();

        EventHandler<ActionEvent> event2 = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    totalUsers = DatabaseData.getTotalUsers();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                int pctg = ServerMain.activeUsersCount*100/totalUsers;
                activePctg.setText(pctg+"%");
                double d = ((double)pctg/100.0)*360;
                activeBar.setLength(d);
            }
        };
        btnActiveBar.setOnAction(event2);
        arcAnim();
        tank.setOpacity(0);
        wave1.setOpacity(0);
        wave2.setOpacity(0);
        makeStageDragable();

    }
    @FXML
    private void updateGW() throws SQLException, ClassNotFoundException {
        GAMEWEEK++;
        stringToFile("GW", String.valueOf(GAMEWEEK));
        List teamids = DatabaseData.getAllTeamids(GAMEWEEK-1);

        new Thread() {
            public void run()
            {
                System.out.println("started inside thread..");
                idxx=0;
                int Size = teamids.size();
                unit = 360.0/Size;
                unitTank = 680.0/Size;
                progress = 0;
                progressTank = 0;
                progressBar.setLength(progress);
                tank.setOpacity(1);
                wave1.setOpacity(1);
                wave2.setOpacity(1);

                for (Object teamid:teamids){
                    if((int)teamid==0) continue;
                    try {
                        GameweekUpdate.updateNewGameWeek((int)teamid, GAMEWEEK);
                        GameweekUpdate.pointsUpdate((int)teamid, GAMEWEEK);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }

                    try {
                        this.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(()->{
                                DoubleProperty d = progressBar.lengthProperty();
                                DoubleProperty h = tank.prefHeightProperty();
                                DoubleProperty yt = tank.layoutYProperty();
                                DoubleProperty yw1 = wave1.layoutYProperty();
                                DoubleProperty yw2 = wave2.layoutYProperty();
                                double ht = 700;

                                Timeline t = new Timeline();
                                t.getKeyFrames().addAll(
                                        new KeyFrame(new Duration(0), new KeyValue(d, progress), new KeyValue(h, progressTank),
                                                new KeyValue(yt, ht-progressTank), new KeyValue(yw1, ht-progressTank),
                                                new KeyValue(yw2, ht-progressTank)),
                                        new KeyFrame(new Duration(300), new KeyValue(d, progress+unit),  new KeyValue(h, progressTank+unitTank),
                                                new KeyValue(yt, ht-progressTank-unitTank), new KeyValue(yw1, ht-progressTank-unitTank),
                                                new KeyValue(yw2, ht-progressTank-unitTank))

                                );
                                t.play();
                                GWbntText.setText(String.valueOf(idxx));
                            }
                    );
                    progress+=unit;
                    progressTank+=unitTank;
                    idxx++;

                }

                //other universal updates:
                try {
                    GameweekUpdate.Avg_Point_League();
                    GameweekUpdate.Average_points();
                    GameweekUpdate.H2H_Point_update();
                    GameweekUpdate.AvgUserTeamUpdate();
                    GameweekUpdate.User_team_value_update();
                    GameweekUpdate.H2H_fixture_generate();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }


                tank.setOpacity(0);
                wave2.setOpacity(0);
                wave1.setOpacity(0);

                Timeline timeline = new Timeline();
                DoubleProperty op = GWbntText.opacityProperty();

                timeline.getKeyFrames().addAll(
                        new KeyFrame(new Duration(0), new KeyValue(op, 0)),
                        new KeyFrame(Duration.millis(300), event -> {
                            GWbntText.setText("Done");
                        }),
                        new KeyFrame(new Duration(500), new KeyValue(op,1)),
                        new KeyFrame(new Duration(800), new KeyValue(op,0)),
                        new KeyFrame(Duration.millis(800), event -> {
                            GWbntText.setText("GW"+GAMEWEEK);
                        }),
                        new KeyFrame(new Duration(1200), new KeyValue(op, 1)),
                        new KeyFrame(Duration.millis(1200), event -> {
                            progressBar.setLength(0);
                        })
                );
                timeline.play();
            }
        }.start();

        tank.setLayoutY(660);
        tank.setPrefHeight(40);
        wave1.setLayoutY(660);
        wave2.setLayoutY(660);
    }

    public static String FileToString(String filename){
        String string = "";
        try(FileInputStream fin = new FileInputStream("D:\\STUDY_MATERIALS\\L2-T2\\DatabaseProject\\FPL Project Javafx\\"+filename+".txt")) {
            int i=0;
            while((i=fin.read())!=-1){
                string+=(char)i;
            }
            fin.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println(e);
        }
        return string;
    }

    public static void stringToFile(String filename, String str){
        try{
            FileOutputStream fout = new FileOutputStream("D:\\STUDY_MATERIALS\\L2-T2\\DatabaseProject\\FPL Project Javafx\\"+filename+".txt");
            String fileContent = str;
            fout.write(fileContent.getBytes());
            fout.close();
        }catch(Exception e){System.out.println(e);}
    }

    private void arcAnim(){
        DoubleProperty d1 = arc1.lengthProperty();
        DoubleProperty a1 = arc1.startAngleProperty();
        DoubleProperty a2 = arc2.startAngleProperty();
        DoubleProperty a3 = arc3.startAngleProperty();
        DoubleProperty d2 = arc2.lengthProperty();
        DoubleProperty d3 = arc3.lengthProperty();
        DoubleProperty d4 = arc4.lengthProperty();
        DoubleProperty a4 = arc4.startAngleProperty();

        DoubleProperty c1 = cir1.rotateProperty();
        DoubleProperty c2 = cir2.rotateProperty();
        DoubleProperty c11 = cir11.rotateProperty();
        DoubleProperty c21 = cir21.rotateProperty();
        DoubleProperty c111 = cir111.rotateProperty();
        DoubleProperty c211 = cir211.rotateProperty();

        Timeline t = new Timeline();
        t.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(d1, 0), new KeyValue(a1, 0)),
                new KeyFrame(new Duration(4000), new KeyValue(d1, 360), new KeyValue(a1, 180)),
                new KeyFrame(new Duration(8000), new KeyValue(d1, 0), new KeyValue(a1, 360))
        );
        t.setCycleCount(Animation.INDEFINITE);
        t.play();

        Timeline t2 = new Timeline();
        t2.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(d2, 0), new KeyValue(a2, 0)),
                new KeyFrame(new Duration(2000), new KeyValue(d2, 360), new KeyValue(a2, 180)),
                new KeyFrame(new Duration(4000), new KeyValue(d2, 0), new KeyValue(a2, 360))
        );
        t2.setCycleCount(Animation.INDEFINITE);
        t2.play();

        Timeline t3 = new Timeline();
        t3.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(d3, 0), new KeyValue(a3, 0)),
                new KeyFrame(new Duration(1000), new KeyValue(d3, 360), new KeyValue(a3, 180)),
                new KeyFrame(new Duration(2000), new KeyValue(d3, 0), new KeyValue(a3, 360))
        );
        t3.setCycleCount(Animation.INDEFINITE);
        t3.play();

        Timeline t4 = new Timeline();
        t4.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(d4, 0), new KeyValue(a4, 0)),
                //new KeyFrame(new Duration(2000), new KeyValue(d4, 360), new KeyValue(a4, 180)),
                new KeyFrame(new Duration(4000), new KeyValue(d4, 360), new KeyValue(a4, 360))
        );
        t4.setCycleCount(Animation.INDEFINITE);
        t4.play();

        Timeline t5 = new Timeline();
        t5.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(c1, 0), new KeyValue(c2, 0),
                        new KeyValue(c11, 0), new KeyValue(c21, 0),
                        new KeyValue(c111, 0), new KeyValue(c211, 0)),
                new KeyFrame(new Duration(2000), new KeyValue(c1, 90), new KeyValue(c2, 90),
                        new KeyValue(c11, 90), new KeyValue(c21, 90),
                        new KeyValue(c111, 90), new KeyValue(c211, 90))
        );
        t5.setAutoReverse(true);
        t5.setCycleCount(Animation.INDEFINITE);
        t5.play();

        DoubleProperty w1 = wave1.controlY1Property();
        DoubleProperty w2 = wave1.controlY2Property();
        DoubleProperty w11 = wave2.controlY1Property();
        DoubleProperty w21 = wave2.controlY2Property();

        int x = 65;
        int time = 500;


        Timeline t6 = new Timeline();
        t6.getKeyFrames().addAll(
                new KeyFrame(new Duration(0), new KeyValue(w1, x), new KeyValue(w2, -x)),
                new KeyFrame(new Duration(time), new KeyValue(w1, -x), new KeyValue(w2, x)),
                new KeyFrame(new Duration(0), new KeyValue(w11, x), new KeyValue(w21, -x)),
                new KeyFrame(new Duration(time), new KeyValue(w11, -x), new KeyValue(w21, x))
        );
        t6.setCycleCount(Animation.INDEFINITE);
        t6.setAutoReverse(true);
        t6.play();
    }

    @FXML
    private void ExpandWindow(){
        if(isExpand){
            gwPane.setOpacity(0);
            serverPane.setLayoutX(70);

            mainPane.setPrefWidth(565);
            serverPane.setStyle("-fx-background-color: rgba(0,0,0,.01)");
            ServerMain.primaryStage.setWidth(565);
            isExpand = false;
        }
        else {
            gwPane.setOpacity(1);
            serverPane.setLayoutX(713);

            mainPane.setPrefWidth(1208);
            serverPane.setStyle("-fx-background-color: rgba(0,0,0,.2)");
            ServerMain.primaryStage.setWidth(1208);
            isExpand = true;
        }
    }

    private void makeStageDragable(){
        leftPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        final BooleanProperty inDrag = new SimpleBooleanProperty(false);

        leftPane.setOnMouseDragged(event -> {
            ServerMain.primaryStage.setX(event.getScreenX()-xOffset);
            ServerMain.primaryStage.setY(event.getScreenY()-yOffset);
            ServerMain.primaryStage.setOpacity(1.0f);
            inDrag.set(true);
        });
        leftPane.setOnDragDone(event -> {
            ServerMain.primaryStage.setOpacity(1.0f);
        });
        leftPane.setOnMouseReleased(event -> {
            if (inDrag.get()) {
                ServerMain.primaryStage.setOpacity(1.0f);
            }
            inDrag.set(false);
        });

    }

    @FXML
    private void handleMouseEvent(MouseEvent event) {
        if (event.getSource() == close) {
            System.exit(0);
        }
        if(event.getSource()==mini){
            ServerMain.primaryStage.setIconified(true);
        }
    }
}
