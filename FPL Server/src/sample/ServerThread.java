package sample;

import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ServerThread implements Runnable {

    private Socket userSocket;
    private Thread t;
    String Command;
    public static float rm;
    private static boolean exit = false;

    ServerThread(Socket userSocket) {
        this.userSocket = userSocket;
        exit = false;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {

        try {
            networkObjStream userStream = new networkObjStream(userSocket);

            while (Command == null) {
                Command = (String) userStream.read();

                System.out.println(Command);
                String[] splitCommand = Command.split("#");

                if(splitCommand[0].equals("signin")) {
                    String returnString = DatabaseData.checkUserAccount(splitCommand[1], splitCommand[2]);
                    userStream.write(returnString);
                    Command = null;
                }
                if(splitCommand[0].equals("getTeamid")) {
                    int r = DatabaseData.getTeamid(splitCommand[1]);
                    userStream.write(r);
                    Command = null;
                }
                if(splitCommand[0].equals("checkUsername")) {
                    boolean flag = DatabaseData.checkUsername(splitCommand[1], splitCommand[2]);
                    if (flag == false) {
                        userStream.write("false");
                    } else userStream.write("true");
                    Command = null;
                }
                if(splitCommand[0].equals("createUsers")) {
                    DatabaseData.createUsers(splitCommand[1], splitCommand[2], splitCommand[3], splitCommand[4], splitCommand[5]);
                    Command = null;
                }
                if(splitCommand[0].equals("getFixture")){
                    List<String> list = DatabaseData.Fixture(Integer.parseInt(splitCommand[1]));
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("getStatistics")){
                    List<String> list = DatabaseData.Statistics(Integer.parseInt(splitCommand[1]), splitCommand[2], Integer.parseInt(splitCommand[3]));
                    userStream.write(list);
                    Command = null;
                    System.out.println("statistics send");
                }
                if(splitCommand[0].equals("playerHistoryTopInfo")){
                    String str = DatabaseData.playerHistoryTopInfo(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    userStream.write(str);
                    Command = null;
                }
                if(splitCommand[0].equals("playerHistory")){
                    List<String> list = DatabaseData.playerHistory(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("playerFixture")){
                    List<String> list = DatabaseData.playerFixture(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("saveTeamChange")) {
                    userStream.write("send");
                    List list = new ArrayList();
                    list = null;
                    while (list == null) {
                        list = (List) userStream.read();
                    }
                    DatabaseData.saveTeamChange(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]), list);
                    Command = null;
                }
                if(splitCommand[0].equals("getCaptain")) {
                    int id = DatabaseData.getCaptain(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    userStream.write(id);
                    Command = null;
                }
                if(splitCommand[0].equals("setCaptain")) {
                    DatabaseData.setCaptain(Integer.parseInt(splitCommand[3]), Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    Command = null;
                }
                if(splitCommand[0].equals("getChips")){
                    int p = DatabaseData.getChips(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    userStream.write(p);
                    Command = null;
                }
                if(splitCommand[0].equals("benchBoost")){
                    DatabaseData.benchBoost(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    Command = null;
                }
                if(splitCommand[0].equals("tripleCaptain")){
                    DatabaseData.tripleCaptain(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    Command = null;
                }
                if(splitCommand[0].equals("freeHit")){
                    DatabaseData.freeHit(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    Command = null;
                }
                if(splitCommand[0].equals("isPlayedChips")){
                    boolean b = DatabaseData.isPlayedChips(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]),
                            Integer.parseInt(splitCommand[3]));
                    if(b) userStream.write("true");
                    else userStream.write("false");
                    Command = null;
                }
                if(splitCommand[0].equals("SquadSelection")){
                    boolean b = DatabaseData.SquadSelection(splitCommand[1]);
                    if(b) userStream.write("true");
                    else userStream.write("false");
                    Command = null;
                }
                if(splitCommand[0].equals("EnterSquad")){
                    String user = splitCommand[1];
                    int gw = Integer.parseInt(splitCommand[2]);
                    double rm = Double.parseDouble(splitCommand[3]);
                    String user_team_name = splitCommand[4];
                    List<Integer> list = new ArrayList<>();
                    for(int i=0; i<=14; i++){
                        list.add(Integer.parseInt(splitCommand[i+5]));
                    }
                    int tid = DatabaseData.EnterSquad(user, gw, list, rm, user_team_name);
                    userStream.write(String.valueOf(tid));
                    Command = null;
                }
                if(splitCommand[0].equals("AutoPick")){
                    rm = Float.parseFloat(splitCommand[1]);
                    userStream.write("found");
                    List list = new ArrayList<>();
                    list = null;
                    while (list==null){
                        list = (List) userStream.read();
                    }
                    System.out.println("List get");
                    PlayerDetails[] playerDetails1 = new PlayerDetails[15];

                    int k=0;
                    for (Object obj: list){
                        String stra = String.valueOf(obj);
                        System.out.println(stra);
                        String[] strings = stra.split("#");
                        PlayerDetails p = new PlayerDetails();
                        p.setId(Integer.parseInt(strings[0]));
                        p.setString(strings[1]);
                        p.setCost(Float.parseFloat(strings[2]));
                        p.setClub(strings[3]);
                        playerDetails1[k++] = p;
                    }
                    PlayerDetails[] playerDetails2 = DatabaseData.setAutoPick(playerDetails1);
                    System.out.println("func return");
                    List<String> list1 = new ArrayList<>();
                    for(int i=0; i<=14; i++){
                        list1.add(playerDetails2[i].getId()+"#"+playerDetails2[i].getString()+"#"+playerDetails2[i].getCost()+"#"+playerDetails2[i].getClub());
                        System.out.println(playerDetails2[i].getString());
                    }
                    userStream.write(list1);
                    System.out.println("list write");
                    String str = null;
                    while (str==null){
                        str = (String) userStream.read();
                    }
                    userStream.write(String.valueOf(rm));
                    System.out.println("rm write");
                    Command = null;
                }
                if(splitCommand[0].equals("getPlayerData")) {
                    List<String> results = DatabaseData.getPlayerData(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    if(results.size()==0){
                        results.add("-1");
                    }
                    userStream.write(results);
                    Command = null;
                }
                if(splitCommand[0].equals("getBechPriority")) {
                    List r = DatabaseData.getBechPriority(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    userStream.write(r);
                    Command = null;
                }

                //////////////////////////////////////////////////

                if(splitCommand[0].equals("makeTransfers")) {
                    userStream.write("send");
                    List list = new ArrayList();
                    list = null;
                    while (list == null) {
                        list = (List) userStream.read();
                    }
                    DatabaseData.makeTransfers(Float.parseFloat(splitCommand[3]), Integer.parseInt(splitCommand[1]),
                            Integer.parseInt(splitCommand[2]), Integer.parseInt(splitCommand[4]), list);
                    Command = null;
                }
                if(splitCommand[0].equals("DeleteLeague")){
                    DatabaseData.DeleteLeague(splitCommand[1]);
                    Command = null;
                }
                if(splitCommand[0].equals("createClassicLeagues")){
                    DatabaseData.createClassicLeagues(splitCommand[1], Integer.parseInt(splitCommand[2]), Integer.parseInt(splitCommand[3]));
                    Command = null;
                }
                if(splitCommand[0].equals("joinClassicLeagues")){
                    boolean b = DatabaseData.joinClassicLeagues(splitCommand[1], Integer.parseInt(splitCommand[2]), Integer.parseInt(splitCommand[3]));
                    if(b)
                        userStream.write("true");
                    else userStream.write("false");
                    Command = null;
                }
                if(splitCommand[0].equals("leagueTableLoad")){
                    List<String> list = DatabaseData.leaguesTableLoad(Integer.parseInt(splitCommand[1]), 0);
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("getGWRank")){
                    int r = DatabaseData.getGWRank(Integer.parseInt(splitCommand[1]), splitCommand[2], Integer.parseInt(splitCommand[3]));
                    List<Integer> list = new ArrayList<>();
                    list.add(r);
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("getLeagueStartGW")){
                    int r = DatabaseData.getLeagueStartGW(splitCommand[1]);
                    userStream.write(r);
                    Command = null;
                }
                if(splitCommand[0].equals("futureLeagueTeam")){
                    List<String> list = DatabaseData.futureLeagueTeam(splitCommand[1]);
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("teamPreGWRank")){
                    List<String> list = DatabaseData.teamPreGWRank(splitCommand[1], Integer.parseInt(splitCommand[2]));
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("presentLeagueTeam")){
                    List<String> list = DatabaseData.presentLeagueTeam(splitCommand[1], Integer.parseInt(splitCommand[2]));
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("getLeagueAdmin")){
                    int admin = DatabaseData.getLeagueAdmin(splitCommand[1]);
                    userStream.write(admin);
                    Command = null;
                }
                if(splitCommand[0].equals("getGWpoints")){
                    int p = DatabaseData.getGWpoints(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    userStream.write(p);
                    Command = null;
                }
                if(splitCommand[0].equals("getTotalpoints")){
                    int p = DatabaseData.getTotalpoints(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    userStream.write(p);
                    Command = null;
                }
                if(splitCommand[0].equals("getHighestPoints")){
                    int p = DatabaseData.getHighestPoints(Integer.parseInt(splitCommand[1]));
                    userStream.write(p);
                    Command = null;
                }
                if(splitCommand[0].equals("setWildCard")){
                    DatabaseData.setWildCard(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    Command = null;
                }
                if(splitCommand[0].equals("setTransfersINandOUT")){
                    DatabaseData.setTransfersINandOUT(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]), Integer.parseInt(splitCommand[3]));
                    Command = null;
                }
                if(splitCommand[0].equals("getTransfersInfo")){
                    String str = DatabaseData.getTransfersInfo(Integer.parseInt(splitCommand[1]), Integer.parseInt(splitCommand[2]));
                    userStream.write(str);
                    Command = null;
                }

                ////////////////////////////////////////////////////////

                if(splitCommand[0].equals("globaltableload")){
                    int teamid = Integer.parseInt(splitCommand[1]);
                    String str = DatabaseData.globaltableload(teamid);
                    userStream.write(str);
                    Command = null;
                }
                if(splitCommand[0].equals("globalLeagueTeams")){
                    List<String> list = DatabaseData.globalLeagueTeams();
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("DreamTeamLoad")){
                    List<String> list = DatabaseData.DreamTeamLoad();
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("TopGWPlayer")){
                    List<String> list = DatabaseData.TopGWPlayer();
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("topTransfersIn")){
                    List<String> list = DatabaseData.topTransfersIn();
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("topTransfersOut")){
                    List<String> list = DatabaseData.topTransfersIn();
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("bestLeagues")){
                    List<String> list = DatabaseData.bestLeagues();
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("mostValuedTeam")){
                    List<String> list = DatabaseData.mostValuedTeam();
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("getStatusInfo")){
                    String str = DatabaseData.getStatusInfo();
                    userStream.write(str);
                    Command = null;
                }
                if(splitCommand[0].equals("CreatePublicClassicLeague")){
                    int id = Integer.parseInt(splitCommand[1]);
                    DatabaseData.CreatePublicClassicLeague(id);
                    Command = null;
                }
                if(splitCommand[0].equals("publicTableLoad")){
                    int id = Integer.parseInt(splitCommand[1]);
                    List<String> list = DatabaseData.leaguesTableLoad(id, 1);
                    userStream.write(list);
                    Command = null;
                }

                //////////////////////////////////////////////

                if(splitCommand[0].equals("createH2HLeagues")){
                    String name = splitCommand[1];
                    int id = Integer.parseInt(splitCommand[2]);
                    String gw = splitCommand[3];
                    DatabaseData.createH2HLeagues(name,id, gw);
                    Command = null;
                }
                if(splitCommand[0].equals("joinH2HLeagues")){
                    boolean b = DatabaseData.joinH2HLeagues(splitCommand[1], Integer.parseInt(splitCommand[2]), Integer.parseInt(splitCommand[3]));
                    if(b)
                        userStream.write("true");
                    else userStream.write("false");
                    Command = null;
                }
                if(splitCommand[0].equals("H2HLeagueLoad")){
                    List<String> list = DatabaseData.H2HLeagueLoad(Integer.parseInt(splitCommand[1]));
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("H2HLeagueTeamLoad")){
                    String code = splitCommand[1];
                    List<String> list = DatabaseData.H2HLeagueTeamLoad(code);
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("H2HFixtureLoad")){
                    String code = splitCommand[1];
                    int gw = Integer.parseInt(splitCommand[2]);
                    List<String> list = DatabaseData.H2HFixtureLoad(code, gw);
                    userStream.write(list);
                    Command = null;
                }
                if(splitCommand[0].equals("getH2HStartGw")){
                    String code = splitCommand[1];
                    int i = DatabaseData.getH2HStartGw(code);
                    userStream.write(i);
                    Command = null;
                }







            }
        } catch (Exception ex) {
            System.out.println("client Disconnected");
            ServerMain.activeUsersCount--;
            ServerUI.btnActiveUsers.fire();
            ServerUI.btnActiveBar.fire();
        }
    }
}


