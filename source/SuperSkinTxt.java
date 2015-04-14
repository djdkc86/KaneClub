
import java.io.*;
import java.util.*;
import java.text.*;

public class SuperSkinTxt{
    private final String FILENAME = "superskin.txt";
    private int amount, weeks;
    
    public SuperSkinTxt(){
        readFile();
    }
    
    public void writeFile(int weeks, int amount) throws FileNotFoundException{
        PrintWriter output = new PrintWriter(FILENAME);
        output.println(weeks);
        output.println(amount);
        output.close();
    }
    
    public void readFile(){
        try{
            int i = 0;
            File inputFile = new File(FILENAME);
            Scanner input = new Scanner(inputFile);
            while(i < 2){
                if(i == 0){
                    this.weeks = Integer.parseInt(input.nextLine().trim());
                    i++;
                }else{
                    this.amount = Integer.parseInt(input.nextLine().trim());
                    i++;
                }
            }
            
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    public int getWeeks(){
        return weeks;
    }
    
    public int getAmount(){
        return amount;
    }
}