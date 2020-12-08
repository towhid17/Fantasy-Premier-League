package sample;

import com.kieferlam.javafxblur.Blur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerMain extends Application {
    private static final String connection = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static Stage primaryStage;
    static AnchorPane root;
    public static int activeUsersCount = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Fantasy Server");
        primaryStage.setScene(new Scene(root, 1200, 720, Color.TRANSPARENT));
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();
        Blur.applyBlur(primaryStage);
        new StartServer();
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection conn = DriverManager.getConnection(connection, "fpltest", "fpltest");
        return conn;
    }

    public static void main(String[] args) {
        Blur.loadBlurLibrary();
        launch(args);
    }
}

class StartServer implements Runnable{
    Thread t;
    StartServer() {
        System.out.println("start");
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        System.out.println("start");
        ServerSocket welcomeSocket = null;
        try {
            welcomeSocket = new ServerSocket(33333);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket userSocket = null;
        while (true) {
            try {
                userSocket = welcomeSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ServerThread wt = new ServerThread(userSocket);
            ServerMain.activeUsersCount++;
            ServerUI.btnActiveUsers.fire();
            ServerUI.btnActiveBar.fire();
        }
    }
}