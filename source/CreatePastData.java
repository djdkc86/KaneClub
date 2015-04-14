

import java.util.*;
import java.io.*;

public class CreatePastData{
    ArrayList<PlayerPanel> scores;
    private String fileName, s, st, g, ss;
    private int madeMoney;
    
    public CreatePastData(String fileName, ArrayList<PlayerPanel> people, int madeMoney, String s, String st, String g, String ss) throws FileNotFoundException{
        this.fileName = fileName;
        scores = new ArrayList<> (people.size());
        for(PlayerPanel p: people){
            scores.add(p);
        }
        this.madeMoney = madeMoney;
        this.s = s;
        this.st = st;
        this.g = g;
        this.ss = ss;
        writeFile();
    }
    
    public void writeFile(){
        try{
            PrintWriter output = new PrintWriter(fileName);
            output.println(scores.size());
            output.println(madeMoney);
            for(PlayerPanel p: scores){
                boolean gg[] = p.getGoodG();
                output.println(p.getName());
                for(int score : p.front9){
                    output.print(score+"/");
                }
                output.print(p.getOutScore()+"/");
                for(int score : p.back9){
                    output.print(score+"/");
                }
                output.print(p.getInScore()+"/");
                output.print(p.getTotalScore()+"/");
                output.print(p.getPulled()+"/");
                output.println(p.getPlusMinus());
                for(int i = 0; i < 4; i++){
                    if(i == 3){
                        output.println(gg[i]);
                    }else{
                        output.print(gg[i]+"/");
                    }
                }
                output.println(p.wasSuperWon());
                output.println(p.getInTheMoney());
                output.println(p.moneyMade());
                for(boolean good : p.frontSkins){
                    output.print(good+"/");
                }
                for(int i = 0; i < 9; i++){
                    if(i == 8){
                        output.println(p.backSkins[i]);
                    }else{
                        output.print(p.backSkins[i]+"/");
                    }
                }
                output.println(p.getFP()+"/"+p.getSP()+"/"+p.getTP());
            }
            output.println(s);
            output.println(st);
            output.println(g);
            output.println(ss);
            output.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
}