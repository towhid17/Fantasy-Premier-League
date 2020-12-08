package sample.Clients;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Controllers.Menu;
import sample.Controllers.signin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientMain extends Application {
    private static final String connection = "jdbc:oracle:thin:@Towhids-PC:1521:orcl";
    public static AnchorPane root;
    public static Stage primaryStage = null;
    public static int themeNo = 1;
    public static Pane window;
    String User = "";
    static List<GridPane> grid = new ArrayList<>();
    private static int current_index = 0;
    public static Scene scene;


    @Override
    public void start(Stage primaryStage) throws Exception{
        ServerData.manualInitialize();
        User = FileToString("user");

        loadFxml();

        this.primaryStage = primaryStage;
        primaryStage.setResizable(true);
        scene = new Scene(root, 1200, 800, Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Fantasy");
        primaryStage.setScene(scene);
        primaryStage.show();

        if(User.length()!=0){
            signin.USER = User;
            signin.btnAutoSignin.fire();
            current_index = 1;
            root.getChildren().add(grid.get(11));
        }
        else
            root.getChildren().add(grid.get(0));

        primaryStage.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(!newValue) {
                    // if windows doesn't focused
                    Menu.btnInActiveWindow.fire();
                }
                else {
                    Menu.btnActiveWindow.fire();
                }
            }
        });

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

    public static Pane getWindow(int idx){
        window = grid.get(idx);
        return window;
    }

    public static void setPane(int idx) {
        root.getChildren().remove(grid.get(current_index));
        root.getChildren().add(grid.get(idx));
        current_index = idx;
    }

    public static void addPane(int idx){
        root.getChildren().add(grid.get(idx));
    }

    public static void  removePane(int idx){
        root.getChildren().remove(grid.get(idx));
    }

    public static void setTheme(int idx) throws IOException {
        themeNo = idx;
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(connection, "fpltest", "fpltest");
        return conn;
    }

    public static void loadFxml() throws IOException {
        root = FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/transparent.fxml"));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/signin.fxml")));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/status.fxml")));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/myteam.fxml")));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/points.fxml")));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/transfers.fxml")));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/leagues.fxml")));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/fixtures.fxml")));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/statistics.fxml")));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/rules.fxml")));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/Settings.fxml")));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/playerWindow.fxml")));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/Menu.fxml")));
        grid.add(FXMLLoader.load(ClientMain.class.getResource("/sample/FXMLS/squadSelection.fxml")));
    }

    public static void removeFxml(){
        root.getChildren().removeAll();
        grid.clear();
    }


    public static void main(String[] args){

        launch(args);

    }



}
