package sample;

import javafx.util.Pair;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class checkmain {
    public static void check(int tn){
        ArrayList<Integer> uidList = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=1; i<=tn; i++){
            uidList.add(i);
            map.put(i, 0);
        }
        ArrayList<Pair<Integer, Integer>> matchPair = new ArrayList<>();
        ArrayList<Integer> matchcheck = new ArrayList<>();
        for(int i=0; i<tn-1; i++){
            for(int j=i+1; j<tn; j++){
                matchPair.add(new Pair<Integer, Integer>(uidList.get(i), uidList.get(j)));
                matchcheck.add(0);
                System.out.println(uidList.get(i)+" : "+uidList.get(j));
            }
        }
        int matchinserted = 0;
        int gwinserted = 1;
        boolean isMatchCompleted = false;

        for(gwinserted=1; gwinserted<=38; gwinserted++){
            System.out.println("Gameweek: "+gwinserted);
            int inserted = 0;
            int k=0;
            while (inserted<tn/2){
                while (matchcheck.get(k)!=0){
                    k++;
                    //System.out.println("k: "+k);
                    if(k>=matchcheck.size()){
                        isMatchCompleted = true;
                        System.out.println("false "+gwinserted);
                        break;
                    }
                }
                //System.out.println("afterK");
                if(isMatchCompleted) break;
                Pair<Integer, Integer> pair = matchPair.get(k);
                if(map.get(pair.getKey())==1 || map.get(pair.getValue())==1){
                    k++;
                    continue;
                }
                else {
                    matchcheck.set(k, 1);
                    map.replace(pair.getKey(), 1);
                    map.replace(pair.getValue(), 1);
                    System.out.println(inserted+ " : Team A: "+ pair.getKey() + " - "+pair.getValue() +" : Team B");
                    inserted++;
                }
            }
            k=0;
            for(int i=0; i<uidList.size(); i++){
                map.replace(uidList.get(i), 0);
            }
            if(isMatchCompleted) break;

        }


    }


    public static void Round_Robin_Schedule(int teams){
        ArrayList<Integer> uidList = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int i=1; i<teams; i++){
            uidList.add(i);
            map.put(i, 0);
        }
        int lastid = teams;
        int rotate = teams/2;
        int round = teams-1;

        int firstIdx = 0;
        for(int i=1; i<=round; i++){
            System.out.println("Round: "+i);
            int lr = 0;
            System.out.println("1: Team A: "+uidList.get(firstIdx)+" - "+teams+" : Team B");
            for(int j=0; j<rotate-1; j++){
                lr++;
                int li = firstIdx-lr;
                int ri = (firstIdx+lr)%(teams-1);
                if(li<0) li = teams-1-li*(-1);
                System.out.println((j+2)+": Team A: "+ uidList.get(li)+ " - "+uidList.get(ri)+" : Team B");
            }
            firstIdx = (firstIdx+rotate)%(teams-1);
        }

    }

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

    public static void main(String[] args){
        //check(8);
        //Round_Robin_Schedule(10);
        System.out.println(getMd5("AVERAGE"));
    }
}
