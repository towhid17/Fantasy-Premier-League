package sample;

import javafx.util.Pair;
import sample.DatabaseData;
import sample.PlayerDetails;
import sample.ServerMain;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameweekUpdate {
    public static int averagePoint = 0;

    public static void updateNewGameWeek(int teamid, int Gameweek) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();


        int chips = getChips(Gameweek, teamid);
        int GW = Gameweek-1;
        if(chips==4){
            GW = Gameweek-2;
        }
        int[] fp = new int[100];
        int[] bp = new int[100];
        int i=0;
        st.execute("SELECT PLAYER_ID from FIELD_PLAYER where USER_TEAM_ID="+teamid+" AND GAMEWEEK="+GW);
        ResultSet rs2 = st.getResultSet();
        while (rs2.next()){
            fp[i++] = rs2.getInt(1);
        }
        rs2.close();

        for (int j=0; j<=10; j++){
            st.execute("INSERT into FIELD_PLAYER values ("+teamid+","+fp[j]+","+Gameweek+")");
        }

        st.execute("SELECT PLAYER_ID, PRIORITY from BENCH_PLAYER where USER_TEAM_ID="+teamid+" AND GAMEWEEK="+GW);
        ResultSet rs3 = st.getResultSet();
        i=0;
        int[] pr = new int[100];
        while (rs3.next()){
            bp[i] = rs3.getInt(1);
            pr[i] = rs3.getInt(2);
            i++;
        }
        rs3.close();

        for (int j=0; j<=3; j++){
            st.execute("INSERT into BENCH_PLAYER values ("+teamid+","+bp[j]+","+Gameweek+","+pr[j]+")");
        }

        st.execute("select CAPTAIN, REMAINING_MONEY, TRANSFERS, USER_TEAM_NAME from USER_TEAM where USER_TEAM_ID="+teamid+" and GAMEWEEK="+GW);
        ResultSet rs4 = st.getResultSet();
        int capid = 0;
        int rm = 0;
        int tr = 0;
        String tn = "";
        while (rs4.next()){
            capid = rs4.getInt(1);
            rm = rs4.getInt(2);
            tr = rs4.getInt(3);
            tn = rs4.getString(4);
        }
        rs4.close();

        if(tr<0){
            tr=1;
        }
        else if(tr<3) {
            tr+=1;
        }

        st.execute("INSERT INTO USER_TEAM(USER_TEAM_ID, USER_TEAM_NAME,CAPTAIN, REMAINING_MONEY, GAMEWEEK, TRANSFERS) VALUES("+teamid+", '"+tn+"' ,"+capid+","+rm+","+(Gameweek)+","+tr+")");

        st.close();
        conn.close();
    }

    public static boolean pointsUpdate(int teamid, int Gameweek)
            throws SQLException, ClassNotFoundException {

        if((Gameweek-1)==0)
            return false;

        //chips
        int chips = getChips(Gameweek, teamid);

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();
        int points = 0;

        st.execute("SELECT SUM(points)\n" +
                "from(with ps as (\n" +
                "SELECT PLAYER_STAT.PLAYER_ID as psid , PLAYER_STAT.POINTS as points\n" +
                "from PLAYER_STAT \n" +
                "WHERE GAMEWEEK = "+(Gameweek-1)+"\n" +
                ")\n" +
                "SELECT FIELD_PLAYER.PLAYER_ID, points\n" +
                "FROM FIELD_PLAYER\n" +
                "INNER JOIN ps \n" +
                "on FIELD_PLAYER.PLAYER_ID=psid\n" +
                "WHERE FIELD_PLAYER.USER_TEAM_ID="+teamid+" AND FIELD_PLAYER.GAMEWEEK="+(Gameweek-1)+"\n" +
                ")");

        ResultSet rs = st.getResultSet();
        while (rs.next()){
            points = rs.getInt(1);
        }
        rs.close();

        //captain
        int capid = 0;
        st.execute("select CAPTAIN from USER_TEAM where USER_TEAM_ID="+teamid+" and GAMEWEEK="+(Gameweek-1));
        ResultSet rs1 = st.getResultSet();
        while (rs1.next()){
            capid = rs1.getInt(1);
        }
        rs1.close();

        st.execute("SELECT SUM(POINTS)\n" +
                "FROM\n" +
                "(\n" +
                "SELECT POINTS FROM PLAYER_STAT\n" +
                "WHERE PLAYER_ID = "+capid+" and GAMEWEEK="+(Gameweek-1)+"\n" +
                ")");
        ResultSet rs2 = st.getResultSet();
        int capp = 0;
        while (rs2.next()){
            capp = rs2.getInt(1);
        }
        rs2.close();

        points += capp;
        if(chips==3){
            points+=capp;
        }
        points += pointsFromBench(teamid, Gameweek);
        System.out.println(points);
        st.execute("SELECT TRANSFERS FROM USER_TEAM\n" +
                "WHERE USER_TEAM_ID = "+teamid+" AND GAMEWEEK="+(Gameweek-1)+"");

        ResultSet rstr = st.getResultSet();
        int tr = 0;
        while (rstr.next()){
            tr = rstr.getInt(1);
        }
        if(tr<0){
            if(chips!=1) {
                points += tr * 4;
            }
        }
        rstr.close();

        System.out.println(points);

        st.execute("update USER_TEAM set POINTS="+points+" where USER_TEAM_ID="+teamid+" and GAMEWEEK="+(Gameweek-1));
        st.close();
        conn.close();
        return true;
    }

    private static int pointsFromBench(int teamid, int Gameweek)
            throws SQLException, ClassNotFoundException {

        int chips = getChips(Gameweek, teamid);

        if(chips==2){
            Connection conn = ServerMain.getConnection();
            Statement st = conn.createStatement();
            st.execute("SELECT SUM(points)\n" +
                    "from\n" +
                    "(\n" +
                    "with ps as (\n" +
                    "SELECT PLAYER_STAT.PLAYER_ID as psid , PLAYER_STAT.POINTS as points\n" +
                    "from PLAYER_STAT \n" +
                    "WHERE GAMEWEEK = "+(Gameweek-1)+"\n" +
                    ")\n" +
                    "SELECT BENCH_PLAYER.PLAYER_ID, points\n" +
                    "FROM BENCH_PLAYER\n" +
                    "INNER JOIN ps \n" +
                    "on BENCH_PLAYER.PLAYER_ID=psid\n" +
                    "WHERE BENCH_PLAYER.USER_TEAM_ID=1 AND BENCH_PLAYER.GAMEWEEK="+(Gameweek-1)+"\n" +
                    ")");
            ResultSet rs = st.getResultSet();
            int ans = 0;
            while (rs.next()){
                ans = rs.getInt(1);
            }
            return ans;
        }

        PlayerDetails[] dataplayerDetails = new PlayerDetails[15];
        int[] BenchPlayerPriority = new int[4];

        for(int i=0; i<=14; i++){
            dataplayerDetails[i] = new PlayerDetails();
        }

        List list = DatabaseData.getPlayerData(teamid, Gameweek-1);
        dataplayerDetails = toplayerDetails(list);

        List bpr = DatabaseData.getBechPriority(teamid, Gameweek-1);
        int ind = 0;
        for(Object o:bpr){
            BenchPlayerPriority[ind++]=(int)o;
        }

        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("SELECT pid \n" +
                "FROM\n" +
                "(\n" +
                "with ps as (\n" +
                "SELECT PLAYER_STAT.PLAYER_ID as psid, sum(PLAYER_STAT.MIN_PLAYED) as min\n" +
                "from PLAYER_STAT \n" +
                "WHERE GAMEWEEK = "+(Gameweek-1)+"\n" +
                "GROUP BY PLAYER_STAT.PLAYER_ID\n" +
                ")\n" +
                "SELECT FIELD_PLAYER.PLAYER_ID as pid, min\n" +
                "FROM FIELD_PLAYER\n" +
                "INNER JOIN ps \n" +
                "on FIELD_PLAYER.PLAYER_ID=psid\n" +
                "WHERE FIELD_PLAYER.USER_TEAM_ID="+teamid+" and FIELD_PLAYER.GAMEWEEK="+(Gameweek-1)+"\n" +
                ")\n" +
                "WHERE min <= 1");

        ResultSet rs3 = st.getResultSet();
        int[] offIds = new int[11];
        for(int i=0; i<=10; i++){
            offIds[i]=0;
        }
        int itr=0;
        boolean[] pb = new boolean[4];
        for(int i=0; i<=3; i++){
            pb[i] = true;
        }
        while (rs3.next()){
            offIds[itr++] = rs3.getInt(1);
        }
        rs3.close();


        int points = 0;
        int p = 0;
        for(int i=0; i<=10; i++){
            if(offIds[i]!=0){
                for(int j=0; j<=14; j++){
                    if(offIds[i]==dataplayerDetails[j].getId()){
                        if(dataplayerDetails[j].getPos()=="GK"){
                            p = isPlayed(BenchPlayerPriority[0], teamid, Gameweek);
                            if(p>0 && pb[0]){
                                playerUpdateforPoints(offIds[i], BenchPlayerPriority[0], teamid, Gameweek);
                                points+=p;
                                pb[0] = false;
                                p = -1;
                            }
                        }
                        else {
                            p = isPlayed(BenchPlayerPriority[1], teamid, Gameweek);
                            if(p>0 && pb[1]){
                                System.out.println("pb1");
                                playerUpdateforPoints(offIds[i], BenchPlayerPriority[1], teamid, Gameweek);
                                points+=p;
                                pb[1] = false;
                                p = -1;
                                continue;
                            }
                            p = isPlayed(BenchPlayerPriority[2], teamid, Gameweek);
                            if(p>0 && pb[2]){
                                System.out.println("pb2");
                                playerUpdateforPoints(offIds[i], BenchPlayerPriority[2], teamid, Gameweek);
                                points+=p;
                                pb[2] = false;
                                p = -1;
                                continue;
                            }
                            p = isPlayed(BenchPlayerPriority[3], teamid, Gameweek);
                            if(p>0 && pb[3]){
                                System.out.println("pb3");
                                playerUpdateforPoints(offIds[i], BenchPlayerPriority[3], teamid, Gameweek);
                                points+=p;
                                pb[3] = false;
                                p = -1;
                            }
                        }

                    }
                }
            }
            else break;
        }
        st.close();
        conn.close();

        if(points<0) points=0;
        return points;

    }

    private static int isPlayed(int pid, int teamid, int Gameweek) throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        Statement st = con.createStatement();

        st.execute("\n" +
                "SELECT sum(PLAYER_STAT.MIN_PLAYED) as min, sum(PLAYER_STAT.POINTS) as pnt\n" +
                "from PLAYER_STAT \n" +
                "WHERE GAMEWEEK = "+(Gameweek-1)+" and PLAYER_ID = "+pid+"\n" +
                "GROUP BY PLAYER_STAT.PLAYER_ID\n");
        ResultSet rs = st.getResultSet();
        int min = 0;
        int pnt = 0;
        while (rs.next()){
            min = rs.getInt(1);
            pnt = rs.getInt(2);
        }

        st.close();
        con.close();

        if(min>0 && pnt>0){
            return pnt;
        }

        return -1;
    }

    private static void playerUpdateforPoints(int fid, int bid, int teamid, int Gameweek) throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        Statement st = con.createStatement();

        st.execute("update FIELD_PLAYER set PLAYER_ID="+bid+" where PLAYER_ID="+fid+" and GAMEWEEK="+(Gameweek-1)+" and USER_TEAM_ID="+teamid);
        st.execute("update BENCH_PLAYER set PLAYER_ID="+fid+" where PLAYER_ID="+bid+" and GAMEWEEK="+(Gameweek-1)+" and USER_TEAM_ID="+teamid);

        st.close();
        con.close();
    }

//    public static int isTeamUpdated(int teamid, int Gameweek) throws SQLException, ClassNotFoundException {
//        int flag = 0;
//
//        Connection conn = ClientMain.getConnection();
//        Statement st = conn.createStatement();
//        st.execute("SELECT PLAYER_ID from FIELD_PLAYER where USER_TEAM_ID="+teamid+" AND GAMEWEEK="+Gameweek);
//
//        ResultSet rs1 = st.getResultSet();
//        while (rs1.next()){
//            flag = rs1.getInt(1);
//        }
//        rs1.close();
//        st.close();
//        conn.close();
//        return flag;
//    }

    public static PlayerDetails[] toplayerDetails(List rs) {
        PlayerDetails[] dataplayerDetails = new PlayerDetails[15];
        for (int i = 0; i <= 14; i++) {
            dataplayerDetails[i] = new PlayerDetails();
        }
        int mf = 7;
        int df = 2;
        int srt = 12;

        int ind = 0;

        for(Object obj:rs){
            String str1 = String.valueOf(obj);
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
        return dataplayerDetails;
    }

    public static int getChips(int Gameweek, int teamid) throws SQLException, ClassNotFoundException {
        Connection conn = ServerMain.getConnection();
        Statement st = conn.createStatement();

        st.execute("SELECT CHIPS FROM USER_TEAM \n" +
                "WHERE USER_TEAM_ID = "+teamid+" AND GAMEWEEK="+(Gameweek-1));
        ResultSet rs = st.getResultSet();
        int chips=0;
        while (rs.next()){
            chips = rs.getInt(1);
        }
        st.close();
        conn.close();
        return chips;
    }

    public static void Avg_Point_League() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{call AVG_POINT_LEAGUE("+(ServerUI.GAMEWEEK-1)+")}");
        cs.execute();
        cs.close();
        con.close();
        System.out.println("AVG POINT UPDATED");
    }

    public static void User_team_value_update() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        CallableStatement cs = con.prepareCall("{call USER_TEAM_VALUE_UPDATE("+(ServerUI.GAMEWEEK-1)+")}");
        cs.execute();
        cs.close();
        con.close();
        System.out.println("user team Value updated");
    }

    public static void AvgUserTeamUpdate() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        Statement st = con.createStatement();

        st.execute("INSERT into USER_TEAM(USER_TEAM_ID, USER_TEAM_NAME, GAMEWEEK, CHIPS, CAPTAIN) " +
                " VALUES (0, 'AVERAGE', "+ServerUI.GAMEWEEK+", 1 "+", 1)");

        st.execute("update USER_TEAM set POINTS = "+averagePoint+" " +
                "where GAMEWEEK = "+(ServerUI.GAMEWEEK-1)+" and USER_TEAM_ID = 0");

        st.close();
        con.close();
    }

    public static void H2H_fixture_generate() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        Statement st = con.createStatement();

        st.execute("select LEAGUE_CODE from H2H_LEAGUE where START_GW="+ServerUI.GAMEWEEK);
        ResultSet rs = st.getResultSet();
        List<String> codes = new ArrayList<>();
        while (rs.next()){
            String c = rs.getString(1);
            codes.add(c);
            System.out.println("code::::: "+ c);
        }
        for(int ikj = 0; ikj<codes.size(); ikj++){
            String code = codes.get(ikj);
            System.out.println("Code:::::: "+code);
            st.execute("select count(*) from H2H_LEAGUE_TEAMS where LEAGUE_CODE='"+code+"'");
            ResultSet rs1 = st.getResultSet();
            int tn = 0;
            while (rs1.next()){
                tn = rs1.getInt(1);
            }

            System.out.println("First League Fixture ... tn : "+tn);
            if(tn%2!=0){
                st.execute("insert into H2H_LEAGUE_TEAMS(LEAGUE_CODE, USER_TEAM_ID) values('"+code+"' , "+0+")");
                tn++;
            }

            st.execute("select USER_TEAM_ID from H2H_LEAGUE_TEAMS where LEAGUE_CODE='"+code+"' order by USER_TEAM_ID");
            ResultSet rs2 = st.getResultSet();

            int teams = tn;
            int tt = 1;
            int lastid = 0;
            ArrayList<Integer> uidList = new ArrayList<>();
            while (rs2.next()){
                if(tt>=teams){
                    lastid = rs2.getInt(1);
                }
                else
                    uidList.add(rs2.getInt(1));
                tt++;
            }


            int rotate = teams/2;
            int round = teams-1;

            int firstIdx = 0;
            int insertedGW = ServerUI.GAMEWEEK;
            for(int i=1; i<=round; i++){
                System.out.println("Round: "+i);
                int lr = 0;
                System.out.println("1: Team A: "+uidList.get(firstIdx)+" - "+lastid+" : Team B");
                st.execute("insert into H2H_FIXTURE(league_code, team_a, team_b, gameweek) " +
                        " values('"+code+"', "+uidList.get(firstIdx)+", "+lastid+", "+insertedGW+")");
                for(int j=0; j<rotate-1; j++){
                    lr++;
                    int li = firstIdx-lr;
                    int ri = (firstIdx+lr)%(teams-1);
                    if(li<0) li = teams-1-li*(-1);
                    System.out.println((j+2)+": Team A: "+ uidList.get(li)+ " - "+uidList.get(ri)+" : Team B");
                    st.execute("insert into H2H_FIXTURE(league_code, team_a, team_b, gameweek) " +
                            " values('"+code+"', "+uidList.get(li)+", "+uidList.get(ri)+", "+insertedGW+")");
                }
                firstIdx = (firstIdx+rotate)%(teams-1);
                insertedGW++;
            }


            int idd = 0;
            while (insertedGW+idd<=38){
                st.execute("select TEAM_A, TEAM_B" +
                        " FROM H2H_FIXTURE WHERE LEAGUE_CODE= '"+code+"' and GAMEWEEK="+(ServerUI.GAMEWEEK+idd));
                ResultSet rs3 = st.getResultSet();
                while (rs3.next()){
                    int teamA = rs3.getInt(1);
                    int teamB = rs3.getInt(2);
                    Statement st1 = con.createStatement();
                    st1.execute("insert into H2H_FIXTURE(league_code, team_a, team_b, gameweek) " +
                            "values('"+code+"', "+teamB+", "+teamA+", "+(insertedGW+idd)+") ");
                }
                idd++;
                //rs3.close();
            }

        }
        st.close();
        con.close();
    }

    public static void H2H_Point_update() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        Statement st = con.createStatement();

        System.out.println("H2H Point Update Enter::::::::");

        st.execute("select LEAGUE_CODE from H2H_LEAGUE");
        ResultSet rs = st.getResultSet();
        List<String> codes = new ArrayList<>();
        while (rs.next()) {
            String c = rs.getString(1);
            codes.add(c);
            System.out.println("code::::: " + c);
        }

        for (int i = 0; i < codes.size(); i++) {
            String code = codes.get(i);
            st.execute("WITH TABLEA AS\t\n" +
                    "\t(\n" +
                    "\tSELECT F.TEAM_A AS A, F.TEAM_B AS B, UT.POINTS AS P, F.GAMEWEEK AS G\n" +
                    "\t\tFROM H2H_FIXTURE F\n" +
                    "\t\tINNER JOIN USER_TEAM UT ON UT.USER_TEAM_ID = F.TEAM_A\n" +
                    "\t\tWHERE F.GAMEWEEK = "+(ServerUI.GAMEWEEK-1)+" AND F.LEAGUE_CODE = '"+code+"' AND UT.GAMEWEEK="+(ServerUI.GAMEWEEK-1)+"\n" +
                    "\t\t)\n" +
                    "\t\tSELECT TABLEA.A, TABLEA.B, TABLEA.P AS PA, UTT.POINTS AS PB\n" +
                    "\t\tFROM USER_TEAM UTT \n" +
                    "\t\tINNER JOIN TABLEA ON TABLEA.B = UTT.USER_TEAM_ID\n" +
                    "\t\tWHERE UTT.GAMEWEEK = "+(ServerUI.GAMEWEEK-1)+"\n" +
                    "\t\t");

            ResultSet rs1 = st.getResultSet();
            while (rs1.next()){
                int ta, tb, tap, tbp;
                ta = rs1.getInt(1);
                tb = rs1.getInt(2);
                tap = rs1.getInt(3);
                tbp = rs1.getInt(4);

                if(ta==0){
                    tap = averagePoint;
                }
                if(tb==0){
                    tbp = averagePoint;
                }

                int tapp, tbpp, wa, la, wb, lb, da, db;
                if(tap>tbp){
                    tapp = 3;
                    tbpp = 0;
                    wa = 1;
                    wb = 0;
                    lb = 1;
                    la = 0;
                    da = 0;
                    db = 0;
                }
                else if(tbp>tap){
                    tapp = 0;
                    tbpp = 3;
                    wa = 0;
                    wb = 1;
                    lb = 0;
                    la = 1;
                    da = 0;
                    db = 0;
                }
                else {
                    tapp = 1;
                    tbpp = 1;
                    wa = 0;
                    wb = 0;
                    lb = 0;
                    la = 0;
                    da = 1;
                    db = 1;
                }
                Statement st2 = con.createStatement();
                st2.execute("update H2H_LEAGUE_TEAMS set POINTS = NVL(POINTS, 0)+"+tapp+", WIN = NVL(WIN, 0)+"+wa+", LOSE = NVL(LOSE, 0)+"+la+", DRAW = NVL(DRAW, 0)+"+da+" where USER_TEAM_ID = "+ta +"" +
                        " and LEAGUE_CODE = '"+code+"' ");
                st2.execute("update H2H_LEAGUE_TEAMS set POINTS = NVL(POINTS, 0)+"+tbpp+", WIN = NVL(WIN, 0)+"+wb+", LOSE = NVL(LOSE, 0)+"+lb+", DRAW = NVL(DRAW, 0)+"+db+" where USER_TEAM_ID = "+tb +"" +
                        " and LEAGUE_CODE = '"+code+"' ");

            }
        }
        st.close();
        con.close();
    }

    public static void Average_points() throws SQLException, ClassNotFoundException {
        Connection con = ServerMain.getConnection();
        Statement st = con.createStatement();

        st.execute("select sum(POINTS) from USER_TEAM" +
                " where GAMEWEEK="+(ServerUI.GAMEWEEK-1));
        ResultSet rs = st.getResultSet();
        int points = 0;
        while (rs.next()){
            points = rs.getInt(1);
        }

        int mid = 1;
        st.execute("select max(USER_TEAM_ID) from USERS");
        ResultSet rs2 = st.getResultSet();
        while (rs2.next()){
            mid = rs2.getInt(1);
        }
        averagePoint = points/mid;
    }
}
