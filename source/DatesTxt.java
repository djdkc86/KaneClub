
import java.io.*;
import java.util.*;
import java.text.*;

public class DatesTxt{
    private final String FILENAME = "/Users/djdrty/Desktop/KaneClub/dates.txt";
    private int size;
    private String [] dates;
    private Date date;
    private DateFormat df = new SimpleDateFormat("MMM dd yyy");
    
    public DatesTxt(boolean read) throws FileNotFoundException, IOException{
        LineNumberReader lr = new LineNumberReader(new FileReader(new File(FILENAME)));
        lr.skip(Long.MAX_VALUE);
        size = lr.getLineNumber() + 1;
        lr.close();
        dates = new String[size];
        date = new Date();
        if(!read){
            appendFile();
        }else{
            readFile();
        }
    }
    
    public void readFile(){
        try{
            Scanner input = new Scanner(new File(FILENAME));
            for(int i = 0; i < size; i++){
                dates[i] = input.nextLine();
            }
            input.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    
    public void appendFile() throws FileNotFoundException, IOException{
        File file = new File(FILENAME);
        if(!file.exists()){
            file.createNewFile();
        }
        
        PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));
        //bw.newLine();
        pw.append("\n"+df.format(date));
        pw.flush();
        pw.close();
    }
    
    public int getSize(){
        return size;
    }
    
    public String getDates(int i){
        return dates[i];
    }
    
}