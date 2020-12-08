package sample;

import javafx.scene.layout.Pane;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class DatabaseData {
    public static String getMd5(String input) {
        try {

            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
            catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
    }

    public static List getResults(ResultSet rs) throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();
        List results = new ArrayList();
        while (rs.next()) {
            String str = "";
            for (int i = 0; i < columnCount; i++) {
                str+=rs.getObject(i + 1)+"#";
            }
            results.add(str);
        }
        return results;
    }

    public static String checkUserAccount(String user, String pass) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        String p = getMd5(pass);
        String qry1 = "select USER_ID from users where USERNAME='"+user+"' and PASSWORD='"+p+"'";
        st.execute(qry1);

        int uid = 0;
        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            uid = rs.getInt(1);
        }
        st.close();
        conn.close();
        if(uid==0) return "false";
        else return "true";
    }

    public static int getTeamid(String USER) throws SQLException, ClassNotFoundException {
        int teamid = 0;
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String qry = "select USER_TEAM_ID from users where USERNAME='"+ USER+"'";
        st.execute(qry);

        ResultSet rs = st.getResultSet();
        while (rs.next())
            teamid = rs.getInt(1);
        System.out.println(teamid);
        rs.close();
        st.close();
        conn.close();
        return teamid;
    }

    public static boolean checkUsername(String user, String email) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        String qry1 = "select USERNAME, EMAIL from users";
        st.execute(qry1);
        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            if(user.equals(rs.getString(1)) || email.equals(rs.getString(2))){
                return false;
            }
        }
        rs.close();
        st.close();
        conn.close();
        return true;
    }

    public static void createUsers(String username, String firstname, String lastname, String email, String password)
            throws SQLException, ClassNotFoundException
    {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        int uid = 0;
        st.execute("select max(USER_ID) from USERS");
        ResultSet rs = st.getResultSet();
        while (rs.next())
            uid = rs.getInt(1);
        uid++;
        String pass = getMd5(password);
        String qry2 = "INSERT INTO USERS (USER_ID, username, first_name, last_name ,email, password ) VALUES('" + uid + "','"+ username + "','" + firstname + "','" + lastname + "','" + email + "','" + pass + "')";
        st.execute(qry2);
        st.close();
        conn.close();
    }

    public static List getAllTeamids(int GW) throws SQLException, ClassNotFoundException {
        List list = new ArrayList();

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("select USER_TEAM_ID from USER_TEAM where GAMEWEEK="+GW);
        ResultSet rs = st.getResultSet();
        while (rs.next()){
            list.add(rs.getInt(1));
        }
        st.close();
        conn.close();
        return list;
    }

    public static int getTotalUsers() throws SQLException, ClassNotFoundException {
        int r = 0;
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("SELECT COUNT(USERS.USERNAME)\n" +
                "FROM USERS");
        ResultSet rs = st.getResultSet();
        while (rs.next()){
            r = rs.getInt(1);
        }
        st.close();
        conn.close();
        return r; }

    public static List Fixture(int GW) throws ClassNotFoundException, SQLException {
        List<String> list = new ArrayList<>();
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        String qry = "SELECT HOME_TEAM as htn, HOME_TEAM_SCORE, AWAY_TEAM_SCORE, TEAM_NAME AS atn, htav, TEAM_ABBRV as atav\n" +
                "FROM\n" +
                "(\n" +
                "\tSELECT * FROM\n" +
                "\t(\n" +
                "\t\tSELECT TEAM_NAME AS HOME_TEAM, TEAM_ABBRV as htav, AWAY_TEAM, HOME_TEAM_SCORE, AWAY_TEAM_SCORE\n" +
                "\t\tFROM\n" +
                "\t\t(\n" +
                "\t\t\tSELECT *\n" +
                "\t\t\tFROM FIXTURE\n" +
                "\t\t\tINNER JOIN TEAM ON FIXTURE.HOME_TEAM = TEAM.TEAM_ID\n" +
                "\t\t\tWHERE GAMEWEEK = "+GW+"\n" +
                "\t\t)\n" +
                "\t)\n" +
                "\tINNER JOIN TEAM ON AWAY_TEAM = TEAM.TEAM_ID\n" +
                ")";
        boolean flag = st.execute(qry);

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            String home = rs.getString(1);
            int hg = rs.getInt(2);
            int ag = rs.getInt(3);
            String away = rs.getString(4);
            String htav = rs.getString(5);
            String atav = rs.getString(6);

            String str = home+"#"+hg+"#"+ag+"#"+away+"#"+htav+"#"+atav;
            list.add(str);

        }
        rs.close();
        st.close();
        conn.close();

        return list;
    }

    public static List Statistics(int position, String team, int idx) throws ClassNotFoundException, SQLException {
        List<String> list = new ArrayList<>();

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String qry0 = "SELECT PLAYER_ID, FIRST_NAME, LAST_NAME, POSITION, COST, SQUAD_NUMBER, TEAM_ABBRV, POINTS\n" +
                "FROM (SELECT * FROM PLAYER\n" +
                "INNER JOIN TEAM ON PLAYER.TEAM_ID=TEAM.TEAM_ID\n" +
                ")";
        String qry1 = "SELECT PLAYER_ID, FIRST_NAME, LAST_NAME, POSITION, COST, SQUAD_NUMBER, TEAM_ABBRV, POINTS\n" +
                "FROM (SELECT * FROM PLAYER\n" +
                "INNER JOIN TEAM ON PLAYER.TEAM_ID=TEAM.TEAM_ID\n" +
                "where PLAYER.POSITION="+position+")";
        String qry2 = "SELECT PLAYER_ID, FIRST_NAME, LAST_NAME, POSITION, COST, SQUAD_NUMBER, TEAM_ABBRV, POINTS\n" +
                "FROM (SELECT * FROM PLAYER\n" +
                "INNER JOIN TEAM ON PLAYER.TEAM_ID=TEAM.TEAM_ID\n" +
                "where TEAM.TEAM_NAME='"+team+"')";
        String qry ="";

        if(idx==0){
            qry=qry0;
        }
        else if(idx==1){
            qry=qry1;
        }
        else if(idx==2){
            qry=qry2;
        }
        else {
            return null;
        }
        boolean flag = st.execute(qry);

        if (flag == true)
        {
            ResultSet rs = st.getResultSet();
            while (rs.next()) {
                int i = rs.getInt(1);
                String player = rs.getString(2)+" "+rs.getString(3);
                int p = rs.getInt(4);
                double c = rs.getDouble(5);
                c = c/10;
                double s = rs.getDouble(6);
                String t = rs.getString(7);
                int po = rs.getInt(8);

                String ans = i+"#"+player+"#"+p+"#"+c+"#"+s+"#"+t+"#"+po;
                list.add(ans);
            }
            rs.close();
        }
        st.close();
        conn.close();

        System.out.println("statistics done");

        return list;

    }

    public static String playerHistoryTopInfo(int Gameweek, int id) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        String str = "";

        st.execute("select FIRST_NAME, LAST_NAME, POINTS, COST from PLAYER where PLAYER_ID=" + id + "");
        ResultSet rs3 = st.getResultSet();
        while (rs3.next()){
            str += rs3.getString(2)+"#";
            str += rs3.getInt(3)+"#";
            str += rs3.getDouble(4)+"#";
        }
        rs3.close();

        st.execute("select POINTS from PLAYER_STAT where PLAYER_ID="+id+" AND " +
                "GAMEWEEK="+Gameweek+"");
        ResultSet rs4 = st.getResultSet();
        int cg = -100000;
        while (rs4.next()) {
            cg = rs4.getInt(1);
        }
        rs4.close();

        str+=cg+"#";

        st.execute("SELECT PLAYER.TEAM_ID, TEAM.TEAM_NAME, TEAM.TEAM_ABBRV\n" +
                "FROM PLAYER\n" +
                "INNER JOIN TEAM on TEAM.TEAM_ID = PLAYER.TEAM_ID\n" +
                "WHERE PLAYER_ID="+id+"");
        ResultSet rs1 = st.getResultSet();
        rs1.next();
        str += rs1.getInt(1)+"#";
        str += rs1.getString(2)+"#";
        str += rs1.getString(3)+"#";
        rs1.close();

        st.execute("SELECT SUM(BONUS) \n" +
                "FROM PLAYER_STAT\n" +
                "WHERE PLAYER_ID ="+id+"");
        ResultSet rs5 = st.getResultSet();
        while (rs5.next()) {
            str += rs5.getInt(1);
        }
        rs5.close();

        st.close();
        conn.close();

        return str;
    }

    public static List<String> playerHistory(int id, int teamid) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        List<String> list = new ArrayList<>();

        String qry = "\n" +
                "SELECT PLAYER_STAT.GAMEWEEK, MIN_PLAYED, GOAL_SCORED, ASSIST, CLEAN_SHEET, OWN_GOALS, PENALTIES_SAVED, PENALTIES_MISSED, " +
                "YELLOW_CARDS, RED_CARDS, POINTS, BONUS, SAVES, GOALS_CONCEDED, " +
                "FIXTURE.HOME_TEAM, FIXTURE.AWAY_TEAM, FIXTURE.HOME_TEAM_SCORE, FIXTURE.AWAY_TEAM_SCORE\n" +
                "FROM PLAYER_STAT \n" +
                "INNER JOIN FIXTURE on PLAYER_STAT.MATCH_ID=FIXTURE.MATCH_ID\n" +
                "WHERE PLAYER_ID="+id+" and PLAYER_STAT.GAMEWEEK<"+ServerUI.GAMEWEEK+"";
        boolean flag = st.execute(qry);

        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            String opponent;
            int ht, at, op, s1, s2;
            ht = rs.getInt(15);
            at = rs.getInt(16);
            if (ht == teamid) {
                op = at;
                s1 = rs.getInt(17);
                s2 = rs.getInt(18);
            } else {
                op = ht;
                s1 = rs.getInt(18);
                s2 = rs.getInt(17);
            }
            Statement st1 = conn.createStatement();
            st1.execute("SELECT TEAM_NAME from TEAM where TEAM_ID=" + op + "");
            ResultSet rs2 = st1.getResultSet();
            rs2.next();
            opponent = rs2.getString(1);
            rs2.close();
            opponent = opponent + " " + String.valueOf(s1) + "-" + String.valueOf(s2);
            String ans = "";
            ans += rs.getInt(1) + "#" + opponent + "#" + rs.getInt(2) + "#" + rs.getInt(3) + "#" +
                    rs.getInt(4) + "#" + rs.getInt(5) + "#" + rs.getInt(6) + "#" + rs.getInt(7) + "#" +
                    rs.getInt(8) + "#" + rs.getInt(9) + "#" + rs.getInt(10) + "#" + rs.getInt(11) + "#" +
                    rs.getInt(12) + "#" + rs.getInt(13) + "#" + rs.getInt(14) + "#";

            list.add(ans);
        }
        rs.close();
        st.close();
        conn.close();

        return list;
    }

    public static List<String> playerFixture(int id, int Gameweek) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        List<String> list = new ArrayList<>();

        st.execute("SELECT GAMEWEEK, TEAM_ABBRV\n" +
                "FROM\n" +
                "\t(SELECT GAMEWEEK, AWAY_TEAM as tid\n" +
                "\tFROM FIXTURE \n" +
                "\tWHERE HOME_TEAM = (\n" +
                "\tSELECT TEAM_ID FROM PLAYER \n" +
                "\tWHERE PLAYER_ID = "+id+")\n" +
                "\tAND GAMEWEEK>="+ServerUI.GAMEWEEK+" AND GAMEWEEK<=29\n" +
                "\tUNION All\n" +
                "\tSELECT GAMEWEEK, HOME_TEAM as tid\n" +
                "\tFROM FIXTURE \n" +
                "\tWHERE AWAY_TEAM = (\n" +
                "\tSELECT TEAM_ID FROM PLAYER \n" +
                "\tWHERE PLAYER_ID = "+id+")\n" +
                "\tAND GAMEWEEK>="+ServerUI.GAMEWEEK+" AND GAMEWEEK<=29)\n" +
                "\tINNER JOIN TEAM on\n" +
                "\tTEAM_ID = tid\n" +
                "\tORDER BY GAMEWEEK");

        ResultSet rs = st.getResultSet();
        while(rs.next()){
            String str = rs.getInt(1)+"#"+rs.getString(2);
            list.add(str);
        }
        return list;
    }

    public static void saveTeamChange(int teamid, int Gameweek, List list) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("delete from FIELD_PLAYER where USER_TEAM_ID="+teamid+" and GAMEWEEK="+Gameweek);
        st.execute("delete from BENCH_PLAYER where USER_TEAM_ID="+teamid+" and GAMEWEEK="+Gameweek);

        for (Object obj:list){
            String str = String.valueOf(obj);
            System.out.println(str);
            String[] strArr = str.split("#");
            if(strArr[1].equals("1")){
                st.execute("insert into FIELD_PLAYER values(" + teamid + "," + Integer.parseInt(strArr[0]) + "," + Gameweek + ")");
            }
            else {
                st.execute("insert into BENCH_PLAYER values(" + teamid + "," + Integer.parseInt(strArr[0]) + "," + Gameweek + ","+ Integer.parseInt(strArr[2])+")");
            }
        }

        st.close();
        conn.close();

    }

    public static int getCaptain(int teamid, int Gameweek) throws ClassNotFoundException, SQLException {
        int captain = 0;
        Connection conn = ServerMain.getConnection();

        Statement st = conn.createStatement();

        st.execute("select CAPTAIN from USER_TEAM where USER_TEAM_ID="+teamid+" and GAMEWEEK="+Gameweek);
        ResultSet rs = st.getResultSet();
        while (rs.next()){
            captain = rs.getInt(1);

        }
        st.close();
        conn.close();
        return captain;

    }

    public static void setCaptain(int id, int teamid, int Gameweek) throws ClassNotFoundException, SQLException{
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        st.execute("update USER_TEAM set CAPTAIN="+id+" where USER_TEAM_ID="+teamid+" and GAMEWEEK="+Gameweek);
        st.close();
        conn.close();
    }

    public static int getChips(int Gameweek, int teamid) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("SELECT CHIPS FROM USER_TEAM \n" +
                "WHERE USER_TEAM_ID = "+ teamid+" AND GAMEWEEK="+Gameweek);
        ResultSet rs = st.getResultSet();
        int chips=0;
        while (rs.next()){
            chips = rs.getInt(1);
        }
        st.close();
        conn.close();
        return chips;
    }

    public static void benchBoost(int Gameweek, int teamid) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        int chips=getChips(Gameweek, teamid);

        if(chips==0){
            if(!isPlayedChips(2, teamid, Gameweek)) {
                st.execute("UPDATE USER_TEAM set CHIPS=2 where GAMEWEEK=" + Gameweek + " and USER_TEAM_ID=" + teamid);
            }
        }
        else if(chips==2){
            st.execute("UPDATE USER_TEAM set CHIPS=0 where GAMEWEEK="+ Gameweek+" and USER_TEAM_ID="+ teamid);
        }
        st.close();
        conn.close();
    }

    public static void tripleCaptain(int Gameweek, int teamid) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        int chips=getChips(Gameweek, teamid);

        if(chips==0){
            if(!isPlayedChips(3, teamid, Gameweek)) {
                st.execute("UPDATE USER_TEAM set CHIPS=3 where GAMEWEEK=" + Gameweek + " and USER_TEAM_ID=" + teamid);
            }
        }
        else if(chips==3){
            st.execute("UPDATE USER_TEAM set CHIPS=0 where GAMEWEEK="+Gameweek+" and USER_TEAM_ID="+ teamid);
        }
        st.close();
        conn.close();
    }

    public static void freeHit(int Gameweek, int teamid) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        int chips=getChips(Gameweek, teamid);

        if(chips==0){
            if(!isPlayedChips(4, teamid, Gameweek)) {
                st.execute("UPDATE USER_TEAM set CHIPS=4 where GAMEWEEK=" + Gameweek + " and USER_TEAM_ID=" + teamid);
            }
        }
        st.close();
        conn.close();
    }

    public static boolean isPlayedChips(int i, int teamid, int Gameweek) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("SELECT GAMEWEEK FROM USER_TEAM\n" +
                "WHERE GAMEWEEK<"+(Gameweek)+" AND USER_TEAM_ID="+ teamid+" AND CHIPS="+i);
        ResultSet rs = st.getResultSet();
        int gw=0;
        while (rs.next()){
            gw = rs.getInt(1);
        }
        st.close();
        conn.close();
        if(gw==0) return false;
        else return true;
    }

    public static int EnterSquad(String USER, int Gameweek, List<Integer> list, double remainingMoney, String user_team_name) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st1 = conn.createStatement();
        st1.execute("select max(USER_TEAM_ID) from USERS");
        ResultSet rs1 = st1.getResultSet();
        rs1.next();
        int id = rs1.getInt(1);
        id = id + 1;
        rs1.close();
        st1.execute("UPDATE USERS SET USER_TEAM_ID="+id+" WHERE USERNAME='"+ USER+"'");
        st1.execute("INSERT INTO USER_TEAM(USER_TEAM_ID, USER_TEAM_NAME, GAMEWEEK, TRANSFERS) VALUES("+id+", '"+user_team_name+"' ,"+(Gameweek)+","+"2)");
        int pr = 0;
        for(int i=0; i<=14; i++){
            if(i==1 || i==5 || i==6 || i==11) {
                st1.execute("insert into BENCH_PLAYER values("+id+","+ list.get(i)+","+(Gameweek)+","+(pr++)+")");
            }
            else{
                st1.execute("insert into FIELD_PLAYER values("+id+","+ list.get(i)+","+(Gameweek)+")");
            }
        }
        System.out.println("bench done");
        st1.execute("UPDATE USER_TEAM set REMAINING_MONEY="+remainingMoney+" where USER_TEAM_ID="+id);
        st1.execute("update USER_TEAM set CAPTAIN="+ list.get(13)+" where USER_TEAM_ID="+ id+" and GAMEWEEK="+ Gameweek);

        st1.close();
        conn.close();

        System.out.println("id:::::::: "+id);
        return id;
    }

    public static PlayerDetails[] setAutoPick(PlayerDetails[] playerDetails) throws SQLException, ClassNotFoundException{
        validTeamgenerate(playerDetails);
        return playerDetails;
    }

    private static void validTeamgenerate(PlayerDetails[] playerDetails)  throws SQLException, ClassNotFoundException {
        float totalCost = 0;
        int cc = 0;

        for(int i=0; i<=14; i++){
            if(playerDetails[i].getId()<=0){
                cc++;
            }
        }

        totalCost = ServerThread.rm;
        totalCost = totalCost/cc;
        totalCost = totalCost*10;

        //gk
        for(int i=0; i<=1; i++){
            if(playerDetails[i].getId()>0) continue;
            RandomPlayerGenerate(1, i, totalCost, playerDetails);
            ServerThread.rm -= playerDetails[i].getCost()/10.0;
        }
        //def
        for(int i=2; i<=6; i++) {
            if(playerDetails[i].getId()>0) continue;
            RandomPlayerGenerate(2, i, totalCost, playerDetails);
            ServerThread.rm -= playerDetails[i].getCost()/10.0;
        }
        //mf
        for(int i=7; i<=11; i++) {
            if(playerDetails[i].getId()>0) continue;
            RandomPlayerGenerate(3, i, totalCost, playerDetails);
            ServerThread.rm -= playerDetails[i].getCost()/10.0;

        }
        //srt
        for(int i=12; i<=14; i++) {
            if(playerDetails[i].getId()>0) continue;
            RandomPlayerGenerate(4, i, totalCost, playerDetails);
            ServerThread.rm -= playerDetails[i].getCost()/10.0;

        }

    }

    private static void RandomPlayerGenerate(int pos, int plnum, float Evalue, PlayerDetails[] playerDetails) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        st.execute("SELECT PLAYER_ID, LAST_NAME, COST, team.TEAM_ABBRV\n" +
                "from PLAYER\n" +
                "INNER JOIN TEAM\n" +
                "ON PLAYER.TEAM_ID=TEAM.TEAM_ID\n" +
                "WHERE PLAYER.POSITION="+pos+" AND PLAYER.COST<="+Evalue+" AND PLAYER.COST>="+30+"\n" +
                "ORDER BY PLAYER.POINTS DESC");
        ResultSet rs1 = st.getResultSet();

        int id=0;
        String lname="";
        int cost = 0;
        String team = "";

        while (rs1.next()){
            id = rs1.getInt(1);
            lname = rs1.getString(2);
            cost = rs1.getInt(3);
            team = rs1.getString(4);

            boolean b = false;
            b = isInteam(id, playerDetails);

            playerDetails[plnum].setId(id);
            playerDetails[plnum].setString(lname);
            playerDetails[plnum].setCost(cost);
            playerDetails[plnum].setClub(team);

            if(teamPlayerCheck(playerDetails) || b){
                playerDetails[plnum].setId(-1*plnum);
                playerDetails[plnum].setString("");
                playerDetails[plnum].setCost(0);
                playerDetails[plnum].setClub("");
                continue;
            }

            break;

        }
        conn.close();
    }

    private static boolean isInteam(int id, PlayerDetails[] playerDetails) {
        boolean b = false;
        for(int i=0; i<=14; i++){
            if(id==playerDetails[i].getId()){
                b = true;
                break;
            }
        }
        return b;
    }

    private static boolean teamPlayerCheck(PlayerDetails[] playerDetails){
        HashMap<String, Integer> map = new HashMap<>();
        for(int i=0; i<=14; i++){
            if(playerDetails[i].getId()>0) {
                String c = playerDetails[i].getClub();
                if(!c.equals("")) {
                    if (map.containsKey(c)) {
                        map.replace(c, 0);
                    } else {
                        map.put(c, 0);
                    }
                }

            }
        }
        String teams = "";
        boolean b = false;
        for(int i=0; i<=14; i++) {
            if(playerDetails[i].getId()>0) {
                String c = playerDetails[i].getClub();
                if(!c.equals("")) {
                    if (map.containsKey(c)) {
                        int n = map.get(c) + 1;
                        map.replace(c, n);
                        if (n > 3) {
                            teams += c + ",";
                            b = true;
                        }
                    } else {
                        map.put(c, 1);
                    }
                }
            }
        }

        return b;
    }

    public static boolean SquadSelection(String username) throws ClassNotFoundException, SQLException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        st.execute("select USER_TEAM_ID from USERS where USERNAME='"+username+"'");
        ResultSet rs = st.getResultSet();
        int id = 0;
        while (rs.next()){
            id = rs.getInt(1);
        }

        st.close();
        conn.close();

        if(id==0){
            return true;
        }
        else return false;
    }

    public static List<String> getPlayerData(int teamid, int Gameweek) throws SQLException, ClassNotFoundException {
        List<String> results = new ArrayList<String>();
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        if (teamid != 0) {
            String qry1 = "with pst(pid, point) as\n" +
                    "(\n" +
                    "SELECT PLAYER_STAT.PLAYER_ID , SUM(PLAYER_STAT.POINTS)\n" +
                    "from PLAYER_STAT\n" +
                    "WHERE GAMEWEEK="+Gameweek+"\n" +
                    "GROUP BY PLAYER_STAT.PLAYER_ID\n" +
                    "\t)\n" +
                    "\t\n" +
                    "SELECT PLAYERID, NAME, COST, POS, TEAM.TEAM_ABBRV, pnts\n" +
                    "FROM\n" +
                    "\t(\n" +
                    "\tSELECT FIELD_PLAYER.PLAYER_ID AS PLAYERID, PLAYER.LAST_NAME AS NAME, PLAYER.TEAM_ID AS TID, PLAYER.COST AS COST, PLAYER.POSITION AS POS, pst.point as pnts\n" +
                    "\tfrom FIELD_PLAYER\n" +
                    "\tINNER JOIN PLAYER on FIELD_PLAYER.PLAYER_ID = PLAYER.PLAYER_ID\n" +
                    "\tINNER JOIN pst on FIELD_PLAYER.PLAYER_ID=pst.pid\n" +
                    "\twhere USER_TEAM_ID = "+teamid+" and GAMEWEEK="+Gameweek+"\n" +
                    "\t)\n" +
                    "\tINNER JOIN TEAM ON TID = TEAM.TEAM_ID\n" +
                    "\t";
            Statement st1 = conn.createStatement();
            st1.execute(qry1);
            ResultSet rs1 = st1.getResultSet();

            while (rs1.next()) {
                int i = rs1.getInt(1);
                String n = rs1.getString(2);
                String[] arrOfStr = n.split(" ");
                for (String a : arrOfStr) {
                    n = a;
                }
                int c = rs1.getInt(3);
                int p = rs1.getInt(4);
                String t = rs1.getString(5);
                String pp = "";
                int points = rs1.getInt(6);

                String str = p +"#"+ n +"#"+ c +"#"+ i +"#"+ t +"#"+ points;
                results.add(str);
            }
            rs1.close();
            st.close();


            String qry2 = "\twith pst(pid, point) as\n" +
                    "(\n" +
                    "SELECT PLAYER_STAT.PLAYER_ID , SUM(PLAYER_STAT.POINTS)\n" +
                    "from PLAYER_STAT\n" +
                    "WHERE GAMEWEEK="+Gameweek+"\n" +
                    "GROUP BY PLAYER_STAT.PLAYER_ID\n" +
                    "\t)\n" +
                    "\t\n" +
                    "SELECT PLAYERID, NAME, COST, POS, TEAM.TEAM_ABBRV, pnts\n" +
                    "FROM\n" +
                    "\t(\n" +
                    "\tSELECT BENCH_PLAYER.PLAYER_ID AS PLAYERID, PLAYER.LAST_NAME AS NAME, PLAYER.TEAM_ID AS TID, PLAYER.COST AS COST, PLAYER.POSITION AS POS, pst.point as pnts\n" +
                    "\tfrom BENCH_PLAYER\n" +
                    "\tINNER JOIN PLAYER on BENCH_PLAYER.PLAYER_ID = PLAYER.PLAYER_ID\n" +
                    "\tINNER JOIN pst on BENCH_PLAYER.PLAYER_ID=pst.pid\n" +
                    "\twhere USER_TEAM_ID = "+teamid+" and GAMEWEEK="+Gameweek+"\n" +
                    "\t)\n" +
                    "\tINNER JOIN TEAM ON TID = TEAM.TEAM_ID\n" +
                    "\t";
            Statement st2 = conn.createStatement();
            st2.execute(qry2);
            ResultSet rs2 = st2.getResultSet();

            while (rs2.next()) {
                int i = rs2.getInt(1);
                String n = rs2.getString(2);
                String[] arrOfStr = n.split(" ");
                for (String a : arrOfStr) {
                    n = a;
                }
                int c = rs2.getInt(3);
                int p = rs2.getInt(4);
                String t = rs2.getString(5);
                String pp = "";
                int points = rs2.getInt(6);

                String str =  p +"#"+ n +"#"+ c +"#"+ i +"#"+ t +"#"+ points;
                results.add(str);
            }
            rs2.close();
            st2.close();
        }

        st.close();
        conn.close();

        return results;
    }

    public static List getBechPriority(int teamid, int Gameweek) throws SQLException, ClassNotFoundException {
        List list = new ArrayList();
        Connection conn = ServerMain.getConnection();
        Statement st3 = conn.createStatement();
        st3.execute("SELECT PLAYER_ID from BENCH_PLAYER where USER_TEAM_ID="+teamid+" and GAMEWEEK="+Gameweek+" order by PRIORITY");
        ResultSet rs5 = st3.getResultSet();
        while (rs5.next()){
            int x = rs5.getInt(1);
            list.add(x);
        }
        rs5.close();
        st3.close();
        conn.close();
        return list;
    }


    /////////////////////////////////////////////

    public static void getJersey() throws ClassNotFoundException, SQLException {
        Connection conn = ServerMain.getConnection();
        System.out.println("connected");
        Statement st = conn.createStatement();
        st.execute("select TEAM_ABBRV, KIT, KITGK from TEAM");
        ResultSet rs = st.getResultSet();
        int i=0;
        while (rs.next()){
            String t = rs.getString(1);
            Blob blob1 = rs.getBlob(2);
            Blob blob2 = rs.getBlob(3);
//            InputStream fis = blob1.getBinaryStream(0, blob1.length());
//            BufferedImage image1 = ImageIO.read(fis);
//            File outputfile = new File("/Jersey/"+t+".png");
//            ImageIO.write(image1, "png", outputfile);
//
//            InputStream fis1 = blob1.getBinaryStream(0, blob1.length());
//            BufferedImage image2 = ImageIO.read(fis1);
//            File outputfile1 = new File("/Jersey/"+t+"GK.png");
//            ImageIO.write(image2, "png", outputfile1);


        }

    }

    public static void makeTransfers(float RemainingMoney, int teamid, int Gameweek, int tr, List list) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        int chips = GameweekUpdate.getChips(Gameweek+1, teamid);

        st.execute("SELECT TRANSFERS FROM USER_TEAM WHERE USER_TEAM_ID="+teamid+" AND GAMEWEEK="+(Gameweek)+"");
        ResultSet rs = st.getResultSet();
        int transfers = 0;
        while(rs.next()){
            transfers = rs.getInt(1);
        }
        st.execute("UPDATE USER_TEAM set REMAINING_MONEY="+RemainingMoney+" where USER_TEAM_ID="+teamid+" and GAMEWEEK="+Gameweek);
        if(chips!=1) {
            st.execute("update USER_TEAM set TRANSFERS=" + (transfers - tr) + " where USER_TEAM_ID=" + teamid + " and GAMEWEEK=" + Gameweek + "");
        }

        System.out.println("before delete : makeTransfer");

        st.execute("delete from FIELD_PLAYER where USER_TEAM_ID="+teamid+" and GAMEWEEK="+Gameweek);
        st.execute("delete from BENCH_PLAYER where USER_TEAM_ID="+teamid+" and GAMEWEEK="+Gameweek);

        for (Object obj:list){
            String str = String.valueOf(obj);
            System.out.println(str);
            String[] strArr = str.split("#");
            if(strArr[1].equals("1")){
                st.execute("insert into FIELD_PLAYER values(" + teamid + "," + Integer.parseInt(strArr[0]) + "," + Gameweek + ")");
            }
            else {
                st.execute("insert into BENCH_PLAYER values(" + teamid + "," + Integer.parseInt(strArr[0]) + "," + Gameweek + ","+ Integer.parseInt(strArr[2])+")");
            }
        }

        st.close();
        conn.close();

        System.out.println("makeTransfers Done");
    }

    public static void DeleteLeague(String code) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        st.execute("DELETE from LEAGUE_TEAMS where LEAGUE_CODE='"+code+"'");
        st.execute("DELETE from LEAGUE where LEAGUE_CODE='"+code+"'");
        st.close();
        conn.close();
    }

    public static void createClassicLeagues(String LeagueName, int teamid, int ScoreGw) throws ClassNotFoundException, SQLException {
        UUID uuid = UUID.randomUUID();
        String ClassicCode = uuid.toString().substring(0,8);

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        st.execute("INSERT INTO LEAGUE(LEAGUE_CODE, LEAGUE_NAME, ADMINISTRATOR, TYPE, START_GW) values('"+ClassicCode+"', '"+LeagueName+"', "+teamid+", "+0+", "+ScoreGw+")");
        st.execute("insert into LEAGUE_TEAMS values ('"+ClassicCode+"', "+ teamid+", "+ScoreGw+")");
        st.close();
        conn.close();
    }

    public static boolean joinClassicLeagues(String ClassicCode, int teamid, int Gameweek) throws ClassNotFoundException, SQLException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("select LEAGUE_CODE from LEAGUE_TEAMS where USER_TEAM_ID="+teamid+"");
        ResultSet rs1 = st.getResultSet();
        while (rs1.next()){
            String code1 = rs1.getString(1);
            if(code1.equals(ClassicCode)){
                System.out.println("Already in this League");
                return false;
            }
        }


        st.execute("select LEAGUE_CODE, START_GW from LEAGUE");
        ResultSet rs = st.getResultSet();
        String code = "";
        int GameWk = 0;
        while(rs.next()){
            code = rs.getString(1);
            GameWk = rs.getInt(2);
            if(code.equals(ClassicCode)){
                if(GameWk<Gameweek){
                    GameWk = Gameweek;
                }
                st.execute("insert into LEAGUE_TEAMS(LEAGUE_CODE, USER_TEAM_ID, JOINED_GW) values ('"+ClassicCode+"', '"+teamid+"', '"+GameWk+"')");
                st.close();
                conn.close();
                return true;
            }
        }
        st.close();
        conn.close();
        return false;
    }

    public static List<String> leaguesTableLoad(int teamid, int type) throws ClassNotFoundException, SQLException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        List<String> list = new ArrayList<>();

        st.execute("SELECT LEAGUE.league_name, LEAGUE.LEAGUE_CODE from LEAGUE_TEAMS\n" +
                "INNER JOIN LEAGUE on LEAGUE.LEAGUE_CODE=LEAGUE_TEAMS.LEAGUE_CODE\n" +
                "where USER_TEAM_ID = "+teamid+" and TYPE = "+type);
        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            String name = rs.getString(1);
            String c = rs.getString(2);
            String str = name+"#"+c;
            list.add(str);
        }
        st.close();
        conn.close();
        return list;
    }

    public static int getGWRank(int GW, String code, int teamid) throws SQLException, ClassNotFoundException {

        Connection con = ServerMain.getConnection();
        Statement st = con.createStatement();

        st.execute("SELECT R \n" +
                "FROM(\n" +
                "with lteamTable as\n" +
                "\t\t(\n" +
                "\t\tSELECT USER_TEAM_ID as lutid\n" +
                "\t\tfrom LEAGUE_TEAMS\n" +
                "\t\tWHERE LEAGUE_CODE='"+code+"'\n" +
                "\t\t)\n" +
                "\t\tSELECT USER_TEAM_ID as ptuid, RANK() OVER(ORDER BY SUM(POINTS) DESC) AS R\n" +
                "\t\tFROM USER_TEAM\n" +
                "\t\tINNER JOIN lteamTable \n" +
                "\t\tON USER_TEAM_ID = lutid\n" +
                "\t\tWHERE GAMEWEEK<"+GW+"\n" +
                "\t\tGROUP BY USER_TEAM_ID\n" +
                "\t\t)\n" +
                "\tWHERE PTUID="+ teamid);

        ResultSet rs = st.getResultSet();
        int rank = 0;
        while (rs.next()){
            rank = rs.getInt(1);
        }

        System.out.println(rank);

        rs.close();
        st.close();
        con.close();
        return rank;
    }

    public static int getLeagueStartGW(String code) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("select START_GW from LEAGUE where LEAGUE_CODE='"+code+"'");
        ResultSet rssr = st.getResultSet();
        int GameWk=0;
        while (rssr.next()){
            GameWk = rssr.getInt(1);
        }
        rssr.close();
        st.close();
        conn.close();
        return GameWk;
    }

    public static List<String> futureLeagueTeam(String code) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        List<String> list = new ArrayList<>();

        int stgw = getLeagueStartGW(code);

        if(stgw>=ServerUI.GAMEWEEK) {
            st.execute("SELECT LEAGUE_TEAMS.USER_TEAM_ID, USER_TEAM.USER_TEAM_NAME \n" +
                    "FROM LEAGUE_TEAMS\n" +
                    "INNER JOIN USER_TEAM ON USER_TEAM.USER_TEAM_ID=LEAGUE_TEAMS.USER_TEAM_ID\n" +
                    "WHERE USER_TEAM.GAMEWEEK = " + ServerUI.GAMEWEEK + " AND LEAGUE_TEAMS.LEAGUE_CODE='" + code + "'");
            ResultSet rss = st.getResultSet();
            while (rss.next()) {
                int id = rss.getInt(1);
                String name = rss.getString(2);
                String str = id + "#" + name;
                list.add(str);
            }
        }
        else{
            st.execute("SELECT LEAGUE_TEAMS.USER_TEAM_ID, USER_TEAM.USER_TEAM_NAME FROM LEAGUE_TEAMS \n" +
                    "INNER JOIN USER_TEAM ON USER_TEAM.USER_TEAM_ID=LEAGUE_TEAMS.USER_TEAM_ID\n" +
                    "WHERE USER_TEAM.GAMEWEEK = "+ServerUI.GAMEWEEK+ " AND LEAGUE_TEAMS.LEAGUE_CODE='"+code+"' AND LEAGUE_TEAMS.JOINED_GW = "+ ServerUI.GAMEWEEK);
            ResultSet rss = st.getResultSet();
            while (rss.next()) {
                int id = rss.getInt(1);
                String name = rss.getString(2);
                String str = id + "#" + name;
                list.add(str);
            }
        }
        st.close();
        conn.close();
        return list;
    }

    public static List<String> teamPreGWRank(String code, int Gameweek) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st2 = conn.createStatement();

        List<String> list = new ArrayList<>();

        st2.execute("SELECT ptuid, R \n" +
                "FROM(\n" +
                "with lteamTable as\n" +
                "\t\t(\n" +
                "\t\tSELECT USER_TEAM_ID as lutid\n" +
                "\t\tfrom LEAGUE_TEAMS\n" +
                "\t\tWHERE LEAGUE_CODE='"+code+"'\n" +
                "\t\t)\n" +
                "\t\tSELECT USER_TEAM_ID as ptuid, RANK() OVER(ORDER BY SUM(POINTS) DESC) AS R\n" +
                "\t\tFROM USER_TEAM\n" +
                "\t\tINNER JOIN lteamTable \n" +
                "\t\tON USER_TEAM_ID = lutid\n" +
                "\t\tWHERE GAMEWEEK<"+(Gameweek-1)+"\n" +
                "\t\tGROUP BY USER_TEAM_ID\n" +
                "\t\t)");

        ResultSet rs2 = st2.getResultSet();
        while (rs2.next()){
            String str = rs2.getInt(1)+"#"+rs2.getInt(2);
            list.add(str);
        }
        rs2.close();
        st2.close();
        conn.close();

        return list;
    }

    public static List<String> presentLeagueTeam(String code, int Gameweek)  throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        List<String> list = new ArrayList<>();

        st.execute("SELECT ptuid as TEAMID, tpoints as TOTAL_POINTS, USER_TEAM.USER_TEAM_NAME, USER_TEAM.POINTS, " +
                "RANK() OVER(ORDER BY tpoints DESC)\n" +
                "from \n" +
                "(\n" +
                "with lteamTable as\n" +
                "\t\t(\n" +
                "\t\tSELECT USER_TEAM_ID as lutid\n" +
                "\t\tfrom LEAGUE_TEAMS\n" +
                "\t\tWHERE LEAGUE_CODE='"+code+"' AND JOINED_GW<"+ Gameweek +"\n" +
                "\t\t)\n" +
                "\t\tSELECT USER_TEAM_ID as ptuid, SUM(POINTS) as tpoints\n" +
                "\t\tFROM USER_TEAM\n" +
                "\t\tINNER JOIN lteamTable \n" +
                "\t\tON USER_TEAM_ID = lutid\n" +
                "\t\tWHERE GAMEWEEK<"+ Gameweek+"\n" +
                "\t\tGROUP BY USER_TEAM_ID\n" +
                "\t)\n" +
                "INNER JOIN USER_TEAM\n" +
                "ON USER_TEAM.USER_TEAM_ID = ptuid\n" +
                "WHERE USER_TEAM.GAMEWEEK = "+(Gameweek-1)+"\n" +
                "ORDER BY tpoints DESC");
        ResultSet rs = st.getResultSet();
        while (rs.next()){
            int id = rs.getInt(1);
            int tpoints = rs.getInt(2);
            String name = rs.getString(3);
            int gwpoints = rs.getInt(4);
            int rank = rs.getInt(5);

            String str = id+"#"+tpoints+"#"+name+"#"+gwpoints+"#"+rank;
            list.add(str);
        }
        st.close();
        conn.close();

        return list;
    }

    public static int getLeagueAdmin(String code) throws ClassNotFoundException, SQLException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("select ADMINISTRATOR from LEAGUE where LEAGUE_CODE='"+code+"'");
        ResultSet rs = st.getResultSet();
        int admin = -1;
        while (rs.next()){
            admin = rs.getInt(1);
        }
        st.close();
        conn.close();
        return admin;
    }

    public static int getGWpoints(int gw, int teamid) throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        Statement st = con.createStatement();

        st.execute("SELECT POINTS from USER_TEAM where USER_TEAM_ID="+teamid+" and GAMEWEEK="+gw);
        ResultSet rs = st.getResultSet();
        int p = 0;
        while (rs.next()){
            p = rs.getInt(1);
        }
        st.close();
        con.close();
        return p;
    }

    public static int getTotalpoints(int Gameweek, int teamid) throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        Statement st = con.createStatement();

        st.execute("SELECT sum (POINTS) from USER_TEAM where USER_TEAM_ID="+ teamid+" and GAMEWEEK<="+Gameweek);
        ResultSet rs = st.getResultSet();
        int p = 0;
        while (rs.next()){
            p = rs.getInt(1);
        }
        st.close();
        con.close();
        return p;
    }

    public static int getHighestPoints(int Gameweek) throws SQLException, ClassNotFoundException {
        int pts = 0;
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("SELECT MAX(pts) from\n" +
                "(SELECT SUM(POINTS) as pts, USER_TEAM_ID\n" +
                "FROM USER_TEAM\n" +
                "WHERE GAMEWEEK<="+Gameweek+"\n" +
                "GROUP BY USER_TEAM_ID\n" +
                ")");
        ResultSet rs = st.getResultSet();

        while (rs.next()){
            pts = rs.getInt(1);
        }
        st.close();
        conn.close();
        return pts;
    }

    public static void setWildCard(int Gameweek, int teamid) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        st.execute("UPDATE USER_TEAM set CHIPS=1 where USER_TEAM_ID="+ teamid+" and GAMEWEEK="+ Gameweek);
        st.close();
        conn.close();
    }

    public static void setTransfersINandOUT(int idin, int idout, int Gameweek) throws SQLException, ClassNotFoundException {
        Connection conn  = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("update PLAYER_STAT set TRANSFERSIN = TRANSFERSIN+1 where PLAYER_ID="+ idin+" and GAMEWEEK="+ Gameweek);
        st.execute("update PLAYER_STAT set TRANSFERSOUT = TRANSFERSOUT+1 where PLAYER_ID="+ idout+" and GAMEWEEK="+ Gameweek);

        st.close();
        conn.close();
    }

    public static String getTransfersInfo(int Gameweek, int teamid) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("select REMAINING_MONEY, TRANSFERS from USER_TEAM where GAMEWEEK="+Gameweek+" and USER_TEAM_ID="+teamid);
        ResultSet rs = st.getResultSet();

        String str = "";

        while (rs.next()){
            str += rs.getDouble(1)+"#";
            str += rs.getInt(2)+"#";
        }

        st.close();
        conn.close();

        return str;
    }

    ////////////////////////////////

    public static String globaltableload(int teamid) throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{? = call GLOBAL_LEAGUES("+teamid+", "+ ServerUI.GAMEWEEK+")}");
        cs.registerOutParameter(1, Types.VARCHAR);
        cs.execute();
        String rs = cs.getString(1);
        return rs;
    }

    public static List<String> globalLeagueTeams() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{? = call GLOBAL_LEAGUE_TEAMS("+(ServerUI.GAMEWEEK-1)+")}");
        cs.registerOutParameter(1, Types.VARCHAR);
        cs.execute();
        String str = cs.getString(1);
        List<String> list = new ArrayList<>();
        String[] strs = str.split("@");
        System.out.println(str);
        System.out.println(strs.length);
        for(int i=0; i<strs.length; i++){
            System.out.println(strs[i]);
            list.add(strs[i]);
        }
        System.out.println("list");
        return list;
    }

    public static List<String> DreamTeamLoad() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{? = call DREAM_TEAM()}");
        cs.registerOutParameter(1, Types.VARCHAR);
        cs.execute();
        String str = cs.getString(1);
        List<String> list = new ArrayList<>();
        String[] strs = str.split("@");
        System.out.println(str);
        System.out.println(strs.length);
        for(int i=0; i<strs.length; i++){
            System.out.println(strs[i]);
            list.add(strs[i]);
        }
        System.out.println("list");
        return list;
    }

    public static List<String> TopGWPlayer() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{? = call TOPGWPLAYERS("+(ServerUI.GAMEWEEK-1)+")}");
        cs.registerOutParameter(1, Types.VARCHAR);
        cs.execute();
        String str = cs.getString(1);
        List<String> list = new ArrayList<>();
        String[] strs = str.split("@");
        System.out.println(str);
        System.out.println(strs.length);
        for(int i=0; i<strs.length; i++){
            System.out.println(strs[i]);
            list.add(strs[i]);
        }
        System.out.println("list");
        return list;
    }

    public static List<String> topTransfersIn() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{? = call TOP_TRANSFERS_IN("+(ServerUI.GAMEWEEK)+")}");
        cs.registerOutParameter(1, Types.VARCHAR);
        cs.execute();
        String str = cs.getString(1);
        List<String> list = new ArrayList<>();
        String[] strs = str.split("@");
        System.out.println(str);
        System.out.println(strs.length);
        for(int i=0; i<strs.length; i++){
            System.out.println(strs[i]);
            list.add(strs[i]);
        }
        System.out.println("list");
        return list;
    }

    public static List<String> topTransfersOut() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{? = call TOP_TRANSFERS_OUT("+(ServerUI.GAMEWEEK)+")}");
        cs.registerOutParameter(1, Types.VARCHAR);
        cs.execute();
        String str = cs.getString(1);
        List<String> list = new ArrayList<>();
        String[] strs = str.split("@");
        System.out.println(str);
        System.out.println(strs.length);
        for(int i=0; i<strs.length; i++){
            System.out.println(strs[i]);
            list.add(strs[i]);
        }
        System.out.println("list");
        return list;
    }

    public static List<String> bestLeagues() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{? = call BEST_LEAGUES()}");
        cs.registerOutParameter(1, Types.VARCHAR);
        cs.execute();
        String str = cs.getString(1);
        List<String> list = new ArrayList<>();
        String[] strs = str.split("@");
        System.out.println(str);
        System.out.println(strs.length);
        for(int i=0; i<strs.length; i++){
            System.out.println(strs[i]);
            list.add(strs[i]);
        }
        System.out.println("list");
        return list;
    }

    public static List<String> mostValuedTeam() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{? = call MOST_VALUED_TEAM("+(ServerUI.GAMEWEEK-1)+")}");
        cs.registerOutParameter(1, Types.VARCHAR);
        cs.execute();
        String str = cs.getString(1);
        List<String> list = new ArrayList<>();
        String[] strs = str.split("@");
        System.out.println(str);
        System.out.println(strs.length);
        for(int i=0; i<strs.length; i++){
            System.out.println(strs[i]);
            list.add(strs[i]);
        }
        System.out.println("list");
        return list;
    }

    public static String getStatusInfo() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{? = call STATUS_INFO("+(ServerUI.GAMEWEEK)+")}");
        cs.registerOutParameter(1, Types.VARCHAR);
        cs.execute();
        String str = cs.getString(1);
        return str;
    }

    ////////////////////////////////

    public static void CreatePublicClassicLeague(int teamid) throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{call PUBLIC_LEAGUE("+teamid+","+(ServerUI.GAMEWEEK)+")}");
        cs.execute();
        System.out.println("public league created");
    }

    public static void createH2HLeagues(String LeagueName, int teamid, String ScoreGw) throws SQLException, ClassNotFoundException {
        UUID uuid = UUID.randomUUID();
        String ClassicCode = uuid.toString().substring(0,8);

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        st.execute("INSERT INTO H2H_LEAGUE(LEAGUE_CODE, LEAGUE_NAME, ADMINISTRATOR, MAXIMUM_TEAMS, START_GW) values('"+ClassicCode+"', '"+LeagueName+"', "+teamid+", "+50+", "+ScoreGw+")");
        st.execute("insert into H2H_LEAGUE_TEAMS(LEAGUE_CODE, USER_TEAM_ID) values ('"+ClassicCode+"', "+ teamid+")");
        st.close();
        conn.close();
    }

    public static boolean joinH2HLeagues(String ClassicCode, int teamid, int Gameweek) throws ClassNotFoundException, SQLException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("select START_GW from H2H_LEAGUE where LEAGUE_CODE='"+ClassicCode+"'");

        ResultSet r = st.getResultSet();
        int sgw = 0;
        while (r.next()){
            sgw = r.getInt(1);
        }

        if(sgw<=ServerUI.GAMEWEEK) return false;

        st.execute("select LEAGUE_CODE from H2H_LEAGUE_TEAMS where USER_TEAM_ID="+teamid+"");
        ResultSet rs1 = st.getResultSet();
        while (rs1.next()){
            String code1 = rs1.getString(1);
            if(code1.equals(ClassicCode)){
                System.out.println("Already in this League");
                return false;
            }
        }


        st.execute("select LEAGUE_CODE from H2H_LEAGUE where LEAGUE_CODE='"+ClassicCode+"'");
        ResultSet rs = st.getResultSet();
        String code = "";
        while(rs.next()){
            code = rs.getString(1);
        }
        if(code=="") return false;
        else
            st.execute("insert into H2H_LEAGUE_TEAMS(LEAGUE_CODE, USER_TEAM_ID) values ('"+ClassicCode+"', "+teamid+")");
        st.close();
        conn.close();
        return true;
    }

    public static List<String> H2HLeagueLoad(int teamid) throws SQLException, ClassNotFoundException{
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        List<String> list = new ArrayList<>();

        st.execute("SELECT H2H_LEAGUE.league_name, H2H_LEAGUE.LEAGUE_CODE from H2H_LEAGUE_TEAMS\n" +
                "INNER JOIN H2H_LEAGUE on H2H_LEAGUE.LEAGUE_CODE=H2H_LEAGUE_TEAMS.LEAGUE_CODE\n" +
                "where USER_TEAM_ID = "+teamid);
        ResultSet rs = st.getResultSet();
        while (rs.next()) {
            String name = rs.getString(1);
            String c = rs.getString(2);
            String str = name+"#"+c;
            list.add(str);
        }
        st.close();
        conn.close();

        return list;
    }

    public static List<String> H2HLeagueTeamLoad(String code) throws SQLException, ClassNotFoundException {
        List<String> list = new ArrayList<>();

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("select ht.USER_TEAM_ID, ut.USER_TEAM_NAME, WIN, LOSE, DRAW, ht.POINTS" +
                " FROM H2H_LEAGUE_TEAMS ht " +
                " inner join USER_TEAM ut on ut.USER_TEAM_ID=ht.USER_TEAM_ID" +
                " WHERE LEAGUE_CODE='"+code+"' and ut.GAMEWEEK = "+(ServerUI.GAMEWEEK)+" "+
                " ORDER by ht.POINTS");

        ResultSet rs = st.getResultSet();
        int i=0;
        while (rs.next()){
             String str = i+"#"+rs.getInt(1)+"#"+rs.getString(2)+"#"+rs.getInt(3)+
                     "#"+rs.getInt(4)+"#"+rs.getInt(5)+"#"+rs.getInt(6);
             list.add(str);
             i++;
        }
        return list;
    }

    public static List<String> H2HFixtureLoad(String code, int gw) throws SQLException, ClassNotFoundException {
        List<String> list = new ArrayList<>();

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();


        if(gw<ServerUI.GAMEWEEK) {
            st.execute("WITH TABLEA AS\t\n" +
                    "\t(\n" +
                    "\tSELECT UT.USER_TEAM_NAME AS A, F.TEAM_B AS B, UT.POINTS AS P, F.GAMEWEEK AS G\n" +
                    "\t\tFROM H2H_FIXTURE F\n" +
                    "\t\tINNER JOIN USER_TEAM UT ON UT.USER_TEAM_ID = F.TEAM_A\n" +
                    "\t\tWHERE F.GAMEWEEK = " + gw + " AND F.LEAGUE_CODE = '" + code + "' AND UT.GAMEWEEK=" + gw + "\n" +
                    "\t\t)\n" +
                    "\t\tSELECT TABLEA.A, UTT.USER_TEAM_NAME, TABLEA.P AS PA, UTT.POINTS AS PB\n" +
                    "\t\tFROM USER_TEAM UTT \n" +
                    "\t\tINNER JOIN TABLEA ON TABLEA.B = UTT.USER_TEAM_ID\n" +
                    "\t\tWHERE UTT.GAMEWEEK = " + gw + "\n" +
                    "\t\t");

            ResultSet rs = st.getResultSet();
            while (rs.next()){
                String str = rs.getString(1)+"#"+rs.getString(2)+"#"+rs.getInt(3)+"#"+rs.getInt(4);
                list.add(str);
            }
            return list;

        }
        st.execute("WITH TABLEA AS\t\n" +
                "\t(\n" +
                "\tSELECT UT.USER_TEAM_NAME AS A, F.TEAM_B AS B, F.GAMEWEEK AS G\n" +
                "\t\tFROM H2H_FIXTURE F\n" +
                "\t\tINNER JOIN USER_TEAM UT ON UT.USER_TEAM_ID = F.TEAM_A\n" +
                "\t\tWHERE F.GAMEWEEK = " + gw + " AND F.LEAGUE_CODE = '" + code + "' AND UT.GAMEWEEK=" + (ServerUI.GAMEWEEK) + "\n" +
                "\t\t)\n" +
                "\t\tSELECT TABLEA.A, UTT.USER_TEAM_NAME\n" +
                "\t\tFROM USER_TEAM UTT \n" +
                "\t\tINNER JOIN TABLEA ON TABLEA.B = UTT.USER_TEAM_ID\n" +
                "\t\tWHERE UTT.GAMEWEEK = " + (ServerUI.GAMEWEEK) + "\n" +
                "\t\t");

        ResultSet rs = st.getResultSet();
        while (rs.next()){
            String str = rs.getString(1)+"#"+rs.getString(2)+"#"+0+"#"+0;
            list.add(str);
        }
        return list;
    }

    public static int getH2HStartGw(String code) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("select START_GW from H2H_LEAGUE where LEAGUE_CODE='"+code+"'");
        ResultSet rs = st.getResultSet();
        int gw = 0;
        while (rs.next()){
            gw = rs.getInt(1);
        }
        return gw;
    }



}
