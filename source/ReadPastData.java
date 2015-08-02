
import java.io.*;
import java.util.*;
import java.text.*;

public class ReadPastData{
    private String fileName, name, s, st, g, ss, date;
    private int inTheMoney, numberOfPlayers, outScore, inScore, totalScore, pulled, plusminus, moneyMade;
    private int [] front9, back9;
    private boolean [] greenies, skins, place;
    private boolean superWon, moneyMaker;
    ArrayList<PastPlayer> pp;
    PastPlayer player;
    
    public ReadPastData(String name){
        this.fileName = "/Users/djdrty/Desktop/KaneClub/"+name+".txt";
        this.date = name;
        front9 = new int[9];
        back9 = new int[9];
        greenies = new boolean[4];
        skins = new boolean[18];
        place = new boolean[3];
        readFile();
    }
    
    public void readFile(){
        try{
            
            Scanner input = new Scanner(new File(fileName));
            String line;
            line = input.nextLine();
            numberOfPlayers = Integer.parseInt(line.trim());
            line = input.nextLine();
            inTheMoney = Integer.parseInt(line.trim());
            
            pp = new ArrayList<>(numberOfPlayers);
            
            for(int i = 0; i < numberOfPlayers; i++){
                for(int j = 0; j < 8 ; j++){
                    line = input.nextLine();
                    if(j == 0){
                        name = line;
                    }
                    if(j == 1){
                        String [] ary = line.split("/");
                        int [] numberAry = new int [ary.length];
                        for(int k = 0; k < ary.length; k++){
                            numberAry[k] = Integer.parseInt(ary[k].trim());
                        }
                        plusminus = numberAry[numberAry.length-1];
                        pulled = numberAry[numberAry.length-2];
                        totalScore =numberAry[numberAry.length-3];
                        inScore = numberAry[numberAry.length-4];
                        outScore = numberAry[9];
                        for(int k = 0; k < 9; k++){
                            front9[k] = numberAry[k];
                            back9[k] = numberAry[k+10];
                        }
                    }
                    if(j == 2){
                        String [] ary = line.split("/");
                        for(int k = 0; k<4;k++){
                            greenies[k] = Boolean.parseBoolean(ary[k].trim());
                        }
                    }
                    if(j == 3){
                        superWon = Boolean.parseBoolean(line.trim());
                    }
                    
                    if(j == 4){
                        moneyMaker = Boolean.parseBoolean(line.trim());
                    }
                    
                    if(j == 5){
                        moneyMade = Integer.parseInt(line.trim());
                    }
                    if(j == 6){
                        String [] ary = line.split("/");
                        for(int k = 0; k < 18; k++){
                            skins[k] = Boolean.parseBoolean(ary[k].trim());
                        }
                    }
                    if(j == 7){
                        String [] ary = line.split("/");
                        for(int k = 0; k< 3; k++){
                            place[k] = Boolean.parseBoolean(ary[k].trim());
                        }
                    }
                }
                player = new PastPlayer(name, front9, back9, outScore, inScore, totalScore, pulled, plusminus, moneyMade, greenies, superWon, moneyMaker, skins, place);
                pp.add(player);
            }
            s = input.nextLine();
            st = input.nextLine();
            g = input.nextLine();
            ss = input.nextLine();
            
            new PastRound(pp, s, st, g, ss, inTheMoney, date);
            
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
}