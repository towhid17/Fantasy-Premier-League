package sample.Controllers;

import sample.Clients.ClientMain;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.io.FileOutputStream;
import java.io.IOException;

public class settings {
    @FXML
    Button btnhome, btnt1, btncpset, btnt3, btnt4, btnt5, btnt6;
    @FXML
    ColorPicker colorpicker;
    @FXML
    Circle btnClose;
    public static int themeno = 1;
    @FXML
    private void handleMouseEvent(MouseEvent event) throws IOException {
        if(event.getSource() == btnClose){
            System.exit(0);
        }

        if(event.getSource().equals(btnhome))
        {
            ClientMain.removePane(9);
        }
        if(event.getSource().equals(btnt1)){

            fileWrite("sample/FXMLS");
            Menu.btntheme.fire();

        }
        if(event.getSource().equals(btnt3)){
            fileWrite("ThemeT3");
            Menu.btntheme.fire();
        }
        if(event.getSource().equals(btnt4)){
            fileWrite("ThemeT4");
            Menu.btntheme.fire();
        }
        if(event.getSource().equals(btncpset)){
            Color color = colorpicker.getValue();
            fileWrite(String.valueOf(color));
            Menu.btntheme.fire();
        }
        if(event.getSource().equals(btnt5)){
            fileWrite("ThemeT5");
            Menu.btntheme.fire();
        }
        if(event.getSource().equals(btnt6)){
            fileWrite("ThemeT6");
            Menu.btntheme.fire();
        }

    }

    private void fileWrite(String str){
        try{
            FileOutputStream fout = new FileOutputStream("src/sample.Files/theme.txt");
            String fileContent = str;
            fout.write(fileContent.getBytes());
            fout.close();
        }catch(Exception e){System.out.println(e);}
    }
}


