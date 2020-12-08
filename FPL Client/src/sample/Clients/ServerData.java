package sample.Clients;

import sample.Controllers.transfers;
import sample.Objects.PlayerDetails;
import sample.Server.networkObjStream;

import java.util.ArrayList;
import java.util.List;

import static sample.Controllers.signin.*;
import static sample.Controllers.signin.dataplayerDetails;

public class ServerData {
    public static networkObjStream nc;
    static List rs;

    public static void manualInitialize(){
        nc = new networkObjStream("127.0.0.1", 33333);
    }

    public static int getTeamid(String USER) {
        nc.write("getTeamid#"+USER);
        int id=0;
        while (id==0){
            id = (int)nc.read();
        }
        return id;
    }

    public static boolean checkUserAccount(String user, String pass) {
        nc.write("signin#"+user+"#"+pass);
        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }
        if(str.equals("true"))
            return true;
        else
            return false;
    }

    public static boolean checkUsername(String user, String email) {
        nc.write("checkUsername#"+user+"#"+email);
        String str = "";
        while (str==""){
            str = String.valueOf(nc.read());
        }
        if(str.equals("true"))
            return true;
        else
            return false;
    }

    public static void createUsers(String username, String firstname, String lastname, String email, String password){
        nc.write("createUsers#"+username+"#"+firstname+"#"+lastname+"#"+email+"#"+password);
    }


    public static List<String> getFixture(int Gameweek){
        nc.write("getFixture#"+Gameweek);
        while (rs==null){
            rs = (List)nc.read();
        }

        List<String> list = rs;
        rs = null;

        return list;
    }

    public static List<String> getStatistics(int position, String team, int idx){
        nc.write("getStatistics#"+position+"#"+team+"#"+idx);
        while (rs==null){
            rs = (List) nc.read();
        }

        List<String> ans = rs;
        rs = null;

        System.out.println("statistics got");
        return ans;
    }


    public static boolean SquadSelection(String username) {
        nc.write("SquadSelection#"+username);
        String str = null;
        while (str==null){
            str = (String) nc.read();
        }
        if(str.equals("true")) return true;
        else return false;
    }

    public static String playerHistoryTopInfo(int Gameweek, int id){
        nc.write("playerHistoryTopInfo#"+Gameweek+"#"+id);
        String str = null;
        while (str==null){
            str = (String)nc.read();
        }
        return str;
    }

    public static List playerHistory(int id, int teamid){
        nc.write("playerHistory#"+id+"#"+teamid);
        while (rs==null){
            rs = (List) nc.read();
        }
        List list = rs;
        rs = null;

        return list;
    }

    public static List playerFixture(int id, int Gameweek){
        nc.write("playerFixture#"+id+"#"+Gameweek);
        while (rs==null){
            rs = (List) nc.read();
        }
        List list = rs;
        rs = null;

        return list;
    }

    public static int EnterSquad(String user, int Gameweek, String str, double rm, String user_team_name){
        nc.write("EnterSquad#"+user+"#"+Gameweek+"#"+rm+"#"+user_team_name+"#"+str);
        String id = null;
        while (id==null){
            id = (String) nc.read();
        }

        System.out.println("Teamid:  "+id);

        return Integer.parseInt(id);
    }

    public static PlayerDetails[] AutoPick(PlayerDetails[] playerDetails, float remainingMoney){
        nc.write("AutoPick#"+remainingMoney);
        String str = null;
        while (str==null){
            str = (String) nc.read();
        }
        System.out.println("str get");
        List<String> list = new ArrayList<>();
        for(int i=0; i<=14; i++){
            list.add(""+playerDetails[i].getId()+"#"+playerDetails[i].getString()+"#"+playerDetails[i].getCost()+"#"+playerDetails[i].getClub()+"#");
            System.out.println(playerDetails[i].getId());
        }
        nc.write(list);
        System.out.println("list write");
        while (rs == null){
            rs = (List) nc.read();
        }
        System.out.println("list get");
        nc.write("RemainingMoney");
        System.out.println("tr send");
        str = null;
        while (str==null){
            str = (String) nc.read();
        }
        transfers.RemainingMoney = Float.parseFloat(str);

        System.out.println("tm get");

        PlayerDetails[] playerDetails1 = new PlayerDetails[15];
        int k=0;
        for (Object obj: rs){
            String stra = String.valueOf(obj);
            String[] strings = stra.split("#");
            PlayerDetails p = new PlayerDetails();
            p.setId(Integer.parseInt(strings[0]));
            p.setString(strings[1]);
            p.setCost(Float.parseFloat(strings[2]));
            p.setClub(strings[3]);
            playerDetails1[k++] = p;
        }

        rs = null;

        System.out.println("return");
        return playerDetails1;
    }

    public static int getCaptain(int Gameweek, int teamid) {
        System.out.println("getcptain write");
        nc.write("getCaptain#"+teamid+"#"+Gameweek);
        System.out.println("write done");
        int id = 0;
        while (id==0){
            id = (int)nc.read();
        }
        System.out.println("got cap");
        return id;
    }

    public static void setCaptain(int id){
        nc.write("setCaptain#"+teamid+"#"+Gameweek+"#"+id);
    }

    public static void benchBoost(int Gameweek, int teamid) {
        nc.write("benchBoost#"+Gameweek+"#"+teamid);
    }

    public static void tripleCaptain(int Gameweek, int teamid) {
        nc.write("tripleCaptain#"+Gameweek+"#"+teamid);
    }

    public static void freeHit(int Gameweek, int teamid) {
        nc.write("freeHit#"+Gameweek+"#"+teamid);
    }

    public static boolean isPlayedChips(int i, int teamid, int Gameweek){
        nc.write("isPlayedChips#"+i+"#"+teamid+"#"+Gameweek);
        String str = null;
        while (str==null){
            str = (String) nc.read();
        }
        if(str.equals("true")) return true;
        else return false;
    }

    public static int getChips(int Gameweek, int teamid)  {
        int p = -1;
        nc.write("getChips#"+Gameweek+"#"+teamid);
        while (p==-1){
            p = (int)nc.read();
        }
        return p;
    }

    public static void saveTeamChange() {
        nc.write("saveTeamChange#"+teamid+"#"+Gameweek);
        String sr = "";
        while (sr==""){
            sr = String.valueOf(nc.read());
        }
        int pr = 0;
        if(sr.equals("send")){
            List list = new ArrayList();
            for(int i=0; i<=14; i++){
                if(dataplayerDetails[i].getIsOnField()==1) {
                    String str = dataplayerDetails[i].getId() +"#"+ dataplayerDetails[i].getIsOnField() +"#"+ "-1";
                    list.add(str);
                }
                else {
                    for(int j=0; j<=3; j++){
                        if(BenchPlayerPriority[j]==dataplayerDetails[i].getId()){
                            pr = j;
                        }
                    }
                    String str = dataplayerDetails[i].getId() +"#"+ dataplayerDetails[i].getIsOnField() +"#"+ pr+"";
                    list.add(str);
                }

            }
            nc.write(list);
        }

    }

    public static PlayerDetails[] getPlayerData(int teamid, int Gameweek) {
        PlayerDetails[] dataplayerDetails = new PlayerDetails[15];
        for (int i = 0; i <= 14; i++) {
            dataplayerDetails[i] = new PlayerDetails();
        }
        int mf = 7;
        int df = 2;
        int srt = 12;

        nc.write("getPlayerData#"+teamid+"#"+Gameweek);
        while (rs==null) {
            rs = (List)nc.read();
        }

        int ind = 0;

        for(Object obj:rs){
            String str1 = String.valueOf(obj);
            if(str1.equals("-1")){
                break;
            }
            String[] arrOfStr = str1.split("#");
            int i = Integer.parseInt(arrOfStr[3]);
            String n = arrOfStr[1];
            int c  = Integer.parseInt(arrOfStr[2]);
            int p  = Integer.parseInt(arrOfStr[0]);
            String t = arrOfStr[4];
            int points = Integer.parseInt(arrOfStr[5]);
            String pp = "";
            System.out.println(n);

            if(ind<11) {
                if (p == 1) {
                    pp = "GK";
                    dataplayerDetails[0].setPos(pp);
                    dataplayerDetails[0].setString(n);
                    dataplayerDetails[0].setCost(c);
                    dataplayerDetails[0].setId(i);
                    dataplayerDetails[0].setClub(t);
                    dataplayerDetails[0].setIsOnField(1);
                    dataplayerDetails[0].setPoints(points);
                } else if (p == 2) {
                    pp = "DEF";
                    dataplayerDetails[df].setPos(pp);
                    dataplayerDetails[df].setString(n);
                    dataplayerDetails[df].setCost(c);
                    dataplayerDetails[df].setId(i);
                    dataplayerDetails[df].setClub(t);
                    dataplayerDetails[df].setIsOnField(1);
                    dataplayerDetails[df].setPoints(points);
                    df = df + 1;
                } else if (p == 3) {
                    pp = "MF";
                    dataplayerDetails[mf].setPos(pp);
                    dataplayerDetails[mf].setString(n);
                    dataplayerDetails[mf].setCost(c);
                    dataplayerDetails[mf].setId(i);
                    dataplayerDetails[mf].setClub(t);
                    dataplayerDetails[mf].setIsOnField(1);
                    dataplayerDetails[mf].setPoints(points);

                    mf = mf + 1;
                } else if (p == 4) {
                    pp = "ST";
                    dataplayerDetails[srt].setPos(pp);
                    dataplayerDetails[srt].setString(n);
                    dataplayerDetails[srt].setCost(c);
                    dataplayerDetails[srt].setId(i);
                    dataplayerDetails[srt].setClub(t);
                    dataplayerDetails[srt].setIsOnField(1);
                    dataplayerDetails[srt].setPoints(points);
                    srt = srt + 1;
                }
            }
            else {
                if (p == 1) {
                    pp = "GK";
                    dataplayerDetails[1].setPos(pp);
                    dataplayerDetails[1].setString(n);
                    dataplayerDetails[1].setCost(c);
                    dataplayerDetails[1].setId(i);
                    dataplayerDetails[1].setClub(t);
                    dataplayerDetails[1].setIsOnField(0);
                    dataplayerDetails[1].setPoints(points);
                } else if (p == 2) {
                    pp = "DEF";
                    dataplayerDetails[df].setPos(pp);
                    dataplayerDetails[df].setString(n);
                    dataplayerDetails[df].setCost(c);
                    dataplayerDetails[df].setId(i);
                    dataplayerDetails[df].setClub(t);
                    dataplayerDetails[df].setIsOnField(0);
                    dataplayerDetails[df].setPoints(points);
                    df = df + 1;
                } else if (p == 3) {
                    pp = "MF";
                    dataplayerDetails[mf].setPos(pp);
                    dataplayerDetails[mf].setString(n);
                    dataplayerDetails[mf].setCost(c);
                    dataplayerDetails[mf].setId(i);
                    dataplayerDetails[mf].setClub(t);
                    dataplayerDetails[mf].setIsOnField(0);
                    dataplayerDetails[mf].setPoints(points);
                    mf = mf + 1;
                } else if (p == 4) {
                    pp = "ST";
                    dataplayerDetails[srt].setPos(pp);
                    dataplayerDetails[srt].setString(n);
                    dataplayerDetails[srt].setCost(c);
                    dataplayerDetails[srt].setId(i);
                    dataplayerDetails[srt].setClub(t);
                    dataplayerDetails[srt].setIsOnField(0);
                    dataplayerDetails[srt].setPoints(points);
                    srt = srt + 1;
                }
            }
            ind++;

        }
        rs = null;
        return dataplayerDetails;
    }

    public static int[] getBechPriority(int teamid, int Gameweek) {
        int[] BenchPlayerPriority = new int[4];
        nc.write("getBechPriority#"+teamid+"#"+Gameweek);
        while (rs == null){
            rs = (List)nc.read();
        }
        int idx = 0;
        for(Object obj:rs){
            BenchPlayerPriority[idx] = (int)obj;
            idx++;
        }
        rs = null;
        return BenchPlayerPriority;
    }

    public static void makeTransfers(float RemainingMoney){
        int tr = 0;

        for(int i=0; i<=14; i++){
            if(dataplayerDetails[i].getId()!=playerDetails[i].getId()){
                tr++;
                if(dataplayerDetails[i].getIsOnField()==0){
                    for(int j=0; j<=3; j++){
                        if(BenchPlayerPriority[j]==dataplayerDetails[i].getId()){
                            BenchPlayerPriority[j] = playerDetails[i].getId();
                            BenchPlayerPrioritycheck[j] = playerDetails[i].getId();
                        }
                    }
                }
            }
        }

        nc.write("makeTransfers#"+teamid+"#"+Gameweek+"#"+RemainingMoney+"#"+tr);
        String sr = "";
        while (sr==""){
            sr = String.valueOf(nc.read());
        }
        int pr = 0;
        if(sr.equals("send")){
            List list = new ArrayList();
            for(int i=0; i<=14; i++){
                if(playerDetails[i].getIsOnField()==1) {
                    String str = playerDetails[i].getId() +"#"+ playerDetails[i].getIsOnField() +"#"+ "-1";
                    list.add(str);
                }
                else {
                    for(int j=0; j<=3; j++){
                        if(BenchPlayerPriority[j]==playerDetails[i].getId()){
                            pr = j;
                        }
                    }
                    String str = playerDetails[i].getId() +"#"+ playerDetails[i].getIsOnField() +"#"+ pr+"";
                    list.add(str);
                }

            }
            nc.write(list);
        }

    }

    public static void DeleteLeague(String code){
        nc.write("DeleteLeague#"+code);
    }

    public static void createClassicLeagues(String LeagueName, int teamid, String Gw){
        nc.write("createClassicLeagues#"+LeagueName+"#"+teamid+"#"+Gw);
    }

    public static boolean joinClassicLeagues(String code, int teamid, int gw){
        nc.write("joinClassicLeagues#"+code+"#"+teamid+"#"+gw);
        String str = "";
        while (str.length()==0){
            str = (String) nc.read();
        }
        if(str.equals("true")) return true;
        else return false;
    }

    public static List<String> leagueTableLoad(int teamid){
        nc.write("leagueTableLoad#"+teamid);
        while (rs==null){
            rs = (List)nc.read();
        }
        List<String> list = rs;
        rs = null;
        return list;
    }

    public static int getGWRank(int GW, String code) {
        nc.write("getGWRank#"+GW+"#"+code+"#"+teamid);
        while (rs==null){
            rs = (List) nc.read();
        }
        int r = (int) rs.get(0);
        rs = null;
        return r;

    }

    public static int getLeagueStartGW(String code){
        nc.write("getLeagueStartGW#"+code);
        int gw = 0;
        while (gw == 0){
            gw = (int)nc.read();
        }
        return gw;
    }

    public static List futureLeagueTeam(String code){
        nc.write("futureLeagueTeam#"+code);
        while (rs==null){
            rs = (List)nc.read();
        }
        List list = rs;
        rs = null;
        return list;
    }

    public static List teamPreGWRank(String code, int Gameweek){
        nc.write("teamPreGWRank#"+code+"#"+Gameweek);
        while (rs==null){
            rs = (List)nc.read();
        }
        List list = rs;
        rs = null;
        return list;
    }

    public static List presentLeagueTeam(String code, int Gameweek){
        nc.write("presentLeagueTeam#"+code+"#"+Gameweek);
        while (rs==null){
            rs = (List)nc.read();
        }
        List list = rs;
        rs = null;
        return list;
    }

    public static int getLeagueAdmin(String code) {
        int admin = 0;
        nc.write("getLeagueAdmin#"+code);
        while (admin == 0){
            admin = (int)nc.read();
        }
        return admin;
    }

    public static int getGWpoints(int gw, int teamid) {
        int p = -100000;
        nc.write("getGWpoints#"+gw+"#"+teamid);
        while (p==-100000){
            p = (int)nc.read();
        }
        return p;
    }

    public static int getTotalpoints(int Gameweek, int teamid) {
        int p = -100000;
        nc.write("getTotalpoints#"+Gameweek+"#"+teamid);
        while (p==-100000){
            p = (int)nc.read();
        }
        return p;
    }

    public static int getHighestPoints(int Gameweek)  {
        int p = -100000;
        nc.write("getHighestPoints#"+Gameweek);
        while (p==-100000){
            p = (int)nc.read();
        }
        return p;
    }

    public static void setWildCard(int Gameweek, int teamid){
        nc.write("setWildCard#"+Gameweek+"#"+teamid);
    }

    public static void setTransfersINandOUT(int idin, int idout, int Gameweek){
        nc.write("setTransfersINandOUT#"+idin+"#"+idout+"#"+Gameweek);
    }

    public static String getTransfersInfo(int Gameweek, int teamid){
        nc.write("getTransfersInfo#"+Gameweek+"#"+teamid);
        String str = null;
        while (str==null){
            str = (String)nc.read();
        }
        return str;
    }

    public static String globaltableload(int teamid){
        nc.write("globaltableload#"+teamid);
        String ans = null;
        while (ans==null){
            ans = (String)nc.read();
        }
        return ans;
    }

    public static List<String> globalLeagueTeams(){
        nc.write("globalLeagueTeams");
        rs = null;
        while (rs==null){
            rs = (List)nc.read();
        }
        List<String> list = rs;
        rs = null;
        return list;
    }

    public static List<String> DreamTeamLoad() {
        nc.write("DreamTeamLoad");
        rs = null;
        while (rs==null){
            rs = (List)nc.read();
        }
        List<String> list = rs;
        rs = null;
        return list;
    }

    public static List<String> TopGWPlayer() {
        nc.write("TopGWPlayer");
        rs = null;
        while (rs==null){
            rs = (List)nc.read();
        }
        List<String> list = rs;
        rs = null;
        return list;
    }

    public static List<String> topTransfersIn() {
        nc.write("topTransfersIn");
        rs = null;
        while (rs==null){
            rs = (List)nc.read();
        }
        List<String> list = rs;
        rs = null;
        return list;
    }

    public static List<String> topTransfersOut() {
        nc.write("topTransfersOut");
        rs = null;
        while (rs==null){
            rs = (List)nc.read();
        }
        List<String> list = rs;
        rs = null;
        return list;
    }

    public static List<String> bestLeagues() {
        nc.write("bestLeagues");
        rs = null;
        while (rs==null){
            rs = (List)nc.read();
        }
        List<String> list = rs;
        rs = null;
        return list;
    }

    public static List<String> mostValuedTeam() {
        nc.write("mostValuedTeam");
        rs = null;
        while (rs==null){
            rs = (List)nc.read();
        }
        List<String> list = rs;
        rs = null;
        return list;
    }

    public static String getStatusInfo() {
        nc.write("getStatusInfo");
        String str = null;
        while (str==null){
            str = (String)nc.read();
        }
        return str;
    }

    public static void CreatePublicClassicLeague(int teamid){
        nc.write("CreatePublicClassicLeague#"+teamid);
    }

    public static List<String> publicTableLoad(int teamid){
        nc.write("publicTableLoad#"+teamid);
        while (rs==null){
            rs = (List)nc.read();
        }
        List<String> list = rs;
        rs = null;
        return list;
    }

    ///////////////////////////////

    public static void createH2HLeagues(String name, int teamid, String gw){
        if(gw==null || gw==""){
            gw = String.valueOf(Gameweek);
        }
        nc.write("createH2HLeagues#"+name+"#"+teamid+"#"+gw);
    }

    public static boolean joinH2HLeagues(String code, int teamid, int gw){
        nc.write("joinH2HLeagues#"+code+"#"+teamid+"#"+gw);
        String str = "";
        while (str.length()==0){
            str = (String) nc.read();
        }
        if(str.equals("true")) return true;
        else return false;
    }

    public static List<String> H2HLeagueLoad(int teamid){
        nc.write("H2HLeagueLoad#"+teamid);
        rs=null;
        while (rs==null){
            rs = (List)nc.read();
        }
        List<String> list = rs;
        rs = null;
        return list;
    }

    public static List<String> H2HLeagueTeamLoad(String code){
        nc.write("H2HLeagueTeamLoad#"+code);
        rs = null;
        while (rs==null){
            rs = (List)nc.read();
        }
        List<String> list = rs;
        rs = null;
        return list;
    }

    public static int getH2HStartGw(String code){
        nc.write("getH2HStartGw#"+code);
        int i=0;
        while (i==0){
            i = (int)nc.read();
        }
        return i;
    }

    public static List<String> H2HFixtureLoad(String code, int gw){
        nc.write("H2HFixtureLoad#"+code+"#"+gw);
        rs = null;
        while (rs==null){
            rs = (List)nc.read();
        }
        List<String> list = rs;
        rs = null;
        return list;
    }



}
