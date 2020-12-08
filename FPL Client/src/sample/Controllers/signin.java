package sample.Controllers;

import animatefx.animation.ZoomIn;
import animatefx.animation.ZoomOut;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import sample.Clients.ClientMain;
import sample.Clients.ServerData;
import sample.Objects.PlayerDetails;

public class signin{
    @FXML TextField username;
    @FXML PasswordField password;
    @FXML PasswordField repassword;
    @FXML TextField email;
    @FXML Circle btnClose;
    @FXML ImageView btnBack;
    @FXML Button btnSignup;
    @FXML Button btnGetStarted;
    @FXML Text signupPromptText;
    @FXML Text signinPromptText;
    @FXML TextField firstname;
    @FXML TextField lastname;
    @FXML TextField signinUsername;
    @FXML PasswordField signinPassword;
    @FXML Button btnSignin;
    public static Button btnAutoSignin = new Button();
    public static int Gameweek = 0;
    public static int teamid;
    public static PlayerDetails[] playerDetails = new PlayerDetails[15];
    public static PlayerDetails[] dataplayerDetails = new PlayerDetails[15];
    public static String USER;
    @FXML Pane pnlSignin;
    @FXML Pane pnlSignup;
    @FXML AnchorPane ancRoot;
    public static int[] BenchPlayerPriority = new int[4];
    public static int[] BenchPlayerPrioritycheck = new int[4];



    ////////////////////////METHODS///////////////////////////////////////

    public void initialize(){
        String gw = ClientMain.FileToString("GW");
        signin.Gameweek = Integer.parseInt(gw);
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                Menu.btnUsernameSet.fire();
                if(ServerData.SquadSelection(USER)){
                    Menu.btnSquadSelectionCheck.fire();
                    sample.Controllers.squadSelection.btnsetinitLabel.fire();
                }
                else{
                    teamid=ServerData.getTeamid(USER);
                    points.pointsTeamId = teamid;
                    myteam.btnChips.fire();
                    sample.Controllers.transfers.btnChipsWildcard.fire();
                    sample.Controllers.transfers.btngetInfo.fire();
                    teamFromDatabase();
                    myteam.btnAutoMyteam.fire();
                    sample.Controllers.transfers.btnAutoTransfers.fire();
                    points.pointsContructor();
                    sample.Controllers.leagues.btnleaguesLoad.fire();
                    points.playerPointsCalc(Gameweek-1, teamid);
                    points.pointsGW = Gameweek-1;
                    points.btnAutoPoints.fire();
                    points.btnsetInfo.fire();
                }
            }
        };
        btnAutoSignin.setOnAction(event);
    }

    @FXML
    private void signinAction()  {
        if(signinUsername.getText().equals("")) {
            signinPromptText.setText("Username?");
            return;
        }
        else if(signinPassword.getText().equals("")){
            signinPromptText.setText("Password?");
            return;
        }
        if (ServerData.checkUserAccount(signinUsername.getText(), signinPassword.getText())) {
            System.out.println("user found!");
            USER = signinUsername.getText();
            ClientMain.removePane(0);
            ClientMain.addPane(11);
            Menu.btnUsernameSet.fire();
            Menu.btnInitialBlurSet.fire();
            if(ServerData.SquadSelection(USER)){
                Menu.btnSquadSelectionCheck.fire();
                playerDetailsConstructor();
                squadSelection.playerArrayInitialize();
                squadSelection.btnsetinitLabel.fire();
            }
            else {
                teamid = ServerData.getTeamid(USER);
                points.pointsTeamId = teamid;
                myteam.btnChips.fire();
                transfers.btnChipsWildcard.fire();
                sample.Controllers.transfers.btngetInfo.fire();
                teamFromDatabase();
                myteam.btnAutoMyteam.fire();
                sample.Controllers.transfers.btnAutoTransfers.fire();
                points.pointsContructor();
                points.playerPointsCalc(Gameweek-1, teamid);
                points.btnAutoPoints.fire();
                points.btnsetInfo.fire();
                sample.Controllers.leagues.btnleaguesLoad.fire();
            }
//            try{
//                FileOutputStream fout = new FileOutputStream("src/sample.Files/user.txt");
//                String fileContent = USER;
//                fout.write(fileContent.getBytes());
//                fout.close();
//            }catch(Exception e){System.out.println(e);}
        }
        signinPromptText.setText("Username or Password didn't match!");
    }

    @FXML
    private void getStartedAction(){
        String pt = "";
        if(username.getText().equals("")) {
            pt += "Username";
        }
        if(firstname.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "First name ";
        }
        if(lastname.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Last name ";
        }
        if(email.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Email ";
        }
        if(password.getText().equals("")) {
            if(pt.length()>0)
                pt+=", ";
            pt += "Password ";
        }
        int l = pt.length();
        if(l>0){
            pt += ("Required!");
            signupPromptText.setText(pt);
            return;
        }

        if (!password.getText().equals(repassword.getText())) {
            signupPromptText.setText("Password doesn't match! Try Again");
            return;
        }

        if(!ServerData.checkUsername(username.getText(), email.getText())){
            signupPromptText.setText("Username or Email already Exist");
            return;
        }

        USER = username.getText();
        ServerData.createUsers(username.getText(), firstname.getText(), lastname.getText(), email.getText(), password.getText());

        ClientMain.removePane(0);
        ClientMain.addPane(11);
        Menu.btnUsernameSet.fire();
        Menu.btnSquadSelectionCheck.fire();
        sample.Controllers.squadSelection.btnsetinitLabel.fire();
        playerDetailsConstructor();
        squadSelection.playerArrayInitialize();

    }

    public static void teamFromDatabase() {

        for(int i=0; i<=14; i++){
            //plb[i] = false;
            playerDetails[i] = new PlayerDetails();
            dataplayerDetails[i] = new PlayerDetails();
        }

        dataplayerDetails = ServerData.getPlayerData(teamid, Gameweek);
        BenchPlayerPriority = ServerData.getBechPriority(teamid, Gameweek);

        for(int i=0; i<=3; i++) {
            BenchPlayerPrioritycheck[i] = BenchPlayerPriority[i];
        }

        for(int i=0; i<=14; i++){
            playerDetails[i].setString(dataplayerDetails[i].getString());
            playerDetails[i].setClub(dataplayerDetails[i].getClub());
            playerDetails[i].setId(dataplayerDetails[i].getId());
            playerDetails[i].setCost(dataplayerDetails[i].getCost());
            playerDetails[i].setPos(dataplayerDetails[i].getPos());
            playerDetails[i].setIsOnField(dataplayerDetails[i].getIsOnField());
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event){
        if (event.getSource().equals(btnSignup)){
            new ZoomIn(pnlSignup).play();
            pnlSignup.toFront();
        }
    }

    @FXML
    private void handleMouseEvent(MouseEvent event){
        if(event.getSource() == btnClose){
            new ZoomOut(ancRoot).play();
            System.exit(0);

        }
        if(event.getSource().equals(btnBack))
        {
            new ZoomIn(pnlSignin).play();
            pnlSignin.toFront();
        }


    }

    private void playerDetailsConstructor(){
        for(int i=0; i<=14; i++){
            dataplayerDetails[i] = new PlayerDetails();
            playerDetails[i] = new PlayerDetails();
        }
    }

}

